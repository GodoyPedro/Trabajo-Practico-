package predeterminado;

public class Deposito extends Operacion {
	
	public void depositar(Cuenta cuenta, double monto) throws ErrorAlIntroducirSaldo {
		
		cuenta.agregarSaldo(monto);
		
	}
}
