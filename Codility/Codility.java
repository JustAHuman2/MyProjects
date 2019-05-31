/*
 *	@author Stepanov Dmitry 03.04.2019
 *	This application splits input into words and sort them by the length, removing anagrams
 *	Was made for Codility platform, that's why everything in one class
*/

package ua.com.foxminded1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Codility {
	public static void main(String[] args) {

		String S = "abc abcd acb abcd dbc";
		String[] words = S.split(" ");
		Comparator<String> stringLengthComparator = new StringLengthSort();
		Arrays.sort(words, stringLengthComparator);
		ArrayList<String> noAnagram = new ArrayList<>();
		noAnagram.add(words[0]);

		for (int i = 1; i < words.length; i++) {
			if (words[i - 1].length() < words[i].length()) {
				noAnagram.add(words[i]);
			}
			if (words[i - 1].length() == words[i].length()) {
				if (isAnagram(words[i - 1], words[i]) == false) {
					checkAndAdd(noAnagram, words[i]);
				}
			}
		}
		for (int i = 1; i < words.length; i++) {
		}
	}

	public static boolean checkAndAdd(ArrayList<String> noAnagram, String word) {
		for (int i = 0; i < noAnagram.size(); i++) {
			if (noAnagram.get(i).length() == word.length()) {
				if (isAnagram(word, noAnagram.get(i)) == true) {
					return false;
				}
			}
		}
		noAnagram.add(word);
		return true;
	}

	public static boolean isAnagram(String firstWord, String secondWord) {
		char[] word1 = firstWord.toCharArray();
		char[] word2 = secondWord.toCharArray();
		Arrays.sort(word1);
		Arrays.sort(word2);
		return Arrays.equals(word1, word2);
	}
}

class StringLengthSort implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		if (o1.length() > o2.length()) {
			return 1;
		} else {
			if (o1.length() < o2.length()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}