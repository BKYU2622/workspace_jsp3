package com.example.demo.pojo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.util.MyBatisCommonFactory;

// noticeDao와 오라클 서버 사이에는 myBatis가 있다
public class NoticeDao {
	Logger logger = Logger.getLogger(NoticeDao.class);

	public List<Map<String, Object>> noticeList(Map<String, Object> pMap) {
		logger.info("noticeList");
		List<Map<String, Object>> nList = null;	
		// 물리적으로 떨어져있는 오라클 서버와 연결통로를 확보함
		// 아래 객체를 생성하려면 드라이버 클래스, 계정정보, SID명(orcl11), port번호-MapperConfig.xml
		// Connection과 PreparedStatement가 필요없어짐
		SqlSessionFactory sqlSessionFactory = null; // 
		try { 
			sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
			// ResultSet - 오라클 커서를 조작하는데 필요한 메서드를 정의하고 있음
			// ResultSet이 인터페이스인 이유는 업무마다 domain마다 테이블명과 컬럼명이 다 다르다 => 결정할 수 없다
			// sqlSession.commit(),sqlSession.rollback();
			SqlSession sqlSession = sqlSessionFactory.openSession();
			// 메서드 이름과 쿼리문의 id를 upmu[1]="noticeList", upmu[0]="notice" -> NoticeController
			// myBatis를 사용하면 반복문 사용이 필요없다 - 리턴타입을 결정만해주면 알아서 담아줌
			// n건이고 그 안에 컬럼 정보는 Map에 담김
			// Map은 키와 값으로 처리됨 - 키를 myBatis에서 대문자로 작성해줌
			// 결론: 꺼낼때는 반드시 대문자로 해야함
			nList = sqlSession.selectList("noticeList", pMap); // 오라클과 만나는 곳 - notice.xml에서 쿼리문을 id로 찾음  
			// 관전 포인트 1. myBatis레이어에서 간섭하여 null이면 객체를 주입해줌
			// 			   2. 만일 개인이 없다면 null인 상태가 되어야 함
			// 결론: selectList의 반환타입이 null인 상태라면 myBatis가 디폴트 객체를 주입해 줌
			//nList = null;
			logger.info(nList.size()); // 하나이면 0, 하나가 아니면 NullPointerException
			System.out.println(nList);
		} catch (Exception e) {
			e.printStackTrace(); // stack영역에 쌓여있는 에러메시지 모두를 라인번호와 함께 찍어줌 - 디버깅 하는데 도움이 됨
		}
		return nList;
	}

	public int noticeInsert(Map<String, Object> pMap) {
		logger.info("noticeInsert");
		int result = 0;
		SqlSessionFactory sqlSessionFactory = null;  
		try { 
			sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
			SqlSession sqlSession = sqlSessionFactory.openSession();
			// upmu[1] == 메서드 이름 == notice.xml의 id값
			result = sqlSession.insert("noticeInsert", pMap);
			sqlSession.commit();
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int noticeUpdate(Map<String, Object> pMap) {
		logger.info("noticeUpdate");
		int result = 0;
		SqlSessionFactory sqlSessionFactory = null;  
		try { 
			sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
			SqlSession sqlSession = sqlSessionFactory.openSession();
			result = sqlSession.update("noticeUpdate", pMap);
			sqlSession.commit();
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int noticeDelete(Map<String, Object> pMap) {
		logger.info("noticeDelete");
		int result = 0;
		SqlSessionFactory sqlSessionFactory = null;  
		try { 
			sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
			SqlSession sqlSession = sqlSessionFactory.openSession();
			result = sqlSession.delete("noticeDelete", pMap);
			sqlSession.commit();
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
