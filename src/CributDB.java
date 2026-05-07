import java.io.RandomAccessFile;
import java.io.IOException;

public class CributDB {
	// Este objeto representa el fichero binario
	private RandomAccessFile cributDB;
	private int numCributs;

	public CributDB (String fileName) throws IOException {
		cributDB = new RandomAccessFile (fileName, "rw");
		numCributs = (int)cributDB.length() / Cribut.SIZE;
	}

	public int getNumCributs() {
		return numCributs;
	}

	public void close() throws IOException {
		cributDB.close();
	}

	public void reset() throws IOException {
		cributDB.setLength (0);
		numCributs = 0;
	}

	public Cribut readCribut (int n) throws IOException {
		//Ir a la posicion
		cributDB.seek(n * Cribut.SIZE);
		//Crear byte "32"
		byte[] record = new byte[Cribut.SIZE];
		//leer bytes
		cributDB.read(record);
		//desempaquetar bytes
		return Cribut.fromBytes(record);
	}

	public int searchCribut (String name) throws IOException {
		//Como el fichero cributDB solo entiendo en binario, no se le puede pasar un string
		//Primero hay que leer los cribut y comparar con el nombre hasta encontrar el que es correcto
		for (int i = 0; i < numCributs; i++) {
			Cribut cb = readCribut(i);
			if (cb.getNom().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
    }

	public void writeCribut (int n, Cribut cb) throws IOException {
		//Ir a la posicion
		cributDB.seek(n * Cribut.SIZE);
		//Crear byte
		byte[] record = cb.toBytes();
		//Escribir un cribut
		cributDB.write(record);
	}

	public void appendCribut (Cribut cb) throws IOException {
		writeCribut (numCributs, cb);
		numCributs++;
	}

	//La idea principal es coger el indice del cribut que se desea eliminar, despues coger el ultimo cribut de la lista y sobrescribirla encima del cribut que se desea borrar
	// y como habra un cribut duplicado, acortamos las lineas
	public boolean deleteCribut (String name) throws IOException {
		//Buscar el indice del cribut que se desea eliminar
		int n = searchCribut(name);
		if (n == -1) {
			return false;
		}
		//Cogemos el ultimo cribut
		Cribut lastCribut = readCribut(numCributs - 1);
		//Sobrescribimos el cribut para tener el duplicado
		writeCribut(n, lastCribut);
		//Acortamos el fichero para eliminar el ultimo registro
		/*
		Tenemos que tener en cuenta el "numCributs" y multiplicarlos por el tamaño del cribut
		y asi creamos la nueva longitud del fichero sin tener en cuenta del cribut eliminado
		 */
		//Restamos un cribut porque habra un registro menos
		numCributs--;
		cributDB.setLength(numCributs * Cribut.SIZE);
		return true;
	}

}
