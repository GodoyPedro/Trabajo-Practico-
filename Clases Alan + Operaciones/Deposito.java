public class Deposito extends Operacion{

	public void depositar(Cuenta cuenta, double saldo){
		cuenta.agregarSaldo(saldo);
		cuenta.agregarMovimiento("deposito", saldo, cuenta.obtenerSaldo());
	}
}