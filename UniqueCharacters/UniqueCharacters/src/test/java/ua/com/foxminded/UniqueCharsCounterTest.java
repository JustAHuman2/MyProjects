package ua.com.foxminded;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class UniqueCharsCounterTest {

	private UniqueCharsCounter uniqueCharsCounter;

	@Before
	public void setup() {
		uniqueCharsCounter = new UniqueCharsCounter();
	}

	@Test
	public void givenEmptyString_whenCountChars_thenEmptyString() {
		Map<Character, Long> expected = new HashMap<>();

		Map<Character, Long> actual = uniqueCharsCounter.countChars("");

		assertEquals(expected, actual);
	}

	@Test
	public void givenSpaces_whenCountChars_thenResult() {
		Map<Character, Long> expected = new HashMap<>();
		expected.put(' ', 7l);

		Map<Character, Long> actual = uniqueCharsCounter.countChars("       ");

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumbers_whenCountChars_thenResult() {
		Map<Character, Long> expected = new HashMap<>();
		expected.put('0', 1l);
		expected.put('1', 3l);
		expected.put('2', 2l);

		Map<Character, Long> actual = uniqueCharsCounter.countChars("012121");

		assertEquals(expected, actual);
	}

	@Test
	public void givenText_whenCountChars_thenResult() {
		Map<Character, Long> expected = new HashMap<>();
		expected.put('H', 1l);
		expected.put('e', 1l);
		expected.put('l', 3l);
		expected.put('o', 2l);
		expected.put(' ', 1l);
		expected.put('w', 1l);
		expected.put('r', 1l);
		expected.put('d', 1l);
		expected.put('!', 1l);

		Map<Character, Long> actual = uniqueCharsCounter.countChars("Hello world!");

		assertEquals(expected, actual);
	}
}