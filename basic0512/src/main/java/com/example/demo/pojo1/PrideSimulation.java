package com.example.demo.pojo1;

class Pride {
	int speed; // 0-int의 디폴트값은 0
	
}

public class PrideSimulation {

	public static void main(String[] args) {
		Pride myCar = new Pride();
		System.out.println(myCar); // @abcd1234 주소번지를 갖고 있고 또 실체도 존재함
		int speed = myCar.speed; 
		System.out.println(speed); // 0
		
		myCar = null; // 다시 초기화를 함 - null
		myCar = new Pride(); // 새로운 차가 생성됨 -> 주소번지가 새로 부여됨, @abcd1325
		// 타입은 같지만 새로운 객체가 만들어 짐
		System.out.println(myCar); // 주소번지는 설사 새로 부여되었다 하더라도 실체가 없다
		speed = myCar.speed; // 0 - null인 상태에서 변수를 호출할 수 없다
		System.out.println(speed); // 0
	}

}
