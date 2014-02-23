package PatientUpdate;
import Application.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Updates an already existing patient or adds a new patient
 * based upon user input
 * Implements interface Loader
 */

public class PatientUpdate implements Loader {

	public PatientUpdate(MedicalDB db, Console cs) {
		boolean flag = true;
		while(flag) {
			Display();
			int id = Input(cs);
			
			switch(id) {
				case 1 :
					AddNew(db, cs);
					break;
					
				case 2 :
					Update(db, cs);
					break;
					
				case 3 :
					System.out.println("======================================");
					System.out.println("Patient record added/updated.Thank you for using patient update module");
					flag = false;
			}
		}
		
	}
	
	public void Display() {
		System.out.println("======================================");
		System.out.println("Type 1 to enter the information for a new patient");
		System.out.println("Type 2 to update the information of an existing patient");
		System.out.println("Type 3 to exit the Patient Update module");
		System.out.println("======================================");
	}
	
	public int Input(Console cs) {
		int id ;
		while(true) {
			
			String input = cs.readLine();
			if(input.matches("[1-3]")) {
				id = Integer.parseInt(input);
				return id;
			}
			else {
				System.out.println("Please type an integer for patient update module options");
				Display();
			}		
		}

	}
	
	private void AddNew(MedicalDB mDB, Console cs) {
		
		try {
			int health_care_no = Integer.parseInt(CheckForPatient(mDB, cs, 1));
			String name = CheckForInput(cs, 2);
			String birth_day = CheckForInput(cs, 3);
			String phone = CheckForInput(cs, 4);
			String address = CheckForInput(cs, 5);
		
			Boolean rs = mDB.connector.executeNonQuery("INSERT INTO PATIENT VALUES(" + health_care_no + ", '" + name + "', '" +
				address + "', TO_DATE('" + birth_day + "', 'yyyymmdd'), '" + phone + "')");
			
		}	catch(NumberFormatException nfe ) {
				nfe.printStackTrace();
		}
	}
	
	private void Update(MedicalDB mDB, Console cs) {
		
		try {
			
			int health_care_no = Integer.parseInt(CheckForPatient(mDB, cs, 2));

			String name = CheckForInput(cs, 2);
			String birth_day = CheckForInput(cs, 3);
			String phone = CheckForInput(cs, 4);
			String address = CheckForInput(cs, 5);
		
			Boolean rs = mDB.connector.executeNonQuery("UPDATE PATIENT SET NAME='" + name + "', " + 
				"ADDRESS='" + address + "', " + "BIRTH_DAY=TO_DATE('" + birth_day + "', 'yyyymmdd'), " +
				"PHONE='" + phone + "' WHERE HEALTH_CARE_NO=" + health_care_no);
			
			if(rs == false) {
				System.out.println("Patient does not exist in database");
			}
			
		}	catch(NumberFormatException nfe ) {
			nfe.printStackTrace();
		}
	}
	
	private String CheckForInput(Console cs, int type) {
		
		boolean correct = true;
		while(correct) {

			if(type == 2) {
				String name = cs.readLine("Enter Name of Patient - ");
				if(name.length() <= 200 && name.length() > 0 && !name.contains("'") &&
						!name.contains("\"")) {
					return name;
				}
				else if(name.contains("") || name.contains("'") || name.contains("\"")) {
					System.out.println("Name cannot be null or contain ' or \" chars");
				}
				else {
					System.out.println("Size of name beyond limit of 100 chars.");
				}
			}
			else if(type == 3) {
				String birth_day = cs.readLine("Enter birth-day of patient in yyyymmdd format - ");
				if(birth_day.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")) {
					return birth_day;
				}
				else {
					System.out.println("Birthday in wrong format. Try again.");
				}
			}
			else if(type == 4) {
				String phone = cs.readLine("Enter phone number of patient - ");
				if(phone.length() <= 10 && phone.matches("[0-9]+")) {
					return phone;
				}
				else if(phone.length() > 10) {
					System.out.println("Size of phone beyond limit of 10 chars.");
				}
				else {
					System.out.println("Phone number contains invalid characters");
				}
			}
			else if(type == 5) {
				String address = cs.readLine("Enter address of patient - ");
				if(address.length() <= 200 && !address.contains("'") &&
						!address.contains("\"")) {
					return address;
				}
				else if(address.contains("'") || address.contains("\"")) {
					System.out.println("Adress  cannot contain ' or \" chars");
				}
				else {
					System.out.println("Size of address beyond limit of 200 chars");
				}
			}
		}
		return "";
	}
	
	private String CheckForPatient(MedicalDB mDB, Console cs, int type) {
		boolean correct = true;
		
		try {
			
			while(correct) {
				if(type == 1) {
					String health_care_no = cs.readLine("Enter Health Care Number - ");
					if(health_care_no.matches("[0-9]+") && health_care_no.length() <= 38) {
						ResultSet rs = mDB.connector.executeQuery("SELECT NAME FROM PATIENT WHERE" + 
								" HEALTH_CARE_NO=" + Integer.parseInt(health_care_no));
						if(!rs.first()) {
							return health_care_no;
						}
						else {
							System.out.println("Health Care Number already exists in table patient");
						}
					}
					else if(health_care_no.length() > 38) {
						System.out.println("Size of number beyond limit of 38 chars.");
					}
					else {
						System.out.println("Health care number is not a number. Try again.!!");
					}
				}
				else if(type == 2) {
					String health_care_no = cs.readLine("Enter Health Care Number or Name of Patient - ");
					if(health_care_no.matches("[0-9]+")) {
						ResultSet rs = mDB.connector.executeQuery("SELECT HEALTH_CARE_NO FROM PATIENT WHERE" +
								" HEALTH_CARE_NO=" + Integer.parseInt(health_care_no));
						if(rs.first()) {
							return health_care_no;
						}
						else {
							System.out.println("Health Care Number does not exist in table patient");
						}
					}
					else {
						ResultSet rs = mDB.connector.executeQuery("SELECT HEALTH_CARE_NO, NAME FROM PATIENT WHERE" +
								" NAME LIKE '%" + health_care_no+ "%'");
						if(!rs.first()) {
							System.out.println("Name does not exist in table patient");
						}
						else {
							rs.beforeFirst();
							while(rs.next()) {
								System.out.println("======================================");
								System.out.println("HEALTH_CARE_NO" + "\t" + "NAME");
								System.out.println("======================================");
								System.out.println(rs.getString(1) + '\t' + rs.getString(2));
								System.out.println("======================================");
							}
							health_care_no = cs.readLine("Type health care number of patient to update");
							rs.beforeFirst();
							while(rs.next()) {
								if(health_care_no.matches(rs.getString(1))) {
									return health_care_no;
								}
							}
							System.out.println("Health_care_no not in list");
						}
					}
					
				}
			}
		}	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	
}
