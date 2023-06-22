<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.ArrayList" %>        
<%@ page import="com.util.BSPageBar" %>        
<%
    //스크립틀릿 - 지변, 메소드선언 불가함, 인스턴스화 가능함
    int size = 0;//지변이니까 초기화를 생략하면 에러발생함.
    List<Map<String,Object>> bList = (List<Map<String,Object>>)request.getAttribute("bList");
    if(bList !=null){
       size = bList.size();   
    }
    int numPerPage = 5;
    int nowPage = 0;
    if(request.getParameter("nowPage")!=null){
       nowPage = Integer.parseInt(request.getParameter("nowPage"));
    }
    //out.print(size);//0이 출력됨
    //out.print(mList);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판[webapp]</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/board.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
    const boardList = () => {
       //alert('boardList');
       location.href="/board/boardList.pj2";
    }
    const boardDetail = (b_no) => {
       console.log('boardDetail 사용자가 선택한 글번호 : '+b_no);
       location.href="/board/boardDetail.pj2?b_no="+b_no;
    }
    const boardSearch = () => {
        const gubun = document.querySelector('#gubun').value;
        const keyword = document.querySelector('#keyword').value;
        console.log(gubun+", "+keyword);
        window.location.href="/board/boardList.pj2?gubun="+gubun+"&keyword="+keyword;            
    }
    const searchEnter = (event) => {
       console.log(window.event.keyCode);
       if(window.event.keyCode == 13){
          boardSearch();
       }
    }
    const boardInsert = () => {
       document.querySelector("#f_board").submit();
    }
    const zipcodeSearch = () => {
       
    }
    //자식창에서 부모창 함수 호출하기
    window.call = function(zipcode, address){
       console.log('자식창에서 호출 : '+ zipcode+', '+address);
       document.querySelector('#mem_zipcode').value=zipcode;
       document.querySelector('#mem_address').value=address;
    }
    const zipcodeForm = () => {
       cmm_window_popup('zipcodeSearch.jsp','700','600','zipcodeForm');
    }
    const fileDown = (fname) => {//fname -> MVC패턴실습2 자유게시판.xslx=파일명(pds에 생기는 파일이름)
    	location.href="downLoad.jsp?b_file="+fname;
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
          <h2>게시판 <small>게시글목록</small></h2>
          <hr />
       </div>
       <!-- 검색기 시작 -->
       <div class="row">
          <div class="col-3">
              <select id="gubun" class="form-select" aria-label="분류선택">
                 <option value="none">분류선택</option>
                 <option value="b_title">제목</option>
                 <option value="b_writer">작성자</option>
                 <option value="b_content">내용</option>
              </select>        
          </div>
          <div class="col-6">
             <input type="text" id="keyword" class="form-control" placeholder="검색어를 입력하세요" 
                  aria-label="검색어를 입력하세요" aria-describedby="btn_search" onkeyup="searchEnter()"/>
          </div>
          <div class="col-3">
             <button id="btn_search" class="btn btn-danger" onClick="boardSearch()">검색</button>
          </div>
       </div>    
       <!-- 검색기 끝 -->
       
       <!-- 회원목록 시작 -->
       <div class='board-list'>
          <table class="table table-hover">
              <thead>
                 <tr>
                     <th width="10%">#</th>
                     <th width="30%">제목</th>
                     <th width="20%">파일</th>
                     <th width="20%">작성자</th>
                     <th width="20%">조회수</th>
                 </tr>
              </thead>
              <tbody>
<%
    //for(int i=0;i<size;i++){
    for(int i=nowPage*numPerPage;i<(nowPage*numPerPage)+numPerPage;i++){
       if(size == i) break;
       Map<String,Object> rmap = bList.get(i);
%>            
                 <tr>
                     <th><%=rmap.get("B_NO") %></th>
                     <th><a href="javascript:boardDetail('<%=rmap.get("B_NO")%>')"><%=rmap.get("B_TITLE") %></a></th>
                     <th>
                     <a href="javascript:fileDown('<%=rmap.get("B_FILE")%>')"><%=rmap.get("B_FILE") %></a>
                     </th>
                     <th><%=rmap.get("B_WRITER") %></th>
                     <th><%=rmap.get("B_HIT") %></th>
                 </tr>
<%
    }
%>               
              </tbody>
          </table> 
    
<hr />  
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->
    <div style="display:flex;justify-content:center;">
    <ul class="pagination">
<%
    String pagePath = "boardList.pj2";
    BSPageBar bspb = new BSPageBar(numPerPage, size, nowPage, pagePath);
    out.print(bspb.getPageBar());
%>
    </ul>
    </div>
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->         
          <div class='board-footer'>
              <button class="btn btn-warning" onclick="boardList()">
                 전체조회
              </button>&nbsp; 
              <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#boardForm">
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
    <!-- ========================== [[ 게시판 Modal ]] ========================== -->
    <div class="modal" id="boardForm">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
    
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">게시판</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <!-- Modal body -->
          <!-- 오늘 첨부파일 업로드 하기 위해서는 form 전송시 enctype="multipart/form-data" 속성 추가 필요 -->
          <div class="modal-body">
           <form id="f_board" method="post" enctype="multipart/form-data" action="./boardInsert.pj2">
             <input type="hidden" name="method" value="boardInsert">
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="b_title" name="b_title" placeholder="Enter 제목" />
                <label for="b_title">제목</label>
              </div>          
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="b_writer" name="b_writer" placeholder="Enter 작성자" />
                <label for="b_writer">작성자</label>
              </div>           	
              <div class="form-floating mb-3 mt-3">
                <textarea rows="5" class="form-control h-25" aria-label="With textarea" id="b_content" name="b_content"></textarea>
              </div>   
              
              <div class="input-group mb-3">
			  <input type="file" class="form-control" id="b_file" name="b_file">
			  <label class="input-group-text" for="b_file">Upload</label><!-- id와 라벨의 for는 일치시키는게 좋다. -->
		 	 </div>
              
                     
           </form>
          </div>   
          <div class="modal-footer">
            <input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="boardInsert()"  value="저장">
            <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
          </div>
    
        </div>
      </div>
    </div>
    <!-- ========================== [[ 게시판 Modal ]] ========================== -->           
</body>
</html>