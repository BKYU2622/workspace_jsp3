package com.example.demo.pojo2;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.util.MyBatisCommonFactory;

public class BoardDao {

	Logger logger = Logger.getLogger(BoardDao.class);	
	// myBatis 레이어코드 추가됨
	SqlSessionFactory sqlSessionFactory = null;
	SqlSession sqlSession = null;
	
	// 디폴트 생성자는 생략할 수 있다 - 생략되며 JVM이 대신 주입해줌 - 그래서 전변이 초기화를 생략해도 됨
	public BoardDao() { // 파라미터가 없는 생성자를 디폴트 생성자라고 한다
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
	} 
	// 전체조회일 때 - 1건이나 n건이다
	// 상세보기일 때 - 1건 -> bList.size()=1
	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("boardList==>" + pMap); // {키=값, 키2=값2....}
		List<Map<String, Object>> bList = null;
		try {
			// sqlSession을 생성하려면 먼저 SqlSessionFactory를 생성한다
			// 어디서 했나? -> 생성자에서 진행함
			sqlSession = sqlSessionFactory.openSession();
			bList = sqlSession.selectList("boardList", pMap);
			// 주의: 커밋이나 롤백의 대상이 아니다
			logger.info(bList);
		} catch (Exception e) {
			// logger.info(e.toString());
			// logger.info(e.getMessage());
			e.printStackTrace(); // 에러메시지와 라인번호까지 같이 출력
		}
		return bList;
	}
	
	public int boardInsert(Map<String, Object> pMap) {
		logger.info("boardInsert");
		int result = 0;
		try {
			sqlSession = sqlSessionFactory.openSession();
			result = sqlSession.insert("boardInsert", pMap);
			logger.info("insert문 요청시 오라클서버에서 처리 후에 반환값:"+result);
			sqlSession.commit(); // auto커밋 모드가 꺼져 있어서 반드시 따로 commit()을 호출하야 반영 됨
		} catch (Exception e) {
			logger.info(e.toString()); // 발생한 익셉션의 이름만 출력해줌 - 디버깅하는데 도움이 덜 됨
		}
		return result;
	}

	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("boardUpdate");
		int result = 0;
		try {
			sqlSession = sqlSessionFactory.openSession();
			result = sqlSession.update("boardUpdate", pMap);
			logger.info("update문 요청시 오라클서버에서 처리 후에 반환값:"+result);
			sqlSession.commit();
		} catch (Exception e) {
			logger.info(e.toString());
		}
		
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("boardDelete");
		int result = 0;
		try {
			sqlSession = sqlSessionFactory.openSession();
			// 쿼리문을 board.xml에서 id값으로 찾아온다
			// insert로 했을 때 처리가 되기는 하지만 상징성이 있으니까 바꿔서 사용하자
			result = sqlSession.delete("boardDelete", pMap);
			logger.info("delete문 요청시 오라클서버에서 처리 후에 반환값:"+result);
			sqlSession.commit(); 
			logger.info(result); // 0이면 삭제 실패, 1이면 삭제 성공
		} catch (Exception e) {
			logger.info(e.toString()); 
		}
		return result;
	}

}
