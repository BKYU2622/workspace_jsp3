<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap" %>        
<%
	//여기는 상세보기 페이지입니다.
	//그래서 조회되면 1건, 없으면 0건이다.
	//n건이 조회되면 안된다(pk가 적용되지 않았다-그래서 n건이 조회됨)
	//왜 n건이 나올 수 있는걸까?
	//myBatis는 동적 쿼리(if문, when구문, choose문)를 지원하니까
    int size = 0;//지변이니까 초기화를 생략하면 에러발생함.
    //jsp에서는 오라클 연동을 직접 하지 않는다 - 왜냐면 인스턴스화를 할 수 없다.
    //ActionServlet을 경유해서 BoardController에서 forward를 통해서 
    //요청객체에 담긴 정보를 꺼내서 쓴다.
    //myBatis를 이용해서 DB연동을 하고 있어서 조회결과가 없더라도 객체주입은 된다.
    
    List<Map<String, Object>> bList = (List<Map<String, Object>>)request.getAttribute("bList");
    Map<String, Object> rmap = null;
    if(bList!=null && bList.size()>0){
    	out.print("b_no가 존재하는 경우");
    	size = bList.size();
    	//List도 배열처럼 index로 접근한다.
    	rmap = bList.get(0);//파라미터에 왜 0을 적었나?
    	//insert here-조회결과가 있을때만 출력됨
    	out.print(rmap);//{키=값, 키1=값1, 키2=값2..}=>이런식으로 찍히는게 map이다.
    }
    //insert here-조회결과가 없으면 null인 상황
   // out.print(bList); - 브라우저에 쓰기 - 서버사이드에서 결정된 문자열 출력 - 데이터셋 역할(JSON포맷 - Gson API, 단순문자열포맷)
    //rmap = bList.get(0);//null, {} 둘 중 뭘로 찍히는지
   // out.print(rmap);//{}안에 아무값도 들어가지 않는다.
    
    //List<Map<>> -> [{},{},{}....] -> JSON으로 맞는 포맷 -> IOS, 안드로이드 서비스 가능함, Vue.js, ReactJS도 가능
    //공공포털, 클라우드 서비스를 이용한 시스템을 고려하고 있다면 무조건 알아둬야함
    //List<Map> -> JSON
    //JSON -> List<Map>
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항상세</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/board.css">
<script type="text/javascript">
    const boardDelete = () => {
       window.location.href="/board/boardDelete.pj2?b_no=<%=rmap.get("B_NO") %>";    
    }
    const boardList = () => {
       window.location.href="/board/boardList.pj2";
    }
    const boardUpdate = () => {
       document.querySelector("#f_board").submit();
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
          <h2>게시판 <small>글상세보기</small></h2>
          <hr />
       </div>
       
       <!-- 공지목록 시작 -->


          <div style="width:58rem;">
            <div class="card-body">
              <div class='book-detail'>
                <div class='book-header'>
                
             <div class="input-group mb-3">
             <span class="input-group-text">제목</span>
             <div style="width:500px;">
                <input type="text" class="form-control" value="<%=rmap.get("B_TITLE") %>" readonly>
             </div>
             </div>
             
             <div class="input-group mb-3">
             <span class="input-group-text">작성자</span>
             <div class="col-xs-3">
                <input type="text" class="form-control" value="<%=rmap.get("B_WRITER") %>" readonly><!-- readonly:읽기만 가능, 수정 불가 -->
             </div>
             </div>
             
             <div class="input-group mb-3">
             <span class="input-group-text">내용</span>
             <div class="col-xs-8">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="5" cols="80" readonly><%=rmap.get("B_CONTENT") %></textarea>
             </div>
             </div>
             

                </div>
              </div>
            </div>
          <hr/>
            <div class='detail-link'>
              <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#boardUpdateForm">
               수정
              </button>
              &nbsp;
              <button class="btn btn-warning" onclick="boardDelete()">
                삭제
              </button>
              &nbsp;
              <button class="btn btn-success" onclick="boardList()">
              공지목록
              </button>
            </div>
          </div>  
       
       <!-- 회원목록   끝  -->    
       
    </div>
    <!-- body end       -->
    <!-- footer start -->
    <%@include file="/include/gym_footer.jsp"%>
    <!-- footer end    --> 
    
    <!-- ========================== [[ 공지사항 수정 Modal ]] ========================== 소스는 1개인데 화면은 Modal아닌것과 Modal인것 2개-->
    <div class="modal" id="boardUpdateForm">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
    
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">공지사항수정</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
    
          <!--수정관련 Modal body -->
          <!-- 기존에 있는 레코드를 변경하는 것=pk(b_no)가 있어야한다. -->
          <!-- b_no는 사용자로부터 입력받은 값이 아니다(시퀀스(=개발자가 필요로 하는 값)로 치환한 값) -->
          <div class="modal-body">
           <form id="f_board" method="post" action="./boardUpdate.pj2"><!-- get방식은 한글깨짐 -->
           <!-- hidden속성은 화면 없이 서버측에 값을 넘길때 사용함 -->
             <input type="hidden" name="b_no" value="<%=rmap.get("B_NO") %>">
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="b_title" name="b_title" placeholder="Enter 제목" value="<%=rmap.get("B_TITLE") %>"/>
                <label for="mem_id">제목</label>
              </div>          
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="b_writer" name="b_writer" placeholder="Enter 작성자" value="<%=rmap.get("B_WRITER") %>"/>
                <label for="mem_pw">작성자</label>
              </div>               
              <div class="form-floating mb-3 mt-3">
                <textarea rows="5" class="form-control h-25" aria-label="With textarea" id="b_content" name="b_content"><%=rmap.get("B_CONTENT") %></textarea>
              </div>          
           </form>
          </div>
    
          <!-- Modal footer -->
          <div class="modal-footer">
            <input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="boardUpdate()"  value="저장">
            <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
          </div>
    
        </div>
      </div>
    </div>
    <!-- ========================== [[ 회원가입 Modal ]] ========================== -->    
    
</body>
</html>