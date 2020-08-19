package dungeon;

import java.util.Arrays;
import java.util.List;

public enum GameCharacterType {
	slime{
		@Override
		String getName() {
			return "すらいむ";
		}
		@Override
		List<Attack> getAttackList() {
			return Arrays.asList(Attack.Normal);
		}
		@Override
		int getHealthPoint() {
			return 20;
		}
		@Override
		int getAttackPoint() {
			return 1;
		}
		@Override
		int getDefencePoint() {
			return 5;
		}
		@Override
		int getEXPoint() {
			return 10;
		}
	},
	goblin{
		@Override
		String getName() {
			return "GOB:LIN";
		}
		@Override
		List<Attack> getAttackList() {
			return Arrays.asList(Attack.Normal,Attack.Fire);
		}
		@Override
		int getHealthPoint() {
			return 50;
		}
		@Override
		int getAttackPoint() {
			return 2;
		}
		@Override
		int getDefencePoint() {
			return 10;
		}
		@Override
		int getEXPoint() {
			return 50;
		}
	},
	king_of_rock{
		@Override
		String getName() {
			return "きんぐおぶろっく";
		}
		@Override
		List<Attack> getAttackList() {
			return Arrays.asList(Attack.Normal,Attack.Fire,Attack.ice);
		}
		@Override
		int getHealthPoint() {
			return 500;
		}
		@Override
		int getAttackPoint() {
			return 35;
		}
		@Override
		int getDefencePoint() {
			return 15;
		}
		@Override
		int getEXPoint() {
			return 200;
		}
	},
	bad_rabbit{
		@Override
		String getName() {
			return "やばいうさぎ";
		}
		@Override
		List<Attack> getAttackList() {
			return Arrays.asList(Attack.Normal,Attack.Fire,Attack.ice);
		}
		@Override
		int getHealthPoint() {
			return 750;
		}
		@Override
		int getAttackPoint() {
			return 42;
		}
		@Override
		int getDefencePoint() {
			return 20;
		}
		@Override
		int getEXPoint() {
			return 500;
		}
	}
	;
	
	abstract String getName();
	abstract List<Attack> getAttackList();
	abstract int getHealthPoint();
	abstract int getAttackPoint();
	abstract int getDefencePoint();
 	abstract int getEXPoint();
	
}
