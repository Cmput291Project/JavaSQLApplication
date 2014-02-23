package SearchEngine;
import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;

import Application.*;

/*
 * Class to search for medical_records using Patient information
 */
public class SearchByPatient {

	public SearchByPatient(MedicalDB db, Console cs) {
		boolean flag = true;
		
			Diplay();
			String name = Input(db, cs);

	}

	private String Input(MedicalDB mDB, Console cs) {
		// TODO Auto-generated method stub
		String name = CheckForPatient(mDB, cs);
	    
		return name;
	}

	private String CheckForPatient(MedicalDB mDB, Console cs) {
		// TODO Auto-generated method stub
		boolean correct = true;
		
		try {
			while(correct) {
				String health_care_no = cs.readLine("Enter name or health care number of patient - ");
				if(health_care_no.matches("[0-9]+")) {
					ResultSet rs = mDB.connector.executeQuery("SELECT HEALTH_CARE_NO FROM PATIENT WHERE" +
							" HEALTH_CARE_NO=" + Integer.parseInt(health_care_no));
					if(rs.first()) {
						ListItems(mDB, health_care_no, 1);
						return health_care_no;
					}
					else {
						System.out.println("Health Care Number does not exist in table patient");
					}
				}
				else {
					ResultSet rs = mDB.connector.executeQuery("SELECT NAME,HEALTH_CARE_NO FROM PATIENT WHERE " +
								"NAME LIKE '%" + health_care_no +"%'");
					if(rs.first()) {
						ListItems(mDB, health_care_no, 2);
						return rs.getString(1);
					}
					else {
						System.out.println("Name does not exist in table patient");
					}
					
				}
			}
		}	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private boolean ListItems(MedicalDB mDB, String health_care_no, int type) {
		// TODO Auto-generated method stub

		ResultSet rs = null;
		
		try {
		
			if(type == 1) {
				rs = mDB.connector.executeQuery("SELECT PATIENT_NO, NAME, TEST_NAME, TEST_DATE, " +
					"RESULT FROM TEST_RECORD, PATIENT, TEST_TYPE WHERE PATIENT_NO=" +
					health_care_no +" AND PATIENT_NO=HEALTH_CARE_NO AND TEST_TYPE.TYPE_ID = TEST_RECORD.TYPE_ID " +
					"ORDER BY PATIENT_NO, NAME, TEST_NAME, TEST_DATE, RESULT");
			}
			else if(type == 2) {
				rs = mDB.connector.executeQuery("SELECT PATIENT_NO, NAME, TEST_NAME, TEST_DATE, " +
						"RESULT FROM TEST_RECORD, PATIENT, TEST_TYPE WHERE NAME LIKE '%" + health_care_no + "%' " +
						"AND PATIENT_NO=HEALTH_CARE_NO AND TEST_TYPE.TYPE_ID = TEST_RECORD.TYPE_ID " +
						"ORDER BY PATIENT_NO, NAME, TEST_NAME, TEST_DATE, RESULT");
			}
		
			if(!rs.first()) {
				System.out.println("No records of patient in table test_record");
				return false;
			}
			
			else {
				rs.beforeFirst();
			}
			

			while(rs.next()) {
				System.out.println("======================================");
				System.out.println("PATIENT_NO\tNAME\tTEST_NAME\tTEST_DATE\tRESULT");
				System.out.println("======================================");
				System.out.println(rs.getString(1)+"\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" +
						rs.getString(4) + "\t" + rs.getString(5));
				System.out.println("======================================");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	private void Diplay() {
			// TODO Auto-generated method stub
			
	}
}