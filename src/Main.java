import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import acm.program.ConsoleProgram;

public class Main extends ConsoleProgram {

	private static final String CRIBUT_FILES   = "cribut.txt";
	private static final String CRIBUT_DB_NAME = "cributDB.dat";
	private CributDB cributDB;

	public void run() {
		setFont ("*-*-24");
		try {
			cributDB = new CributDB (CRIBUT_DB_NAME);
			loadFromFiles();
		} catch (IOException ex) {
			println ("Error generant base de dades!");
			System.exit (-1);
		}

		for (;;) {
			printMenu();
			int option = getOption();
			switch (option) {
				case 1:
					llistaNoms();
					break;
				case 2:
					infoCribut();
					break;
				case 3:
					eliminaCribut();
					break;
				case 4:
					quit();
					break;
			}
			println();
		}
	}

	private void printMenu() {
		println ("Menú d'opcions:");
		println ("1 - Llista els noms de les cribut.");
		println ("2 - Mostra la informació d'una cribut.");
		println ("3 - Elimina una cribut.");
		println ("4 - Sortir.");
	}

	private int getOption() {
		int option;
		do {
			option = readInt ("Escull una opció: ");
		} while (option <= 0 || option > 4);
		return option;
	}

	private void loadFromFiles() throws IOException {
		cributDB.reset();
		BufferedReader input = new BufferedReader (
		                       new FileReader (CRIBUT_FILES));
		String fileName = input.readLine();
		while (fileName != null) {
			try {
				Cribut cb = CributReader.readCributFile (fileName);
				cributDB.appendCribut (cb);
			}
			catch (IOException ioe) {
				println (ioe + System.lineSeparator());
			}
			fileName = input.readLine();
		}
		input.close();
	}


	private void llistaNoms() {
		int numCributs = cributDB.getNumCributs();
		println();
		try {
			for (int i = 0; i < numCributs; i++) {
				Cribut cb = cributDB.readCribut (i);
				println (cb.getNom());
			}
		} catch (IOException ex) {
			println ("Error a la base de dades!");
		}
	}

	private void infoCribut() {
		String nom = readLine ("Escriu el nom de la cribut: ");
		try {
			int n = cributDB.searchCribut (nom);
			if (n >= 0) {
				Cribut cb = cributDB.readCribut (n);
				println (cb);
			} else {
				println ("Cribut no trobada.");
			}
		} catch (IOException ex) {
			println ("Error a la base de dades!");
		}
	}

	private void eliminaCribut() {
		String nom = readLine ("Escriu el nom de la cribut: ");
		try {
			boolean success = cributDB.deleteCribut (nom);
			if (!success) {
				println ("Cribut no trobada.");
			}
		} catch (IOException ex) {
			println ("Error a la base de dades!");
		}
	}

	private void quit() {
		try {
			cributDB.close();
			System.exit (0);
		} catch (IOException ex) {
			println ("Error tancant base de dades!");
			System.exit (-1);
		}
	}

}
