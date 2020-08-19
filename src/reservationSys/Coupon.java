package reservationSys;

public enum Coupon {
	minus500yen{		
		@Override
		int priceExec(int i) {
			return i - 500;
		}
		@Override
		String getName() {
			return "500円引き";
		}
		@Override
		String priceExecString() {
			return "- 500";
		}
		
	},
	per50off{		
		@Override
		int priceExec(int i) {
			return (int) (i * 0.5);
		}
		@Override
		String getName() {
			return "50% OFF";
		}
		@Override
		String priceExecString() {
			return "50% OFF";
		}
		
	};
	
	abstract String getName();
	abstract int priceExec(int i);
	abstract String priceExecString();
}
