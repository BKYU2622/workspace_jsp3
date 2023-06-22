package com.example.demo.pojo3;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller3 {
	// 둘 다 사용자의 요청을 받는 메서드인데 리턴타입을 다르게 가져간다
	// 왜냐면 Spring에서 제공되던 ModelAndview를 재현해 본다
	// 리턴타입이 두 가지가 되었다 - 그러니까 리턴타입을 String에서 Object로 바꿔야 할 것이다
	// String이면 응답페이지의 위치가 webapp아래 이고 ModelAndView이면 WEB-INF views\화면이름.jsp
	public String execute(HttpServletRequest req, HttpServletResponse res);
	
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res, Map<String, Object>pMap); // 메서드 오버로딩
	
	//카카오 로그인 콜백(사용자가 요청시 카카오 서버측에서 호출하는 메소드) 메소드 선언
	public Object kakaoCallback(HttpServletRequest req, HttpServletResponse res);
	
	// POJO3에서는 메서드 단위로 나눠보기로 하였다
	// ModelAndView 컨셉이 필요한 메서드이다
	public Object jsonQnaList(HttpServletRequest req, HttpServletResponse res); // 리액트와 비벼보기
	
	// 전체 조회
	public Object qnaList(HttpServletRequest req, HttpServletResponse res);
	
	// 질문 등록
	public Object qnaInsert(HttpServletRequest req, HttpServletResponse res);
	
	// 질문 상세 조회
	public Object qnaDetail(HttpServletRequest req, HttpServletResponse res);
	
	// 질문 수정
	public Object qnaUpdate(HttpServletRequest req, HttpServletResponse res);
	
	// 질문 삭제
	public Object qnaDelete(HttpServletRequest req, HttpServletResponse res);
	
	
}
/*
	pj1과 pj2에서는 없었던 ModelAndView를 추가하였다
	ModelAndView는 목록화면을 지원하기 위해 설계되었다
	jsp페이지의 배포 위치는 WEB-INF\views아래이다
	객체가 유지되는 scope는 request이다

	XXXController클래스도 메서드 단위로 나누어서 구현해본다
	pj1과 pj2에서는 NoticeController 또는 BoardController는 if문으로 분기하였다 => 직관적이지 않음
	 
*/