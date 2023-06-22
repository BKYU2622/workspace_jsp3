package com.util;

public class HangulConversion {
	public static String toUTF(String en){
		if(en == null){
			return null;
		}
		try {
			return new String(en.getBytes("8859_1"),"utf-8");
		} catch (Exception e) {
			return en;
		}
	}
	public static String toEng(String ko){
		if(ko == null){
			return null;
		}
		try {
			return new String(ko.getBytes("euc-kr"),"8859_1");
		} catch (Exception e) {
			return ko;
		}
	}
	public static String toKor(String en){
		if(en == null)
			return null;
		try {
			return new String(en.getBytes("8859_1"),"euc-kr");
		} catch (Exception e) {
			return en;
		}
	}
}