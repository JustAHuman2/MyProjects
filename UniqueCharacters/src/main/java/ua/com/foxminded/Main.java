package ua.com.foxminded;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("Enter string: ");
		Scanner scanner = new Scanner(System.in);
		CachedCharsCounter counter = new CachedCharsCounter(new UniqueCharsCounter());
		while (scanner.hasNext()) {
			counter.countChars(scanner.nextLine())
					.forEach((key, value) -> System.out.println("\"" + key + "\"" + " - " + value));
		}
		scanner.close();
	}
}