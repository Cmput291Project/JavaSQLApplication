package Application;

import java.io.Console;
/*
 * Loader interface typifies Loader classes
 */
public interface Loader {

	void Display();
	int Input(Console cs);
	//int CheckForInput(MedicalDB mDB, Console cs, int type);
}
