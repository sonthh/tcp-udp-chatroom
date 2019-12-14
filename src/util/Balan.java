package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Balan {

	public static boolean isNumeric(String strNum) {
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	public static int getPriority(String ch) {
		ch = ch.toLowerCase();
		int p = 0;
		if (ch.equals("+") || ch.equals("-")) {
			return p;
		}
		p++;
		if (ch.equals("*") || ch.equals("/")) {
			return p;
		}
		p++;
		if (ch.equals("sin") || ch.equals("cose") || ch.equals("tan") || ch.equals("asin") || ch.equals("acos")
				|| ch.equals("^")) {
			return p;
		}
		return 0;
	}

	public static boolean isOperator(String ch) {
		if (ch.equals("+") || ch.equals("-") || ch.equals("*") || ch.equals("/") || ch.equals("sin") || ch.equals("cos")
				|| ch.equals("tan") || ch.equals("asin") || ch.equals("acos") || ch.equals("^")) {
			return true;
		}
		return false;
	}

	// String input = "1+2*3+sin(30)+2";
	// System.out.println(input);

	public static void main(String[] args) {
		//System.out.println(calculate("1+2*3+sin(30)+2^2"));
		System.out.println(calculate("1+2*3+sin(30)+(2^2)^2"));

	}

	public static String calculate(String input) {
		Stack<String> k = new Stack<>();
		List<String> elements = new ArrayList<>();
		char[] a = input.toCharArray();
		for (int i = 0; i < a.length;) {
			String str = "";

			// la so
			if ((a[i] >= '0' && a[i] <= '9') || a[i] == '.') {
				do {
					str = str.concat(a[i] + "");
					i++;
					if (i >= a.length)
						break;
				} while ((a[i] >= '0' && a[i] <= '9') || a[i] == '.');
			}
			// la toan tu sin cos asin acos
			else if (a[i] >= 'a' && a[i] <= 'z') {
				do {
					str = str.concat(a[i] + "");
					i++;
					if (i >= a.length)
						break;
				} while (a[i] >= 'a' && a[i] <= 'z');
			} else if (a[i] == '+' || a[i] == '-' || a[i] == '*' || a[i] == '/' || a[i] == '(' || a[i] == ')'
					|| a[i] == '^') {
				// la toan tu
				str = str.concat(a[i] + "");
				i++;
			}

//			System.out.println(str);
			if (isNumeric(str)) {
				elements.add(str);
			}

			if (str.equals("(")) {
				k.push(str);
			}

			if (isOperator(str)) {
				if (!k.isEmpty()) {
					// cau lenh nay` kiem tra do uu tien giua cac toan tu
					if (isOperator(k.peek()) // top cua stack la toan tu thi so sanh do uu tien toan tu
							&& (getPriority(str) <= getPriority(k.peek()))) {
						do {
							String m = k.pop();
							elements.add(m);
						} while (!k.isEmpty() && isOperator(k.peek()) && getPriority(str) <= getPriority(k.peek()));
						k.push(str);
					} else {
						k.push(str);
					}
				} else {
					k.push(str);
				}
			}
			if (str.equals(")")) {
				String d;
				while (true) {
					d = k.pop();
					// pop luon ca dau (
					if (d.equals("("))
						break;
					elements.add(d);
				}
			}

		}
		while (!k.isEmpty()) {
			String m = k.pop();
			elements.add(m);
		}

		double[] data = new double[100];
		int idxData = 0;
		for (int i = 0; i < elements.size(); i++) {
			String element = elements.get(i);

			if (isNumeric(element)) {
				data[idxData++] = Double.parseDouble(element);
			}

			if (isOperator(element)) {
				if (element.equals("+")) {
					double temp = data[idxData - 1] + data[idxData - 2];
					data[idxData - 2] = temp;
					idxData--;
				} else if (element.equals("-")) {
					double temp = data[idxData - 2] - data[idxData - 1];
					data[idxData - 2] = temp;
					idxData--;
				} else if (element.equals("*")) {
					double temp = data[idxData - 1] * data[idxData - 2];
					data[idxData - 2] = temp;
					idxData--;
				} else if (element.equals("/")) {
					double temp = data[idxData - 2] / data[idxData - 1];
					data[idxData - 2] = temp;
					idxData--;
				} else if (element.equals("^")) {
					double temp = Math.pow(data[idxData - 2], data[idxData - 1]);
					data[idxData - 2] = temp;
					idxData--;
				} else if (element.equals("sin")) {
					double temp = data[idxData - 1];
					data[idxData - 1] = Math.sin(temp * Math.PI / 180);
				} else if (element.equals("cos")) {
					double temp = data[idxData - 1];
					data[idxData - 1] = Math.cos(temp * Math.PI / 180);
				} else if (element.equals("asin")) {
					double temp = data[idxData - 1];
					data[idxData - 1] = Math.asin(temp) * 180 / Math.PI;
				} else if (element.equals("acos")) {
					double temp = data[idxData - 1];
					data[idxData - 1] = Math.acos(temp) * 180 / Math.PI;
				}
			}
		}
		return data[0] + "";
	}

}
