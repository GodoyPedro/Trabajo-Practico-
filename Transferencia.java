
public class Transferencia extends Operacion implements Reversible {
	
	
	public void transferir(Cuenta cuenta1, Cuenta cuenta2, int montoATransferir) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorTransferencia {
		
		verificarTransferencia(cuenta1, cuenta2);
		cuenta1.quitarSaldo(montoATransferir);
		cuenta2.agregarSaldo(montoATransferir);

	}
	public void revertirMovimiento(Cuenta...cuentas) {

		for(Cuenta cuenta : cuentas) {
			
			cuenta.cambiarSaldo(cuenta.obtenerSaldoPrevio());
			
		}

	}
	
	private void verificarTransferencia(Cuenta cuenta1, Cuenta cuenta2) throws ErrorTransferencia {
		
		if(cuenta1.getClass().getName().equals("CajaDeAhorroDolares") || 
				cuenta2.getClass().getName().equals("CajaDeAhorroDolares")) {
			
			throw new ErrorTransferencia("No se puede transferir usando una cuenta en dolares");
		}
	}
}
