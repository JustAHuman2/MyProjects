package ua.com.foxminded;

public class DivisionResult {

	private int dividend;
	private int divider;
	private int divideResult;
	private DivisionStep[] steps;

	public DivisionResult(int dividend, int divider) {
		this.dividend = dividend;
		this.divider = divider;
	}

	int getDividend() {
		return dividend;
	}

	int getDivider() {
		return divider;
	}

	void setResult(int result) {
		this.divideResult = result;
	}

	int getResult() {
		return divideResult;
	}

	void setDivisionStep(DivisionStep[] steps) {
		this.steps = steps;
	}

	DivisionStep[] getSteps() {
		return steps;
	}
}