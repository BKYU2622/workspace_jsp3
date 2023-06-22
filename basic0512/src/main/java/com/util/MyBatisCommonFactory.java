package com.util;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
/*
 * 자바에서 제공하는 JDBC API를 사용해서 오라클 서버와 연동하는 경우 반복되는 코드로 코드양이 많아 진다.
 * 이 문제를 해결하기 위해서 MyBatis가 제공되었다
 * 오라클 서버에 대한 물리적인 정보는 MapperConfig.xml문서에 적혀 있다 - 드라이버(ojdbc.jar), URL정보, 계정, 비번 관리 됨
 * 또한 DML구문 즉 select, insert, update, delete를 자바 코드로 작성할 때 String객체를 사용하는데
 * 이것 또한 불편하고 특히 파라미터 값을 셋팅할 때 마다 더블쿼테이션과 싱글쿼테이션을 작성해야 하므로 불편했다 - 토드에서는 모두 삭제해야 하기 때문
 * 마이바티스의 경우 모든 쿼리문을 xml문서에 작성하므로 이러한 문제를 고민할 필요가 없다 - 더블, 싱글, 변수처리 부분을 삭제하지 않아도 됨
 * 바로 갈무리한다음 토드나 오렌지 같은 툴을 이용해서 즉시 실행해 볼 수도 있는 것이다.
 * 마지막으로는 select의 경우 반드시 ResultSet 인터페이스를 활용하여 오라클 서버에서 꺼내온 정보를 - 오라클의 커서를 움직이면서 
 * 자료구조에 담아야 했는데 이것도 생략이 가능하다 - List<Map>에 자동으로 담아주기 때문에 30% 줄어듦
 * 다만 쿼리문 속성으로 resultType=map을 주기만 하면 알아서(자동) 조회된 결과값이 들어간다 - how? -> xml문서의 속성값으로 map을 정해줌
 * 여기에 다가 프로시저와 동적쿼리문도 제공되어서 if문이나 반복문도 사용할 수 있다.
 * 반복되는 코드 - 30%이상이 줄어든다
 * 1. 드라이버 클래스를 로딩한다 - Class.forName("oracle.jdbc.driver.OracleDriver") - 오라클 제조사에서 제공함 - ojdbc6.jar - 배포
 * : 배포위치는? /WEB-INF/lib/ojdbc6.jar
 * 2. 물리적으로 떨어져 있는 오라클 서버와 연결통로를 만듦
 * 계정(scott), 비번을(tiger) 알아야 함, 드라이버 방식: OCI방식, THIN방식(멀티티어효율적) - 2-tier(계층), 3-tier
 * 위의 1번과 2번 과정을 생략할 수 있다
 * 
 */
public class MyBatisCommonFactory { 
	static Logger logger = Logger.getLogger(MyBatisCommonFactory.class);
	// 마이바티스에서 제공하는 클래스임 - 역할: 물리적으로 떨어져 있는 오라클 서버와 톰캣 서버가 연동
	// 왜 오라클이 제공하지 않고 마이바티스측에서 제공하는가? - 라이브러리이다 - 떼어내도 서비스에 큰 지장이 없다 - 아마도 프레임워크가 아니라 라이브러리일 것이다
	public static SqlSessionFactory sqlSessionFactory = null; // MyBatis API -> 메이븐 저장소에서 myBatis-3.xxx.jar기 배포되어 있어야 함
	//SqlSessionFactory객체를 생성해 주는 메소드 입니다.
	public static void init() {
		try {
			// xml문서 안에 계정 정보와 비번, 드라이버 클래스, 또 쿼리문을 담고 있는 member.xml문서의 물리적인 경로가 여기에 담김 -> com/mybatis/MapperConfig.xml
			String resource = "com/mybatis/MapperConfig.xml"; // SQL매핑 오픈소스이다 - 라이브러리 
			// 위 문서에서 필요한 정보를 수집하려면 IO클래스가 필요하다 - stream클래스, 네트워크 프로그램, 소켓통신, 멀티스레드
			// xml문서에 서버에 대한 정보가 있으니 이 문서를 스캔해야 한다 - IO패키지 필요함
			Reader reader = Resources.getResourceAsReader(resource);
			logger.info("before sqlSessionFactory : "+sqlSessionFactory);
			//if(sqlSessionFactory == null) {
			// build라는 메서드를 통해서 객체를 생성함(sqlSessionFactory) - 반드시 드라이버 클래스와 DB정보가 필요함 
			// 두 번째 파라미터에 있는 development는 MapperConfig.xml에서 선언된 Id값이다
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
			//}
			logger.info("after sqlSessionFactory : "+sqlSessionFactory);
		} catch (Exception e) {
			logger.info("[[ Exception ]] "+e.toString());
		}
	}// end of init
	public static SqlSessionFactory getSqlSessionFactory() {
		// 직접 인스턴스화를 하지 않더라도 메서드를 경유해서 전역변수에 초기화를 진행하면
		// 이런 방식으로도 객체를 주입할 수 있다
		// 왜 메서드를 통해서 객체를 주입받는 걸까? if문을 사용할 수 있으니까 조건에 따라 인스턴스화 되는 상황을 제어할 수 있다. - 싱글톤패턴으로 객체관리가 가능함
		init();
		return sqlSessionFactory;
		// 아래에서 리턴되는 변수는 전역변수이다 - 그러니까 init메서드 경유를 통해서 객체를 생성받을 수 있다
	}
}
/*
 * SqlSessionFactory 객체를 생성하기 - init()에서 구현 
 * 객체를 반환하는 메서드는 getSessionFactory이다
 * 
 * 
 * 
 */