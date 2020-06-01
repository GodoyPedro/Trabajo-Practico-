
public class ErrorDatosInvalidos extends Exception {
	
	/**
	 * Pre: Esta excepcion se utiliza en: OperadorDeArchivos.
	 *
	 *Post: Lanza una excepcion cuando el numero de tarjeta ingresado no existe en la base de datos.
	 */
	
	public ErrorDatosInvalidos(String mensaje) {
		
		super(mensaje);
	}
}
