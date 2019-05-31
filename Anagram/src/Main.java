/*
 *	@author Stepanov Dmitry 03.10.2018
 *	This application reverses all the words of input text:
 *	E.g. "abcd efgh" => "dcba hgfe"
 *	All non-letter symbols stays on the same places:
 *	E.g. "a1bcd efg!h" => "d1cba hgf!e"	
*/

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		Anagram anagram = new Anagram();		
		System.out.print(anagram.reverseText(input));
		scanner.close();
	}
}