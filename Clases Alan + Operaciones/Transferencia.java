public class Transferencia extends Operacion implements Reversible{

	
	public void transferir(Cuenta cuenta1,Cuenta cuenta2, double saldo){
		cuenta1.quitarSaldo(saldo);
		cuenta2.agregarSaldo(saldo);
		cuenta1.agregarMovimiento("transferencia", saldo, cuenta1.obtenerSaldo());
		cuenta2.agregarMovimiento("transferencia", -saldo, cuenta1.obtenerSaldo());
	}

	@Override
	public void revertirMovimiento(Cuenta cuenta) {
		cuenta.saldo = cuenta.saldoPrevio;
	}
}