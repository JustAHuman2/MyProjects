package ua.com.foxminded;

import ua.com.foxminded.DivisionResult;
import ua.com.foxminded.NumberUtils;

public class DivisionFormatter {

	public String format(DivisionResult divisionResult) {
		if (divisionResult == null) {
			return "There is no input (null)";
		}
		int dividend = divisionResult.getDividend();
		int dividendLength = NumberUtils.getLength(dividend);
		int divider = divisionResult.getDivider();
		int dividerLength = NumberUtils.getLength(divider);
		int divideResult = divisionResult.getResult();
		int resultLength = NumberUtils.getLength(divideResult);
		DivisionStep[] steps = divisionResult.getSteps();
		int firstDigit = ((DivisionStep) steps[0]).getMultiplication();
		int firstDigitLength = NumberUtils.getLength(firstDigit);
		int gap = dividendLength - firstDigitLength;
		int borderSize = Math.max(dividerLength, resultLength);
		int mod = ((DivisionStep) steps[steps.length - 1]).getMultiplication();
		int modSpace = dividendLength - NumberUtils.getLength(mod) + 2;

		String lastLine = repeatChar(modSpace, ' ') + mod;
		StringBuilder result = new StringBuilder();
		result.append(" _" + dividend + "|" + divider + System.lineSeparator());
		result.append(
				"  " + firstDigit + repeatChar(gap, ' ') + "|" + repeatChar(borderSize, '-') + System.lineSeparator());
		result.append("  " + repeatChar(firstDigitLength, '-') + repeatChar(gap, ' ') + "|" + divideResult
				+ System.lineSeparator());
		return printNumbers(result, steps, lastLine).toString();
	}

	private StringBuilder printNumbers(StringBuilder result, DivisionStep[] steps, String lastLine) {
		for (int i = 1; i < steps.length - 1; i++) {
			int multiplication = ((DivisionStep) steps[i]).getMultiplication();
			result.append(repeatChar(i, ' ') + "_" + (multiplication + System.lineSeparator()));
			int quotient = ((DivisionStep) steps[i]).getQuotient();
			result.append(repeatChar(i + 1, ' ') + quotient + System.lineSeparator() + repeatChar(i + 1, ' ')
					+ repeatChar(NumberUtils.getLength(quotient), '-') + System.lineSeparator());
		}
		result.append(lastLine);
		return result;
	}

	private String repeatChar(int times, char character) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < times; i++) {
			result.append(character);
		}
		return result.toString();
	}
}