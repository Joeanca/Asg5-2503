import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.io.FileReader;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;

/**
 * @author Jorge Castano date: April 5th, 2017 version: 1.0 This class allows
 *         for the reading of a text file, it sorts through removing anything
 *         which is not a letter proceeds to count how many times a word occurs
 *         in a file, what a word's length is and prints statistics which
 *         include: the amount of total words, unique words, prepositions, 20
 *         most frequent, most frequent, least frequent and avg frequency
 *         longest word, shortest word, and average length of word. Then a
 *         printout of all the words sorted alphabetically.
 */
public class A5 {

	private int totalCounter, stopCounter, averageFrequency, averageLength;
	private Stack<Word> stack = new Stack<Word>();
	private HashMap<Integer, Word> hashmap;
	private String[] stopWords = { "a", "about", "all", "am", "an", "and", "any", "are", "as", "at", "be", "been",
			"but", "by", "can", "cannot", "could", "did", "do", "does", "else", "for", "from", "get", "got", "had",
			"has", "have", "he", "her", "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it", "its", "like",
			"more", "me", "my", "no", "now", "not", "of", "on", "one", "or", "our", "out", "said", "say", "says", "she",
			"so", "some", "than", "that", "thats", "the", "their", "them", "then", "there", "these", "they", "this",
			"to", "too", "us", "upon", "was", "we", "were", "what", "with", "when", "where", "which", "while", "who",
			"whom", "why", "will", "you", "your", "up", "down", "left", "right", "man", "woman", "would", "should",
			"dont", "after", "before", "im", "men" };
	private TreeMap alphaTree = new TreeMap();
	private TreeMap lengthTree = new TreeMap();
	private TreeMap countTree = new TreeMap();

	/**
	 * main method start here.
	 */
	public static void main(String[] args) {
		A5 asg = new A5();
		asg.run();
	}

	/**
	 * this method reads in the words and calls on other to perform operations.
	 */
	private void run() {
		try {
			// FileReader file = new FileReader("inp3.txt");
			// Scanner inp = new Scanner(file);
			Scanner inp = new Scanner(System.in);
			String word = "";

			while (inp.hasNext()) {
				// removes all non-alpha characters.
				word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");
				if (!word.equals("")) {
					totalCounter++;
					Word temp = new Word(word);
					// checks if Word exists then adds to stack if it doesn't
					if (!doesItExist(temp))
						// adds to stack
						stack.push(temp);
				}
			}
			// close file
			inp.close();
			// to transfer from stack to HashMap
			createMap();
			// to delete from HashMap the stopWords provided
			deleteStopWords();
			// to fill the trees from the hashMap.
			fillTrees();
			// main print call (note there are overloaded methods for print()
			print();
		} catch (Exception e) {
			System.out.println(e);// "Error opening file"
		}

	}

	/**
	 * @param t
	 *            takes in a Word object which is compared against existing
	 *            element in the stack which augment count
	 * @return a boolean which informs caller whether the word exists or not.
	 */
	private boolean doesItExist(Word temp) {
		String chkWord = temp.getWord();
		for (Word x : stack) {
			if (x.getWord().equals(chkWord)) {
				x.setCounter();
				return true;
			}
		}
		return false;
	}

	/**
	 * transfers the stack into the hashmap for further processing
	 */
	private void createMap() {
		// declares the size of the hashmap as the size of the stack which is
		// the amount of words in the file.
		hashmap = new HashMap<Integer, Word>(stack.size());
		for (int i = 0; i < stack.size(); i++) {
			Word temp = stack.get(i);
			hashmap.put(temp.getWord().hashCode(), temp);
		}

	}

	/**
	 * checks the hashmap for words contained in the stopWords array which are
	 * to be deleted if they exist
	 */
	private void deleteStopWords() {
		for (int i = 0; i < stopWords.length; i++) {
			String value = stopWords[i];
			int key = value.hashCode();
			if (hashmap.remove(key) != null)
				stopCounter++;
		}
	}

	/**
	 * takes the content of the hashmap and places it into the treemaps
	 * according to its custom sort as defined in the Word class
	 */
	private void fillTrees() {
		Word temp = new Word("temp");
		countTree = new TreeMap(temp.DECENDING_ORDER);
		lengthTree = new TreeMap(temp.LENGTH_ORDER);
		Collection<Word> collection = hashmap.values();
		for (Word i : collection) {
			alphaTree.put(i, i);
			lengthTree.put(i, i);
			countTree.put(i, i);
		}

	}

	/**
	 * @param t
	 *            Takes in a treemap in order to find the highest value within
	 * @return the Word with the highest value (count or length) depending on
	 *         tree passed in.
	 */
	private Word getMost(TreeMap t) {
		{
			Collection<Word> collection = t.values();
			Iterator<Word> iterateMe = collection.iterator();
			return (Word) iterateMe.next();
		}
	}

	/**
	 * @param t
	 *            Takes in a treemap in order to find the lowest value within
	 * @return the Word with the lowest value (count or length) depending on
	 *         tree passed in.
	 */
	private Word getLast(TreeMap t) {
		Collection<Word> collection = t.values();
		Word temp = new Word("");
		for (Word i : collection) {
			temp = i;
		}
		return temp;
	}

	/**
	 * This method is the print handler note there are overloaded versions of
	 * this method which take in a tree and a tree, int.
	 */
	private void print() {
		System.out.println("------\n");
		System.out.println("Total Words: " + totalCounter);
		System.out.println("Stop Words: " + stopCounter);
		System.out.println("Unique Words: " + hashmap.size());
		System.out.println("\n------\n");
		System.out.println("20 Most Frequent");
		// calls on overloaded print asks for 20 to be printed
		print(countTree, 20);
		System.out.println("\n------\n");
		System.out.println("Statistics for Word Frequencies");
		System.out.println("The most frequent word is " + getMost(countTree));
		System.out.println("The least frequent word is " + getLast(countTree));
		// calls to average out method
		average();
		System.out.println("The average word frequency is " + averageFrequency);
		System.out.println("\n------\n");
		System.out.println("Statistics for Word Length");
		System.out.println("The longest word is " + getMost(lengthTree));
		System.out.println("The least frequent word is " + getLast(lengthTree));
		System.out.println("The average word length is " + 6);
		System.out.println("\n------\n");
		System.out.println("All Words");
		// calls to print the words alphabetically
		print(alphaTree);
		System.out.println("\n------");
	}

	/**
	 * @param t
	 *            Takes in a treeMap and prints its values in order
	 */
	private void print(TreeMap t) {
		Collection<Word> collection = t.values();
		for (Word i : collection) {
			System.out.println(i);
		}
	}

	/**
	 * @param t
	 * @param i
	 *            Takes in a treeMap and prints i elements
	 * 
	 */
	private void print(TreeMap t, int i) {
		Collection<Word> collection = t.values();
		Iterator<Word> iterateMe = collection.iterator();
		for (int j = 0; (j < i) && iterateMe.hasNext(); j++) {
			System.out.println(iterateMe.next());
		}
	}

	/**
	 * This method reads the contents of the hashmap, calculates the combined
	 * frequency and combined length and divides it by the amount of words found
	 * within
	 */
	private void average() {
		Collection<Word> collection = alphaTree.values();
		for (Word i : collection) {
			averageFrequency += i.getCounter();
			averageLength += i.getWord().length();
		}
		averageFrequency /= hashmap.size();
		averageLength /= hashmap.size();
	}

}
