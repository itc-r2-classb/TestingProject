package mobPrograming_Suica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Mod_1 {
	
	public static void main(String[] args) {
		int suica = charge(), input = 0, currentStation = 2;
		boolean travelEnd = false;
		//
		// [0]   [1]   [2]   [3]
		//    [0]   [1]   [2]
		//
		String[] station = new String[] {
				"千葉", "西千葉", "稲毛", 
				"新検見川", "幕張本郷", "幕張", 
				"津田沼", "東船橋", "船橋", "西船橋"
		};
		int[] price = new int[] {
				200,200,160,
				160,160,160,
				180,180,180
		};
		while( !travelEnd ) {
			if( suica == -1) {
				travelEnd = true;
				break;
			}
			System.out.println();
			System.out.println("チャージ金額: " + comma(suica) + " 円");
			System.out.println("現在地: " + station[currentStation]);
			List<Integer> sta = new ArrayList<>();
			for( int i = 0; i < station.length; i++ ) {
				if( currentStation != i ) {
					sta.add(i);
				}
			}
			List<String> list = new ArrayList<>();
			String t = "";
			for( int i = 0 ; i < sta.size(); i++) {
				if( i % 3 == 0 ) {
					if( !t.equalsIgnoreCase("") ) {
						list.add(t);
					}
					t = (i+1) + ": " + station[sta.get(i)] + "  ";
				} else {
					t = t + (i+1) + ": " + station[sta.get(i)] + "  ";
				}
			}
			list.add(t);
			System.out.println("行き先を選んでください");
			for(String tmp : list) {
				System.out.println(tmp);
			}
			input = choice(new String[0], 1, sta.size());
			if( input == -1 ) {
				travelEnd = true;
				break;
			}
			// 0始まりなので -1 必須
			int targetStation = sta.get(input - 1);
			
			System.out.println();
			System.out.println("行先: " + station[targetStation]);
			
			// 経路運賃計算
			int paymentValue = 0;
			if( currentStation < targetStation ) {
				// 西船橋方面
				for( int i = currentStation; i < targetStation; i++ ) {
					paymentValue = paymentValue + price[i];
					if( paymentValue > suica ) {
						int c = charge(true);
						if( c == -1) {
							travelEnd = true;
							break;
						}
						suica = suica + c;
					}
					System.out.println(station[i] + " --["+ price[i] +"円]--> " + station[i+1] + " :" + comma(paymentValue) + "円");
					currentStation = i+1;
				}
			} else {
				// 千葉方面
				for( int i = currentStation; i > targetStation; i-- ) {
					paymentValue = paymentValue + price[i-1];
					if( paymentValue > suica ) {
						int c = charge(true);
						if( c == -1) {
							travelEnd = true;
							break;
						}
						suica = suica + c;
					}
					System.out.println(station[i] + " --["+ price[i-1] +"円]--> " + station[i-1] + " :" + comma(paymentValue) + "円");
					currentStation = i-1;
				}
			}
			
			System.out.println();
			System.out.println("運賃: " + comma(paymentValue) + "円");
			suica = suica - paymentValue;
		}
		System.out.println("終了します。");
		System.out.println("[PROGRAM END]");
	}

	private static int charge() {
		return charge(false);
	}
	
	private static int charge(boolean isNotEnough) {
		int[] price = new int [] {
				1000,2000,3000,5000,10000
		};
		List<String> list = new ArrayList<>();
		String t = "";
		for( int i = 0 ; i < price.length; i++) {
			if( i % 3 == 0 ) {
				if( !t.equalsIgnoreCase("") ) {
					list.add(t);
				}
				t = (i+1) + ": " + comma(price[i]) + "円  ";
			} else {
				t = t + (i+1) + ": " + comma(price[i]) + "円  ";
			}
		}
		list.add(t);
		printSikiri1();
		if( isNotEnough ) {
			System.out.println("残高不足です。");
		}
		System.out.println("チャージ金額を選んでください");
		for(String tmp : list) {
			System.out.println(tmp);
		}
		int i = choice(new String[0], 1, price.length);
		if( i == -1 ) {
			return -1;
		}
		return price[i - 1];
	}

	public static void printSikiri1() {
		System.out.println("========================================================");
	}
	public static void printSikiri2() {
		System.out.println("--------------------------------------------------------");
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
	
	public static int choice(String[] choiceMsg, int min, int max) {
		boolean ok = false, onceMore = false;
		int input = 0;
		while (!ok) {
			printSikiri1();
			if (onceMore) {
				System.out.println("[ERROR] 範囲内で入力ください");
			}
			onceMore = true;
			for (String s : choiceMsg) {
				System.out.println(s);
			}
			input = inputInt("番号[" + min + " - " + max + "]");
			if (min <= input && input <= max) {
				ok = true;
			} else if(input == 9999) {
				return -1;
			}
		}
		return input;
	}
	
	public static int inputInt(String msg) {
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

}
