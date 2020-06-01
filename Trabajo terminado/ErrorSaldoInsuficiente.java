public class ErrorSaldoInsuficiente extends Exception {

	/**
	 * Pre: Esta excepcion se utiliza en: Cuenta, CuentaCorriente, CajaDeAhorroPesos, 
	 *      CajaDeAhorroDolares, Extraccion, Transferencia, ComprarDolares, Cajero.
	 * 
	 * Post: Lanza una excepcion cuando se intenta retirar un monto mas grande 
	 *       que el saldo actual de la cuenta.
	 * 
	 */
	
    public ErrorSaldoInsuficiente()	{
    	
        super("Usted no posee el saldo suficiente para realizar esta operación");
        
    }
}
