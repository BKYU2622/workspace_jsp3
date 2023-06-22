<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/main.css?abc">
</head>
<body>
	<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->

	<!-- body start    -->
	<div class="container">

		<form id="f_login" action="/auth2/login.pj3" method="post">
			<div class="mb-3 mt-3">
				<label for="mem_email" class="form-label">Email:</label> <input
					type="text" class="form-control" id="mem_email"
					placeholder="Enter Email" name="mem_email">
			</div>
			<div class="mb-3">
				<label for="pwd" class="form-label">Password:</label> <input
					type="password" class="form-control" id="mem_pw"
					placeholder="Enter password" name="mem_pw">
			</div>
			<button type="button" id="btn-login" class="btn btn-primary">로그인</button>
			<script>
				const btnLogin = document.querySelector("#btn-login")
				btnLogin.addEventListener('click', (e) => {
					//alert('11');
					document.querySelector("#f_login").submit();
				})
			</script><!-- id는 내껄로, img는 파일에 넣었던거 -->
			<a
				href="https://kauth.kakao.com/oauth/authorize?client_id=07a1c647a84d2693791232a3e335f25f&redirect_uri=http://localhost:9000/auth/kakao/callback&response_type=code"><img
				height="38px" src="/images/kakao_login/ko/kakao_login_medium_narrow.png"></a>
			<button type="button" class="btn btn-success" data-bs-toggle="modal"
				data-bs-target="#memberForm">회원가입</button>
		</form>

	</div>
	<!-- body end    -->

	<!-- footer start -->
	<%@include file="/include/gym_footer.jsp"%>
	<!-- footer end    -->
</body>