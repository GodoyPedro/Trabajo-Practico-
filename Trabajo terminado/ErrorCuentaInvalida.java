

public class ErrorCuentaInvalida extends Exception {
	
	/**
	 * Pre: Esta excepcion se utiliza en: ComprarDolares.
	 * 
	 * Post: Lanza una excepcion cuando se intenta comprar dolares con una cuenta que no 
	 *       puede realizar dicha operacion.
	 * 
	 */
	
	public ErrorCuentaInvalida(String mensaje) {
		
		super(mensaje);
	}
}
