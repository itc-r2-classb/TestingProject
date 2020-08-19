package lib;

import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Utility {
	public static void printSikiri1() {
		System.out.println("========================================================");
	}
	public static void printSikiri2() {
		System.out.println("--------------------------------------------------------");
	}
	
	public static void sleep(long mili) {
		try {
			Thread.sleep(mili);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public static String comma(int num) {
		String base = "";
		int i = 0;
		List<String> list = Arrays.asList(String.valueOf(num).split(""));
		Collections.reverse(list);
		for( String s : list ) {
			if(i >= 3) {
				i = 0;
				base = "," + base;
			}
			base = s + base;
			i++;
		}
		return base;
	}
	
	public static String spacer(String msg, int min_len) {
		String out = msg;
		for(; out.length() < min_len;) {
			out += "  ";
		}
		return out;
	}
	
	public static String spacerLeft(String msg, int min_len) {
		String out = msg;
		for(int i = out.length(); i <= min_len; i++) {
			out = " "+out;
		}
		return out;
	}
	
	public static int choice(String[] choiceMsg, int min, int max, boolean isLine) {
		boolean ok = false, onceMore = false;
		int input = 0;
		while (!ok) {
			if(isLine) {
				printSikiri1();
			}
			if (onceMore) {
				System.out.println("[ERROR] 範囲内で入力ください");
			}
			onceMore = true;
			for (String s : choiceMsg) {
				System.out.println(s);
			}
			input = inputInt("番号[" + min + " - " + max + "]");
			if (min <= input && input <= max)
				ok = true;
		}
		return input;
	}

	public static int choice(String[] choiceMsg, int min, int max) {
		return choice(choiceMsg, min, max, true);
	}

	public static void msg(String label, String trueMsg, String falseMsg, boolean data) {
		System.out.print(label + ": ");
		if (data) {
			System.out.println(trueMsg);
		} else {
			System.out.println(falseMsg);
		}
	}

	public static boolean inputBoolean(String msg) {
		int i = 0;
		Scanner sn = new Scanner(System.in);
		try {
			System.out.print(msg + "(1:Y,0:N) > ");
			i = sn.nextInt();
			while (i < 0 || i > 1) {
				System.out.println("0か1で入力してください");
				System.out.print(msg + "(1:Y,0:N) > ");
				i = sn.nextInt();
			}
		} catch (InputMismatchException e) {
			System.out.println("整数で入力してください。");
			return inputBoolean(msg);
		}
		if (i == 1)
			return true;
		return false;
	}
	
	public static int inputInt(String msg) {
		int i = 0;
		Scanner sn = new Scanner(System.in);
		boolean ok = false;
		while(!ok) {
			try {
				System.out.print(msg + " > ");
				String t = sn.next();
				i = Integer.parseInt(t);
				ok = true;
			} catch (Exception e) {
				System.out.println("整数で入力してください。");
			}
		}
		return i;
	}
	
	public static String inputString(String msg) {
		String i = "";
		Scanner sn = new Scanner(System.in);
		System.out.print(msg + " > ");
		return sn.nextLine();
	}
	
}
