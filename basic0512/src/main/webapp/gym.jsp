<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>터짐</title>
<link rel="stylesheet" href="./css/gym.css?1">
<%@ include file="/common/bootstrap_common.jsp" %>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<!-- 95ca84e313edbe0efe434174e0e40202  -->
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=95ca84e313edbe0efe434174e0e40202"></script>
</head>
<body>
<!--======================== header start  ========================-->
<%@ include file="/include/gym_header.jsp" %>
<!--======================== header end     ========================-->
<!--======================== body start      ========================-->
<div class="container">
	<div class="main_header"></div>
	<div class="main">
		<div>이벤트존</div>
		<hr style="height:2px"/>
		<div>추천수업존</div>
		<hr style="height:2px"/>
		<div class="mapwrap">
			<div id="map" class="map"></div>
			<!-- <div id="map" style="width:500px;height:400px;"></div> -->
			<script type="text/javascript">
				//console.log(document.getElementById("map"));
				//document.querySelector("#map");
				const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
				// 배열 선언 - 마커를 여러개 표시하고 싶은 경우일 때 참고
				// 자바스크립트 배열 복습 -> 깊은 복사(새로운 배열 생성해서 사용함)와 얕은 복사(원본사용함)
				const positions = [
					{
						content: "<div style='padding:5px;'><h4>구디아카데미</h4><img src='./images/gudi.png'></div>",
						latlng: new kakao.maps.LatLng(37.4765002, 126.8799586)
					}
				];
				const options = { //지도를 생성할 때 필요한 기본 옵션
					center: positions[0].latlng,
					level: 4 //지도의 레벨(확대, 축소 정도)
				};
				
				const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴		
				
				//제목 :  카카오 맵에 마커 표시하기
				// 지도를 클릭한 위치에 표출할 마커입니다
				const marker = new kakao.maps.Marker({ 
					map:map, //마커를 표시할 지도
				    // 지도 중심좌표에 마커를 생성합니다 
				    position: positions[0].latlng //마커의 위치 잡아주기
				}); 
				// 마커에 표시할 인포윈도우를 생성하기
				const infoWindow = new kakao.maps.InfoWindow({
					//생성자 안에 좌중괄호 우중괄호를 붙이면 실행문 작성가능함
					content: positions[0].content //인포윈도우(다이얼로그창, 팝업창)에 표시할 내용
				});//아래코드에서 클로저를 사용할 때는 반드시 세미콜론 붙일 것.
				
				// 마커에 이벤트를 등록하는 함수 구현
				//구현하는 즉시 실행시키기 위해서 클로저 사용함
				(function(marker, infoWindow){
					//마커에 mouseover 이벤트를 등록하고 마우스 오버시에 인포윈도우를 표시함
					//@param:marker는 이벤트 소스이다 
					//@param:marker에 적용할 수 있는 이벤트 핸들러 이름임
					//@param:콜백함수-네가 마커에 마우스 오버 시키면 내가 실행을 약속할께
					kakao.maps.event.addListener(marker, 'mouseover', function(){
						infoWindow.open(map, marker);
					});
					//마커에 mouseout 이벤트를 등록하고 마우스 아웃시에 인포윈도우를 닫음
					kakao.maps.event.addListener(marker, 'mouseout', function(){
						infoWindow.close();
					});
					
				})(marker, infoWindow);//여기 괄호가 함수를 호출하는 코드임
				
				// 지도에 마커를 표시합니다
				marker.setMap(map);
	
				
			</script>			
			
		</div>
		
		<hr style="height: 2px" />
		<table class="table" style="minWidth: 700px">
			<tbody style="border: 1px solid lightgray">
				<tr>
					<td style="borderRight: 1px solid lightgray">주소</td>
					<td>서울특별시 금천구 가산디지털2로 95 KM타워 3층 (T: 02-818-7950)</td>
				</tr>
				<tr>
					<td style="borderRight: 1px solid lightgray">버스</td>
					<td>디지털3단지 사거리 정류장<br /> 지선 5536/5714 간선 503/504 일반 21
					</td>
				</tr>
				<tr>
					<td style="borderRight: 1px solid lightgray">지하철</td>
					<td>지하철 1, 7호선 가산디지털단지역 5번출구 200m</td>
				</tr>
			</tbody>
		</table>		
		
	</div>
	<div class="main_footer"></div>
</div>
<!--======================== body end        ========================-->
<!--======================== footer start  ========================-->
<%@ include file="/include/gym_footer.jsp" %>
<!--======================== footer end     ========================-->
</body>
</html>