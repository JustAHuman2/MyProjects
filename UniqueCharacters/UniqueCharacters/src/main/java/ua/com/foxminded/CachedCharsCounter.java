package ua.com.foxminded;

import java.util.Map;
import java.util.HashMap;

public class CachedCharsCounter implements CharsCounter {

	private final Map<String, Map<Character, Long>> cache = new HashMap<>();
	private CharsCounter charsCounter;

	public CachedCharsCounter(CharsCounter charsCounter) {
		this.charsCounter = charsCounter;
	}

	public Map<Character, Long> countChars(String text) {
		return cache.computeIfAbsent(text, key -> charsCounter.countChars(text));
	}
}