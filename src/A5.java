import java.util.Scanner;

public class A5 {

	private int totalCounter, stopCounter;
	
	
	public static void main(String[] args) {
		A5 asg = new A5();
		asg.run();
	}

	private void run() {
		HashTable<Word> table= new HashTable<Word>();
		try{
			Scanner inp = new Scanner("inp3.txt");
			String word = "";

			while (inp.hasNext()) {
				word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");
				if (!word.equals("")) {
					totalCounter++;
					Word w = new Word(word);
						// add to list
						table.add(w);
				}
			}
			inp.close();
			deleteStopWords();
		}catch (Exception e) {
			System.out.println(e);// "Error opening file"
		}
		

	}
	
	private void deleteStopWords() {
		
	}
	

}
