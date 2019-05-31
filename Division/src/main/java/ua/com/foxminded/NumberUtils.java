package ua.com.foxminded;

public class NumberUtils {

	static int getLength(int number) {
		if (number == 0) {
			return 1;
		}
		if (number < 0) {
			return (int) (Math.log10(Math.abs(number)) + 1) + 1;
		}
		return (int) (Math.log10(number) + 1);
	}

	static int[] splitToDigits(int number) {
		int[] digits = new int[getLength(Math.abs(number))];
		for (int i = digits.length - 1; i >= 0; i--) {
			digits[i] = number % 10;
			number /= 10;
		}
		return digits;
	}
}
