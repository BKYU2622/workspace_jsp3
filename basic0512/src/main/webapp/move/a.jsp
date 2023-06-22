<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>a.jsp</title>
</head>
<body>
	<h2>sendRedirect메서드를 이용한 페이지 이동 - response객체가 제공</h2>
	<ul>
        <li>내용1</li>
        <li>내용2</li>
        <li>내용3</li>
    </ul>
    <%
       // 4번
       response.sendRedirect("b.jsp");
    %>
    <p>내용4</p>
</body>
</html>