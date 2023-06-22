package com.example.demo.pojo2;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class BoardLogic {

	Logger logger = Logger.getLogger(BoardLogic.class);
	// BoardLogic에서 BoardDao에 선언된 메서드를 호출함
	// 전변으로 선언하고 생성자 호출도 함 -> 이른 인스턴스화
	private BoardDao boardDao = new BoardDao();
	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("boardList"); // 시간과 클래스명, 라인번호 
		List<Map<String, Object>> bList = null;
		bList = boardDao.boardList(pMap); // 조회결과가 없더라도 myBatis레이어에서 주입 
		return bList;
	}
	
	public int boardInsert(Map<String, Object> pMap) {
		logger.info("boardInsert");
		int result = 0;
		result = boardDao.boardInsert(pMap);
		return result;
	}

	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("boardUpdate");
		int result = 0;
		result = boardDao.boardUpdate(pMap);
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("boardDelete");
		int result = 0;
		result = boardDao.boardDelete(pMap);
		return result;
	}

}
