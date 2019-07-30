package ua.com.foxminded;

import static java.lang.System.lineSeparator;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Formatter {

	public String format(List<Racer> racers, int topNumber) {
		AtomicInteger index = new AtomicInteger();
		return racers.stream().sorted(Comparator.comparing(Racer::getLapTime))
				.map(r -> formatLine(index.incrementAndGet(), r, topNumber))
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

	private String formatLine(int index, Racer racer, int topNumber) {
		String result = String.format("%s. %-17s | %-25s | %s", index, racer.getName(), racer.getTeam(),
				racer.getLapTime()) + lineSeparator();
		if (index == topNumber) {
			return result + repeatChar('-', result.length() - 1) + lineSeparator();
		}
		return result;
	}

	private String repeatChar(char character, int size) {
		StringBuilder result = new StringBuilder();
		IntStream.range(0, size).forEach(i -> result.append(character));
		return result.toString();
	}
}