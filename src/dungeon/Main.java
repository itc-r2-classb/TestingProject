package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dungeon.LevelChange.VerticalType;

public class Main {

	// mapX 横, mapY 縦, mapRotate 方向 上:0 右:1 下:2 左:3
	static int mapX = 0, mapY = 0, mapRotate = 0, input = 0, maxHP = 0, nowHP = 0, attack = 0, defence = 0, exp = 0,
			lv = 0;
	static int[] expMap = new int[] {
			100, 200, 400, 500, 1000, 2000, 4000, 8000, 10000, 20000, 40000, 100000, 500000
	};
	static Map map;
	static HashMap<Map, List<Location>> foundLocation;

	public static void main(String[] args) {
		gameReset();
		s1();
		System.out.println("   探索系ゲーム的試作");
		s1();

		MovementResultType type = null;

		boolean gameend = false;

		gameTask: while (!gameend) {
			//戦闘
			double encount = Math.random();

			// 探索済みエリア設定
			int[][] mapData = map.getMap();
			int nowLoc = mapData[mapY][mapX];
			Location loc = new Location(mapX, mapY);
			List<Location> foundArea = getFoundLocation(map);
			for( int x = (int) (mapX - Settings.FOUND_AREA.getValue()); x <= (mapX + Settings.FOUND_AREA.getValue()); x++ ) {
				if( x < 0 ) {
					continue;
				}
				for( int y = (int) (mapY - Settings.FOUND_AREA.getValue()); y <= (mapY + Settings.FOUND_AREA.getValue()); y++ ) {
					if( y < 0 ) {
						continue;
					}
					Location cuLoc = new Location(x, y);
					if(!foundArea.contains(cuLoc)) {
						foundArea.add(cuLoc);
					}
				}
			}
			setFoundLocation(map, foundArea);

//			for( Location lc : foundArea ) {
//				System.out.println("x: " + lc.getX() + " y:" + lc.getY() + " r: " + lc.getRotate());
//			}
			// マップ描画
			System.out.println(map.getName());
			for (int y = 0; y < mapData.length; y++) {
				for (int x = 0; x < mapData[y].length; x++) {
					int i = mapData[y][x];
					if (y == mapY && x == mapX) {
						i = -1;
					}
					Location cuLoc = new Location(x, y);
					// 探索済みであるか
					if( foundArea.contains(cuLoc) ) {
						// 探索済み > 通常通り表示
						switch (i) {
						case 0:
							System.out.print("■");
							break;
						case -1:
							switch (mapRotate) {
							case 0:
								System.out.print("↑");
								break;
							case 1:
								System.out.print("→");
								break;
							case 2:
								System.out.print("↓");
								break;
							case 3:
								System.out.print("←");
								break;
							}
							break;
						case 1:
							System.out.print("　");
							break;
						default:
							try {
								Object obj = map.getMapObject()[i];
								if (obj instanceof ItemBox) {
									System.out.print("！");
								} else if (obj instanceof LevelChange) {
									System.out.print("階");
								}
							} catch (Exception e) {
								System.out.print("　");
							}
							break;
						}
					} else {
						// 探索済みでない > 非表示
						System.out.print("・");
					}
				}
				System.out.println();
			}
			//マップ描画終わり
			if (type != null && !type.equals(MovementResultType.DONE)) {
				System.out.println(type.getMessage());
			} else {
				System.out.println();
			}
			// デバッグ用メッセージ
			if (Settings.DEBUG.getValue() == 1) {
				System.out.print(
						"[D] X:" + mapX + " Y:" + mapY + " R:" + mapRotate);
				if(Settings.ENCOUNT_PERCENT.getValue() == 0) {
					System.out.println("  エンカウントなし");
				} else {
					System.out.println("  Enc:" + encount + " <= " + Settings.ENCOUNT_PERCENT.getValue() +
							" [" + (encount <= Settings.ENCOUNT_PERCENT.getValue()) + "]");
				}
			}

			if (input == 1 && type.equals(MovementResultType.DONE) && encount <= Settings.ENCOUNT_PERCENT.getValue()) {
				// 戦闘開始
				sleep(400);
				System.out.println("何かが道をふさいだ!");
				sleep(1000);
				battleStart();
				input = 0;
			} else {
				// 行動選択
				input = choice(new String[] {
						"どうする？ / YOU[Lv" + lv + "]  HP: " + nowHP + " / " + maxHP + "  exp: " + exp,
						"1. 進む["+(int)(Settings.ENCOUNT_PERCENT.getValue() * 100)+"%で敵と遭遇]   2. 左を向く   3. 右を向く  4. 後ろを向く",
						"5. 休む(現在体力の50%回復 or 20%で強襲を受けるかも)",
						"6. 調べる"
				}, 1, 6);
				if (input == 1) {
					type = goForward();
				} else if (input == 2) {
					// 左
					rotateLeft();
					type = null;
				} else if (input == 3) {
					// 右
					rotateRight();
					type = null;
				} else if (input == 4) {
					// 後ろ
					rotateRight();
					rotateRight();
					type = null;
				} else if (input == 5) {
					System.out.print("少し休もう");
					for (int i = 0; i < 3; i++) {
						sleep(500);
						System.out.print(".");
					}
					sleep(1000);
					System.out.println();
					System.out.println();
					if (Math.random() <= 0.2) {
						System.out.println("しかし敵に襲われてしまった！");
						sleep(500);
						System.out.println();
						battleStart();
					} else {
						System.out.println("ゆっくり体を休めた... 現在体力の50%回復");
						nowHP = Math.min(nowHP / 2 + nowHP, maxHP);
						sleep(1000);
					}
				} else if (input == 6) {
					// 調べる
					Object obj = map.getMapObject()[nowLoc];
					if (obj instanceof ItemBox) {
						System.out.println();
						ItemBox box = (ItemBox) obj;
						System.out.println("あなた は 宝箱 を あけた");
						sleep(1200);
						if (box instanceof ItemEXPBox) {
							giveEXP(((ItemEXPBox) box).getEXP(), false);
						}
						map.getMap()[mapY][mapX] = 1;
						sleep(1000);
					} else if (obj instanceof LevelChange) {
						System.out.println();
						System.out.println();
						LevelChange level = (LevelChange) obj;
						if (level.getType().equals(VerticalType.UP)) {
							System.out.println("上り階段がある...");
						} else if (level.getType().equals(VerticalType.DOWN)) {
							System.out.println("下り階段がある...");
						} else {
							System.out.println("階段がある...");
						}
						input = choice(new String[] {
								"1. すすむ  2. まだここに残る",
						}, 1, 2);
						if (input == 1) {
							System.out.print("階段を進みます");
							for (int i = 0; i < 3; i++) {
								sleep(500);
								System.out.print(".");
							}
							map = level.getMap();
							loc = level.getMap().getStartLocation();
							if (level.getLocation() != null) {
								loc = level.getLocation();
							}
							mapX = loc.getX();
							mapY = loc.getY();
							mapRotate = loc.getRotate();
						} else {
							System.out.println("まだ残ることにしました。");
						}
						sleep(1200);

					} else {
						System.out.println("異常なし。");
					}
					sleep(500);
				} else {
					sleep(500);
				}
			}

			// HPチェック
			if (nowHP <= 0) {
				sleep(1500);
				System.out.println();
				System.out.println("あなた は たおれてしまった...");
				input = choice(new String[] {
						"= = = C O N T I N U E ? = = =",
						"1. Yes   2. No",
				}, 1, 2);
				if (input == 1) {
					gameReset();
					for (int i = 0; i < 5; i++) {
						System.out.println();
						System.out.println();
						sleep(750);
					}
				} else {
					gameend = true;
				}
			}
			System.out.println();
			System.out.println();
			// ループ処理の一連の流れが終了 (上に戻る)
		}
		// メインメソッド終了
		System.out.println("[PROGRAM END]");
	}

	// 右回り
	static void rotateRight() {
		mapRotate++;
		if (mapRotate > 3) {
			int tmp = mapRotate % 3;
			mapRotate = -1 + tmp;
		}
	}

	// 左回り
	static void rotateLeft() {
		mapRotate--;
		if (mapRotate < 0) {
			int tmp = -(mapRotate) % 3;
			mapRotate = 4 - tmp;
		}
	}

	// 進む
	static MovementResultType goForward() {
		// 移動予定座標変数(初期値は現在地)
		int moveX = mapX, moveY = mapY;
		/*
		 *  X →→
		 * Y
		 * ↓
		 * ↓
		 */
		switch (mapRotate) {
		case 0:
			// 上向き > 上に動く > Yを減らす
			moveY--;
			break;
		case 1:
			// 右向き > 右に動く > Xを増やす
			moveX++;
			break;
		case 2:
			moveY++;
			break;
		case 3:
			moveX--;
			break;
		}

		// 移動先チェック
		switch (map.getMap()[moveY][moveX]) {
		case 0:
			return MovementResultType.WALL;
		default:
			// 異常なし > 座標変更
			mapX = moveX;
			mapY = moveY;
			break;
		}
		// 問題なければDONEを返して終了
		return MovementResultType.DONE;
	}

	static void gameReset() {
		// 初期値設定
		input = 2;
		maxHP = 1000;
		nowHP = maxHP;
		attack = 15;
		defence = 15;
		exp = 0;
		lv = 1;
		map = Map.Level01;
		mapX = map.getStartLocation().getX();
		mapY = map.getStartLocation().getY();
		mapRotate = map.getStartLocation().getRotate();
		foundLocation = new HashMap<>();
		foundLocation.put(map, new ArrayList<>());
	}

	static List<Location> getFoundLocation(Map map) {
		if (foundLocation.get(map) == null) {
			foundLocation.put(map, new ArrayList<>());
		}
		return foundLocation.get(map);
	}

	static void setFoundLocation(Map map, List<Location> locList) {
		foundLocation.put(map, locList);
	}

	static void battleStart() {
		battleStart(Character.geneChara(map.getCharacterType()));
	}

	static void battleStart(Character tmpc) {
		Character c = null;
		boolean battleend = false;
		battleTask: while (!battleend) {

			if (c == null) {
				c = tmpc;
				System.out.println(c.getName() + " が あらわれた!");
			}
			s1();
			System.out.println(c.getName() + " / HP: " + c.getNowHP() + "/" + c.getHP());
			System.out.println("YOU[Lv." + lv + "]  / HP: " + nowHP + "/" + maxHP);
			input = choice(new String[] {
					"どうする？",
					"1. 通常攻撃  2. 炎攻撃  3. 氷攻撃  4. にげる"
			}, 1, 4);

			List<Integer> l = new ArrayList<Integer>();

			// 0: 自分  1: 敵
			l.add(0);
			l.add(1);

			int num = 0;

			while (l.size() > num) {
				if (l.get(num).equals(0)) {
					if (input <= Attack.values().length) {
						Attack a = Attack.values()[input - 1];
						sleep(300);
						System.out.println("あなた の こうげき！");
						sleep(500);
						if (Math.random() < a.getHitPercent()) {
							int b = (int) (a.getBaseDamage() * attack * Math.min(Math.random() + 0.5, 1));
							//							System.out.println(b);
							int d = 0;
							if (Math.random() <= 0.2) {
								d = c.criticalDamage(b);
								System.out.println("＞＞ かいしん の いちげき！ ＜＜");
								sleep(500);
							} else {
								d = c.damage(b);
							}
							if (d == 0) {
								System.out.println(c.getName() + " はびくともしない...");
							} else {
								System.out.println(c.getName() + " に " + d + " のダメージ！");
							}
						} else {
							System.out.println("はずれ～＞＜");
						}
						sleep(1000);
					} else if (input == Attack.values().length + 1) {
						// esc
						System.out.println("あなた は にげだした！");
						sleep(1500);
						if (Math.random() <= 0.2) {
							if (Math.random() <= 0.1) {
								System.out.println("が、偶然あった石ころで転んでしまった！");
							} else if (Math.random() <= 0.2) {
								System.out.println("が、逃げた先が行き止まりでした...");
							} else if (Math.random() <= 0.3) {
								System.out.println("が、足が思うように動かなかった！ｴﾍﾍ");
							} else {
								System.out.println("が、だめでした★");
							}
							sleep(1000);
						} else {
							c = null;
							battleend = true;
							break;
						}

					}
				} else if (l.get(num).equals(1)) {
					Attack a = c.getAttackList().get((int) (Math.random() * c.getAttackList().size()));
					sleep(300);
					System.out.println(c.getName() + " の " + a.getName() + "！");
					sleep(500);
					int b = (int) (a.getBaseDamage() * c.getAttackPoint() * Math.min(Math.random() + 0.5, 1));
					//					System.out.println(b);
					int d = c.attack(a);
					if (d <= -1) {
						System.out.println("はずれ～");
					} else {
						d = Math.max(d - defence, 0);
						if (Math.random() <= 0.1) {
							d = (int) (d * 2);
							System.out.println("＞＞ かいしん の いちげき！ ＜＜");
							sleep(500);
						}
						if (d == 0) {
							System.out.println("あなた はびくともしない...");
						} else {
							System.out.println("あなた に " + d + " のダメージ！");
						}
						nowHP = nowHP - d;
					}

					sleep(1000);
				}
				if (nowHP <= 0) {
					c = null;
					battleend = true;
					break battleTask;
				}
				if (c != null) {
					if (c.getNowHP() <= 0) {
						sleep(1000);
						System.out.println();
						System.out.println(c.getName() + " を たおした！");
						sleep(1500);
						int add = (int) (c.getEXPoint() * Math.max(Math.random(), 0.8));
						giveEXP(add);
						sleep(1500);
						battleend = true;
						c = null;
						sleep(1000);
						break battleTask;
					}
				}
				num++;
			}
			//

			System.out.println();

		}
	}

	private static void giveEXP(int add) {
		giveEXP(add, true);
	}

	private static void giveEXP(int add, boolean isNokori) {
		exp = exp + add;
		System.out.println(add + " の 経験値 を 得た！");
		if (expMap[lv] <= exp) {
			lv++;
			System.out.println("あなた は Lv." + lv + " に レベルアップ した！");
			int up = (int) (Math.random() * 10);
			if (up != 0) {
				sleep(1500);
				System.out.println("攻撃力 が " + up + " あがった！");
				attack = attack + up;
			}
			up = (int) (Math.random() * 10);
			if (up != 0) {
				sleep(1500);
				System.out.println("防御力 が " + up + " あがった！");
				defence = defence + up;
			}

			up = (int) (Math.floor(Math.random() * 100));
			if (up != 0) {
				sleep(1500);
				System.out.println("最大体力 が " + up + " あがった！");
				maxHP = maxHP + up;
			}
			sleep(1500);
			System.out.println("体力が全回復した！");
			nowHP = maxHP;
		} else {
			if (isNokori) {
				System.out.println("レベルアップまで: " + (expMap[lv] - exp) + "pt");
			}
		}
	}

	static void s1() {
		System.out.println("========================================================");
	}

	static void s2() {
		System.out.println("--------------------------------------------------------");
	}

	static void sleep(long mili) {
		try {
			Thread.sleep(mili);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	static String comma(int num) {
		String base = "";
		int i = 0;
		List<String> list = Arrays.asList(String.valueOf(num).split(""));
		Collections.reverse(list);
		for (String s : list) {
			if (i >= 3) {
				i = 0;
				base = "," + base;
			}
			base = s + base;
			i++;
		}
		return base;
	}

	static int askTicketCount(String msg) {
		try {
			Scanner sn = new Scanner(System.in);
			System.out.print(msg + " > ");
			String s = sn.next();
			int i = Integer.parseInt(s);
			if (i < 0) {
				System.out.println("0以上整数で指定してください。");
				return askTicketCount(msg);
			}
			return i;
		} catch (Exception e) {
			System.out.println("0以上整数で指定してください。");
			return askTicketCount(msg);
		}
	}

	static String spacer(String msg, int min_len) {
		String out = msg;
		for (; out.length() < min_len;) {
			out += "  ";
		}
		return out;
	}

	static String spacerLeft(String msg, int min_len) {
		String out = msg;
		for (int i = out.length(); i <= min_len; i++) {
			out = " " + out;
		}
		return out;
	}

	static int choice(String[] choiceMsg, int min, int max) {
		boolean ok = false, onceMore = false;
		int input = 0;
		while (!ok) {
			s1();
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
