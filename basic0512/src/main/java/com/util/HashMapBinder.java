package com.util;
//내 안에는 요청 객체가 없다.
//공통코드를 뽑아낸다-초보는 아니다.
//반복되는 코드를 찾아내서.. 
//코드의 양은 많아지더라도 복잡도는 증가하지 않게 코딩하기
//재사용이 높은 코드를 작성하려면 인터페이스 혹은 추상클래스 중심의 코딩을 전개하기 
//상속은 재사용을 위한 모범답안은 아니다.왜냐면 결합도가 높아서, 

import java.io.File;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class HashMapBinder {
	Logger logger = Logger.getLogger(HashMapBinder.class);
	
	HttpServletRequest req = null;
	//첨부파일 구현으로 인한 선언
	//form전송시 enctype이 추가되면(반드시 POST) HttpServletRequest로는 요청을 받아올 수 없다.
	//그래서 cos.jar가 지원하는 MultipartRequest를 사용하자
	MultipartRequest multi = null;//cosXXXX.jar
	//첨부 파일이 업로드 되는 물리적인 이름
	String realFolder = null;
	//첨부파일에 대한 한글 처리 필요
	String encType = "utf-8";
	//첨부파일의 최대 크기
	int maxSize = 5*1024*1024;//5MB제한
	//전역변수의 초기화는 생성자가 해주니까 초기화를 생략가능함
	//지역변수는 해주지 않아서 초기화를 반드시 해야함
	//전역변수는 인스턴스변수.변수명으로 호출이 가능하고 지변은 불가함
	
	public HashMapBinder(){} //디폴트 생성자
	//생성자의 파라미터를 통해서 서블릿 클래스가 톰캣 서버로부터 주입받은
	//주소번지를 인스턴스화 할 때 생성자를 호출하니까 그 생성자의 
	//파라미터로 서블릿이 쥐고 있는 요청 객체의 원본을 받아온다.
	public HashMapBinder(HttpServletRequest req) {//파라미터가 있는 생성자
		this.req = req;
		this.realFolder = "D:\\workspace_jsp\\basic0512\\src\\main\\webapp\\pds";
	}
	//첨부파일이 있을때 사용자가 입력한 값 청취하기=읽어오기=pMap에 담아주기
	public void multiBind(Map<String, Object> pMap) {
		logger.info("multiBind");
		pMap.clear();//기존의 값을 가진건 비워주기
		//예외가 발생할 가능성이 있는 코드는 반드시 try..catch 처리할것=런타임에러=XXXException이 발동하는 경우
		//발생할 경우=IO패키지, java.net.*, java.sql.* 모두 다 고려할 것
		try {
			//생성자를 호출하는 것만으로도 파일 업로드 처리는 완성
			multi = new MultipartRequest(req, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
			//DefaultFileRenamePolicy=같은 이름이 중복되어 들어오는걸 방지(옵저버)
		} catch (Exception e) {
			logger.info(e.toString());
		}
		//아래 코드부터는 어떤역할? = encType이면 표준요청객체로 사용자가 입력한 값을 읽을 수 없다.
		//첨부파일의 파일 이름을 얻어오기 - pds 폴더에 사용자가 선택한 파일을 업로드 해주기 위해서
		//사용자가 입력한 값을 읽어오는 코드임 - b_title, b_writer, b_content 포함 -> b_file 제외임
		Enumeration<String> em = multi.getParameterNames();//제네릭
		while (em.hasMoreElements()) {//데이터가 있는지 확인해주는 메소드
			String key = em.nextElement();
			pMap.put(key, multi.getParameter(key));//Map에 사용자가 입력한 값이 담김 - 파일은 제외됨
		}//end of while
		Enumeration<String> files = multi.getFileNames();//첨부파일이 여러개 일때도 받아줌
		//첨부파일이 있는거야?
		if(files!=null) {
			//file을 선언하는 이유-File객체는 파일명을 객체로 만들어주는 클래스이지만 파일의 내용까지 담고있지는 않음
			File file = null;
			while (files.hasMoreElements()) {
				String fname = files.nextElement();
				String filename = multi.getFilesystemName(fname);
				pMap.put("b_file", filename);
				if(filename!=null && filename.length()>1) {
					file = new File(realFolder+"\\"+filename);
				}//end of if
			}///end of while
			//75번 라인에서 파일 객체가 생성완료됨 - 파일크기를 계산 가능함 XXX.zip(5.2MB)
			//첨부파일의 크기를 담을 수 있는 처리 추가
			double size = 0;
			if(file!=null) {
				size = file.length();//파일크기를 바이트로 계산해줌
				size = size/1024.0;
				pMap.put("b_size", size);
			}//첨부파일이 존재하는 경우 파일의 크기를 계산해줌 - 단위:KB(10의 3승)
		}
	}
	
	public void bind(Map<String, Object> pMap) {
		pMap.clear();//이미 쥐고 있는 데이터가 있으면 비워라
		//s가 붙으면 복수형 - 자루안에 데이터가 존재하는지 유무를 반환하는 메소드 - hasMoreElements()
		Enumeration<String> en = req.getParameterNames();//mem_id, mem_pw, mem_name
		while(en.hasMoreElements()) {
			String key = en.nextElement();//key에 mem_id, mem_pw, mem_name, gubun, keyword 들어감
			logger.info(req.getParameter(key));//한글깨짐 출력한 코드
			//GET방식의 한글처리-server.xml -> URIEncoding="utf-8" 사용
			//POST방식의 한글처리 -> HangulConversion.java 사용 -> toUTF
			//서로 다르다.
			pMap.put(key, HangulConversion.toUTF(req.getParameter(key)));//한글처리 끝-POST방식
		}
	}
}

/*
 * 아래 코드가 부서관리, 사원관리, 주문관리마다 필요하다.
	//java collection framework(List, Map)에는 읽기와 쓰기, 담기와 꺼내기에 대한
 	//인터페이스를 제공한다.
 	//Enumeration은 자료구조에 담는 역할이 아니라 꺼내기와 관련된 메소드를 제공함. 
 *
 * Enumeration<String> enu = req.getParameterNames();
 * <input type="text" name="mem_id">
 * req.getParameter("mem_id")
 * <input type="text" name="mem_pw">
 * req.getParameter("mem_pw")
 * <input type="text" name="mem_name">
 * req.getParameter("mem_name")
 * 
 */