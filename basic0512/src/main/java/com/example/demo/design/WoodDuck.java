package com.example.demo.design;

public class WoodDuck extends Duck {
	public WoodDuck() {
		flyBehavior = new FlyNoWay();
	}
	
	@Override
	public void display() {
		System.out.println("나는 나무오리입니다.");
		
	}

}
