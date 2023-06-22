<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, java.util.List, java.util.ArrayList, java.util.HashMap"  %>
<%@ page import="com.google.gson.Gson" %>
<% 
	// 스크립틀릿 = 메서드 정의 불가, 호출만 가능, 지변만 가능, 인스턴스화 가능, 제어문 가능
	List<Map<String, Object>> fruitList = new ArrayList<>(); 
	Map<String, Object> rMap = new HashMap<>();
		rMap.put("first", "토마토");
		rMap.put("second", "키위");
		fruitList.add(rMap);
		
	rMap = new HashMap<>();
		rMap.put("first", "수박");
		rMap.put("second", "딸기");
		fruitList.add(rMap);
		
		Gson g = new Gson();
		String temp = g.toJson(fruitList);
		out.print(temp);
%>