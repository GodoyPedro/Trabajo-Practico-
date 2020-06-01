
public class ConsultarSaldo extends Operacion {
	
	/**
	 * post: Devuelve el saldo de la cuenta indicada.
	 * @param cuenta
	 * @return
	 */
	public double consultarSaldo(Cuenta cuenta) {
		
		return cuenta.obtenerSaldo();
		
	}
}
