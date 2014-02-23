package Application;

import java.io.*;

import MedicalTest.*;
import Prescription.*;
import PatientUpdate.*;
import SearchEngine.*;

/*
 * MainMenu presents user with multiple module options
 * Implements interface Loader
 */

public class MainMenu implements Loader {

	
	public MainMenu(MedicalDB mDB, Console cs) {
		
		boolean flag = true;
		while(flag) {
			Display();
			int id = Input(cs);
			
			switch (id) {
				case 1 :
					Prescription pR = new Prescription(mDB, cs);
					break;
				
				case 2 :
					MedicalTest mT = new MedicalTest(mDB, cs);
					break;
					
				case 3:
					PatientUpdate pU = new PatientUpdate(mDB, cs);
					break;
					
				case 4:
					SearchEngine sE = new SearchEngine(mDB, cs);	
					break;
				case 5:
					flag = false;
					System.out.println("Thank you for using the application");				
			}
		}
	}
	
	/*
	 * Displays the main menu : 4 choices of modules inside the application
	 */
	
	public void Display() {
		System.out.println("======================================");
		System.out.println("Welcome to the Medical Database Application");
		System.out.println("Please type an integer corresponding to the appropriate module");
		System.out.println("Type 1 for Prescription");
		System.out.println("Type 2 for MedicalTest");
		System.out.println("Type 3 for PatientUpdate");
		System.out.println("Type 4 for SearchEngine");
		System.out.println("Type 5 for Exit");
		System.out.println("======================================");
	}
	
	/*
	 * Returns the id of the module typed by the user. Needs InputOutput stream
	 * to get the value from the terminal. Buffered Reader class to be used.
	 */
	
	public int Input(Console cs) {
		
		int id = 1;
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			
			String input = cs.readLine();
			if(input.matches("[1-5]")) {
				id = Integer.parseInt(input);
				return id;
			}
			else {
				System.out.println("Please type an integer for your module options");
				Display();
			}
			
		}
	}
}
