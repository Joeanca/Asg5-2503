import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.io.FileReader;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;

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

	public static void main(String[] args) {
		A5 asg = new A5();
		asg.run();
	}

	private void run() {
		try {
			FileReader file = new FileReader("inp3.txt");
			Scanner inp = new Scanner(file);
			String word = "";

			while (inp.hasNext()) {
				word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");
				if (!word.equals("")) {
					totalCounter++;
					Word temp = new Word(word);
					// add to list
					if (!doesItExist(temp))
						stack.push(temp);
				}
			}
			inp.close();
			createMap();
			deleteStopWords();
			fillTrees();

			print();
		} catch (Exception e) {
			System.out.println(e);// "Error opening file"
		}

	}

	private void print() {
		System.out.println("------");
		System.out.println();
		System.out.println("Total Words: " + totalCounter);
		System.out.println("Stop Words: " + stopCounter);
		System.out.println("Unique Words: " + hashmap.size());
		System.out.println();
		System.out.println("------");
		System.out.println();
		System.out.println("20 Most Frequent");
		print(countTree, 20);
		System.out.println();
		System.out.println("------");
		System.out.println();
		System.out.println("Statistics for Word Frequencies");
		System.out.println("The most frequent word is " + getMost(countTree));
		System.out.println("The least frequent word is " + getLast(countTree));
		average();
		System.out.println("The average word frequency is " + averageFrequency);
		System.out.println();
		System.out.println("------");
		System.out.println();
		System.out.println("Statistics for Word Length");
		System.out.println("The longest word is " + getMost(lengthTree));
		System.out.println("The least frequent word is " + getLast(lengthTree));
		System.out.println("The average word length is " + 6);
		System.out.println();
		System.out.println("------");
		System.out.println();
		System.out.println("All Words");
		print(alphaTree);
		System.out.println();
		System.out.println("------");
	}

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

	private void createMap() {
		hashmap = new HashMap<Integer, Word>(stack.size());
		for (int i = 0; i < stack.size(); i++) {
			Word temp = stack.get(i);
			hashmap.put(temp.getWord().hashCode(), temp);
		}

	}

	private void deleteStopWords() {
		for (int i = 0; i < stopWords.length; i++) {
			String value = stopWords[i];
			int key = value.hashCode();
			if (hashmap.remove(key) != null)
				stopCounter++;
		}
	}

	private void fillTrees() {
		Word temp = new Word("temp");
		countTree = new TreeMap(temp.DECENDING_ORDER);
		lengthTree = new TreeMap(temp.LENGTH_ORDER);
		Collection<Word> coll = hashmap.values();
		for (Word i : coll) {
			alphaTree.put(i, i);
			lengthTree.put(i, i);
			countTree.put(i, i);
		}

	}

	private Word getMost(TreeMap t) {
		{
			Collection<Word> coll = t.values();
			Iterator<Word> it = coll.iterator();
			return (Word) it.next();
		}
	}

	private Word getLast(TreeMap t) {
		Collection<Word> coll = t.values();
		Word temp = new Word("");
		for (Word i : coll) {
			temp = i;
		}
		return temp;
	}

	private void print(TreeMap t) {
		Collection<Word> coll = t.values();
		for (Word i : coll) {
			System.out.println(i);
		}
	}

	private void print(TreeMap t, int i) {
		Collection<Word> coll = t.values();
		Iterator<Word> it = coll.iterator();
		for (int j = 0; (j < i) && it.hasNext(); j++) {
			System.out.println(it.next());
		}
	}

	private void average() {
		Collection<Word> coll = alphaTree.values();
		for (Word i : coll) {
			averageFrequency += i.getCounter();
			averageLength += i.getWord().length();
		}
		averageFrequency /= hashmap.size();
		averageLength /= hashmap.size();
	}

}
