package ua.com.foxminded;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NumberUtilsTest {

	@Test
	public void givenZero_whenSplitToDigits_thenZero() {
		int[] expected = { 0 };
		int[] actual = NumberUtils.splitToDigits(0);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void givenNumber_whenSplitToDigits_thenNumbersArray() {
		int[] expected = { 1, 2, 3 };
		int[] actual = NumberUtils.splitToDigits(123);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void givenZero_whenGetLength_thenNumberLength() {
		int expected = 1;
		int actual = NumberUtils.getLength(0);
		assertEquals(expected, actual);
	}

	@Test
	public void givenNegativeNumber_whenGetLength_thenNumberLength() {
		int expected = 8;
		int actual = NumberUtils.getLength(-1234567);
		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber_whenGetLength_thenNumberLength() {
		int expected = 3;
		int actual = NumberUtils.getLength(567);
		assertEquals(expected, actual);
	}
}