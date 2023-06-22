<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부모페이지{부제:자식창에서부모함수호출방법}</title>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
//함수이름앞에 const를 사용할 수 있는 건 자바스크립트에서는 함수도 객체이다 -주소번지
	const popupOpen = () => {
		//실행문
		cmm_window_popup('sub.jsp','700','600','zipcodeForm');
	}
	//자식창에서 부모창 함수 호출하기 - 주제 실습목적
	window.call = function(zipcode, address){
		console.log('자식(sub.jsp)창에서 호출 : '  + zipcode + ', '+address);
		document.querySelector("#mem_zipcode").value = zipcode;
		document.querySelector("#mem_address").value = address;
	}
</script>
</head>
<body>
<a href="javascript:popupOpen()">팝업창열기</a>
<br/>
<input type="text" id="mem_zipcode"/><br>
<input type="text" id="mem_address"/>
</body>
</html>