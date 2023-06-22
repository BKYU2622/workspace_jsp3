package com.example.demo.design;

// 추상 클래스를 상속 받아서 청둥오리를 만듦
public class MallardDuck extends Duck {
	public MallardDuck() {
		flyBehavior = new FlyWithWings();
	}
	
	@Override
	public void display() {
		System.out.println("나는 청둥오리입니다.");
		
	}

}
