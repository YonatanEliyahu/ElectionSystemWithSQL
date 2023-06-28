import java.io.Serializable;
import java.util.ArrayList;

public class genSet<T extends Citizen>implements Serializable  {

	private ArrayList<T> mySet;
	
	public genSet() {
		mySet = new ArrayList<T>();
	}
	
	public boolean add(T newObj) {
		if (!mySet.contains(newObj)){
			mySet.add(newObj);
			return true;
		}
		return false;
	}
	
	public int size() {
		return mySet.size();
	}
	
	public boolean remove(Object o) {
		return mySet.remove(o);
	}

	public ArrayList<T> getMySet() {
		return mySet;
	}

	public T get(int index) {	
		return mySet.get(index);
	}

	@Override
	public String toString() {
		return  mySet.toString();
	}	

}

