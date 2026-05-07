public class Cribut {

	private String nom;
	private char   element; // 'T', 'A', 'V', 'F' 
	private short  salut;
	private String costAtac;
	private short  danyAtac;
	
	private static final int NOM_LIMIT = 8;
	private static final int COST_ATAC_LIMIT = 5;

	public static final int SIZE = NOM_LIMIT*2 + 2 + 2 + COST_ATAC_LIMIT*2 + 2; /* TO-DO:
		Establir valor en funció de les mides dels camps. */

	/*
	Para calcular los tamaños de los campos tenemos que tener el cuenta que un char = 2 bytes
	nom -> 8 chars -> 8 x 2 = 16 bytes
	element -> 1 char = 2 bytes
	salut -> short = 2 bytes
	costAtac -> 5 chars -> 5 x 2 = 10 bytes
	danyAtac -> short = 2 bytes
	Entonces si hacemos la suma total de todos los bytes es igual a 32, esto quiere decir que cada
	cribut ocupa 32 bytes
	 */
	public Cribut (String nom, char element, short salut, String costAtac, short danyAtac) {
		this.nom      = nom;
		this.element  = element;
		this.salut    = salut;
		this.costAtac = costAtac;
		this.danyAtac = danyAtac;
	}

	// Getters
	public String getNom      () { return nom;      }
	public char   getElement  () { return element;  }
	public short  getSalut    () { return salut;    }
	public String getCostAtac () { return costAtac; }
	public short  getDanyAtac () { return danyAtac; }

	//El objetivo de esta funcion es transformar el obejtos a bytes porque un fichero binario solo guarda bytes
	public byte[] toBytes() {
		//Primero crear un array de 32 bytes que es el tamaño necesario para guardar un cribut completo
		byte[] record = new byte[SIZE];
		int offset = 0; //Variable para contar los bytes necesarios para cada elemento
		//Luego insertar toda la información necesaria del objeto (nom, element...)
		//nom
		PackUtils.packString(nom, NOM_LIMIT, record, offset);
		offset += 2 * NOM_LIMIT;
		//element
		PackUtils.packChar(element, record, offset);
		offset += 2;
		//salut
		PackUtils.packShort(salut, record, offset);
		offset += 2;
		//costAtac
		PackUtils.packString(costAtac, COST_ATAC_LIMIT, record, offset);
		offset += 2 * COST_ATAC_LIMIT;
		//danyAtac
		PackUtils.packShort(danyAtac, record, offset);

		return record;
	}

	//Esta funcion tiene como objetivo de desempaquetar los bytes y crear una instacia con los valores obtenidos
	public static Cribut fromBytes (byte[] record) {
		int offset = 0;
		//nom
		String nom = PackUtils.unpackString(NOM_LIMIT, record, offset);
		offset += 2 * NOM_LIMIT;
		//element
		char element = PackUtils.unpackChar(record, offset);
		offset += 2;
		//salut
		short salut = PackUtils.unpackShort(record, offset);
		offset += 2;
		//costAtac
		String costAtac = PackUtils.unpackString(COST_ATAC_LIMIT, record, offset);
		offset += 2 * COST_ATAC_LIMIT;
		//danyAtac
		short danyAtac = PackUtils.unpackShort(record, offset);

		return new Cribut(nom, element, salut, costAtac, danyAtac);
	}

	public String toString() {
		String ls = System.lineSeparator();
		String result = nom;
		switch (element) {
			case 'T':
				result += " (element: Terra)" + ls;
				break;
			case 'A':
				result += " (element: Aigua)" + ls;
				break;
			case 'V':
				result += " (element: Vent)" + ls;
				break;
			case 'F':
				result += " (element: Foc)" + ls;
				break;
		}
		result += "Té " +  salut + " punts de salut." +  ls;
		result += costAtac + ": causa " + danyAtac + " punts de dany.";
		return result;
	}

}
