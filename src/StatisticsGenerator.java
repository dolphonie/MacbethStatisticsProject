import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
				// Check for act and scene numbers
				if (playWords.get(0).equals("act")) {
					actNumber++;
					sceneNumber = -1;
					countBySection.add(new ArrayList<HashMap<String, Integer>>());
					continue;
				}

				if (playWords.get(0).equals("scene")) {
					sceneNumber++;
					countBySection.get(countBySection.size() - 1).add(new HashMap<String, Integer>());
					continue;
				}

				// Iterate through all words in line
				for (String curWord : playWords) {
					// Whole play word count iterator
					incrementWordInMap(wordCountWholePlay, curWord);

					// Scene word count iterator
					HashMap<String, Integer> curSceneMap = countBySection.get(actNumber).get(sceneNumber);
					incrementWordInMap(curSceneMap, curWord);
				}
				
			}

			wordCountWholePlay = sortByComparator(wordCountWholePlay);
			// Prints wholePlayWordCount
			for (String keyValue : wordCountWholePlay.keySet()) {
				writer.write(keyValue);
				String value = ": " + wordCountWholePlay.get(keyValue) + "\n";
				writer.write(value);
				System.out.print(keyValue);
				System.out.print(value);
			}
			writer.write("\n\n\n\n");
			for (ArrayList<HashMap<String, Integer>> scene : countBySection) {
				for (HashMap<String, Integer> wordMap : scene) {
					writer.write("\n\n\n\n");
					writer.write("Act " + countBySection.indexOf(scene) + "\nScene: " + scene.indexOf(wordMap) + "\n");
					wordMap = sortByComparator(wordMap);
					for (String keyValue : wordMap.keySet()) {
						writer.write(keyValue);
						String value = ": " + wordMap.get(keyValue) + "\n";
						writer.write(value);
						System.out.print(keyValue);
						System.out.print(value);
					}
				}
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

			// remove punctuation
			if (wordToAdd.length() > 0) {
				for (int i = 0; i < 3; i++) {
					if (wordToAdd.substring(0, 1).equals("(") || wordToAdd.substring(0, 1).equals("'")
							|| wordToAdd.substring(0, 1).equals("\"") || wordToAdd.substring(0, 1).equals("[")) {
						wordToAdd = wordToAdd.substring(1);
						// System.out.println("foo");
					}
					int length = wordToAdd.length();
					if (wordToAdd.substring(length - 1, length).equals(")")
							|| wordToAdd.substring(length - 1, length).equals(",")
							|| wordToAdd.substring(length - 1, length).equals(".")
							|| wordToAdd.substring(length - 1, length).equals("!")
							|| wordToAdd.substring(length - 1, length).equals("?")
							|| wordToAdd.substring(length - 1, length).equals(";")
							|| wordToAdd.substring(length - 1, length).equals("\"")
							|| wordToAdd.substring(length - 1, length).equals("]")) {
						wordToAdd = wordToAdd.substring(0, length - 1);
						// System.out.println("foo");
					}
				}
			}

			words.add(wordToAdd);
			curWordIndex = newWordIndex;
			if (newWordIndex == play.length())
				break;
		}

		return words;
	}

	// returns -1 if fails
	private static int romanNumeralConversion(String numeral) {
		numeral = numeral.toLowerCase();
		switch (numeral) {
		case "i":
			return 1;
		case "ii":
			return 2;
		case "iii":
			return 3;
		case "iv":
			return 4;
		case "v":
			return 5;
		case "vi":
			return 6;
		case "vii":
			return 7;
		case "viii":
			return 8;
		case "ix":
			return 9;
		case "x":
			return 10;
		default:
			return -1;
		}
	}

	private static void incrementWordInMap(HashMap<String, Integer> map, String putThis) {
		if (map.containsKey(putThis)) {
			map.replace(putThis, map.get(putThis) + 1);
		} else {
			map.put(putThis, 1);
		}
	}

	 private static HashMap<String, Integer> sortByComparator(HashMap<String, Integer> unsortMap)
	    {

	        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

	        // Sorting the list based on values
	        Collections.sort(list, new Comparator<Entry<String, Integer>>()
	        {
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2)
	            {
	                
	                    return o2.getValue().compareTo(o1.getValue());
	                
	              
	            }
	        });

	        // Maintaining insertion order with the help of LinkedList
	        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	        for (Entry<String, Integer> entry : list)
	        {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }

	        return sortedMap;
	    }

	
	private static int actNumber = -1;
	private static int sceneNumber = -1;
	private static FileWriter writer;
	private static FileReader reader;
	private static BufferedReader in;
	private static ArrayList<ArrayList<HashMap<String, Integer>>> countBySection = new ArrayList<ArrayList<HashMap<String, Integer>>>();
	private static HashMap<String, Integer> wordCountWholePlay = new HashMap<String, Integer>();
	private static String curLine = new String();
}
