package ua.com.foxminded;

import static java.util.stream.Collectors.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Function;

public class UniqueCharsCounter implements CharsCounter {

	public Map<Character, Long> countChars(String text) {
		return text.chars().mapToObj(c -> (char) c)
				.collect(groupingBy(Function.identity(), LinkedHashMap::new, counting()));
	}
}