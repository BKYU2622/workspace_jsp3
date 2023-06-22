<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.ArrayList" %>        
<%@ page import="com.util.BSPageBar" %>        
<%
	//스크립틀릿 - 지변, 메소드선언 불가함, 인스턴스화 가능함
	int size = 0;//지변이니까 초기화를 생략하면 에러발생함.
	List<Map<String,Object>> nList = (List<Map<String,Object>>)request.getAttribute("nList");
	// nList가 null이 아니기 위해서는 테스트 시나리오를 어떻게 가져가야 할까?
	// 경우의 수: 한 건도 등록되지 않았을 때 -> 0출력 - myBatis에서 간섭(인터셉트) nList = new ArrayList<>();
	if(nList !=null){
		size = nList.size();	
	}
	out.print(nList); // List<Map> -> [{},{},{}] -> JSON포멧 -> 하이브리드앱서비스 가능
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/notice.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	// 자바스크립트에서는 함수는 객체이다
	const noticeDetail = (n_no) => {
		console.log(n_no);
		location.href = "/notice/noticeDetail.pj1?n_no=" + n_no; // 상수를 변수로 치환 +로 연결
	}
	const noticeInsert = () => {
		// submit() 함수를 호출해야 action url이 요청된다
		console.log("noticeInsert");
		document.querySelector("#f_notice").submit(); // 실제 전송이 일어나는 코드임
	}
</script>
</head>
<body>
	<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->
	<!-- body start    -->
	<div class="container">
		<div class="page-header">
			<h2>공지사항 <small>공지목록</small></h2>
			<hr />
		</div>
		<!-- 검색기 시작 -->
		<div class="row">
			<div class="col-3">
		    	<select id="gubun" class="form-select" aria-label="분류선택">
		      		<option value="none">분류선택</option>
		      		<option value="n_title">제목</option>
		      		<option value="n_writer">작성자</option>
		      		<option value="n_content">내용</option>
		    	</select>			
		  	</div>
			<div class="col-6">
		 		<input type="text" id="keyword" class="form-control" placeholder="검색어를 입력하세요" 
		           aria-label="검색어를 입력하세요" aria-describedby="btn_search" onkeyup="searchEnter()"/>
			</div>
			<div class="col-3">
		 		<button id="btn_search" class="btn btn-danger" onClick="noticeSearch()">검색</button>
		 	</div>
		</div>		
		<!-- 검색기 끝 -->
		
		<!-- 회원목록 시작 -->
		<div class='notice-list'>
			<table class="table table-hover">
		    	<thead>
		      		<tr>
		        		<th width="10%">#</th>
		        		<th width="50%">제목</th>
		        		<th width="20%">작성자</th>
		        		<th width="20%">날짜</th>
		      		</tr>
		    	</thead>
		    	<tbody>
<%
	for(int i=0; i<size; i++) {
		Map<String, Object> rmap = nList.get(i);
%>
		      		<tr>
		        		<td width="10%" align="center"><%= rmap.get("N_NO") %></td>  <!-- 꺼낼 때는 반드시 대문자로 적어야 함, 소문자로 쓰면 출력되지 않음 -->
		        		<td width="50%" align="left">
		        		<a href="javascript:noticeDetail('<%= rmap.get("N_NO") %>')"> <!-- 바깥쪽이 더블이면 안쪽은 싱글 -->
		        		<%= rmap.get("N_TITLE")%>
		        		</a>
		        		</td> 
		        		<td width="20%" align="center"><%= rmap.get("N_WRITER") %></td>
		        		<td width="20%" align="center"><%= rmap.get("N_DATE") %></td>
		      		</tr>
<%
	} // end of for
%>		    	
		    	</tbody>
			</table> 
    	
<hr />  
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->
	<div style="display:flex;justify-content:center;">
	<ul class="pagination">
<%
	String pagePath2 = "noticeList.pj1";
//	BSPageBar bspb = new BSPageBar(numPerPage, size, nowPage, pagePath);
//	out.print(bspb.getPageBar());
%>
	</ul>
	</div>
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->		  
		  	<div class='notice-footer'>
		    	<button class="btn btn-warning" onclick="noticeList()">
		      		전체조회
		    	</button>&nbsp; 
			    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#noticeForm">
			    글쓰기
			    </button>
		    </div>
		</div>		
		<!-- 회원목록   끝  -->		
		
	</div>
	<!-- body end       -->
	<!-- footer start -->
	<%@include file="/include/gym_footer.jsp"%>
	<!-- footer end    -->	
	<!-- ========================== [[ 공지사항 Modal ]] ========================== -->
	<div class="modal" id="noticeForm">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">공지사항</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	      <!-- Modal body -->
	      <div class="modal-body">
	      <!-- form 전송하기{method, action = ""}  -->
	      	<form id="f_notice" method="get" action="./noticeInsert.pj1">
	      	<!-- hidden: 사용자가 입력하는 값이 아니고 개발자가 필요로 하는 값을 넘길 때 사용함 -->
	      	<!-- 화면은 존재하지 않지만 값을 서버측으로 전달 가능함 -->
	      	<!-- name은 method이고 값은 memberInsert임, 컨트롤 계층에서 분기문으로 사용됨 -->
	      	<!-- input에 사용되는 id값은 자바스크립트에서 사용하고, name값은 서블릿에서 사용함 -->
	      	<!-- upmu[1]="noticeInsert" -->
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="n_title" name="n_title" placeholder="Enter 제목" />
	            <label for="n_title">제목</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="n_writer" name="n_writer" placeholder="Enter 작성자" />
	            <label for="n_writer">작성자</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <textarea rows="5" class="form-control h-25" aria-label="With textarea" id="n_content" name="n_content"></textarea>
	          </div>	      	
	      	</form>
	      </div>	
	      <div class="modal-footer">
	        <input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="noticeInsert()" value="저장">
	        <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
	      </div>
	
	    </div>
	  </div>
	</div>
    <!-- ========================== [[ 공지사항 Modal ]] ========================== -->				
</body>
</html>