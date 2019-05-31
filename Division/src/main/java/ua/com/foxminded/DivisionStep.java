package ua.com.foxminded;

public class DivisionStep {

	private int quotient;
	private int multiplication;

	public DivisionStep() {
	}

	public DivisionStep(int multiplication) {
		this.multiplication = multiplication;
	}

	public DivisionStep(int quotient, int multiplication) {
		this.quotient = quotient;
		this.multiplication = multiplication;
	}

	void setQuotient(int quotient) {
		this.quotient = quotient;
	}

	void setMultiplication(int multiplication) {
		this.multiplication = multiplication;
	}

	int getQuotient() {
		return quotient;
	}

	int getMultiplication() {
		return multiplication;
	}
}