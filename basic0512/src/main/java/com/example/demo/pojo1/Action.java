package com.example.demo.pojo1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * doGet의 리턴타입을 다른 타입으로 변경할 수 있다
 * 인터페이스에 선언된 모든 메서드는 추상메서드이다.
 * void에서 ActionForward로 한 이유는 redirect인지 forward인지와 
 * 업무마다 응답페이지의 path정보가 다르므로 변수로 처리하고자 하였다
 */
public interface Action {
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException;
}
