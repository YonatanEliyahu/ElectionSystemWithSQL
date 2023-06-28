
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;

public class Party implements Serializable {


	public enum SideType {
		Left, Center, Right
	};

	private String name;
	private SideType sidetype;
	private LocalDate date;
	private  ArrayList<PartyMember> candidatesList;


	public Party(String name, String sidetype) {
		this.name = name;
		this.sidetype = SideType.valueOf(sidetype);
		this.date = LocalDate.now();
		this.candidatesList = new ArrayList<>();
	}



	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public SideType getSidetype() {
		return sidetype;
	}


	public void setSidetype(SideType sidetype) {
		this.sidetype = sidetype;
	}


	public LocalDate getDate() {
		return date;
	}

	public ArrayList<PartyMember> getCandidatesList() {
		return candidatesList;
	}


	@Override
	public String toString() {
		return  "Name: " + getName() + " side type: " + getSidetype() + "  Creation day: " + getDate();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null)return false;
		try {
		    if(getName() == ((Party)obj).getName())return true;
		    }catch(Exception e){}
	return false;
	} 
}