
public class Extraccion extends Operacion {
	
	public void extraerFondos(Cuenta cuenta, int monto) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida { 
		
		if(cuenta.getClass().getName().equals("CajaDeAhorroDolares")) {
			
			throw new ErrorCuentaInvalida("No se puede operar con estas cuentas");
		}
		
		cuenta.quitarSaldo(monto);
		
	}
}

