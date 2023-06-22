package com.example.demo.design;

import javax.swing.JFrame;

public class RandomGame {
	// 선언부
	
	// 게으른 인스턴스화와 이른 인스턴스화와의 차이점
	// 게으른 인터스턴스화를 할 땐 사용전에 반드시 생성(생성자 호출)할 것
	// JFrame jf = null; // 게으른 인스턴스화 - 나중에 생성 - 선언만
	
	JFrame jf = new JFrame(); // 이른 인스턴스화
	
	// 생성자 선언
	public RandomGame() {
		System.out.println("RandeomGame 디폴트 생성자");
		// 생성자 안에서 메서드 호출이 가능하다
		// non-static영역이라서 내 안에 있는 메서드를 자유롭게 호출 가능함
		initDispaly();
	}
	
	// 화면 처리부
	public void initDispaly() {
		// jf = new JFrame(); // 이른 인스턴스화가 되면 필요 없어짐
		jf.setTitle("처음 만들어보는 게임111111");
		jf.setSize(400, 300);
		jf.setVisible(true); // false이면 비활성화 됨
		// 똑같은 메서드가 반복되었기 때문에 덮어쓰기 된 것, 마지막이 출력 됨
		jf.setTitle("처음 만들어보는 게임55555555"); 
	}
	
	// 메인 메서드
	public static void main(String[] args) {
		new RandomGame();
		// 내 안에 있는 메서드라 하더라도 static안에서는 non-static은 접근이 불가함
		// 내 안에 있는 메서드라 하더라도 1) 인스턴스화를 하고 호출하거나 
		// 생성자 안에서 호출은 가능하다
		
	}

}
