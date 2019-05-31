public class Anagram {

	static final String SPACE = " ";

	public String reverseText(String text) {
		if (text == null) {
			return "";
		}
		String[] words = text.split(SPACE);
		StringBuilder result = new StringBuilder();
		int counter = 0;
		for (String word : words) {
			result.append(reverseWord(word));
			counter++;
			if (counter < words.length) {
				result.append(SPACE);
			}
		}
		return result.toString();
	}

	private String reverseWord(String word) {
		char[] chars = word.toCharArray();
		int leftIndex = 0;
		int rightIndex = word.length() - 1;
		while (leftIndex < rightIndex) {
			if (!Character.isLetter(chars[leftIndex])) {
				leftIndex++;
			} else if (!Character.isLetter(chars[rightIndex])) {
				rightIndex--;
			} else {
				char leftSymbol = chars[leftIndex];
				chars[leftIndex] = chars[rightIndex];
				chars[rightIndex] = leftSymbol;
				leftIndex++;
				rightIndex--;
			}
		}
		return new String(chars);
	}
}