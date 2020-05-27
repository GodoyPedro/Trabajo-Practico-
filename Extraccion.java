

public class Extraccion extends Operacion {
	
	public void extraerFondos(Cuenta cuenta, int monto) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida { 
		
		cuenta.quitarSaldo(monto);
		
	}
}

