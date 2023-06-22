package com.example.demo.step1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
@WebServlet("/rest/test")
public class RestfulServlet extends HttpServlet {
	Logger logger = Logger.getLogger(RestfulServlet.class);
	// 스프링에서는 annotation으로 지원하는 API가 존재함
	// request와 response 없이도 웹서비스를 제공할 수 있게 되었다. => 서블릿에 대한 의존성을 낮추었다.
	
	// GET전송방식이 디폴트이다.
	// GET방식은 쿼리스트링으로 값을 받을 수 있다. ex) http://localhost:9000/index.jsp?mem_id=toamto
	// GET방식은 링크를 걸 수 있다.
	// &방식으로 값을 연결해서 쓸 수 있다.
	// 단, 전송데이터의 크기는 브라우저 마다 제약이 따른다.
	// 사용자가 입력한 값이 그대로 노출됨 - 보안 취약 - 로그인 구현시에는 사용하지 말자.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doGet");		// 오류 넘버가 405번이면 대응하는 메서드 이름 오타, 404번이면 URL 경로 잘못 설정
		// 사용자가 화면에 입력한 값을 단위테스트 가능하다. - POST방식은 불가함 - 그래도 하고 싶으면 Postman도구 사용
		// <input type=text id="mem_id" name="mem_id">
		// 파라미터 자리에는 입력받는 콤포턴트의 name이 반드시 와야한다.
		String mem_id = req.getParameter("mem_id");			// getParameter의 리턴타입은 String임
		String mem_name = req.getParameter("mem_name");
		
		// 화면이 없이도 사용자가 입력한 값을 postman을 통해서 단위테스트 가능함
		// 마임타입은 응답페이지와 관계있으니 응답객체를 사용한다.
		// Servlet에 일일이 태그를 넣어주는 것은 비효율적임(아래 내용) => jsp에 화면을 넣는것이 효율적임
	    resp.setContentType("text/html;charset=utf-8");		// setContentType(); = 마임타입을 결정해 준다. / resp = 응답객체
	    PrintWriter out = resp.getWriter();					// 메서드 호출의 리턴값으로 객체를 주입받음
	    out.print("<div>" + mem_id + "</div>");
	    // resp.sendRedirect("페이지 이름을 써준다");

	}
	
	// POST방식은 헤드가 아닌 바디에 전달.
	// POST방식은 링크를 걸 수 없다.
	// POST방식은 브라우저가 절대 인터셉트 할 수 없다.
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPost");
		String mem_id = req.getParameter("mem_id");			
		String mem_pw = req.getParameter("mem_pw");			// 파라미터 안에 값과 출력 값이 다르면 null값 출력
		logger.info(mem_pw.toString()); 	
		
	}
	
	// rest api의 put메서드는 getParameter로 값을 읽을 수 없다.
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPut");
		String temp	= null;		
		String param = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		
		InputStreamReader isr = new InputStreamReader(req.getInputStream());
		BufferedReader br2 = new BufferedReader(isr);
		
		while((temp = br2.readLine()) != null) {
			param += temp;	// +: concat(붙여쓰기)
		}
		logger.info(param);
		// 사용한 IO클래스는 반드시 닫아주어야 한다. - 왜냐면 다른 사람이 탈취, 읽기 할 수 있기 때문
		br.close();
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doDelete");	
		String temp	= null;		
		String param = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		
		while((temp = br.readLine()) != null) {
			param += temp;	// +: concat(붙여쓰기)
		}
		logger.info(param);
		br.close();	
		
	}


}
