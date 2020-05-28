public class Transferencia extends Operacion implements Reversible {
	
	
	public void transferir(Cuenta cuenta1, Cuenta cuenta2, int montoATransferir) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		
		cuenta1.quitarSaldo(montoATransferir);
		cuenta2.agregarSaldo(montoATransferir);

	}
	public void revertirMovimiento(Cuenta...cuentas) {

		for(Cuenta cuenta : cuentas) {
			
			cuenta.cambiarSaldo(cuenta.obtenerSaldoPrevio());
			
		}

	}
}
