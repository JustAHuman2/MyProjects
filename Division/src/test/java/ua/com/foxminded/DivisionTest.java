package ua.com.foxminded;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DivisionTest {

	private Division division;

	@Before
	public void setup() {
		division = new Division();
	}

	@Test
	public void givenNegativeDividend_whenDivide_thenResult() {
		int expected = -5;
		int actual = division.divide(5, -1).getResult();
		assertEquals(expected, actual);
	}

	@Test
	public void givenNegativeDivider_whenDivide_thenResult() {
		int expected = -1;
		int actual = division.divide(1, -1).getResult();
		assertEquals(expected, actual);
	}

	@Test
	public void givenDividendLessThenDivider_whenDivide_thenResult() {
		int expected = 0;
		int actual = division.divide(5, 7).getResult();
		assertEquals(expected, actual);
	}

	@Test
	public void givenNumbers_whenDivide_thenResult() {
		int expected = 19736;
		int actual = division.divide(78945, 4).getResult();
		assertEquals(expected, actual);
	}
}