package com.example.demo.pojo3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

//카카오 컨트롤러도 서블릿이 아니어도 좋다.
//카카오로그인 버튼 클릭 -> 
//https://kauth.kakao.com/oauth/authorize?client_id=REST full키&redirect_uri(callback부분)=http://localhost:9000/auth/kakao/callback&response_type=code"=
public class KakaoController {
	Logger logger = Logger.getLogger(KakaoController.class);
	
	public String kakaoCallback(HttpServletRequest req, HttpServletResponse res) {
		logger.info("kakaoCallback");
		//카카오 로그인 클릭시 카카오 서버에 요청(응답화면도 카카오서버가 제공함)을 하고 요청에 대한 응답 코드를 받아옴
		String code = req.getParameter("code");
		logger.info(code);
		
		String tokenURL = "https://kauth.kakao.com/oauth/token";
		//카카오서버측에서 응답으로 보내주는 토큰값 읽어오기
		BufferedReader br = null;//filter스트림 클래스이고 네트워크 요청에 대한 응답을 읽어오기 위해 선언
		// 카카오로그인 서버에서 인증코드를 넘겨서 돌려받은 토큰값을 담을 객체 
		OAuthToken oauthToken = null;
		try {
			URL url = new URL(tokenURL);
			Map<String, Object> pMap = new HashMap<>();
			pMap.put("grant_type","authorization_code");
			pMap.put("client_id","07a1c647a84d2693791232a3e335f25f");
			pMap.put("redirect_uri","http://localhost:9000/auth/kakao/callback");
			pMap.put("code", code);
			//위에서 정의한 정보들을 POST방식으로 헤더에 담기
			StringBuilder postData = new StringBuilder();
			for(Map.Entry<String, Object> params : pMap.entrySet()) {
				if(postData.length()!=0) postData.append("&");
				postData.append(URLEncoder.encode(params.getKey(),"UTF-8"));
				postData.append("=");
				postData.append(URLEncoder.encode(String.valueOf(params.getValue()),"UTF-8"));
				
			}// end of for
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			// Stream연결
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			System.out.println(conn); // 토큰을 발급해 주는 요청에 대해서 연결이 잘 되면..
			// http 헤더값 넣기 
			// POST방식으로 요청하기
			conn.setRequestMethod("POST");
			// POST방식으로 사용자가 입력한 값을 넘길 때 추가되는 설정임 - postman
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			// 보내는 데이터 길이 - 톰캣에서 카카오서버측에 보내는 메시지의 길이 계산
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			// 서버측에서 보내온 데이터를 출력할 수 있는 상태인지 체크
			conn.setDoOutput(true);
			// POST방식으로 호출하기
			conn.getOutputStream().write(postDataBytes);
			// 여기까지는 카카오서버측에 요청하기
			// 아래부터는 카카오서버측의 응답 받아오기
			// BufferedReader는 필터스트림 클래스로 단독으로는 읽을 수가 없어서 반드시 단독으로 읽을 수 있는
			// InputStreamReader와 연결해서 읽어올 수 있다. 만일 BufferedReader를 사용하지 않으면 read메서드만 사용가능한데
			// BufferedReader를 사용했기에 readLine메서드를 누릴 수 있는 것이다.
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String inputLine = null;
			StringBuilder sb = new StringBuilder();
			while((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}// end of while - 네트워크를 통해서 넘어오는 패킷을 알아서 StringBuilder에 담기
			String jsonStr = sb.toString();
			// 아래에서 출력되는 값은 인증코드를 POST방식으로 카카오측에 요청하면 응답으로 보내주는 토큰값을 받기 위해서 작성함
			logger.info(jsonStr); // 카카오서버측에서 콜백 URL을 등록한 경우 그 경로로 응답메시지를 전송하고 그 값을 출력함
			ObjectMapper mapper = new ObjectMapper();
			try {
				oauthToken = mapper.readValue(jsonStr, OAuthToken.class);
			} catch (Exception e) {
				
			}
			// 카카오서버에서 넘겨준 토큰을 가지고 카카오 로그인 서버측에 프로필을 받아오는 URL을 다시 요청해야 함
			// 그래서 미리 확인해 보는 것
			logger.info(oauthToken.getAccess_token());
			
			//토큰을 받아오는 요청 다음에 프로필 정보 받아오기 위한 요청임
			URL url2 =new URL("https://kapi.kakao.com/v2/user/me");
			//Stream 연결
			HttpsURLConnection tconn=(HttpsURLConnection)url2.openConnection();
			System.out.println("tconn 연결완료");
			//http header 값 넣기
			tconn.setRequestMethod("POST"); // post이면 헤더에 담음 - 노출이 안 되니까
			tconn.setRequestProperty("Authorization", "Bearer "+ oauthToken.getAccess_token()); // 받아온 토큰
			tconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); // 키와 값으로 전달할 때
			tconn.setDoOutput(true);
			
			// 토큰을 넘겨서 세 번째 요청{카카오 등록된 프로필정보를 받아오는}을 함 
			br = null;
			// 아래에서 다시 생성하면 기존에 참조하던 객체는 Candidate상태로 빠짐(쓰레기값)
			br = new BufferedReader(new InputStreamReader(tconn.getInputStream(),"UTF-8"));
			String inputLine2 = null;
			StringBuilder sb2 = new StringBuilder();
			while((inputLine2 = br.readLine()) != null) {
				sb2.append(inputLine2);
			}// end of while - 네트워크를 통해서 넘어오는 패킷을 알아서 StringBuilder에 담기
			String jsonStr2 = sb2.toString();
			// 아래에서 출력되는 값은 토큰을 POST방식으로 카카오측에 요청하면 응답으로 보내주는 개인 프로필을 받기 위해서 작성함
			logger.info(jsonStr2); // 토큰을 주고 응답으로 받아온 개인 프로필을 출력함
			
			// JSON형태 반환값 처리하기 
			ObjectMapper mapper2 = new ObjectMapper();
			KakaoProfile kakaoProfile = null;
			try {
				kakaoProfile = mapper2.readValue(jsonStr2, KakaoProfile.class);
			} catch (Exception e) {

			}
			// 사용자 정보 가져오기
			logger.info("카카오 아이디(번호): " + kakaoProfile.getId());
			logger.info("카카오 이메일: " + kakaoProfile.getKakao_account().getEmail());
			logger.info("카카오 유저네임: " + kakaoProfile.getProperties().nickname);
			// 약속된 시간동안 유지하고 싶다면 세션에 담아주기
			// scope: page > request > session > application
			HttpSession session = req.getSession();
			session.setAttribute("nickname", kakaoProfile.getProperties().nickname);
			//return jsonStr;
		} catch (Exception e) {
			
		}
		try {
			res.sendRedirect("/gym.jsp");
		} catch (Exception e) {
			
		}
		return null;
	}
}
//niMCJwgD2iwFw_slJYeGSvLeQwyZ5A3CzsHosdljhL9LzM_cuVQK5l7xaECOo6AhWab6RAo9dGgAAAGIrX6yqg-이게 코드