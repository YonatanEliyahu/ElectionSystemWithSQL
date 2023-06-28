import java.io.FileInputStream;
import java.sql.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ElectionRotation implements Serializable, MainUI {
	private ArrayList<Kalpi> kalpies = new ArrayList<Kalpi>();
	private ArrayList<Party> parties = new ArrayList<Party>();

	public void reset() {
		this.kalpies.clear();
		this.parties.clear();
	}
	
	public boolean addKalpi(Kalpi<?> k) {
		return kalpies.add(k);
	}

	public boolean addKalpi(int id, String location) {
		Kalpi k = new Kalpi(id, location);
		return kalpies.add(k);
	}

	public void saveFile() throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("rotation.dat"));
		outFile.writeObject(this);
		outFile.close();
	}

	public boolean addCitizen(Citizen c) {
		Kalpi<Citizen> k = kalpies.get(c.getIdKalpi()-1);
		return k.addCitizen(c);
	}
	
	public Citizen getCitizenById(String ID)
	{
		Citizen res=null;
		for(Kalpi k : this.getKalpies())
		{
			res =k.getCitizenById(ID);
			if(res !=null)
				return res;
		}
		return res;
	}

	public boolean addParty(String Pname, String side) {
		Party p = new Party(Pname, side);
		for (Kalpi kalpi : kalpies) {
			kalpi.addVotePlace();
			kalpi.addParty(p.getName());
		}
		return parties.add(p);
	}

	public boolean addParty(Party p) {
		for (Kalpi kalpi : kalpies) {
			kalpi.addVotePlace();
			kalpi.addParty(p.getName());
		}
		return parties.add(p);
	}

	public Kalpi getKalpiById(int idKalpi) {
		for (Kalpi kalpi : kalpies) {
			if (kalpi.getId() == idKalpi)
				return kalpi;
		}
		return null;
	}

	public void addPartyMember(String partyName, Citizen citizen) {
		for (Party party : parties) {
			if (party.getName().equals(partyName)) {
				PartyMember p;
				try {
					p = new PartyMember(citizen.getName(), citizen.getAge(), citizen.getId(), citizen.getYearOfBrith(),
							citizen.getIdKalpi(), partyName);
					for (PartyMember a : party.getCandidatesList()) {
						if (party.getCandidatesList().contains(p))
							;
						System.out.println("Existing Party Member!");
						return;

					}
					System.out.println("The party member added successfully!");
					System.out.println(
							"----------------------------------------------------------------------------------------");
					party.getCandidatesList().add(p);
				} catch (IdException | IdKalpiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
			return;
		}
	}

	public void addPartyMember(Party party, Citizen citizen) {

		PartyMember p;
		try {
			p = new PartyMember(citizen.getName(), citizen.getAge(), citizen.getId(), citizen.getYearOfBrith(),

					citizen.getIdKalpi(),

					party.getName());
			for (PartyMember a : party.getCandidatesList()) {
				if (party.getCandidatesList().contains(p))
					;
				System.out.println("Existing Party Member!");
				return;
			}
			System.out.println("The party member added successfully!");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			party.getCandidatesList().add(p);
		} catch (IdException | IdKalpiException e) {
			e.printStackTrace();

		}
	}

	public ArrayList<Kalpi> getKalpies() {
		return kalpies;
	}

	public ArrayList<Party> getparties() {
		return parties;

	}

	public void election(Scanner e) throws Exception {
		for (Kalpi kalpi : kalpies) {
			ArrayList<Citizen> arr = kalpi.getCitizens();
			for (Citizen citizen : arr) {
				if (!mysqlconn.alreadyVoted(citizen.getId())) {
					System.out.println(citizen.getName() + " do you want to vote?(Y , N) ");
					boolean decision;
					char c = e.next().charAt(0);
					decision = ((c == 'Y') || (c == 'y')) ? true : false;
					if (decision) {
						System.out.println("Which party do you want to vote to: ");
						for (Party party : parties) {
							System.out.println("- " + party.getName());
						}
						System.out.println("Enter the name of party: ");
						String partyName = e.next();
						InsertToSql.vote_for(partyName, citizen.getId());
						kalpi.addVotes(partyName, 1);
						System.out.println(
								"----------------------------------------------------------------------------------------");

					}
				}
			}
		}
	}

	public void showTheResult() {
		ArrayList<String> partyList = null;
		ArrayList<Integer> allTheResult = new ArrayList<Integer>();
		System.out.println("The results of the vote by kalpi:  ");
		for (Kalpi kalpi : kalpies) {
			partyList = kalpi.getParties();
			ArrayList<Integer> voteList = kalpi.getNumOfVotes();
			System.out.println("The results of the vote at the kalpi:  " + kalpi.getId());
			for (int i = 0; i < partyList.size(); i++) {
				if (allTheResult.size() <= i) {
					allTheResult.add(0);
				}
				int updatedValue = allTheResult.get(i) + voteList.get(i);
				allTheResult.set(i, updatedValue);
				System.out.print(partyList.get(i) + " ");
				System.out.println(voteList.get(i));
			}
			System.out.println(
					"----------------------------------------------------------------------------------------");

		}
		System.out.println("Summary of voting results at all the kalpis are:  ");
		System.out.println(partyList);
		System.out.println(allTheResult);
		System.out.println("----------------------------------------------------------------------------------------");
	}

	public boolean searchParty(String name) {
		for (Party party : parties) {
			if (party.getName().contains(name))
				return true;
		}
		return false;

	}

	public boolean isCitizen(String id) {
		for (Kalpi kalpi : kalpies) {
			ArrayList<Citizen> arr = kalpi.getCitizens();
			for (Citizen citizen : arr) {
				if (citizen.getId().equals(id))
					return true;
			}
		}
		return false;
	}

	public boolean searchKalpi(int idKalpi) {
		for (Kalpi kalpi : kalpies) {
			if (kalpi.getId() == idKalpi)
				return true;
		}
		return false;
	}
}