package Application;

import java.sql.ResultSet;

/*
 * The connection class to medicalDB, to be used for queries and changing anything
 * in the MedicalDb.
 */


public class MedicalDB {
	
	public DBConnector connector;
	
	//Class constructor: initializes the DB connection using the specified Oracle DB. 
	public MedicalDB (String username, String password) {
		System.out.println("Opening connection to Oracle DB...");
		connector = new DBConnector("dbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS", username, password);
		if(connector != null) {
			System.out.println("Connection has been opened successfully!");
		}
	}
	
	//Closes the connection
	public void close () {
		connector.closeConnection();
		System.out.println("Connection Closed!");
	}

	//Creates the DB schema, which in this case is one table. 
	public void createDB () {
		//Let's check if the table exists, and if it does, delete it. Note that this is a little more advance because we are using a transaction and error code (out of the scope so far).
		//Create the table
		connector.executeNonQuery("create table movie(title char(20), movie_number integer, primary key(movie_number))");
		System.out.println("DB Tables created!");
	}
	
	//This method performs three different inserts into the BD
	public void insert () {
		/*connector.executeNonQuery("insert into movie values ('Movie 1',1)");
		connector.executeNonQuery("insert into movie values ('Movie 2',2)");
		connector.executeNonQuery("insert into movie values ('Movie 3',3)");*/
	}
	
	public boolean executeNonQuery(String query) {
		return connector.executeNonQuery(query);
	}
	public ResultSet executeQuery(String query) {
		return connector.executeQuery(query);
	}
	
}
