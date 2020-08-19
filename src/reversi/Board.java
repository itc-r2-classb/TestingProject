package reversi;

import java.util.ArrayList;
import java.util.List;

public class Board {
	Stone[][] board;
	int maxX, maxY;

	Board(Stone[][] s) {
		board = s;
		maxY = s.length;
		maxX = 0;
		for(int i = 0; i < s.length; i++) {
			maxX = Math.max(maxX, s[i].length);
		}
	}

	Stone get(int x, int y) {
		try {
			return board[y][x];
		} catch (Exception e) {
			return Stone.NOT_PLACE;
		}
	}

	void debug(Stone[][] s) {
		board = s;
	}

	boolean set(int x, int y, Stone stone) {
		try {
			board[y][x] = stone;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	void place(int x, int y, Stone stone) {
		int cx = 0, cy = 0;
		// 上
		List<Location> list = new ArrayList<>(), tmp = new ArrayList<>();
		boolean copy = false;
		for (cy = y; cy >= 0; cy--) {
			if (!get(x, cy).equals(Stone.NONE) && !get(x, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(x, cy));
			}
			if (get(x, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 下
		copy = false;
		for (cy = y; cy < maxY; cy++) {
			if (!get(x, cy).equals(Stone.NONE) && !get(x, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(x, cy));
			}
			if (get(x, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 右
		copy = false;
		cy = y;
		for (cx = x; cx < maxX; cx++) {

			if (!get(cx, cy).equals(Stone.NONE) && !get(cx, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(cx, cy));
			}
			if (get(cx, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 左
		copy = false;
		cy = y;
		for (cx = x; cx >= 0; cx--) {
			if (!get(cx, cy).equals(Stone.NONE) && !get(cx, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(cx, cy));
			}
			if (get(cx, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 斜め左上
		copy = false;
		for (int i = 0; i <= Math.min(y, x); i++) {
			cx = x - i;
			cy = y - i;
			if (!get(cx, cy).equals(Stone.NONE) && !get(cx, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(cx, cy));
			}
			if (get(cx, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 斜め右上
		copy = false;
		for (int i = 0; i <= Math.min(y, x); i++) {
			cx = x + i;
			cy = y - i;
			if (!get(cx, cy).equals(Stone.NONE) && !get(cx, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(cx, cy));
			}
			if (get(cx, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 斜め左下
		copy = false;
		for (int i = 0; i <= Math.min(y, x); i++) {
			cx = x - i;
			cy = y + i;
			if (!get(cx, cy).equals(Stone.NONE) && !get(cx, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(cx, cy));
			}
			if (get(cx, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		// 斜め右下
		copy = false;
		for (int i = 0; i <= Math.min(y, x); i++) {
			cx = x + i;
			cy = y + i;
			if (!get(cx, cy).equals(Stone.NONE) && !get(cx, cy).equals(Stone.NOT_PLACE)) {
				tmp.add(new Location(cx, cy));
			}
			if (get(cx, cy).equals(stone)) {
				copy = true;
				break;
			}
		}
		if (copy) {
			list.addAll(tmp);
		}
		tmp.clear();
		for (Location loc : list) {
			set(loc.getX(), loc.getY(), stone);
		}
		set(x, y, stone);
	}

	int getXLength(int y) {
		return board[y].length;
	}

	int getYLength() {
		return board.length;
	}

	int getMaxX() {
		return maxX;
	}

	int getMaxY() {
		return maxY;
	}

}

class Location {
	int x, y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
}
