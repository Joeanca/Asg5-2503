
public class HashTable<T>{
	private Object[] hashList = new Object[100];
		
	public int hashIt(String toHash){
		return 0;
	}
	
	private void increaseSize(){
		Word[] temp = new Word[((hashList.length-1)/2)+(hashList.length-1)];
		System.arraycopy(hashList, 0, temp, 0, hashList.length);

	}

	public void add(T w) {
		hashIt(w.)
	}

	public void setCounter(T w) {
		//Look for the word in the hash and increase counter if yes
		return true;
	}

	//Look for the word and return yes if found
	public boolean find(Word w) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
