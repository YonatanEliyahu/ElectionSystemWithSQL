import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlconn extends mySql {

	public static void setBID() {
		//the following function will set the Kalpi class's static ID counter.
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select Ballot_ID from Ballot ORDER BY Ballot_ID DESC LIMIT 1"); 
			//reading the last Kalpi ID to set the counter of the following kalpies to be entered
			if (rx.next())
				Kalpi.setCounter(rx.getInt("Ballot_ID"));
			else
				Kalpi.setCounter(0);
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void getElectionResults(ElectionRotation rotation) {
		//the following function will read all the data from the DB and will set the votes in each kalpi for each party
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();
			setRotation(rotation);
			
			for(Kalpi kalpi: rotation.getKalpies()) {

				//the following QUERY will read the num of votes for every Party in every kalpi
				ResultSet rx = stmt.executeQuery("SELECT COUNT(Vote_In.CID) as num_of_votes, PName "
													+ "FROM Ballot JOIN Vote_In ON Ballot.Ballot_ID = Vote_In.BID "
													+"JOIN Vote_For ON Vote_In.CID = Vote_For.CID "
													+"WHERE Address ='"+ kalpi.getLocation() + "' group by PName order by PName");
				while(rx.next()) {
					kalpi.addVotes(rx.getString("PName"), rx.getInt("num_of_votes"));
					// setting the num of votes in each kalpi for each party
				}
			}
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean alreadyEnterdKalpi(String location) {
		// the following function will check if the kalpi has already entered the db.
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select * from Ballot WHERE Address='" + location + "'");
			boolean res = rx.next();//true if RX is not empty and something was found in the DB, otherwise it's false
			conn.close();
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean alreadyEnterdParty(String Name) {
		// the following function will check if the Party has already entered the db.
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select * from Party WHERE Party_Name='" + Name + "'");
			boolean res = rx.next();//true if RX is not empty and something was found in the DB, otherwise it's false
			conn.close();
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean alreadyEnterdCitizen(String CID) {
		// the following function will check if the Party has already entered the db.
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select * from Citizen WHERE Citizen_ID='" + CID + "'");
			boolean res = rx.next();//true if RX is not empty and something was found in the DB, otherwise it's false
			conn.close();
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean alreadySettedBallot(String CID) {

		// the following function will check if the Party has already entered the db.
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select * from Vote_In WHERE CID='" + CID + "'");
			boolean res = rx.next();//true if RX is not empty and something was found in the DB, otherwise it's false
			conn.close();
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean alreadyVoted(String id) {
		// the following function will check if the citizen has already voted by
		// checking the Vote_For table,
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select * from Vote_For WHERE CID='" + id + "'");
			boolean res = rx.next();//true if RX is not empty and something was found in the DB, otherwise it's false
			conn.close();
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean alreadyCandidate(String id) {
		// the following function will check if the citizen has already a candidate
		Connection conn = null;
		try {
			Class.forName(myDriver).getDeclaredConstructor().newInstance();

			conn = DriverManager.getConnection(dbUrl, user, pass);

			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("select * from candidate WHERE CID='" + id + "'");
			boolean res = rx.next();//true if RX is not empty and something was found in the DB, otherwise it's false
			conn.close();
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void readQSL(int pick) {
		//the following function will print the DB, 
		//for printing all the DB you must call the function 3 times like it's done on ElectionMain case 11
		
		Connection conn = null;
		try {

			Class.forName(myDriver).getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(dbUrl, user, pass);
			Statement stmt = conn.createStatement();

			switch (pick) {
			case 5:
				System.out.println("----------------------ALL THE BALLOT-----------------------------");
				ResultSet rb = stmt.executeQuery("SELECT * FROM ballot");
				while (rb.next()) {
					System.out.println(rb.getInt("ballot_id") + "-" + rb.getString("address"));
				}
				break;
			case 6:
				System.out.println("----------------------ALL THE CITIZEN-----------------------------");

				ResultSet rs = stmt.executeQuery("SELECT * FROM citizen");

				while (rs.next()) {
					System.out.println(rs.getInt("Citizen_ID") + "-" + rs.getString("first_name") + "-"
							+ rs.getInt("Birth_Year") + "-" + rs.getInt("Age"));
				}

				break;
			case 7:
				System.out.println("----------------------ALL THE PARTIES-----------------------------");

				ResultSet rp = stmt.executeQuery("SELECT * FROM party");

				while (rp.next()) {
					System.out.println(
							rp.getString("party_name") + "-" + rp.getString("side") + "-" + rp.getDate("start_date"));
				}
				break;
			}

			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println();
	}

	public static boolean setRotation(ElectionRotation rotation) {
		rotation.reset();// clearing the rotation to avoid conflicts.
		Connection conn = null;
		try {

			Class.forName(myDriver).getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(dbUrl, user, pass);
			Statement stmt = conn.createStatement();

			ResultSet rb = stmt.executeQuery("SELECT * FROM ballot"); // creating all ballots
			while (rb.next()) {
				rotation.addKalpi(rb.getInt("ballot_id"), rb.getString("address"));
			}
			
			
			// getting all citizens
			ResultSet rc = stmt.executeQuery("SELECT * FROM Citizen left outer join Vote_In on Citizen_ID=CID");
			while (rc.next()) {
				String FN = rc.getString("First_Name");
				String newCID = rc.getString("Citizen_ID");
				int yob = rc.getInt("Birth_Year");
				int age = rc.getInt("Age");
				int kalpiID = rc.getInt("BID") != 0 ? rc.getInt("BID") : 1;

				/*
				 rc {
				 	{First_Name:... ,
				 		Citizen_ID:...
				 		Birth_Year:...
				 		Age:..
				 		BID:...},
				 		{First_Name:... 
				 		Citizen_ID:...
				 		Birth_Year:...
				 		Age:..
				 		BID:...},
				 		{First_Name:... 
				 		Citizen_ID:...
				 		Birth_Year:...
				 		Age:..
				 		BID:...}, ...		
				 }
				
				  */
				Citizen c;
				// String name, String id, int yearOfBirth, int age, int idKalpi
				c = new Citizen(FN, newCID, yob, age, kalpiID);
				rotation.getKalpiById(kalpiID).addCitizen(c);

			}

			// ----- load all parties
			ResultSet rp = stmt.executeQuery("SELECT * FROM party");

			while (rp.next()) {
				rotation.addParty(rp.getString("party_name"), rp.getString("side"));
			}
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static boolean setRotationOnlyBallot(ElectionRotation rotation) {
		//the following function will set only the ballots from the DB into the rotation
		Connection conn = null;
		try {

			Class.forName(myDriver).getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(dbUrl, user, pass);
			Statement stmt = conn.createStatement();

			ResultSet rb = stmt.executeQuery("SELECT * FROM ballot"); // creating all ballots
			while (rb.next()) {
				rotation.addKalpi(rb.getInt("ballot_id"), rb.getString("address"));
			}

			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static boolean checkForCitizen(String id) {
		//the following function will check if the citizen is in the DB 
		Connection conn = null;
		try {

			Class.forName(myDriver).getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(dbUrl, user, pass);
			Statement stmt = conn.createStatement();

			ResultSet rx = stmt.executeQuery("SELECT Citizen_ID FROM Citizen where Citizen_ID='"+id+"'"); // creating all ballots
			boolean res = rx.next();
			conn.close();
			return res;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
