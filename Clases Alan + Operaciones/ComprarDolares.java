public class ComprarDolares extends Operacion{

	public void comprarDolares(Cuenta cuenta1, Cuenta cuenta2, double saldo){
		cuenta1.quitarSaldo((((saldo)*100)*30)/100);
		cuenta2.agregarSaldo(saldo);
		cuenta1.agregarMovimiento("compraDolares", saldo, cuenta1.obtenerSaldo());
		cuenta2.agregarMovimiento("compraDolares", saldo, cuenta2.obtenerSaldo());
	}
}