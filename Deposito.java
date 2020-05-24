public class Deposito extends Operacion {
	
	public void depositar(Cuenta cuenta, int monto) throws ErrorAlIntroducirSaldo {
		
		cuenta.agregarSaldo(monto);
		
	}
}
