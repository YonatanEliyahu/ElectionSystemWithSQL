import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Electionmain {

	public final static int REGULAR = 1;
	public final static int COVID = 2;
	public final static int IDF = 3;
	public final static int IDFCOVID = 4;

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		int choise;
		boolean exit = false;
		ElectionRotation rotation = new ElectionRotation();
		InsertToSql in = new InsertToSql();
		mysqlconn sh = new mysqlconn();
		mysqlconn.setBID();

		
		do {
			System.out.println("\n\n\tPlease select an option:\r\n" + "\t\t1 - Add kalpi \r\n"
					+ "\t\t2 - Add citizen \r\n" + "\t\t3 - Add party \r\n" + "\t\t4 - Add citizen for party \r\n"
					+ "\t\t5 - Show all the kalpi \r\n" + "\t\t6 - Show all the citizen \r\n"
					+ "\t\t7 - Show all the parties \r\n" + "\t\t8 – Election \r\n"
					+ "\t\t9 - Show the election results \r\n" + "\t\t10 - insert \r\n"
					+ "\t\t11 - Show data base from sql \r\n" + "\t\t12 - Exit\r\n");

			choise = s.nextInt();

			switch (choise) {

			case 1:

				addKalpi(rotation);
				break;

			case 2:

				addCitizen(rotation);
				break;

			case 3:

				addParty(rotation);

				break;

			case 4:

				addPartyMember(rotation);

				break;

			case 5:
			case 6:
			case 7:
				mysqlconn.readQSL(choise);

				break;

			case 8:
				Scanner e = new Scanner(System.in);
				mysqlconn.setRotation(rotation);
				election(e, rotation);

				break;

			case 9:
				
				showTheResult(rotation);

				break;

			case 10:
				in.insert(rotation);
				break;

			case 11:
				for (int i = 5; i <= 7; i++)
					mysqlconn.readQSL(i);

				break;

			case 12:

				exit = true;

			}
			if(choise>0 && choise<5) {
				InsertToSql.insert(rotation);
			}

		} while (!exit);
		System.out.println(" Good luck! ");
	}

	public static ElectionRotation loadFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("rotation.dat"));
		ElectionRotation rotation = (ElectionRotation) inFile.readObject();
		inFile.close();
		return rotation;
	}

	public static void saveFile(ElectionRotation rotation) throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("rotation.dat"));
		outFile.writeObject(rotation);
		outFile.close();
	}

	private static void showTheResult(ElectionRotation rotation) {
		mysqlconn.getElectionResults(rotation);
		rotation.showTheResult();
	}

	public static void election(Scanner e, ElectionRotation rotation) {
		try {
			rotation.election(e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void showAllParties(ElectionRotation rotation) {
		if (rotation.getparties().isEmpty()) {
			System.out.println("No parties to show");
			return;
		}
		System.out.println("The parties are: ");
		for (Party p : rotation.getparties()) {
			System.out.println(p);
		}
		System.out.println("----------------------------------------------------------------------------------------");
	}

	static void showAllCitizen(ElectionRotation rotation) {
		System.out.println("The citizens are: ");
		for (Kalpi kalpi : rotation.getKalpies()) {
			ArrayList<Citizen> arr = kalpi.getCitizens();
			for (Citizen c : arr) {
				System.out.println(c);
			}
		}
		System.out.println("----------------------------------------------------------------------------------------");
	}

	public static void showAllKalpi(ElectionRotation rotation) {

		if (rotation.getKalpies().isEmpty()) {
			System.out.println("No kalpies to show");
			return;
		}
		System.out.println("The Kalpies are: ");
		for (Kalpi k : rotation.getKalpies()) {
			System.out.println(k);
		}
		System.out.println("----------------------------------------------------------------------------------------");
	}

	private static void addPartyMember(ElectionRotation rotation) {
		Scanner c = new Scanner(System.in);

		System.out.println("Enter citizen ID:");
		String id = c.next();
		if (!mysqlconn.checkForCitizen(id)) {
			System.out.println("The citizen is not exist! ");
		} else if (mysqlconn.alreadyCandidate(id)) {
			System.out.println("already a Candidate!");
		} else {
			System.out.println("Enter name of the party");
			String partyName = c.next();
			if (!mysqlconn.alreadyEnterdParty(partyName)) {
				System.out.println("The party is not exist! ");
			} else {
				InsertToSql.addCandidate(rotation,partyName,id);
				System.out.println(
						"----------------------------------------------------------------------------------------");
			}

		}
	}

	public static void addParty(ElectionRotation rotation) {

		Scanner c = new Scanner(System.in);
		System.out.println("Enter the name of the patry:");
		String name = c.nextLine();
		if (rotation.searchParty(name)) {
			System.out.println("The party is exist! ");
		} else {
			System.out.println("Select side(Left,Center,Right): ");
			String side = c.nextLine();
			while (!((side.contains("Left")) || (side.contains("Center")) || (side.contains("Right")))) {
				System.out.println(
						"You must choose from the list(Left,Center,Right) and notice a capital letter at the beginning! ");

				System.out.println("Select side(Left,Center,Right): ");
				side = c.nextLine();
			}
			Party p = new Party(name, side);
			rotation.addParty(p);
			System.out.println("The party added successfully!");
			System.out.println(
					"----------------------------------------------------------------------------------------");

		}
	}

	public static void addCitizen(ElectionRotation rotation) throws IdException, IdKalpiException {
		Scanner scan = new Scanner(System.in);
		// set the kalpies list
		mysqlconn.setRotationOnlyBallot(rotation);
		// Get the name of the citizen:
		System.out.println("Enter name:");
		String name = scan.nextLine();
		System.out.println("Enter ID number:");
		String id = scan.next();
		if (rotation.isCitizen(id) == true) {
			System.out.println("The citizen is exist! ");
			return;
		} else
			System.out.println("Enter year of birth:");
		int yearOfBirth = scan.nextInt();
		System.out.println("Enter your age:");
		int age = scan.nextInt();
		if (age < 18) {
			System.out.println("The citizen is under 18! ");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			return;
		} else
			System.out.println("Enter your id kalpi:");
		int idKalpi = scan.nextInt();
		while (rotation.searchKalpi(idKalpi) == false) {
			System.out.println("The Kalpi is not Exist");
			System.out.println("Enter your id kalpi:");
			idKalpi = scan.nextInt();
		}

		Citizen c;
		if (age <= 21) {
			System.out.println("Do you have a weapon?");
			boolean isweapon;
			char cha = scan.next().charAt(0);
			if (cha == 'y')
				isweapon = true;
			else
				isweapon = false;

			c = new Soldier(name, id, yearOfBirth, age, idKalpi, isweapon);
		} else
			c = new Citizen(name, id, yearOfBirth, age, idKalpi);

		rotation.addCitizen(c);
		System.out.println("The citizen added successfully!");
	}

	public static void addKalpi(ElectionRotation rotation) {
		String r = "Regular";
		Scanner c = new Scanner(System.in);
		System.out.println("Enter location:");
		String location = c.nextLine();
		Kalpi k = null;
		k = new Kalpi<Citizen>(location, r);
		rotation.addKalpi(k);
		System.out.println("The kalpi added successfully!");
		System.out.println("----------------------------------------------------------------------------------------");
	}

}
