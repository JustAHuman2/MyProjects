import static org.junit.Assert.*;

import org.junit.Test;

public class AnagramTest {

	private Anagram anagram = new Anagram();

	@Test
	public void givenNull_whenReverseText_thenEmptyText() {
		String actual = anagram.reverseText(null);
		assertEquals("", actual);
	}

	@Test
	public void givenEmptyText_whenReverseText_thenEmptyText() {
		String actual = anagram.reverseText("");
		assertEquals("", actual);
	}

	@Test
	public void givenSpaces_whenReverseText_thenEmptyText() {
		String actual = anagram.reverseText("    ");
		assertEquals("", actual);
	}

	@Test
	public void givenText_whenReverseText_thenReversedText() {
		String actual = anagram.reverseText("ab cde fghj");
		assertEquals("ba edc jhgf", actual);
	}

	@Test
	public void givenLiteralsAndNonliterals_whenReverseText_thenReversedTextNonliteralsOnSameIndex() {
		String actual = anagram.reverseText("abc12 3def4,  !56");
		assertEquals("cba12 3fed4,  !56", actual);
	}

	@Test
	public void givenNonLiterals_whenReverseText_thenNonLiteralsSameOrder() {
		String actual = anagram.reverseText("12345");
		assertEquals("12345", actual);
	}
}