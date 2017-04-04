import java.util.HashMap;
import java.io.FileReader;
import java.util.*;

public class A5 {

	private int totalCounter, stopCounter;
	private HashMap<Integer, Word> hashmap = new HashMap<Integer, Word>();

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
					// add to list
					add(word);
				}
			}
			inp.close();
			deleteStopWords();
		} catch (Exception e) {
			System.out.println(e);// "Error opening file"
		}

	}

	private void deleteStopWords() {
		for (Integer name : hashmap.keySet()) {
			String key = name.toString();
			String value = hashmap.get(name).toString();
			System.out.println(key + " " + value);
		}
	}

	private void add(String x) {
		int key = hashCode(x);
		Word word = new Word(x);
		key = insert(key, x, 1);
		if (key!=-1)
			hashmap.put(key, word);

	}

	private int insert(int i, String x, int j) {

		if (hashmap.get(i) == null)
			return i;
		else if (hashmap.get(i).getWord().equals(x)){
			hashmap.get(i).setCounter();
			return -1;
		}		
		else {
			i = i + (j ^ 2);
			insert(i, x, j++);
		}
		return i;
	}

	private int hashCode(String x) {
		int key = 0;
		for (int i = 0; i < x.length() - 1; i++)
			key += x.charAt(i) + i;
		if (hashmap.size() == 0)
			return key;
		return key;
	}

}
