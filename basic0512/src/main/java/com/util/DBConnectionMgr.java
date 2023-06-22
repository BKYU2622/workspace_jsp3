package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DBConnectionMgr {
    Logger logger = Logger.getLogger(DBConnectionMgr.class);
    
    // 각 제조사가 제공하는 드라이버 클래스를 로딩해야 한다. - ojdbc6.jar, ojdbc8.jar
    private final String driver = "oracle.jdbc.driver.OracleDriver";
    
    // 사용자 계정이 필요하다.
    private final String _user = "scott";
    
    // 비밀번호가 필요하다.
    private final String _passwd = "tiger";
    
    // 물리적으로 떨어져있는 서버의 정보(리소스)
    private final String url = "jdbc:oracle:thin:@localhost:1521:orcl11";	// orcl은 SID값이다.
    
    // 객체 생성하는 메서드를 구현 - 싱글톤 패턴으로 구현해 봄 - 싱글톤: 하나로 여러 사용자가 공유한다.
    public static DBConnectionMgr getInstance() {	// static이 붙은 메서드는 인스턴스화 없이 호출 가능하다.
       DBConnectionMgr dbMgr = null;
       // 아 이래서 객체 생성을 메서드로 구현하나 보다 
       // 메서드 안에서는 if문을 사용할 수 있다
       if(dbMgr == null) {	// 널인지 체크하고 널이면 인스턴스화 해주고 아니면 가지고 있는 걸 사용하기
          dbMgr = new DBConnectionMgr();
       }
       return dbMgr;
    }
    
    // 물리적으로 떨어져있는 오라클 서버와 연결통로를 만들어 준다.
    public Connection getConnection() {
       Connection con = null;
       try {
          Class.forName(driver);	// 드라이버 클래스르를 로딩한다. ojdbc6.jar가 없으면(못찾으면) ClassNotFoundException이 발생한다.
          con = DriverManager.getConnection(url, _user, _passwd);
       } catch (Exception e) {
          // TODO: handle exception
          logger.info("=======================================" + e.toString());
          e.printStackTrace();
       }

       return con;
    }

    public static void freeConnection(Connection con, Statement stmt, ResultSet rs) {
       if (rs != null) {
          try {
             rs.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
       if (stmt != null) {
          try {
             stmt.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
       if (con != null) {
          try {
             con.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
    }

    public static void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
       if (rs != null) {
          try {
             rs.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
       if (pstmt != null) {
          try {
             pstmt.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
       if (con != null) {
          try {
             con.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
    }

    public static void freeConnection(Connection con, Statement stmt) {
       if (stmt != null) {
          try {
             stmt.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
       if (con != null) {
          try {
             con.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
    }

    public static void freeConnection(Connection con, PreparedStatement pstmt) {
       if (pstmt != null) {
          try {
             pstmt.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
       if (con != null) {
          try {
             con.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
    }
    
    public static void main(String[] args) {
    	// int i = Integer.parseInt("10");	// Integer도 static이라 인스턴스화 없이 호출 가능
    	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
    	Connection con = dbMgr.getConnection();
    	System.out.println(con);
    
	}
    
    
    
}
