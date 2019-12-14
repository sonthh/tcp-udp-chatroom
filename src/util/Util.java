package util;

import java.util.StringTokenizer;

public class Util {

	public static void main(String[] args) {
		System.out.println(kiemTra(13));
	}

	public static int kiemTra(int x) {
		if (x == 0)
			return 1;
		if (x == 1) 
			return 2;
		
		int a = 0;
		int b = 1;
		for (int i = 0; i < x - 1; i++) {
			int temp = b;
			b = a + b;
			a = temp;
			if (b > x) {
				return -1;
			}
			if (b == x) {
				return i + 3;
			}
		}
		return -1;
	}

	public static String upperCase(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c >= 'a' && c <= 'z') {
				chars[i] = (char) (c - 32);
			}
		}
		return new String(chars);
	}

	public static String lowerCase(String str) {
		char[] chars = str.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c >= 'A' && c <= 'Z') {
				chars[i] = (char) (c + 32);
			}
		}
		return new String(chars);
	}

	public static int count(String str) {
		StringTokenizer tokenizer = new StringTokenizer(str, " ");
		int count = tokenizer.countTokens();
		return count;
	}
}
