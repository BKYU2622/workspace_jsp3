package com.example.demo.pojo1;

public class UrlPattern {

	public static void main(String[] args) {
		String url = "/notice/noticeList.pj1";
		
		// split의 소유주는 String클래스이고 리턴타입은 String[]이고, 파라미터에는 String을 넣어야 한다
		// 파라미터와 리턴타입을 맞추는 것 만으로도 컴파일을 완성함 - 시행착오법 적용해 볼 수 있다 - 권장하지는 않음
		String upmu[] = url.split("/"); // API를 볼 수 있어야 함
		// 향상된 for문은 전체를 모두 출력하거나 돌릴 때 사용
		// 첫 번째 파라미터에는 제네릭 타입이나, 배열의 내부 타입, 두 번째는 배열이나, 컬렉션의 주소번지(변수명)
		for(String str:upmu) {	
			System.out.println(str);
			if ("notice".equals(str)) {
				// 인스턴스화 -> 개발자가 직접함 -> 자원관리도 개발자가 해야 함 -> 그러나 스프링은 자동으로 해줌
//				NoticeController noticeController = new NoticeController();
			}
			else if ("board".equals(str)) {
//				BoardController boardController = new BoardController();
			}
		}
		
	}

	
}
