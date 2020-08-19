package reversi;

public enum Stone {
	NONE("＿", "なし"),
	NOT_PLACE("　", "設置不可"),
	WHITE("○", "白"),
	BLACK("●", "黒");
	
	String display, name;

	Stone( String d, String name ){
		this.display = d;
		this.name = name;
	}
	
	String getDisplay() {
		return display;
	}
	
	String getName() {
		return name;
	}
}
