package com.example.demo.pojo1;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class AddressBookDialog extends JDialog {
	// 선언부 
	// 선언만 함 - 아직 메모리에 로딩이 안 됨 - 누릴(호출) 수 없음
	// 그럼에도 호출하면 NullPointerException
	AddressBook aBook = null; 
	//AddressBook aBook = new AddressBook();
	
	// 생성자
	public AddressBookDialog() { 
		initDisplay();
	}
	
	// 화면 처리부
	public void initDisplay() {
		this.setTitle("나는 다이얼로그창");
		this.setSize(300, 500);
		this.setVisible(false);
	}
	/*
	public static void main(String[] args) {
		AddressBookDialog aDialog = new AddressBookDialog(); // 생성자 호출하기
		aDialog.setVisible(true);
		
	}
	*/
}
