package reversi;

import lib.Utility;

public class main {
	private static final Stone w = Stone.WHITE, b = Stone.BLACK, n = Stone.NONE, d = Stone.NOT_PLACE;
	
	public static void main(String[] args) {

		String ver = "1.0";

		Utility.printSikiri1();
		System.out.println("   Reversi Ver."+ver);
		Utility.printSikiri1();

		int input = 0;
		Board board = new Board(new Stone[][] {
			{n,n,n,n,n,n,n,n},
			{n,n,n,n,n,n,n,n},
			{n,n,n,n,n,n,n,n},
			{n,n,n,w,b,n,n,n},
			{n,n,n,b,w,n,n,n},
			{n,n,n,n,n,n,n,n},
			{n,n,n,n,n,n,n,n},
			{n,n,n,n,n,n,n,n},
		});
		Stone turn = Stone.WHITE;
		boolean gameEnd = false;
		while( !gameEnd ) {
			// 描画
			System.out.print("x/y");
			for(int x = 0; x < board.maxX;x++) {
				System.out.print(Utility.spacerLeft(String.valueOf(x + 1), 1));
			}
			System.out.println();
			for(int y = 0; y < board.getYLength();y++) {
				System.out.print(Utility.spacer((y+1)+"", 3));
				for(int x = 0; x < board.getXLength(y);x++) {
					Stone current = board.get(x, y);
					System.out.print(current.getDisplay());
				}
				System.out.println();
			}
			boolean ok = false;
			
			int in_x = 0, in_y = 0;
			while(!ok) {
				try {
					String input_str = Utility.inputString(turn.getName()+"のターン: 設置座標[X Y]");
					String[] input_arr = input_str.split(" ");
					if( input_arr.length < 2 ) {
						System.out.println("値が足りません");
					} else {
						in_x = Integer.parseInt(input_arr[0]) - 1;
						in_y = Integer.parseInt(input_arr[1]) - 1;
						if( !board.get(in_x, in_y).equals(Stone.NONE) ) {
							System.out.println("そこには置けません");
						} else {
							ok = true;
						}
					}
				} catch (Exception e) {
					System.out.println("入力が不正です。");
				}
			}
			board.place(in_x, in_y, turn);
			
			if( turn.equals(Stone.BLACK) ) {
				turn = Stone.WHITE;
			} else {
				turn = Stone.BLACK;
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
}
