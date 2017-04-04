import java.util.HashMap;
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
			Scanner inp = new Scanner("inp3.txt");
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
		for (Integer name: hashmap.keySet()){

            String key =name.toString();
            String value = hashmap.get(name).toString();  
            System.out.println(key + " " + value);  
            } 
	}

	private void add(String x) {
		int key = hashCode(x);
		Word word = new Word(x);
		key = insert(key);
		if (hashmap.containsKey(key))
			hashmap.get(key).setCounter();
		else
			hashmap.put(key, word);

	}

	private int insert(int i) {
		if (hashmap.get(i) == null)
			return i;
		else {
			int j = 0;
			while (hashmap.get(i) != null) {
				j++;
				i = i + (j ^ 2);
			}
		}
		return i;
	}

	private int hashCode(String x) {
		int key = 0;
		for (int i = 0; i < x.length() - 1; i++)
			key += x.charAt(i) + i;
		if (hashmap.size() == 0)
			return key;
		while (key > hashmap.size() - 1)
			key = key % hashmap.size();
		return key;
	}

}
