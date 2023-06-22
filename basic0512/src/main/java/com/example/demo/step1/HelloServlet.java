package com.example.demo.step1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/step1/hello")
public class HelloServlet extends HttpServlet {
	Logger logger = Logger.getLogger(HelloServlet.class);
	// 브라우저에서 http://172.16.2.7:9000/step1/hello?mem_id=tomato&mem_pw=123
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet");
		logger.info("doGet호출");
		logger.info("doGet호출");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("<h2>이순신</h2>");
		String mem_id = req.getParameter("mem_id");
		String mem_pw = req.getParameter("mem_pw");
		out.print("<h3>" + mem_id + "</h3>");
		out.print("<span>" + mem_pw + "</span>");	// 인라인 요소
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
