
public class Deposito extends Operacion {
	
	
	/**
	 * pre: El monto debe ser mayor a cero
	 * post: Deposita el monto ingresado en la cuenta indicada
	 * @param cuenta
	 * @param monto
	 * @throws ErrorAlIntroducirSaldo
	 */
	public void depositar(Cuenta cuenta, int monto) throws ErrorAlIntroducirSaldo {
		
		cuenta.depositarSaldo(monto);
		
	}
}
