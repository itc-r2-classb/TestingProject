package calculator;

import java.util.Scanner;

public class main {

	/*
	 * 電卓(対話式)
	 */

	public static void main(String[] args) {
		int endFlg = 0, i = 0;
		int cnt = 0, input = 0, ok = 0;
		String op = "", num = "";
		i = inputInt("数値入力 > ");
		main: while (endFlg == 0) {
			ok=0;
			op="";
			while (!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("="))) {
				if (!op.equals("")) {
					System.out.println("演算子を入力してください");
				}
				System.out.print("演算子(+-*/=) > ");
				op = inputString();
			}
			if(op.equals("=")) {
				System.out.println("答え: " + i);
				op = ""; num = "";
				i = inputInt("数値入力 > ");
				continue;
			}
			System.out.print("数値入力 > ");
			while (ok == 0) {
				try {
					num = inputString();
					if (num.equals("=")) {
						System.out.println("答え: " + i);
						i = 0;
						continue main;
					} else {
						input = Integer.parseInt(num);
					}
					ok = 1;
				} catch (Exception e) {
					System.out.println("正しく入力してください");
				}
			}
			switch (op) {
			case "+":
				i = addition(i, input);
				break;
			case "-":
				i = subtraction(i, input);
				break;
			case "*":
				i = multiplication(i, input);
				break;
			case "/":
				if (input == 0) {
					System.out.println("ゼロ除算はできないんですよ。");
					System.out.println("Program End");
					return;
				}
				i = division(i, input);
				break;
			}
		}

	}

	static int addition(int addend, int augend) {
		return addend + augend;
	}

	static int subtraction(int reduction, int minuend) {
		return reduction - minuend;
	}

	static int multiplication(int multiplier, int multiplicand) {
		return multiplier * multiplicand;
	}

	static int division(int divisor, int dividend) {
		return divisor / dividend;
	}

	static int inputInt(String msg) {
		int i = 0, ok = 0;
		while (ok == 0) {
			try {
				Scanner sn = new Scanner(System.in);
				System.out.print(msg);
				i = Integer.parseInt(sn.next());
				ok = 1;
			} catch (Exception e) {
				System.out.println("数値で入力してください");
			}
		}
		return i;
	}

	static String inputString() {
		Scanner sn = new Scanner(System.in);
		return sn.next();
	}

}
