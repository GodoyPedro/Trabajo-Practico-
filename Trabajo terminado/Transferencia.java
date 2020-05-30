public class Transferencia extends Operacion implements Reversible {
	
	
	public void transferir(Cuenta cuenta1, Cuenta cuenta2, int montoATransferir) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		
		if(cuenta1.obtenerAlias().equals(cuenta2.obtenerAlias())){
			
			System.err.println("NO PODES TRANSFERIR A LA MISMA CUENTA");
		}
		
		else if(cuenta1.getClass().getName().equals("CajaDeAhorroDolares") || 
				cuenta2.getClass().getName().equals("CajaDeAhorroDolares")) {
			
			System.err.println("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES");
		}
		
		cuenta1.retirarSaldo(montoATransferir);
		cuenta2.depositarSaldo(montoATransferir);

	}
	public void revertirMovimiento(Cuenta...cuentas) {

		for(Cuenta cuenta : cuentas) {
			
			cuenta.cambiarSaldo(cuenta.obtenerSaldoPrevio());
			
		}

	}
}