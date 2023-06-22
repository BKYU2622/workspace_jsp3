package com.example.demo.member;

public class StringTest {

	public static void main(String[] args) {
		String insa = "hello";
		System.out.println(insa); // hello

		insa = insa.replace('e', 'o'); // 새로운 객체가 할당 됨(재정의)		
		System.out.println(insa); // hollo
		StringBuilder sb = new StringBuilder("hello");
		sb.append("wow!!!"); // 원본에 추가하기이다 / 덮어쓰기 아님 주위
		System.out.println(sb.toString()); // hello world!!!
		
	}
}
