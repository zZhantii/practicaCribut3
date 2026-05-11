import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CributReader {

	//Para esta funcion, se esta leyendo un archivo .txt que es totalemente explicito, no es necesario crear un bucle porque no lees muchos registros diferentes
	public static Cribut readCributFile (String fileName) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String nom = input.readLine();
		char element = input.readLine().charAt(0);
		short salut = Short.parseShort(input.readLine());
		String costAtac = input.readLine();
		short danyAtac = Short.parseShort(input.readLine());
		input.close();

		return new Cribut(nom, element, salut, costAtac, danyAtac);
	}

}
