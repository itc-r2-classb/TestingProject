package dungeon;

public enum Settings {
	DEBUG{
		@Override
		public double getValue() {
			return 1;
		}
	},
	ENCOUNT_PERCENT{
		@Override
		public double getValue() {
			return 0;
		}
	},
	FOUND_AREA{
		@Override
		public double getValue() {
			return 2; //周囲nマス
		}
	};
	public abstract double getValue();
}
