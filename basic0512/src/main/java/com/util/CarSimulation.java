package com.util;

abstract class Car {
	int speed=0;
	public abstract void go();
	public void stop() {
		System.out.println("6라인(Car 클래스)-stop");
		if(speed>0) {
			speed=speed -1;
		}else {
			speed=0;
		}
		
	}
}

// A is a B - 소나타는 자동차 이다
// Sonata is a Car
class Sonata extends Car {

	@Override
	public void go() {
		
	}
	
	@Override
	public void stop() {
		System.out.println("28라인(Sonata 클래스)-stop");
		if(speed>0) {
			speed=speed -2;
		}else {
			speed=0;
		}
		
	}
}

public class CarSimulation {

	public static void main(String[] args) {
		// 추상클래스와 인터페이스는 반드시 구현체 클래스가 있어야  - 스프링 프레임워크가 추구하는 코딩 방향
		// 단독으로 인스턴스화 할 수 없다
		Car myCar = new Sonata();
		myCar.stop();
		Sonata herCar = new Sonata();
		Car himCar = null;
		Sonata yourCar = null;
		// 형변환 연산자(캐스팅연산자)를 통해서 강제로 형변환 해도 런타임시에는 오류 발생
		yourCar = (Sonata)myCar; // 실행시 오류 발생 
		yourCar = herCar;
	}

}
