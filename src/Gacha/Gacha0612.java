package Gacha;

import lib.Utility;

public class Gacha0612 {

	// 各レアリティ確率
	static double SSRPer = 0.03, SRPer = 0.15, RPer = 0.82;
	//	,NPer = 1 - ( SSRPer + SRPer + RPer );

	static double SSR_PickUpPer = 0.7, SSR_NormalPer = 0.3;

	static int gachaPrice = 300, playCount = 0, resetCount = 0, allPlayCount = 0, review_flag = 0,
			SSRPickCount = 0, SSRCount = 0, SRCount = 0, RCount = 0;

	static boolean VIPMode = false;

	static String gachaName = "[ここにガチャ名を入力]";

	public static void main(String[] args) {

		int gameSelect = Utility.choice(new String[] {
				"ゲームを選択してください。",
				"1. グランブルーファンタジー",
				"2. iM@S スターライトステージ",
				"3. モンスターストライク",
				"4. パズル＆ドラゴンズ"
		}, 1, 4);

		String[] gachaNameList = new String[] {
				"グランブルーファンタジー ガシャ",
				"[デレステ] シンデレラフェス ガシャ",
				"モンスターストライク ガシャ",
				"パズル＆ドラゴンズ ガシャ"
		};

		int[] priceList = new int[] {
				300, 250, 200, 400
		};

		try {
			gachaPrice = priceList[gameSelect - 1];
			gachaName = gachaNameList[gameSelect - 1];
		} catch (Exception e) {
			System.out.println("ゲーム選択時の初期設定でエラーが発生しました。[終了します]");
			System.out.println("[参照配列の参照要素が存在しません]");
			return;
		}
		boolean really = true;
		while (really) {
			really = false;
			Utility.printSikiri1();
			System.out.println("     " + gachaName);
			System.out.println("        1回: " + Utility.comma(gachaPrice) + "円");
			Utility.printSikiri2();
			printOutputPercent();
			Utility.printSikiri1();
			System.out.println("10連ガチャの場合、1枠はSR以上確定です。");

			boolean gameEnd = false;
			while (!gameEnd) {

				int input = Utility.inputInt("回数を入力ください");
				int tenGacha = input / 10,
						// 1の位を消す為に/10*10をしてる(int型は整数のみで小数は受け入れられないため)
						singleGacha = input - (input / 10 * 10);
				System.out.println("10連: " + tenGacha + ", 単発: " + singleGacha);
				// 10連
				if (tenGacha > 0) {
					Utility.printSikiri1();
					System.out.println("10連結果");
				}
				int newSSRPickCount = 0, newSSRCount = 0, newSRCount = 0, newRCount = 0;
				int count = 0;
				for (int i = 0; i < tenGacha; i++) {
					Rarity[] res = GachaPlay(9);
					for (Rarity r : res) {
						if (review_flag == 0 && (r.equals(Rarity.SSR) || r.equals(Rarity.SSR_PICKUP))) {
							review_flag = 1;
						}
						count++;
						System.out.println(count + "回目のガチャ: " + r);
						switch (r) {
						case SSR:
							SSRCount++;
							newSSRCount++;
							break;
						case SSR_PICKUP:
							SSRPickCount++;
							newSSRPickCount++;
							break;
						case SR:
							SRCount++;
							newSRCount++;
							break;
						case R:
							RCount++;
							newRCount++;
							break;
						}
					}
					count++;
					Rarity r = GachaPlay_SR();
					switch (r) {
					case SSR:
						SSRCount++;
						newSSRCount++;
						break;
					case SSR_PICKUP:
						SSRPickCount++;
						newSSRPickCount++;
						break;
					case SR:
						SRCount++;
						newSRCount++;
						break;
					case R:
						RCount++;
						newRCount++;
						break;
					}
					System.out.println(count + "回目のガチャ: " + r);
					System.out.println();
					if (review_flag == 1) {
						pleaseReviewMe();
					}
				}
				// 単発
				if (singleGacha > 0) {
					Utility.printSikiri1();
					System.out.println("単発結果");
				}
				for (int i = 0; i < singleGacha; i++) {
					Rarity r = GachaPlay();
					count++;
					System.out.println(count + "回目のガチャ: " + r);
					switch (r) {
					case SSR:
						SSRCount++;
						newSSRCount++;
						break;
					case SSR_PICKUP:
						SSRPickCount++;
						newSSRPickCount++;
						break;
					case SR:
						SRCount++;
						newSRCount++;
						break;
					case R:
						RCount++;
						newRCount++;
						break;
					}
					if (review_flag == 0 && (r.equals(Rarity.SSR) || r.equals(Rarity.SSR_PICKUP))) {
						pleaseReviewMe();
					}
				}
				if (resetCount >= 3 && !VIPMode) {
					SSRPer = 0.01;
					SRPer = 0.17;
					RPer = 0.82;
					VIPMode = true;
				}
				System.out.println("SSR: " + (newSSRCount + newSSRPickCount) + " (PickUp: " + newSSRPickCount + ", 通常: "
						+ newSSRCount + "), SR: " + newSRCount + ", R: " + newRCount);
				Utility.printSikiri2();
				System.out.println("手持ちレアリティ別カード数:");
				System.out.println("SSR: " + (SSRCount + SSRPickCount) + " (PickUp: " + SSRPickCount + ", 通常: "
						+ SSRCount + "), SR: " + SRCount + ", R: " + RCount);
				System.out.println("現在の総プレイ回数: " + allPlayCount + ", プレイ回数: " + playCount + ", リセット回数: " + resetCount);
				System.out.println("1回 * " + gachaPrice + "円: " + Utility.comma(allPlayCount * gachaPrice) + "円");
				if (!Utility.inputBoolean("続けますか？")) {
					gameEnd = true;
					break;
				}
				Utility.printSikiri1();
				printOutputPercent();
				Utility.printSikiri1();
			}
			if (gameSelect == 2) {
				if (VIPMode || allPlayCount <= 300) {
					int input = Utility.choice(new String[] {
							"？？？「もっと引いていきませんか？」",
							"1. はい(引く)   0. いいえ(引かない)"
					}, 0, 1);
					if (input == 1) {
						System.out.println("？？？「ありがとうございます♪」");
						Utility.sleep(1500);
						really = true;
					} else {
						if (VIPMode) {
							input = Utility.choice(new String[] {
									"？？？「もっと...引いてくれないんですか...？」",
									"1. はい(引く)   0. いいえ(引かない)"
							}, 0, 1);
							if (input == 1) {
								System.out.println("？？？「ありがとうございます💛」");
								Utility.sleep(1500);
								really = true;
							} else {
								System.out.println("？？？「そうですか... また...来てくださいね！プロデューサーさん♪」");
							}
						} else {
							System.out.println("？？？「そうですか... また、来てくださいね！プロデューサーさん♪」");
						}
					}
				} else {
					System.out.println("？？？「また来てくださいね！プロデューサーさん♪」");
				}
			} else {
				System.out.println("ご利用ありがとうございました。");
			}
		}

		System.out.println("[PROGRAM END]");
	}

	static void printOutputPercent() {
		System.out.println("出現確率一覧");
		System.out.println("SSR: " + (SSRPer * 100) + "%( PickUp: " + (SSR_PickUpPer * 100) + "% / 通常: "
				+ (SSR_NormalPer * 100) + "% )");
		System.out.println("SR: " + (SRPer * 100) + "%, R: " + (RPer * 100) + "%");
	}

	static void pleaseReviewMe() {
		System.out.println("はじめてのSSRおめでとうございます！");
		System.out.println("このアプリはいかがですか？ご感想をお願いします。");
		String msg = Utility.inputString("");
		System.out.println("ありがとうございました。2秒後に再開します。");
		Utility.sleep(2000);
		review_flag = -1;
	}

	static Rarity GachaPlay_SR() {
		playCount++;
		allPlayCount++;
		if (checkPlayCountUpper()) {
			countReset();
			return Rarity.SSR_PICKUP;
		}
		double rnd = Math.random();
		if (rnd < SSRPer) {
			rnd = Math.random();
			if (rnd < SSR_PickUpPer) {
				return Rarity.SSR_PICKUP;
			} else {
				return Rarity.SSR;
			}
		} else {
			return Rarity.SR;
		}
	}

	static Rarity GachaPlay() {
		playCount++;
		allPlayCount++;
		if (checkPlayCountUpper()) {
			countReset();
			return Rarity.SSR_PICKUP;
		}
		double rnd = Math.random();
		if (rnd < RPer) {
			return Rarity.R;
		} else if (rnd < (SRPer + RPer)) {
			return Rarity.SR;
		} else if (rnd < (SSRPer + SRPer + RPer)) {
			rnd = Math.random();
			if (rnd < SSR_PickUpPer) {
				return Rarity.SSR_PICKUP;
			} else {
				return Rarity.SSR;
			}
		} else {
			return Rarity.N;
		}
	}

	static Rarity[] GachaPlay(int count) {
		Rarity[] out = new Rarity[count];
		for (int i = 0; i < out.length; i++) {
			out[i] = GachaPlay();
		}
		return out;
	}

	static boolean checkPlayCountUpper() {
		return playCount >= 300;
	}

	static void countReset() {
		playCount = 0;
		resetCount++;
	}
}
