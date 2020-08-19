package mobPrograming_Suica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Mod_1_LIMITED {

	public static void main(String[] args) {
		/*
		 * Suica問題 学習範囲内縛り
		 * メソッドの使用禁止
		 * boolean型の使用禁止
		 * Map, Listが必須
		 * UIは前作を引き継ぐ(なるべく)
		 * ■□■□■□■□■□■□■□■
		 * ■□■□■□未完成□■□■□■
		 * ■□■□■□■□■□■□■□■
		 */
		Scanner sn = new Scanner(System.in);
		// 駅の追加
		HashMap<String, Integer> stationMap = new HashMap<>();
		stationMap.put("千葉", 0);
		stationMap.put("西千葉", 1);
		stationMap.put("稲毛", 2);
		stationMap.put("新検見川", 3);
		stationMap.put("幕張本郷", 4);
		stationMap.put("幕張", 5);
		stationMap.put("津田沼", 6);
		stationMap.put("東船橋", 7);
		stationMap.put("船橋", 8);
		stationMap.put("西船橋", 9);
		
		// 現在地(初期地点を設定)
		String currentStation = "稲毛";
		
		// チャージ金額の選択肢
		int[] price = new int [] {
				1000,2000,3000,5000,10000
		};
		
		int suica = 0;
		
		try {
			// チャージ処理  ======================
			System.out.println("==============================");
			System.out.println("チャージ金額の選択:");
			// ======================
			// チャージ金額のリスト表示処理
			// これを使うとprice配列をすべて表示できますヨ
			List<String> st_list = new ArrayList<>();
			String t = "";
			for( int i = 0 ; i < price.length; i++) {
				if( i % 3 == 0 ) {
					if( !t.equalsIgnoreCase("") /* 文字なしの場合 */ ) {
						st_list.add(t);
					}
					t = (i+1) + ": " + price[i] + "円  ";
				} else {
					t = t + (i+1) + ": " + price[i] + "円  ";
				}
			}
			st_list.add(t);
			// ループ回数増えるけど整形してから表示したほうがいい？
			for(String tmp : st_list) {
				System.out.println(tmp);
			}
			// ======================
			System.out.print("番号[1 - "+price.length+"] > ");
			int input = sn.nextInt();
			if( input == 9999 ) {
				suica = -1;
			} else {
				suica = suica + price[input - 1];
			}
			// できれば範囲外の数値の場合か、
			// 文字である場合、もう一度入力させたりしたいけど
			// 煩雑になるので。。。
			// チャージ処理[END]  ======================
			
			// 以降ループ。
			int end = 0;
			
			// endが0である + suicaが-1でない
			// (もし9999円になったらおしまいなのであえて-1に)
			// (1円ないから起きないけどサ)
			while( end == 0 && suica != -1 ) {
				if( suica == -1 ) {
					// 安全装置的アレ
					// いれとくと安心するかも   しないか。
					break;
				}
				System.out.println();
				System.out.println("チャージ金額: " + suica + "円");
				System.out.println("現在地: " + currentStation);
				// 現在地を除いたすべての駅を取得
				List<String> sta = new ArrayList<>();
				for( String s : stationMap.keySet() ) {
					if( !s.equalsIgnoreCase(currentStation) ) {
						sta.add(s);
					}
				}
				// 駅のリスト表示の組み立て
				// 0: "1: 千葉  2: 西千葉",
				// 1: "3: 稲毛"
				st_list = new ArrayList<>();
				t = "";
				int i = 0;
				for( String s : sta ) {
					if( i % 3 == 0 ) {
						if( !t.equalsIgnoreCase("") ) {
							st_list.add(t);
						}
						t = (i+1) + ": " + s + "  ";
					} else {
						t = t + (i+1) + ": " + s + "  ";
					}
					i++;
				}
				st_list.add(t);
				System.out.println("行き先を選んでください");
				for(String tmp : st_list) {
					System.out.println(tmp);
				}
				System.out.print("番号[1 - "+price.length+"] > ");
				input = sn.nextInt();
				String targetStation = sta.get(input - 1);
				// 選択肢から選択させるものでは
				// できるだけ文字を入力させずに
				// 数値で処理したほうが扱いやすいかも...
				
				// ===========================
				// 移動処理
				
			}
		} catch ( InputMismatchException e ) {
			System.out.println("数値に文字を入れることはできません。");
			System.out.println("[終了します。]");
		} catch ( Exception e ) {
			System.out.println("何らかの例外が発生しました。");
			System.out.println("[終了します。]");
		}
		System.out.println("[EoP]");
	}

}
