import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InsertToSql extends mySql {

	public static void vote_for(String partyName, String CID) {
		// the following function will add a citizen vote to the THE Vote_For TABLE
		try {
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(dbUrl, user, pass);

			String queryVF = " insert into Vote_For (CID, PName)" + " values (?, ?)"; // SETTING THE QUERY
			PreparedStatement preparedStmt = conn.prepareStatement(queryVF);
			preparedStmt.setString(1, CID); // replacing the 1st '?' in the query with the CID
			preparedStmt.setString(2, partyName); //replacing the 2nd '?' in the query with the partyName
			preparedStmt.execute(); //EXECUTE THE QUERY AND ACTUALLY ENTER THE VOTE.

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void addCandidate(ElectionRotation rotation, String partyName, String CID) {
		// the following function will add a Candidate vote to the THE candidate and candidate_for TABLES
		try {
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(dbUrl, user, pass);

			String candidate_for = " insert into candidate_for (CID , PName)" + " values (? , ?)";// SETTING THE QUERY

			PreparedStatement preparedStmt = conn.prepareStatement(candidate_for);
			preparedStmt.setString(1, CID);// replacing the 1st '?' in the query with the CID
			preparedStmt.setString(2, partyName);//replacing the 2nd '?' in the query with the partyName
			preparedStmt.execute();//EXECUTE THE QUERY AND ACTUALLY ENTER THE candidate.
			System.out.println("Citizen #ID " + CID + " in a new candidate in " + partyName);

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void insert(ElectionRotation rotation) {

		try {
			// create a mysql database connection
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(dbUrl, user, pass);

			String query = " insert into ballot (ballot_id, address)" + " values (?, ?)";// SETTING THE QUERY

			for (Kalpi kalpi : rotation.getKalpies()) {
				if (!mysqlconn.alreadyEnterdKalpi(kalpi.getLocation())) { // checking if the kalpi is not in the DB
					
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, kalpi.getId());// replacing the 1st '?' in the query with the kalpi id
					preparedStmt.setString(2, kalpi.getLocation());//replacing the 2nd '?' in the query with the kalpi location
					preparedStmt.execute();
					System.out.println("New Ballot in " + kalpi.getLocation());
				}
			}

			// execute the preparedstatement
			String queryP = " insert into party (party_name, side, start_date)" + " values (?, ?,?)";// SETTING THE QUERY
			for (Party party : rotation.getparties()) {
				if (!mysqlconn.alreadyEnterdParty(party.getName())) { // checking if the party is not in the DB
					PreparedStatement preparedStmt = conn.prepareStatement(queryP);
					preparedStmt.setString(1, party.getName());// replacing the 1st '?' in the query with the party name
					preparedStmt.setString(2, party.getSidetype().toString());// replacing the 2nd '?' in the query with the party type
					preparedStmt.setObject(3, party.getDate());// replacing the 3th '?' in the query with the party est. date
					preparedStmt.execute();
					System.out.println("New Party - " + party.getName());
				}
			}

			String queryC = " insert into citizen (citizen_id, first_name, birth_year, age)" + " values (?, ?,?, ?)";// SETTING THE QUERY
			for (Kalpi kalpi : rotation.getKalpies()) {
				ArrayList<Citizen> arr = kalpi.getCitizens();
				for (Citizen c : arr) {
					if (!mysqlconn.alreadyEnterdCitizen(c.getId())) {// checking if the citizen is not in the DB
						PreparedStatement preparedStmt = conn.prepareStatement(queryC);
						preparedStmt.setString(1, c.getId());// replacing the 1st '?' in the query with the citizen id
						preparedStmt.setString(2, c.getName());// replacing the 2nd '?' in the query with the citizen name
						preparedStmt.setInt(3, c.getYearOfBrith());// replacing the 3th '?' in the query with the citizen YOB
						preparedStmt.setInt(4, c.getAge());// replacing the 4th '?' in the query with the citizen AGE
						preparedStmt.execute();
						System.out.println("New Citizen - " + c.getId() + " - " + c.getName());
					}
				}
			}
			String voter = " insert into vote_in (CID,BID)" + " values (?, ?)";
			for (Kalpi kalpi : rotation.getKalpies()) {
				ArrayList<Citizen> arr = kalpi.getCitizens();
				for (Citizen c : arr) {
					if (!mysqlconn.alreadySettedBallot(c.getId())) {// checking if the citizen assignment is not in the DB
						PreparedStatement preparedStmt = conn.prepareStatement(voter);
						preparedStmt.setString(1, c.getId());// replacing the 1st '?' in the query with the citizen id
						preparedStmt.setInt(2, c.getIdKalpi());// replacing the 2nd '?' in the query with the citizen Kalpi ID
						preparedStmt.execute();
						System.out.println("Citizen #ID " + c.getId() + " assigned to " + c.getIdKalpi());
					}
				}
			}

			conn.close();
			rotation.reset();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
