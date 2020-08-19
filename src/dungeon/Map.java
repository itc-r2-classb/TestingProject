package dungeon;

import dungeon.LevelChange.VerticalType;

public enum Map {
	
	/*
	 * 0: 壁
	 * 1: 通路
	 * -1: 現在位置
	 * 以降マップ固有オブジェクトID
	 */
	
	Level01{
		@Override
		public int[][] getDefaultMap() {
			return new int[][] {
				{0,0,0,0,0,0,0},
				{0,1,1,1,1,1,0},
				{0,1,0,0,0,1,0},
				{0,1,1,2,0,1,0},
				{0,1,0,0,0,1,0},
				{0,1,1,1,1,1,0},
				{0,0,0,0,0,0,0},
			};
		}

		@Override
		public Location getStartLocation() {
			return new Location(5, 4, 0);
		}
		@Override
		public Object[] getMapObject() {
			return new Object[] {
					"w","t",new LevelChange(Map.Level02, VerticalType.DOWN)
			};
		}

		@Override
		public String getName() {
			return "地下1階";
		}

		@Override
		public GameCharacterType[] getCharacterType() {
			return new GameCharacterType[] {
					GameCharacterType.slime,
					GameCharacterType.goblin,
			};
		}
	},
	Level02{
		@Override
		public int[][] getDefaultMap() {
			return new int[][] {
				{0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,3,1,1,1,0,0,1,1,1,1,1,0},
				{0,1,0,0,1,1,1,1,0,0,0,1,0},
				{0,1,0,0,0,0,0,1,0,2,1,1,0},
				{0,1,1,1,1,1,1,1,1,0,0,1,0},
				{0,1,0,1,0,0,0,0,0,0,1,1,0},
				{0,1,0,1,0,0,0,0,1,0,0,1,0},
				{0,1,0,1,1,1,1,1,1,1,1,1,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0},
			};
		}

		@Override
		public Location getStartLocation() {
			return new Location(10, 5, 1);
		}
		@Override
		public Object[] getMapObject() {
			return new Object[] {
					"w","t",
					new ItemEXPBox("1",100),
					new LevelChange(Map.Level01, VerticalType.UP)
			};
		}

		@Override
		public String getName() {
			return "地下2階";
		}

		@Override
		public GameCharacterType[] getCharacterType() {
			return new GameCharacterType[] {
					GameCharacterType.slime,
					GameCharacterType.goblin,
					GameCharacterType.king_of_rock,
					GameCharacterType.bad_rabbit
			};
		}
	};
	
	private int[][] map = getDefaultMap();
	public abstract String getName();
	public int[][] getMap(){
		return map;
	}
	public void setMap(int[][] m){
		map = m;
	}
	public abstract int[][] getDefaultMap();
	public abstract Location getStartLocation();
	public abstract Object[] getMapObject();
	public abstract GameCharacterType[] getCharacterType();
}
