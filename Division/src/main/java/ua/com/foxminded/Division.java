package ua.com.foxminded;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.DivisionResult;
import ua.com.foxminded.NumberUtils;

public class Division {

	public DivisionResult divide(int dividend, int divider) {
		DivisionResult divisionResult = new DivisionResult(dividend, divider);
		int[] dividendDigits = NumberUtils.splitToDigits(dividend);
		int result = 0;
		int dividendDigit = dividendDigits[0];
		List<DivisionStep> steps = new ArrayList<>();

		for (int i = 0; i < dividendDigits.length; i++) {
			if (i == 0) {
				result += dividendDigit / divider;
				steps.add(new DivisionStep(dividendDigit / divider * divider));
			}
			if (i >= dividendDigits.length - 1) {
				DivisionStep lastStep = new DivisionStep(dividend % divider);
				steps.add(lastStep);
				divisionResult.setDivisionStep(steps.toArray(new DivisionStep[steps.size()]));
				divisionResult.setResult(result);
				break;
			}
			if (dividendDigit < divider) {
				dividendDigit = dividendDigit * 10 + dividendDigits[i + 1];
			}
			if (dividendDigit >= divider) {
				dividendDigit = dividendDigit % divider * 10 + dividendDigits[i + 1];
				steps.add(new DivisionStep(dividendDigit / divider * divider, dividendDigit));
				result = result * 10 + (dividendDigit / divider);
			}
		}
		return divisionResult;
	}
}