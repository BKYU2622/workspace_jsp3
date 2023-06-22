<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.ArrayList" %>      
<%@ page import="com.util.BSPageBar2" %>  
<%
	//java.lang이 아닌 패키지는 모두 다 반드시 import문을 써야함- 컨벤션
    //스크립틀릿 - 지변, 메소드선언 불가함, 인스턴스화 가능함
    int size = 0;//지변이니까 초기화를 생략하면 에러발생함.
    List<Map<String,Object>> mList = (List<Map<String,Object>>)request.getAttribute("mList");
    if(mList != null){
    	size = mList.size();//3
    }
    int numPerPage = 3; 
    int nowPage = 0;
    //member/memberCRUD?method=memberSelect&nowPage=1
   	//쿼리스트링으로 현재 내가 바라보는 페이지 번호를 넘긴다
   	if(request.getParameter("nowPage")!=null){
   		//String을 int로 바꿔주는 메소드 호출하기
   		nowPage = Integer.parseInt(request.getParameter("nowPage"));
   	}
    //out.print(size);//0이 출력됨
    //out.print(mList);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리2[POJO2]</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/member.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	//검색 버튼을 눌렀을 때 호출되는 함수입니다
    const memberSearch = () => {
       const gubun = document.querySelector("#gubun").value;//분류선택 - mem_id, mem_name, mem_address
       const keyword = document.querySelector("#keyword").value;//사용자가 입력한 키워드
       //console.log(gubun+" , "+keyword);
       //브라우저 내장객체 중 최상위 객체 - window객체임
       //window.location은 location도 window객체의 자손을 뜻하는 것임
       //조건 검색을 요청하는 URL매핑이다 
       //URL매핑값으로 분기를 함(BoardController. MemberController호출할지 유무)
       window.location.href="/member2/memberList.pj2?gubun="+gubun+"&keyword="+keyword;
       
    }
    const searchEnter = (event) => {
    	if(window.event.keyCode == 13){
    		memberSearch();
    	}
    }
    //가입 버튼을 input 태그로 했으므로 반드시 submit()호출이 필요하다
    //ES6지원 - 화살표 함수 - arrow function
    const memberInsert = () => {
    	document.querySelector("#f_member").submit();//<form id="f_member">
    	//document.getElementById("f_member").submit();
    }
   	const memberDetail = (user_no) => {
   		console.log(user_no);
   		location.href="./memberDetail.pj2?mem_no="+user_no;
   	}
    const zipcodeForm = () => {
    	//파라미터로 값을 넘길 때 싱글 혹은 더블 쿼테이션을 붙이지 않으면 변수취급을 함 -주의
		cmm_window_popup('zipcodeSearch.jsp','700','600','zipcodeForm');    	
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
          <h2>회원관리 <small>회원목록</small></h2>
          <hr />
       </div>
       <!-- 검색기 시작 -->
       <div class="row">
          <div class="col-3">
              <select id="gubun" class="form-select" aria-label="분류선택">
                 <option defaultValue>분류선택</option>
                 <option value="mem_id">아이디</option>
                 <option value="mem_name">이름</option>
                 <option value="mem_address">주소</option>
              </select>        
          </div>
          <div class="col-6">
             <input type="text" id="keyword" class="form-control" placeholder="검색어를 입력하세요" 
                  aria-label="검색어를 입력하세요" aria-describedby="btn_search"  onkeyup="searchEnter()"/>
          </div>
          <div class="col-3">
             <button id="btn_search" class="btn btn-danger" onclick="memberSearch()">검색</button>
          </div>
       </div>    
       <!-- 검색기 끝 -->
       
       <!-- 회원목록 시작 -->
       <div class='member-list'>
          <table class="table table-hover">
              <thead>
                 <tr>
                     <th width="10%">#</th>
                     <th  width="20%">아이디</th>
                     <th  width="20%">이름</th>
                     <th  width="50%">주소</th>
                 </tr>
              </thead>
              <tbody>
<%
	//몇번째 라인에서 터졌니? - IndexOutofBoundException, NullPointerException
	//for(int i=0;i<size;i++){
	//페이징 처리에 필요한 전변은 뭔가 있었나?
	//nowPage :  현재 내가 바라보는 페이지
	//totalRecord == size : 전체 회원의 수
	//페이징처리를 필요로 하는 페이지이름은? pagePath변수에 오는 값은 페이징처리를 적용할 페이지가 되는 것이다
	//예컨데) imageList?method=imageSelect&nowPage=1
	// noticeList?method=noticeSelect&nowPage=1
	// qnaList?method=qnaSelect&nowPage=1
	//numPerPage는 한 페이지에 글을 몇개씩 보여줄거니?
	//(0*3);i<(0*3)+3;i=i+1 -> 0, 1, 2		
	//(1*3);i<(1*3)+3;i=i+1 -> 3, 4, 5
	//2번을 클릭했을 때  따져 보자
	//6;i<9;i=i+1 -> 6, 7, 8
	if(mList !=null && size > 0 ){//조회 결과가 존재하면
	out.print(" before ===> "+nowPage +", "+ numPerPage + ", ");
	out.print("<br />");
	//for(초기화;조건식;증감연산자)
	//for(초기화;i<3;i=i+1) - i가 달라지니까.....
	//for(i=0, 3, 6, 9)
	for(int i=nowPage*numPerPage;i<(nowPage*numPerPage)+numPerPage;i++){	//10 9 8    7 6 5    4 3 2  1 break;
		out.print("for문 내부 ===> " + nowPage +", "+ numPerPage + ", " +i);
		//여기야?		- 이전에
		//마지막 페이지의 숫자가 3바퀴 모두 갖고 있지 않으면 for문을 탈출 해야 한다.
		//왜냐하면 i값에 해당하는 위치에 주소번지가 존재하지 않기 때문이다. - 왜냐면 밖에는 List타입이 있지만 그 안에는 Map타입이다
		//이 Map타입안에 실제 회원님에 정보들이 들어 있으니까.....
		//IndexOutofBoundException이었다. -> List의심해 보자 -> 왜냐면 배열과 비슷하게 index로 주소번지를 가져오니까....
		if(size == i) break;//조건절에서 무조건 numPerPage숫자 만큼 반복 된다. - 변수i가 세번씩 끊어서 		
		Map<String,Object> rmap = mList.get(i);
		//아님 요기야? - 이후에
%>              
                            
                 <tr>
                     <th><%= rmap.get("MEM_NO")%></th><!--  rmap =null이라고 가정해보자 NullPointerException -->
                     <th>
<!-- 자바스크립트와 자바코드의 섞어쓰기 가능함
서버에서 실행된 결과가 텍스트로 클라이언트에 다운로드 되니까 결국은 문자열이 들어 있기때문에 가능함
 -->                     
                     	<a href="javascript:memberDetail('<%=rmap.get("MEM_NO")%>')"><%= rmap.get("MEM_ID")%></a>
                     </th>
                     <th><%= rmap.get("MEM_NAME")%></th>
                     <th><%= rmap.get("MEM_ADDRESS")%></th>
                 </tr>
<%
	}//end of for
	}//end of if -회원이 한 명이라도 있을 때
	else{//회원이 존재하지 않는 경우
%>        
				<tr>
					<td colspan="4">조회결과가 존재하지 않습니다.</td>
				</tr>
<%
	}
%>                          
              </tbody>
          </table> 
          <!--================= [[ 페이징 처리{페이지네이션 처리}  ]] =================-->
			<div style="display:flex;justify-content:center">
			<ul class="pagination">
<%
	//스크립틀릿 - 모두 지변이다, 메소드 선언은 불가함, 인스턴스화는 가능함
	//왜 가능하냐면 파라미터가 없으니까 가능하지 않을까
	//생긴 꼴이 인스턴스변수.변수명으로 호출할 수 있다면 넌 전변이다
			//파라미터로 넘겨지는 값은 지변으로 한다.
			//대신  공통 클래스의 생성자 안에서는 전변으로 다시 초기화가 된다. - 유지된다 - 안심하자.
			String pagePath = "/member2/memberList.pj2";
			//인스턴스화를 통해서 생성자가 호출이 된다 - 인지하고 있나?
			BSPageBar2 pb = new BSPageBar2(numPerPage,size,nowPage,pagePath);//디폴트 생성자 호출하기 - 파라미터가 한 개도 없는 생성자 - 생략가능함 - 누가해준데 -JVM이
			//페이징처리에 필요한 태그문자열은 setPageBar()에서 만들어졌다.
			//그런데 getPageBar()호출로 setPageBar()경유가 되었나?			
			out.print(pb.getPageBar());
%>            
			</ul>
			</div>          
		  <!--================= [[ 페이징 처리{페이지네이션 처리}  ]] =================-->          
          <hr />    
          <!-- 여기여  -->
          <div class='member-footer'>
              <button class="btn btn-warning" onclick="memberSearch()">
                 전체조회
              </button>&nbsp; 
              <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#memberForm">
              회원가입
              </button>
           </div>
       </div>    
       <!-- 회원목록   끝  -->    
       
    </div>
    <!-- body end       -->
    <!-- footer start -->
    <%@include file="/include/gym_footer.jsp"%>
    <!-- footer end    --> 
    <!-- ========================== [[ 회원가입 Modal ]] ========================== -->
    <div class="modal" id="memberForm">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
    
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">회원가입</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
    	  <!-- 서블릿에서는 doGet메소드 안에서 4가지 경우의 수를  처리해야 한다 -->
          <!-- Modal body -->
          <div class="modal-body">
           <form id="f_member" method="post" action="./memberInsert.pj2"><!--  403, 405, 401{인증-외부 소셜에서 해줄때} -->
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="mem_id" name="mem_id" placeholder="Enter 아이디" />
                <label for="mem_id">아이디</label>
              </div>          
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="mem_pw" name="mem_pw" placeholder="Enter 비밀번호" />
                <label for="mem_pw">비밀번호</label>
              </div>          
              <div class="form-floating mb-3 mt-3">
                <input type="text"  class="form-control" id="mem_name" name="mem_name" placeholder="Enter 이름" />
                <label for="mem_name">이름</label>
              </div>       
              
			  <div class="input-group">
			    <input type="text" class="form-control" id="mem_zipcode" name="mem_zipcode" placeholder="우편번호">
			    <input type="button" class="btn btn-success" onclick="zipcodeForm()" value="우편번호찾기">
			  </div>              
			  <div style="margin-bottom:5px;"></div>
			  <input type="text" class="form-control" id="mem_address" name="mem_address" placeholder="주소">
                 
           </form>
          </div>
    
          <!-- Modal footer -->
          <div class="modal-footer">
            <input type="button" class="btn btn-warning" data-bs-dismiss="modal"  onclick="memberInsert()" value="저장(가입)">
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
          </div>
    
        </div>
      </div>
    </div>
    <!-- ========================== [[ 회원가입 Modal ]] ========================== -->    
</body>
</html>
