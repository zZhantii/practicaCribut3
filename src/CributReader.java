import java.awt.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CributReader {

	//Para esta funcion, se está leyendo un archivo .txt que es totalemente explicito, no es necesario crear un bucle porque no lees muchos registros diferentes
	public static Cribut readCributFile (String fileName) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		//Nom
		String nom = input.readLine();
		if (nom == null ||nom.isEmpty()) {
			System.err.println("Nom buit " + fileName);
			throw new IOException("Nom buit " + fileName);
		}

		//Element
		String strelement = input.readLine();
		if (strelement == null ||strelement.isEmpty()) {
			System.err.println("Element buit " + fileName);
			throw new IOException("Element buit " + fileName);
		}
		char element = strelement.toUpperCase().charAt(0);
		if (!"TFVA".contains(String.valueOf(element))){
			System.err.println("Element no vàlid al fitxer " + fileName);
			throw new IOException("Element no vàlid al fitxer " + fileName);
		}

		//Salut
		String strsalut = input.readLine();
		if (strsalut == null ||strsalut.isEmpty()) {
			System.err.println("Salut buit " + fileName);
			throw new IOException("Salut buit " + fileName);
		}
		short salut;
		try {
			salut = Short.parseShort(strsalut);
		} catch (NumberFormatException e) {
			throw new IOException("Salut no vàlda al fitxer " + fileName, e);
		}


		String costAtac = input.readLine();
		if (costAtac == null || costAtac.isEmpty() || costAtac.length() > 5) {
			System.err.println("costAtac no vàlid al fitxer " + fileName);
			throw new IOException("costAtac no vàlid al fitxer " + fileName);
		}

		int digits = 0;
		for (int i = 0; i < costAtac.length(); i++) {
			char c = Character.toUpperCase(costAtac.charAt(i));
			if (Character.isDigit(c)) {
				digits++;
				if (digits > 1) {
					System.err.println("costAtac no vàlid al fitxer " + fileName);
					throw new IOException("costAtac no vàlid al fitxer " + fileName);
				}
			} else if ("TFVA".indexOf(c) < 0) {
				System.err.println("costAtac no vàlid al fitxer " + fileName);
				throw new IOException("costAtac no vàlid al fitxer " + fileName);
			}
		}


		String strdanyAtac = input.readLine();
		if (strdanyAtac == null ||strdanyAtac.isEmpty()) {
			System.err.println("danyAtac buit " + fileName);
			throw new IOException("danyAtac buit " + fileName);
		}
		short danyAtac;
		try {
			danyAtac = Short.parseShort(strdanyAtac);
		} catch (NumberFormatException e) {
			throw new IOException("danyAtac no vàlda al fitxer " + fileName, e);
		}

		input.close();

		return new Cribut(nom, element, salut, costAtac, danyAtac);
	}

}
