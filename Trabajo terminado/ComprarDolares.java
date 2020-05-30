public class ComprarDolares extends Operacion {
	
	public void comprarDolares(Cuenta cuenta1, Cuenta cuenta2, int montoEnDolares) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo { 
		
//		if(cuenta1.getClass().getName().equals("CajaDeAhorroDolares") || !cuenta2.getClass().getName().equals("CajaDeAhorroDolares")) {
//			
//			throw new ErrorCuentaInvalida("No se puede operar con estas cuentas");
//		}
		
		double montoEnPesos = montoEnDolares / 0.015;  //
		cuenta1.retirarSaldo(montoEnPesos * 1.3); //<--- Impuesto
		cuenta2.depositarSaldo(montoEnDolares);
		
	}
}