<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업창{부모창:parentPage}</title>
<script type="text/javascript">
	const zipcodeChoice = (zipcode, address) => {
		//opener뒤에 온 call함수는 부모화면에 정의되어 있는 함수 이어야함.
		opener.call(zipcode, address);//부모창의 자바스크립트 함수 호출하기. 파라미터도 넘겨볼까
		self.close();
	}
</script>
</head>
<body>
<a href="javascript:zipcodeChoice('215778','서울시 금천구 가산동 567')">215778</a>
<span>서울시 금천구 가산동 567</span>
</body>
</html>