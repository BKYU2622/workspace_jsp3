package com.example.demo.pojo1;

public class UpmuArray {

	public static void main(String[] args) {
		String noticeList = "/notice/noticeList.pj1";
		String noticeDetail = "/notice/noticeDetail.pj1";
		String noticeInsert = "/notice/noticeInsert.pj1";
		String noticeUpdate = "/notice/noticeUpdate.pj1";
		String noticeDelete = "/notice/noticeDelete.pj1";
		
		int endList = noticeList.lastIndexOf(".");
		int endDetail = noticeDetail.lastIndexOf(".");
		int endInsert = noticeInsert.lastIndexOf(".");
		int endUpdate = noticeUpdate.lastIndexOf(".");
		int endDelete = noticeDelete.lastIndexOf(".");
		System.out.println(endList);//18
		noticeList = noticeList.substring(1,endList); // 1은 슬래쉬를 날리고 뒤에 18은 .pj1을 날리기 위함
		String upmuList[] = noticeList.split("/");
		for(int i=0;i<upmuList.length;i++) {
			System.out.println("upmuList["+i+"] = "+upmuList[i]);
		}
		System.out.println(noticeList);
		noticeDetail = noticeDetail.substring(1,endDetail);
		String upmuDetail[] = noticeDetail.split("/");
		for(int i=0;i<upmuDetail.length;i++) {
			System.out.println("upmuDetail["+i+"] = "+upmuDetail[i]);
		}		
		System.out.println(endDetail);//20
		// substring은 문자열을 자를때 사용한다 - 자르는 구간이 필요함
		noticeInsert = noticeInsert.substring(1,endInsert);// 1, 18
		String upmuInsert[] = noticeInsert.split("/");
		for(int i=0;i<upmuInsert.length;i++) {
			System.out.println("upmuInsert["+i+"] = "+upmuInsert[i]);
		}			
		// .pj1으로 끝나는 요청만 FrontMVC를 경유하도록 한다 - 컨벤션 - 약속
		System.out.println(endInsert);//20 -> substring(1, 20) -> .pj1을 버리고 간다
		System.out.println(endUpdate);//20
		System.out.println(endDelete);//20
	}

}