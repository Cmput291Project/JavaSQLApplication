package SearchEngine;
import Application.*;
import java.io.*;

import MedicalTest.MedicalTest;
import PatientUpdate.PatientUpdate;
import Prescription.Prescription;

/*
 * Class to load the SearchEngine module
 * for user to be able to use any of the 
 * searches. Implements interface Loader
 */
public class SearchEngine implements Loader {

	public SearchEngine(MedicalDB db, Console cs) {
		boolean flag = true;
		
		while(flag) {
			Display();
			int id = Input(cs);
			
			switch(id) {
			
				case 1 :
					SearchByPatient sP = new SearchByPatient(db, cs);
					break;
				
				case 2 :
					SearchByDate sD = new SearchByDate(db, cs);
					break;
					
				case 3:
					SearchByTestName sT = new SearchByTestName(db, cs);
					break;
					
				case 4:
					flag = false;
					System.out.println("Thank you for using the search module");			
			
			}
		}
	}
	
	public void Display() {
		System.out.println("======================================");
		System.out.println("Type 1 for Search all test records of a particular patient");
		System.out.println("Type 2 for Search all test records prescribed by a given doctor over a particular period of time");
		System.out.println("Type 3 for Search all patients who have reached alarming age of a given test type but havent taken the test");
		System.out.println("Type 4 to exit to main menu");
		System.out.println("======================================");
	}
	
	public int Input(Console cs) {
		int id;
		
		while(true) {
			
			String input = cs.readLine();
			if(input.matches("[1-4]")) {
				id = Integer.parseInt(input);
				return id;
			}
			else {
				System.out.println("Please type an integer for your search options");
				Display();
			}
			
		}	
	}
	
	public void SearchBy(int id) {
		
	}
	/*
	 * returns to main menu
	 */
	public void Exit() {
		
	}
}
