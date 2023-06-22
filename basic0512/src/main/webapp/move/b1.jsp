<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap" %>
<% 
	// 스크립틀릿 - 지변, 메서드선언 불가함, 인스턴스화 가능함
	int size = 0; // 지변이니까 초기화를 생략하면 에러발생 함
	size = (Integer)request.getAttribute("size");
	List<Map<String, Object>> memList = (List<Map<String, Object>>)request.getAttribute("memList");
	out.print(size);	// 0이 출력됨
	out.print("<br>");	// 섞어 쓰고 있기 때문에 <br>로 다음 라인으로 넘겨줘야 함
	out.print(memList);
	// 왜냐하면 List안에 Map 객체가 추가되지 않았으니까
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>b1.jsp</title>
</head>
<body>
    <h2>여기는 b1.jsp페이지 입니다.</h2>
</body>
</html>