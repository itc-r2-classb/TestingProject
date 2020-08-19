package dungeon;

public class ItemEXPBox extends ItemBox {
	private int EXP;

	public ItemEXPBox( String name, int EXP ){
		super(name);
		this.EXP = EXP;
	}

	public int getEXP() {
		return EXP;
	}
}
