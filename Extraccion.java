package predeterminado;


public class Extraccion extends Operacion {
	
	public void extraerFondos(Cuenta cuenta, double monto) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo { 
		
		cuenta.quitarSaldo(monto);
		
	}
}

