<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
  <%@ page import="com.google.gson.Gson"%>
<%
	List<Map<String, Object>> bList = (List)request.getAttribute("bList");
	Gson g = new Gson();
	String imsi = g.toJson(bList);
	out.print(imsi);
%>