<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%
	List<Map<String, Object>> bList = (List)request.getAttribute("bList");
	out.print(bList);
%>