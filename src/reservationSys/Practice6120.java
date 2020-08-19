package reservationSys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Practice6120 {

	public static void main(String[] args) {
		int input = 0;
		System.out.println("================================================");
		System.out.println("  遊園地チケット 予約システム");
		input = choice(new String[] {
				"どの項目を利用しますか？",
				"1. 予約  2. 予約取消  3. 問い合わせ"
		}, 1, 3);
		
		if (input >= 2) {
			System.out.println("================================================");
			System.out.println("電話でのお問い合わせをお願い致します。");
			System.out.println("TEL: 0xxx-555-xxx");
			System.out.println("");
			System.out.println("終了します。ご利用ありがとうございました。");
			return;
		}
		int[] price = new int[] {1500,1000,1100}, ticketCount = new int[price.length], ticketSum = new int[price.length];
		String[] name = new String[] {"通常", "子ども", "シニア"};
		HashMap<String, Coupon> coupon = new HashMap<String, Coupon>(){{
			put("newMonth500", Coupon.minus500yen);
			put("NM5", Coupon.minus500yen);
			put("off50", Coupon.per50off);
			put("offvocal", Coupon.per50off);
		}};
		List<Coupon> coupon_L = new ArrayList<Coupon>();
		int name_maxLen = 0, price_maxLen = 0, ticket_maxLen = 3, sum_maxLen = 1, sum = 0;
		// 名前の長さ
		for( int i = 0; i < name.length; i++ ) {
			name_maxLen = Math.max(name[i].length(), name_maxLen);
		}
		// 単価の長さ
		for( int i = 0; i < price.length; i++ ) {
			price_maxLen = Math.max((comma(price[i])+"円").length(), price_maxLen);
		}
		
		System.out.println("================================================");
		System.out.println("料金表");
		System.out.println(spacer("種類",name_maxLen)+" : 単価");
		for( int i = 0; i < name.length; i++ ) {
			System.out.println(spacer(name[i], name_maxLen) + " : " + comma(price[i]) + "円");
		}
		for( int i = 0; i < name.length; i++ ) {
			ticketCount[i] = askTicketCount("["+name[i]+"]チケット枚数？");
			ticketSum[i] = ticketCount[i] * price[i];
			ticket_maxLen = Math.max((comma(ticketCount[i])+"枚").length(), ticket_maxLen);
			sum_maxLen = Math.max((comma(ticketSum[i])+"円").length(), sum_maxLen);
			sum = sum + ticketSum[i];
		}
		
		boolean loop = true;
		while(loop) {
			System.out.println("クーポンコードを入力 [NEXT / N いずれかで次へ]");
			String tmp_co = inputString("");
			if( coupon.containsKey(tmp_co) ) {
				Coupon c = coupon.get(tmp_co);
				coupon_L.add(c);
				coupon.remove(tmp_co);
				System.out.println("クーポン適用: " + c.getName());
			} else {
				if( tmp_co.equalsIgnoreCase("next") || tmp_co.equalsIgnoreCase("n") ) {
					loop = false;
				} else {
					System.out.println("クーポンが見つからないか、使用済みです。");
				}
			}
			if( coupon.size() <= 0 ) {
				loop = false;
			}
			
		}
		System.out.println("================================================");
		System.out.println("最終確認");
		System.out.println(spacer("種類",name_maxLen)+" : "+spacer("小計",sum_maxLen)+": "+spacer("数量", 1)+" : 単価");
		for( int i = 0; i < name.length; i++ ) {
			System.out.println(
					spacer(name[i], name_maxLen) + " :" + spacerLeft(comma(ticketSum[i])+"円",sum_maxLen) + " :" +
							spacerLeft(comma(ticketCount[i])+"枚",ticket_maxLen)+" :" + spacerLeft(comma(price[i])+"円", price_maxLen)
			);
		}
		System.out.println("------------------------------------------------");
		if( coupon_L.size() > 0 ) {
			System.out.println(spacer("小計", name_maxLen)+" : " + comma(sum) + "円");
			System.out.println("------------------------------------------------");
			System.out.println("使用クーポン: ");
			for( Coupon c : coupon_L ) {
				sum = c.priceExec(sum);
				System.out.println(c.getName() + ": " + c.priceExecString());
			}
			System.out.println("------------------------------------------------");
		}
		System.out.println(spacer("合計", name_maxLen)+" : " + comma(sum) + "円");
		
		input = choice(new String[] {
				"以上でよろしいですか？",
				"1. はい  2. いいえ"
		}, 1, 2);
		
		if( input == 1 ) {
			System.out.println("ご予約ありがとうございます。");
			System.out.println("お問い合わせ番号: "+randomNumChar(7)+"-"+randomNumChar(5));
		} else {
			System.out.println("お手数ですが初めからやり直してください。");
		}
		
	}
	
	static String randomChar(int size) {
		String out = "", list = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for( int i = 0; i < size ; i++ ) {
			out += list.charAt((int) (Math.random() * list.length()));
		}
		return out;
	}
	static String randomNumChar(int size) {
		String out = "", list = "0123456789";
		for( int i = 0; i < size ; i++ ) {
			out += list.charAt((int) (Math.random() * list.length()));
		}
		return out;
	}
	
	static String comma(int num) {
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
	
	static int askTicketCount(String msg){
		try {
			Scanner sn = new Scanner(System.in);
			System.out.print(msg + " > ");
			String s = sn.next();
			int i = Integer.parseInt(s);
			if( i < 0 ) {
				System.out.println("0以上整数で指定してください。");
				return askTicketCount(msg);
			}
			return i;
		} catch(InputMismatchException e) {
			System.out.println("0以上整数で指定してください。");
			return askTicketCount(msg);
		}
	}
	
	static String spacer(String msg, int min_len) {
		String out = msg;
		for(; out.length() < min_len;) {
			out += "  ";
		}
		return out;
	}
	
	static String spacerLeft(String msg, int min_len) {
		String out = msg;
		for(int i = out.length(); i <= min_len; i++) {
			out = " "+out;
		}
		return out;
	}

	static int choice(String[] choiceMsg, int min, int max) {
		boolean ok = false, onceMore = false;
		int input = 0;
		while (!ok) {
			System.out.println("================================================");
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

	static void msg(String label, String trueMsg, String falseMsg, boolean data) {
		System.out.print(label + ": ");
		if (data) {
			System.out.println(trueMsg);
		} else {
			System.out.println(falseMsg);
		}
	}

	static boolean inputBoolean(String msg) {
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

	static int inputInt(String msg) {
		int i = 0;
		Scanner sn = new Scanner(System.in);
		try {
			System.out.print(msg + " > ");
			i = sn.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("整数で入力してください。");
			i = inputInt(msg);
		}
		
		return i;
	}
	
	static String inputString(String msg) {
		String i = "";
		Scanner sn = new Scanner(System.in);
		System.out.print(msg + " > ");
		return sn.next();
	}

}
