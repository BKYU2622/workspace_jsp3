package com.example.demo.pojo2;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.util.HashMapBinder;

/*
 * 클래스 설계 부분 - 아키텍쳐
 * 기능구현보다는 전체적인 클래스 구성, 메소드 설계 - 관전포인트
 * BoardController는 서블릿이 아니어도 좋다 - ActionServlet에서 파라미터로 받아오면 되니까
 * HttpServlet을 상속받지 않고도 request와 response를 사용할 수 있게됨
 * 
 * 컨트롤계층에서 할일(역할)
 * 직접 오라클과 연동하지 않기 - 재사용성, myBatis와 같은 외부 라이브러리를 사용함
 * 응답페이지에 대한 결정, 호출까지 해야되나? 아니면 결정만 하면 되는가? - 설계자 의도 선택, spring은 직접하지 않음(DispatcherServlet)
 * 
 * XXX.jsp로 오는 요청이면 표준 서블릿이 처리
 * XXX.pj1, XXX.pj2로 오는 요청이면 다른 서블릿으로 대체
 * 주의할 사항 : 2개의 인스턴스가 만들어진다.
 * 1)@WebServlet으로 하는 방법
 * 2)web.xml으로 하는 방법
 * 양쪽에 동일한 서블릿을 등록하는건 피할것
 * 서블릿에 대해 싱글톤 패턴을 권장함=하나의 서블릿을 가지고 여러 서비스를 지원하는것 
 * VO의 경우도 private으로 선언하더라도 결국은 하나의 인스턴스 변수로 계속 사용하는게 아니다.
 * 하나, 사용자가 입력한 값을 전달함
 * 둘, 응답페이지의 이름을 결정함
 * 실제로 일하는 클래스는 BoardLogic과 BoardDao{myBatis레이어 지원-myBatis가 아닌 다른 ORM솔루션, Hibernate}이다.
 * 유지보수에 유리한 구조, 획일적인 구조, 구조의 통일(내 업무가 끝나도 다른업무를 지원하기 위해서 또 다른 MVC패턴을 공부하지도 않아도 되도록)
 * 	
 */
public class BoardController implements Controller {
	Logger logger = Logger.getLogger(BoardController.class);
	private BoardLogic boardLogic = new BoardLogic();
	
	//리턴타입을 통해서 redirect와 forward여부를 결정짓고 필요한 페이지까지도 요청할 수 있다 - 스프링이 지원하고 있다.
	//redirect : boardList.jsp(유지안됨)-select한 결과를 아무것도 보여줄 수 없다 or forward : boardList.jsp{유지됨-조회결과를 보여준다.0}
	//redirect : boardList.pj2{입력 성공, 수정성공, 삭제성공 후에}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("execute");
		String upmu[] = (String[])req.getAttribute("upmu");
		String page = null;
		page = "forward:board/boardList";
		int result = 0;//입력, 수정, 삭제가 성공했을때 1, 실패했을때 0 반환
		//인터페이스는 반드시 구현체 클래스가 필요함=HashMap
		Map<String, Object> pMap = new HashMap<>();
		//req.getParameter의 반복을 방어하기 위해서 만듦=HashMapBinder
		//서블릿에서 제공되는 req를 사용할 것 
		//요청객체와 응답객체는 톰캣 서버로부터 요청이 일어나면 그 때 주입을 받음
		HashMapBinder hmb = new HashMapBinder(req);
		
		//너 전체 조회를 원해? - forward(select)
		//-> /board/boardList.pj2
		if("boardList".equals(upmu[1])) {
			//board/boardList.jsp는 null이 출력됨(백엔드 경유안해서)
			logger.info("boardList");//콘솔에 문자열 출력이 안되었다면 오라클서버를 경유하지 않았다는 뜻
			List<Map<String, Object>> bList = null;
			//파라미터로 들어오는 값이 담김 - pMap
			//XXX.pj2?b_no=5&b_title=제목
			//pMap.put("b_no",5);
			//pMap.put("b_title","입력받은 제목");
			hmb.bind(pMap);
			bList = boardLogic.boardList(pMap);
			//요청이 유지되는 동안에는 사용할 수 있다 - 조회결과를 가진 주소번지(bList)
			//첫번째 파라미터는 문자열값
			//두번째 파라미터는 주소번지다.
			req.setAttribute("bList", bList);
			page = "forward:board/boardList";//- -> pageMove[0]=forward, pageMove[1]=boardList.jsp 가 담김
		}
		else if("jsonBoardList".equals(upmu[1])) { // Front-End, Vue.js, React.js, jEasyUI
			logger.info("boardList");
			List<Map<String, Object>> bList = null;
			hmb.bind(pMap);
			bList = boardLogic.boardList(pMap);
			req.setAttribute("bList", bList);
			page = "forward:board/jsonBoardList";
		}
		
		//상세내용 조회 - forward(select)
		else if("boardDetail".equals(upmu[1])) {
			logger.info("boardDetail");
			List<Map<String, Object>> bList = null;

			hmb.bind(pMap);
			bList = boardLogic.boardList(pMap);//pMap.get("b_no") => 1
			req.setAttribute("bList", bList);
			page = "forward:board/boardDetail";
		}
		//글 등록할거야? - redirect
		else if("boardInsert".equals(upmu[1])) {
			logger.info("boardInsert");
			//사용자가 입력한 값만큼 req.getParameter가 반복되므로 이 코드를 줄여줌
			hmb.multiBind(pMap);//HashMapBinder의 bind메소드 호출함-사용자가 입력한 값을 담아줄 주소번지 넘겨줌
			logger.info(pMap);//한글처리된 값 출력해보기{키=값, 키2=값2} -> JSON형식으로 변경처리
			
			//result변수는 어떤 역할이지? - 오라클 서버에 insert문 요청하면 오라클 서버가 리턴해주는 값을 담는다
			result = 0;
			result = boardLogic.boardInsert(pMap);
			if(result==1) {//입력 성공
				page = "redirect:boardList.pj2";//pj2가 붙는 이유-오라클을 경유하기 때문
			//입력 실패했을때	
			}else {//result가 0이면 이쪽으로 오게됨-흐름이 바뀜(if문, switch문)
				page = "redirect:boardError.jsp";
			}
		}
		//글 수정할거야?
		else if("boardUpdate".equals(upmu[1])) {
			logger.info("boardUpdate");
			
			hmb.bind(pMap);//사용자가 입력한 값을 담아줌
			logger.info(pMap);//입력값 확인
			result = 0;
			result = boardLogic.boardUpdate(pMap);
			if(result==1) {//입력 성공
				page = "redirect:boardList.pj2";//pj2가 붙는 이유-오라클을 경유하기 때문
			//입력 실패했을때	
			}else {
				page = "redirect:boardError.jsp";
			}
		}
		//글 삭제할거야?
		else if("boardDelete".equals(upmu[1])) {
			logger.info("boardDelete");
			hmb.bind(pMap);//사용자가 입력한 값을 담아줌
			logger.info(pMap);//입력값 확인
			result = 0;
			
			//insert 자리(비교하기 전에 미리 넣어줘야함)
			result = boardLogic.boardDelete(pMap);//pMap안에는 pMap.get("b_no")가 있어야한다.
			
			if(result==1) {//입력 성공
				page = "redirect:boardList.pj2";//pj2가 붙는 이유-오라클을 경유하기 때문
			//입력 실패했을때	
			}else {
				page = "redirect:boardError.jsp";
			}
		}
		return page;
	}

}