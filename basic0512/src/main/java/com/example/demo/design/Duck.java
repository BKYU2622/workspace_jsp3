package com.example.demo.design;

// 추상 클래스로 설계함
// 추상 클래스는 일반 메서드와 추상 메서드 모두를 가질 수 있음
// 추상 메서드인 경우 일반 메서드와 구분하기 위해서 abstract생략하면 안 됨
// 어떤 관계일 때 혹은 어떤 기준으로 상속관계를 정하면 되나?
// A is a B 관계가 성립되면 둘은 서로 상속관계로 정의한다
public abstract class Duck {
	FlyBehavior flyBehavior = null;
	// 추상 메서드 -  결정되지 않았다, 결정할 수 없다, 잘 모르겠다
	public abstract void display(); // 추상 메서드
	public void swimming() { // 일반 메서드 - 왜냐면 좌우중괄호가 있는 것 만으로 구현이다
		System.out.println("모든 오리는 물 위에 뜬다");
	}
}
