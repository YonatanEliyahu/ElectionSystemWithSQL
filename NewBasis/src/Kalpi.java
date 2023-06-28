import java.io.Serializable;
import java.util.ArrayList;

public class Kalpi<T extends Citizen> implements Serializable {

	private static int counter;
	private int id;
	private String location;
	private genSet<T> voterRegister;
	private float percentageOfVoters;
	private ArrayList<String> parties;
	private ArrayList<Integer> numOfVotes;
	
	public Kalpi(String location , String type) {
		this.id = counter++;
		this.location = location;
		this.voterRegister = new genSet<T>();
		this.percentageOfVoters = 0;
		this.parties = new ArrayList<>();
		this.numOfVotes = new ArrayList<>();
		
	}
	
	public Kalpi(int id, String location) {
		this.id = id;
		this.location = location;
		this.voterRegister = new genSet<T>();
		this.percentageOfVoters = 0;
		this.parties = new ArrayList<>();
		this.numOfVotes = new ArrayList<>();
	}

	public Kalpi() {
		super();
	}

	public void addVotePlace() {
		this.numOfVotes.add(0);

	}
	public void addParty(String name) {
		this.parties.add(name);
	}
	
	public void addVotes(String party , int votes) {
		int index = this.parties.indexOf(party);
		this.numOfVotes.add(index, votes);
	}

	public String getLocation() {
		return location;
	}
	public ArrayList<T> getCitizens() {
		return this.voterRegister.getMySet();
	}
	public void setLocation(String location) {
		this.location = location;
	}


	public void setVoterRegister(genSet<T> voterRegister) {
		this.voterRegister = voterRegister;
	}
	
	public static void setCounter()
	{
		counter++;
	}
	
	public static void setCounter(int newCounter)
	{
		counter=newCounter+1;
	}

	public static int getCounter() {
		return counter;
	}

	public int getId() {
		return id;
	}


	public float getPercentageOfVoters() {
		return percentageOfVoters;
	}


	public ArrayList<String> getParties() {
		return parties;
	}


	public ArrayList<Integer> getNumOfVotes() {
		return numOfVotes;
	}


	public boolean addCitizen(T c) {
		return voterRegister.add(c);
	}

	

	public Citizen getCitizenById(String id) {
		for(Citizen voter : this.voterRegister.getMySet())
		{
			if (voter.getId().equals(id) == true)
				
				return  voter;
		}
		return null;
	}
	
		
	
	@Override
	public String toString() {
		return " - "  + " ID kalpi: " + getId() + " location: " + getLocation();

	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null)return false;
		try {
		    if(getId() == ((Kalpi)obj).getId())return true;
		    }catch(Exception e){}
	return false;
	} 






}

