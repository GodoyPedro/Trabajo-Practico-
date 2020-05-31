
public class Deposito extends Operacion {
	
	//Deposita el monto ingresado en la cuenta indicada.
	public void depositar(Cuenta cuenta, int monto) throws ErrorAlIntroducirSaldo {
		
		cuenta.depositarSaldo(monto);
		
	}
}
