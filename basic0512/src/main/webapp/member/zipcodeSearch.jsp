<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%!
	// 디클러레이션
	// 메서드를 호출하려면 가장 먼저 해야 것은? - 인스턴스화를 하려면 클래스명이 필요
	// 이 파일은 java가 아니라 jsp이다
	// a_jsp.java, 이름_jsp.java - Tomcat이 해줌
	// 만일 톰캣 서버가 아닌 다른 서버를 사용하게 되면 클래스 이름이 달라진다. 명명규칙은 서버마다 다름
	// 그러니까 메서드 호출은 불가하지 않을까?
	// 재사용성을 높이는 코딩을 전개하는 첫번째 방법이 메서드 중심의 코딩을 전개하는 것이다
	// 메서드의 파라미터 갯수를 정할 수 있나? 메서드의 리턴타입을 정하는 기준이 있나? 		
%>
<%
	// 스크립틀릿 - 모두 지변이다, 서블릿으로 전환시 service메서드 내부에 들어가는 코드이기 때문
	// 메서드 선언 불가
	// 여기는 톰캣서버 내부에서 실행이 된 후 html문서를 만들어서 클라이언트측에 응답으로 다운로드 내보내는 방식으로 처리됨
	int size = 0; // 원시형 변수 - 호출하면 값이 나온다
	List<Map<String, Object>> zList = (List)request.getAttribute("zList"); // 강제 형변환
	if(zList !=null) {
		size = zList.size();
	}
	// 확장자 jsp를 갖는 파일에 적힌 자바 코드는 브라우저에서 소스 보기로 절대 볼 수 없다 - 기억
	// html과 자바코드를 섞어쓰지만 실행주체가 다르고 실행되는 시점이 다르다 - 설명할 수 있다면 고수
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우편번호 검색기</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/zipcode.css">
<script type="text/javascript" href="/css/zipcode.css"> 
	const zipcodeSearch = () => {
		const dong = document.querySelector("#dong").value; // 사용자가 입력한 동이름이 담김
		console.log(dong);
		location.href="./memberCRUD?method=zipcodeList&dong="+dong;
	}
</script>
</head>
<body>
	<div class="container">
	<!--===================================[[검색기]]========================================-->
	 	<div class="row">
	 		<div class="col-8">
	 			<input type="text" id="dong" class="form-control" placeholder="동이름을 입력하세요">
	 		</div>
	 		<div class="col-4">
	 		<button type="button" class="btn btn-success" onclick="zipcodeSearch()">찾기</button>
	 		</div>
		</div>
		<div style="margin-bottom:10px"></div>
	<!--===================================[[검색기]]========================================-->
	
	<!--===================================[[검색 결과 출력]]========================================-->
	<div class="main">
			<table class="table table-hover" width="600px">
				<thead>
					<tr>
						<th width="20%">우편번호</th>
						<th width="80%">주소</th>
					</tr>
				</thead>
				<tbody>
				<%
				// 조회 결과가 있나? -> if문
				if(size>0) {
				// 조회 결과가 여러개 인가? -> for문
				for(int i=0; i<size; i++){
					Map<String, Object> rmap = zList.get(i);
				%>
					<tr>
						<td><a href="#"><%= rmap.get("ZIPCODE") %></td> <!-- 마이바티스에서는 무조건 파라미터 안에 대문자로 적어줘야 함, 대문자로 써야 출력 됨  -->
						<td><%= rmap.get("ADDRESS") %></td>
					</tr>
				<%
					}
				}else{
				// else문 
				%>	
					<tr>
						<td colspan="2">조회결과가 없습니다.</td>
					</tr>
				<%
				}
				%>	
				</tbody>
			</table>
		</div>
	<!--===================================[[검색 결과 출력]]========================================-->
	</div>
</body>
</html>