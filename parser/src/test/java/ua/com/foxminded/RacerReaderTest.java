package ua.com.foxminded;

import static org.junit.Assert.*;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class RacerReaderTest {

	private RacerReader reader;

	@Before
	public void setup() throws IOException {
		reader = new RacerReader();
	}

	@Test(expected = NullPointerException.class)
	public void givenNull_whenReadRacers_thenNPE() {
		reader.readRacers(null, null, null);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void givenEmptyStrings_whenReadRacers_thenStringIndexOutOfBoundsException() {
		reader.readRacers("", "", "");
	}

	@Test
	public void givenFiles_whenReadRacers_thenParcedRacersList() {
		Racer racerOne = new Racer("FAM", "Fernando Alonso", "MCLAREN RENAULT",
				new LapTime(of(2018, 5, 24, 12, 13, 4, 512000000), of(2018, 5, 24, 12, 14, 17, 169000000)));
		Racer racerTwo = new Racer("NHR", "Nico Hulkenberg", "RENAULT",
				new LapTime(of(2018, 5, 24, 12, 2, 49, 914000000), of(2018, 5, 24, 12, 4, 2, 979000000)));
		List<Racer> expected = asList(racerOne, racerTwo);

		List<Racer> actual = reader.readRacers("start.log", "end.log", "abbreviations.txt");

		assertEquals(expected, actual);
	}
}