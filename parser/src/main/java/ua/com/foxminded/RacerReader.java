package ua.com.foxminded;

import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import java.util.List;
import java.util.Map;

public class RacerReader {

	private static final String TIME_FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";

	public List<Racer> readRacers(String startTimes, String finishTimes, String abbreviations) {
		Map<String, LocalDateTime> starts = parseStream(startTimes);
		Map<String, LocalDateTime> finishes = parseStream(finishTimes);
		return toStream(abbreviations).map(line -> line.split("_")).map(
				line -> new Racer(line[0], line[1], line[2], new LapTime(starts.get(line[0]), finishes.get(line[0]))))
				.collect(toList());
	}

	private Map<String, LocalDateTime> parseStream(String lines) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
		return toStream(lines).collect(toMap(l -> (String) l.substring(0, 3),
				l -> (LocalDateTime) LocalDateTime.parse(l.substring(3), formatter)));
	}

	private Stream<String> toStream(String fileName) {
		return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))
				.lines();
	}
}