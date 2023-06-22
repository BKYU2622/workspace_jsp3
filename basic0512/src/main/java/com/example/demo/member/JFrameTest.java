package com.example.demo.member;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JFrameTest extends JFrame {
	// 선언부
	String title = null;
	JButton jbtSelect = new JButton("전체 조회");
	
	// 생성자
	public JFrameTest() { 
		System.out.println("디폴트생성자 호출");
	}
	
	// 메서드가 같은 이름이더라도 파라미터의 갯수가 다르거나 타입이 다르면 다른 메서드라 한다
	// 파라미터 자리에 title은 지역변수이고 35번 인스턴스화 할 때 생성자 호출로 인해서
	// 파라미터로 처음 구현하는 회원조회가 복사 된다 - title 변수에 복사됨
	public JFrameTest(String title) { // 생성자 재정의 - 메서드 오버로딩의 규칙을 따름
		System.out.println("파라미터가 String 타입인 생성자 호출");
		// this는 나 자신을 가리키는 수정자이다
		// 파라미터로 복사된 문자열 값이 전변에 대입된다
		//this.title = title; // 초기화 
	}
	
	// 화면그리기
	public void initDisplay() {
		this.add("North", jbtSelect);
		this.setTitle(title);
		this.setSize(500, 400);
		this.setVisible(true);
	}
		
	// 메인메서드	
	public static void main(String[] args) {
		// 클래스 이름 뒤에 리턴타입도 없이 괄호만 오면 생성자
		// 인스턴스화 하면 생성자가 호출된다
		// 생성자는 중복 선언이 가능하다
		// 단, 파라미터 갯수가 달라야 한다
		JFrameTest jft = new JFrameTest("처음 구현하는 회원조회"); // 클래스 이름 뒤에 괄호가 오면 생성자임
		jft.initDisplay();
	}

}
