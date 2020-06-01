
public class ErrorArchivoVacio extends Exception {
	
	/**
	 * Pre: Esta excepcion se utiliza en: OperadorDeArchivos.
	 * 
	 * Post: Lanza una excepcion cuando el archivo no contiene informacion.
	 * 
	 */
	
	public ErrorArchivoVacio(String mensaje) {
		
		super(mensaje);
	}
}
