package com.example.demo.pojo3;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

// Controller3의 구현체 클래스가 된다는 건 추상메서드를 오버라이딩 해야한다
// 내가 다 필요하지 않아도 반드시 오버라이딩 해야함 - 제약조건 - 명세서이다
public class QnAController implements Controller3 {
	Logger logger = Logger.getLogger(QnAController.class);
			
	/***********************************************************************
	 * 조회결과 JSON형식으로 꺼내오기 - 페이지가 아니라..{리액트 비벼보기}
	 * 리턴타입은 String으로 한다
	 * JSON을 지원하는 Gson API활용하기
	 ***********************************************************************/
	@Override
	public Object jsonQnaList(HttpServletRequest req, HttpServletResponse res) {
		logger.info("JsonQnaList");
		return null;
	}
	
	/**********************************************************************
	 * 조회결과 JSP 페이지에 출력하기
	 * 리턴타입은 String으로 한다(그러면 webapp아래에 페이지를 배포할 것)
	 * 예시) "forward:qna/qnaList" -> pageMove[0]=forward, pageMove[1]=qna/qnaList
	 * {Spring에서는 자동으로 지원해줌 - ViewResolver설정이 추가됨}
	 * select qna_no, qna_writer, qna_title, qna_content from qna
	 * select * from qna_comment - 답글에 대한 테이블을 별도로 설계해보기
	 *********************************************************************/
	@Override
	public Object qnaList(HttpServletRequest req, HttpServletResponse res) {
		logger.info("qnaList");
		return null;
	}

	/**********************************************************************
	 * JSP 상세 보기 페이지에 출력하기
	 * 리턴타입은 String으로 한다(그러면 webapp아래에 페이지를 배포할 것)
	 * 예시) "forward:qna/qnaDetail" -> pageMove[0]=forward, pageMove[1]=qna/qnaDetail
	 * {Spring에서는 자동으로 지원해줌 - ViewResolver설정이 추가됨}
	 * select qna_no, qna_writer, qna_title, qna_content from qna
	 * select * from qna_comment - 답글에 대한 테이블을 별도로 설계해보기
	 *********************************************************************/
	@Override
	public Object qnaDetail(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**********************************************************************
	 * 질문 등록하기
	 * 리턴타입은 String으로 한다(그러면 webapp아래에 페이지를 배포할 것)
	 * 등록이 성공하면 어디로 갈 것인가?
	 * 등록 성공시에 목록으로 이동처리할 것이라면 "redirect:qna/qnaList.pj3"
	 * 주의: 확장자가 jsp로 끝나면 오라클 서버를 경유하지 않았다
	 * 오라클 서버를 경유하려면 반드시 XXX.pj3으로 끝나야 한다 
	 * insert into qna values(?,?,?,...)
	 *********************************************************************/
	@Override
	public Object qnaInsert(HttpServletRequest req, HttpServletResponse res) {
		return null;
	}

	@Override
	public Object qnaUpdate(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object qnaDelete(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res, Map<String, Object> pMap) {
		// TODO Auto-generated method stub
		return null;
	}

	// 아래는 카카오 로근인때 필요한 메서드인데 여기서는 죽어도 필요없다
	@Override
	public Object kakaoCallback(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

}
