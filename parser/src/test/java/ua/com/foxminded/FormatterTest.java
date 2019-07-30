package ua.com.foxminded;

import static java.time.LocalDateTime.of;
import static java.lang.System.lineSeparator;
import static java.util.Collections.emptyList;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class FormatterTest {

	private Formatter formatter;
	private StringBuilder expected;
	private Racer racerOne;
	private Racer racerTwo;

	@Before
	public void setup() {
		formatter = new Formatter();
		expected = new StringBuilder();
		LocalDateTime startTimeOne = of(2018, 5, 24, 12, 2, 58, 917000000);
		LocalDateTime finishTimeOne = of(2018, 5, 24, 12, 4, 3, 332000000);
		LocalDateTime startTimeTwo = of(2018, 5, 24, 12, 7, 23, 645000000);
		LocalDateTime finishTimeTwo = of(2018, 5, 24, 12, 8, 36, 586000000);
		racerOne = new Racer("SVF", "Sebastian Vettel", "FERRARI", new LapTime(startTimeOne, finishTimeOne));
		racerTwo = new Racer("PGS", "Pierre Gasly", "SCUDERIA TORO ROSSO HONDA",
				new LapTime(startTimeTwo, finishTimeTwo));
	}

	@Test(expected = NullPointerException.class)
	public void givenNull_whenFormat_thenNPE() {
		formatter.format(null, 1);
	}

	@Test
	public void givenEmptyList_whenFormat_thenEmptyString() {
		String expected = "";
		String actual = formatter.format(emptyList(), 0);
		assertEquals(expected, actual);
	}

	@Test
	public void givenNumberLessThenRacersListSize_whenFormat_thenResultWithLine() {
		expected.append("1. Sebastian Vettel  | FERRARI                   | 1:04.415" + lineSeparator());
		expected.append("-----------------------------------------------------------" + lineSeparator());
		expected.append("2. Pierre Gasly      | SCUDERIA TORO ROSSO HONDA | 1:12.941" + lineSeparator());

		String actual = formatter.format(asList(racerOne, racerTwo), 1);

		assertEquals(expected.toString(), actual);
	}

	@Test
	public void givenRacersListAndZero_whenFormat_thenResultWithOutLine() {
		expected.append("1. Sebastian Vettel  | FERRARI                   | 1:04.415" + lineSeparator());
		expected.append("2. Pierre Gasly      | SCUDERIA TORO ROSSO HONDA | 1:12.941" + lineSeparator());

		String actual = formatter.format(asList(racerOne, racerTwo), 0);

		assertEquals(expected.toString(), actual);
	}

	@Test
	public void givenRacersListSizeSameAsNumber_whenFormat_thenResultWithLine() {
		expected.append("1. Sebastian Vettel  | FERRARI                   | 1:04.415" + lineSeparator());
		expected.append("2. Pierre Gasly      | SCUDERIA TORO ROSSO HONDA | 1:12.941" + lineSeparator());
		expected.append("-----------------------------------------------------------" + lineSeparator());

		String actual = formatter.format(asList(racerOne, racerTwo), 2);

		assertEquals(expected.toString(), actual);
	}

	@Test
	public void givenRacersListSizeLessThenNumber_whenFormat_thenResultWithoutLine() {
		expected.append("1. Sebastian Vettel  | FERRARI                   | 1:04.415" + lineSeparator());
		expected.append("2. Pierre Gasly      | SCUDERIA TORO ROSSO HONDA | 1:12.941" + lineSeparator());

		String actual = formatter.format(asList(racerOne, racerTwo), 5);

		assertEquals(expected.toString(), actual);
	}

	@Test
	public void givenRacersListAndNegativeNumber_whenFormat_thenResultWithoutLine() {
		expected.append("1. Sebastian Vettel  | FERRARI                   | 1:04.415" + lineSeparator());
		expected.append("2. Pierre Gasly      | SCUDERIA TORO ROSSO HONDA | 1:12.941" + lineSeparator());

		String actual = formatter.format(asList(racerOne, racerTwo), -10);

		assertEquals(expected.toString(), actual);
	}
}