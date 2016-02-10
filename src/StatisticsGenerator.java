import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsGenerator {

	public static void main(String[] args) {
		try {
			reader = new FileReader("macbeth.txt");
			in = new BufferedReader(reader);
			writer = new FileWriter("results.txt");
			while (true) {
				curLine = in.readLine();
				if (curLine == null)
					break;
				ArrayList<String> playWords = getWords(curLine);
				for (String curWord : playWords) {
					if (wordCount.containsKey(curWord)) {
						wordCount.replace(curWord, wordCount.get(curWord) + 1);
					}else{
						wordCount.put(curWord, 1);
					}
				}
			}
			
			//Prints hashmap
			for (String keyValue : wordCount.keySet()) {
				writer.write(keyValue);
				String value = ": " + wordCount.get(keyValue) +"\n";
				writer.write(value);
				System.out.print(keyValue);
				System.out.print(value);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Returns words in string in an ArrayList
	private static ArrayList<String> getWords(String play) {
		play = play.toLowerCase();
		ArrayList<String> words = new ArrayList<String>();
		int curWordIndex = 0;
		while (true) {
			// curWordIndex = play.indexOf(' ', curWordIndex);
			int newWordIndex = play.indexOf(' ', curWordIndex + 1);
			if (newWordIndex == -1) {
				newWordIndex = play.length();
			}

			String wordToAdd = play.substring(curWordIndex, newWordIndex);
			wordToAdd = wordToAdd.trim();

			if (wordToAdd.length() > 0) {
				if (wordToAdd.substring(0, 1).equals("(") || wordToAdd.substring(0, 1).equals("'")) {
					wordToAdd = wordToAdd.substring(1);
					// System.out.println("foo");
				}
				int length = wordToAdd.length();
				if (wordToAdd.substring(length - 1, length).equals(")")
						|| wordToAdd.substring(length - 1, length).equals(",")
						|| wordToAdd.substring(length - 1, length).equals(".")
						|| wordToAdd.substring(length - 1, length).equals("!")) {
					wordToAdd = wordToAdd.substring(0, length - 1);
					// System.out.println("foo");
				}
			}

			words.add(wordToAdd);
			curWordIndex = newWordIndex;
			if (newWordIndex == play.length())
				break;
		}

		return words;
	}

	private static FileWriter writer;
	private static FileReader reader;
	private static BufferedReader in;
	private static HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
	private static String curLine = new String();
}
