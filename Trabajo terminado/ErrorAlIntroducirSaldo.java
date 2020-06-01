public class ErrorAlIntroducirSaldo extends Exception {

	/**
	 * Pre: Esta excepcion se utiliza en: Cuenta, CuentaCorriente, 
	 *      CajaDeAhorroDolares, CajaDeAhorroPesos, ComprarDolares, 
	 *      Transferencia, Extraccion, Deposito, OperadorDeArchivos, Cajero.
	 * 
	 * Post: Lanza una excepcion cuando en un parametro se utiliza un valor negativo, 
	 *       generalmente es lo relacionado al saldo de la cuenta.
	 * 
	 */

    public ErrorAlIntroducirSaldo()	{
    	
        super("El saldo a introducir debe ser positivo");
        
    }
}
