package com.example.demo.design;

public class DuckSimulation {
	// 파라미터의 갯수가 다른거나 타입이 다르면 메서드 오버로딩 조건을 만족해서 중복선언 가능
	// 메서드 이름이 같은데 둘은 서로 다른 메서드이다 -> 오버로딩
	// 오버라이딩은 상속관계이거나 추상 클래스여야 함
	void methodA(WoodDuck wd) {
		System.out.println("methodA(WoodDuck)호출");
	}
	
	void methodA(Duck duck) {
		System.out.println("methodA(Duck)호출");
	}
	
	public static void main(String[] args) {
		// 날고 날지 못하고는 해당 객체의 생성자에서 결정되었다
		// 다형성이란 같은 이름의 메서드를 호출했는데 그 기능이 다르게 동작하는 것을 말함
		// 다형성이란 선언부의 타입과 생성부의 타입이 다를 때 기대할 수 있다
		Duck myDuck = new MallardDuck(); // 19번에 myDuck과 21번에 myDuck은 이름은 같지만 주소번지가 다르다
		myDuck.flyBehavior.fly(); // 나는 날 수 있습니다 - z출력
		myDuck = new WoodDuck();
		myDuck.flyBehavior.fly(); // 나는 날지 못 합니다 - 출력
		
		// static 영역에서는 non-static 변수나 메서드를 호출(접근)할 수 없다
		// 내 안에 있는 메서드이더라도 인스턴스화를 하면 호출할 수 있다 - 문제해결 능력
		DuckSimulation ds = new DuckSimulation();
		ds.methodA(myDuck);
		WoodDuck himDuck = new WoodDuck();
		ds.methodA(himDuck);
				
	}

}
