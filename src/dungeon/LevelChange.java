package dungeon;

public class LevelChange {
	
	enum VerticalType {
		UP, DOWN
	}
	
	Map level;
	VerticalType type;
	Location loc;
	
	public LevelChange(Map level, VerticalType type) {
		this.level = level;
		this.type = type;
		this.loc = null;
	}
	
	public LevelChange(Map level, VerticalType type, Location loc) {
		this.level = level;
		this.type = type;
		this.loc = loc;
	}
	
	public Map getMap() {
		return level;
	}

	public VerticalType getType() {
		return type;
	}
	
	public Location getLocation() {
		return loc;
	}
	
}
