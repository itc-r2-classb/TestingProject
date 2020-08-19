package dungeon;

import java.util.List;

public class Character {
	GameCharacterType type;
	int nowHP = 0;
	
	Character(GameCharacterType t){
		this.type = t;
		this.nowHP = t.getHealthPoint();
	}
	
	String getName() {
		return type.getName();
	}
	
	List<Attack> getAttackList(){
		return type.getAttackList();
	}
	
	int attack() {
		return attack(type.getAttackList().get((int) (Math.random()*type.getAttackList().size())));
	}
	
	int attack(Attack a) {
		int damage = 0;
		if( Math.random() > a.getHitPercent() ) {
			// no damaged.
			return -1;
		}
		damage = (int) (a.getBaseDamage() * type.getAttackPoint() * Math.min(Math.max(Math.random() + 0.5, 0.5), 0.9));
		return damage;
	}
	
	int getNowHP() {
		return this.nowHP;
	}
	
	int damage(int damage) {
		int d = Math.max(damage - type.getDefencePoint(), 0);
		this.nowHP = this.nowHP - d;
		return d;
	}
	
	public int criticalDamage(int damage) {
		int d = (int) (Math.max(damage - type.getDefencePoint(), 0) * 2.2);
		this.nowHP = this.nowHP - d;
		return d;
	}

	public int getHP() {
		return type.getHealthPoint();
	}
	
	public static Character geneChara(GameCharacterType[] characterType) {
		Character c = new Character(characterType[(int) (Math.random() * characterType.length)]);
		return c;
	}
	
	static Character geneChara() {
		Character c = new Character(GameCharacterType.values()[(int) (Math.random() * GameCharacterType.values().length)]);
		return c;
	}

	int getAttackPoint() {
		return type.getAttackPoint();
	}
	int getDefencePoint() {
		return type.getDefencePoint();
	}
	int getEXPoint() {
		return type.getEXPoint();
	}

	

	
}
