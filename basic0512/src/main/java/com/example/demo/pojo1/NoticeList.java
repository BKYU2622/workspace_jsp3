package com.example.demo.pojo1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeList {
/*
 * 비교하기 - 관전포인트 - 학습목표
 * 
 * 
 * 
 */
	public static void main(String[] args) {
		List<Map<String, Object>> nList = null;
		//System.out.println(nList.size()); // NullPointerException
		nList = new ArrayList<>(); // 이 때 비로소 Heap메모리 영역에 로딩이 된다
		// 선언부와 생성부의 타입이 다를 때 다형성을 적용할 수 있으니까
		// 같은 메서드라 하더라도 주소번지에 생성된 객체가 어떤 타입 인가에 따라 기능이 다른 것
		Map<String, Object> rMap = new HashMap<>(); // 권장사항 - 인터페이스 = new 구현체 클래스();
		nList.add(rMap); // 위치가 바뀜 - 적용이 됨 - 왜? -> 얕은 복사(원본)라서 적용 가능 / 깊은 복사(새로운 객체)
		System.out.println(nList.size()); // 0 -> 1 
		System.out.println(nList.get(0)); // 주소번지  
		System.out.println(nList.get(0).get("n_no")); // null
		rMap.put("n_no", 10); // 벨류값에 n_no, 키값에 10
		System.out.println(nList.get(0).get("n_no")); // null -> 10
		
	}

}
