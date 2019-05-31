package ua.com.foxminded;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DivisionFormatterTest {

	private DivisionFormatter formatter;
	private StringBuilder expected;

	@Before
	public void setup() {
		formatter = new DivisionFormatter();
		expected = new StringBuilder();
	}

	@Test
	public void givenNull_whenFormat_thenThereIsNoInput() {
		DivisionResult result = null;
		expected.append("There is no input (null)");
		String actual = formatter.format(result);
		assertEquals(expected.toString(), actual);
	}

	@Test
	public void givenZeroes_whenFormat_thenFormatedZeroes() {
		DivisionResult divisionResult = new DivisionResult(0, 0);
		divisionResult.setResult(0);
		DivisionStep[] divisionStep = { new DivisionStep(0) };
		divisionResult.setDivisionStep(divisionStep);
		divisionResult.setResult(0);
		expected.append(" _0|0" + System.lineSeparator());
		expected.append("  0|-" + System.lineSeparator());
		expected.append("  -|0" + System.lineSeparator());
		expected.append("  0");
		String actual = formatter.format(divisionResult);
		assertEquals(expected.toString(), actual);
	}

	@Test
	public void givenNumbers_whenFormat_thenFormatedNumbers() {
		DivisionResult divisionResult = new DivisionResult(554, 3);
		DivisionStep[] steps = { new DivisionStep(3), new DivisionStep(24, 25), new DivisionStep(12, 14),
				new DivisionStep(2) };
		divisionResult.setDivisionStep(steps);
		divisionResult.setResult(184);
		expected.append(" _554|3" + System.lineSeparator());
		expected.append("  3  |---" + System.lineSeparator());
		expected.append("  -  |184" + System.lineSeparator());
		expected.append(" _25" + System.lineSeparator());
		expected.append("  24" + System.lineSeparator());
		expected.append("  --" + System.lineSeparator());
		expected.append("  _14" + System.lineSeparator());
		expected.append("   12" + System.lineSeparator());
		expected.append("   --" + System.lineSeparator());
		expected.append("    2");
		String actual = formatter.format(divisionResult);
		assertEquals(expected.toString(), actual);
	}
}