public class Extraccion extends Operacion{

	public void extraerFondos(Cuenta cuenta, double saldo){
		cuenta.quitarSaldo(saldo);
		cuenta.agregarMovimiento("retirar", saldo, cuenta.obtenerSaldo());
	}
}