package dungeon;

public class Location {
	int x, y, rotate;
	public Location(int x, int y){
		this.x = x;
		this.y = y;
		this.rotate = 0;
	}
	
	public Location(int x, int y, int rotate){
		this.x = x;
		this.y = y;
		this.rotate = rotate;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRotate() {
		return rotate;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;
        Location loc = (Location) o;
//        System.out.println(this.getX()+" == "+loc.getX()+": " + (this.getX() == loc.getX()));
//        System.out.println(this.getY()+" == "+loc.getY()+": " + (this.getY() == loc.getY()));
//        System.out.println(this.getRotate()+" == "+loc.getRotate()+": " + (this.getRotate() == loc.getRotate()));
//        System.out.println(this.getX() == loc.getX() && this.getY() == loc.getY() && this.getRotate() == loc.getRotate());
//        System.out.println();
		return this.getX() == loc.getX() && this.getY() == loc.getY() && this.getRotate() == loc.getRotate();
	}
}
