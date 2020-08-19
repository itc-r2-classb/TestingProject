package dungeon;

public enum Attack {
	Normal{
		@Override
		String getName() {
			return "通常攻撃";
		}
		@Override
		int getBaseDamage() {
			return 3;
		}

		@Override
		double getHitPercent() {
			return 0.9;
		}
	},
	Fire{
		@Override
		String getName() {
			return "炎攻撃";
		}
		@Override
		int getBaseDamage() {
			return 10;
		}

		@Override
		double getHitPercent() {
			return 0.7;
		}
	},
	ice{
		@Override
		String getName() {
			return "氷攻撃";
		}
		@Override
		int getBaseDamage() {
			return 20;
		}

		@Override
		double getHitPercent() {
			return 0.78;
		}
	};
	
	abstract String getName();
	abstract int getBaseDamage();
	abstract double getHitPercent();
	
}
