package MedicalTest;
import Application.*;

import java.io.*;
import java.sql.*;

/*
 * Adds information to an already existing test_record
 * based upon user input
 */
public class MedicalTest {

	public MedicalTest(MedicalDB mDB, Console cs) {

		Display();
		int values[] = Input(cs, mDB);

		if(values != null) {
			Update(values, mDB, cs);
			System.out.println("Record succesfully updated");
			System.out.println("======================================");
		}
		
	}
	
	private void Display() {
		System.out.println("======================================");
		System.out.println("Add a completed medical test entry to table test_record.");
	}
	
	private int[] Input(Console cs, MedicalDB mDB) {
		
		boolean correct = true;	
		
		int values[] = new int[5];
		while(correct) {
			
			String select = "Type 1 to search and update for a record test_record\n" +
					"Type 2 to exit\n";
			
			int selection = Integer.parseInt(cs.readLine(select));
			
			if(selection == 2) {
				values = null;
				return values;
			}

			values[0] = Integer.parseInt(CheckForPatient(mDB, cs));

			values[1] = Integer.parseInt(CheckForDoctor(mDB, cs));

			values[2] = Integer.parseInt(CheckForInput(mDB, cs, 3));
		//values[3] = prescribe_date;
			values[3] = CheckForEntry(values, mDB, cs);
			if(values[3] != -1 ) {
				correct = false;
			}
		}
		return values;
	}
	
	private String CheckForInput(MedicalDB mDB, Console cs, int type) {
		// TODO Auto-generated method stub
		boolean correct = true;
		
		try {
			while(correct) {
				
				if(type == 3) {
					String type_name = cs.readLine("Medical Test Type Name - ");
					ResultSet rs = mDB.connector.executeQuery("SELECT type_id, test_name FROM test_type WHERE " +
							"test_name LIKE'%"+ type_name +"%'");
					if(!rs.next()) {
						System.out.println("Medical test type does not exist please try again.");
					}
					else {
						
						rs.beforeFirst();
						while(rs.next()) {
							System.out.println("======================================");
							System.out.println("TYPE_ID" +'\t'+ "TEST_NAME");
							System.out.println("======================================");
							System.out.println(rs.getString(1) + "\t" + rs.getString(2));
							System.out.println("======================================");
						}
						type_name = cs.readLine("Select the test name by typing the type id - ");
						
						rs.beforeFirst();
						while(rs.next()) {
							if(type_name.matches(rs.getString(1))) {
								return type_name;
							}
						}
						System.out.println("Type id not in list");
					}
				}
				else if(type == 4) {
					String presc_date = cs.readLine("Enter Prescribe Date in yyyymmdd format - ");
					if(presc_date.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")) {
						return presc_date;
					}
					else {
							System.out.println("Date in wrong format. Try again.");
					}
				}
				else if(type == 5) {
					String lab_name = cs.readLine("Enter medical lab name - ");
					ResultSet rs = mDB.connector.executeQuery("SELECT LAB_NAME FROM MEDICAL_LAB" +
							" WHERE LAB_NAME LIKE '%" + lab_name + "%'");
					if(!rs.first()) {
						System.out.println("Lab does not exist");
					}
					else {
						rs.beforeFirst();
						while(rs.next()) {
							System.out.println("======================================");
							System.out.println("LAB_NAME");
							System.out.println("======================================");
							System.out.println(rs.getString(1));
							System.out.println("======================================");
						}
						lab_name = cs.readLine("Select the lab name by typing the lab name - ");
						
						rs.beforeFirst();
						while(rs.next()) {
							if(lab_name.matches(rs.getString(1))) {
								return lab_name;
							}
						}
						System.out.println("Lab name not in list");
					}
				}
				else if(type == 6) {
					String result = cs.readLine("Enter test result -");
					if(result.length() <= 1024 && !result.contains("'") && !result.contains("\"")) {
						return result;
					}
					else if(result.contains("'") || !result.contains("\"")) {
						System.out.println("Cannot have ' or \" chars  in name");
					}
					else {
						System.out.println("Size of result beyond limit of 1024 chars");
					}
				}
				else if(type == 7)  {
					String test_date = cs.readLine("Enter Test Date in yyyymmdd format -");
					if(test_date.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")) {
						return test_date;
					}
					else {
							System.out.println("Date in wrong format. Try again.");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
private String CheckForPatient(MedicalDB mDB, Console cs) {
		
		boolean correct = true;
		
		try {
			while(correct) {

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
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private String CheckForDoctor(MedicalDB mDB, Console cs) {
		
		boolean correct = true;
		
		try {
			while(correct) {

				String employee_no = cs.readLine("Enter Employee Number or Name of Doctor - ");
				if(employee_no.matches("[0-9]+")) {
					ResultSet rs = mDB.connector.executeQuery("SELECT EMPLOYEE_NO FROM DOCTOR WHERE" +
							" EMPLOYEE_NO=" + Integer.parseInt(employee_no));
					if(rs.first()) {
						return employee_no;
					}
					else {
						System.out.println("Employee number does not exist in table doctor");
					}
				}
				else {
					ResultSet rs = mDB.connector.executeQuery("SELECT EMPLOYEE_NO, NAME FROM PATIENT, DOCTOR WHERE" +
							" NAME LIKE '%" + employee_no+ "%' AND PATIENT.HEALTH_CARE_NO = DOCTOR.HEALTH_CARE_NO");
					if(!rs.first()) {
						System.out.println("Name does not exist in table patient");
					}
					else {

						rs.beforeFirst();
						while(rs.next()) {
							System.out.println("======================================");
							System.out.println("EMPLOYEE_NO" +'\t' +  "NAME");
							System.out.println("======================================");
							System.out.println(rs.getString(1) + "\t" + rs.getString(2));
							System.out.println("======================================");
						}
						employee_no = cs.readLine("Select a doctor by entering the employee_no - ");
						rs.beforeFirst();
						while(rs.next()) {
							if(employee_no.matches(rs.getString(1))) {
								return employee_no;
							}
						}
						System.out.println("Employee_no not in list");
					}
				}
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	private int Update(int values[], MedicalDB mDB, Console cs) {
		
		String test_date = CheckForInput(mDB, cs, 7);
		String test_result = CheckForInput(mDB, cs, 6);
		String lab_name = CheckForInput(mDB, cs, 5);
		
		String query = "UPDATE TEST_RECORD SET " +	"TEST_DATE=TO_DATE('" + test_date + 
				"','yyyymmdd'), RESULT='" + test_result + "', MEDICAL_LAB='" + lab_name + 
				"' WHERE PATIENT_NO=" + values[0] + " AND EMPLOYEE_NO=" + values[1] + " AND TYPE_ID=" +
				values[2] + " AND TEST_ID=" + values[3];
		
		Boolean rs = mDB.connector.executeNonQuery(query);
		return 1;
	}
	
	private int CheckForEntry(int[] values, MedicalDB mDB, Console cs) {
		
		String id = "";
		ResultSet rs = mDB.connector.executeQuery("SELECT test_id, result, test_date, medical_lab FROM" +
				" TEST_RECORD WHERE PATIENT_NO=" + values[0] +
				" AND EMPLOYEE_NO = " + values[1] + " AND TYPE_ID = " + values[2]);
		
		try {
			//rs.first();
			if(!rs.first()) {
				System.out.println("No such medical records exist");
				return -1;
			}
			else {
				rs.beforeFirst();
				while(rs.next()) {
					System.out.println("======================================");
					System.out.println("TEST_ID" + '\t' + "RESULT\tTEST_DATE\tMEDICAL_LAB");
					System.out.println("======================================");
					System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) +
							"\t" + rs.getString(4));
					System.out.println("======================================");
				}				
				id = cs.readLine("Type test_id number to choose a test_record to change");
				rs.beforeFirst();
				while(rs.next()) {
					if(id.matches(rs.getString(1))) {
						return Integer.parseInt(id);
					}
				}
				System.out.println("test_id not in list");
				return -1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	
	private void Exit() {
		
	}
}
