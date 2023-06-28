import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public interface MainUI {

	public boolean addKalpi(Kalpi<?> k);
	public boolean addCitizen(Citizen c);
	public void saveFile() throws FileNotFoundException, IOException;
	public void addPartyMember(String partyName, Citizen citizen);
	public void addPartyMember(Party party, Citizen citizen);
	public ArrayList<Kalpi> getKalpies();
	public ArrayList<Party> getparties(); 
	public void election(Scanner e) throws Exception;
	public void showTheResult();
	
}	