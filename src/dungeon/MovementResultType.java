package dungeon;

public enum MovementResultType {
	DONE {
		@Override
		public String getName() {
			return "完了";
		}

		@Override
		public String getMessage() {
			return "移動できた";
		}
	},WALL {
		@Override
		public String getName() {
			return "壁";
		}

		@Override
		public String getMessage() {
			return "壁に体当たりした。";
		}
	},HOLE {
		@Override
		public String getName() {
			return "穴";
		}

		@Override
		public String getMessage() {
			return "通れないほどの穴が空いている...";
		}
	};
	public abstract String getName();
	public abstract String getMessage();
}
