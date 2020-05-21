package predeterminado;

public class ComprarDolares extends Operacion {
	
	public void comprarDolares(Cuenta cuenta1, Cuenta cuenta2, double montoEnDolares) { //El usuario ingresaría la cantidad en dólares que quiere sacar.
		
		double montoEnPesos = montoEnDolares / 0.015;  //
		cuenta1.quitarSaldo(montoEnPesos * 1.3); //<--- Impuesto
		System.out.println(montoEnDolares);
		cuenta2.agregarSaldo(montoEnDolares);
		System.out.println("Pesos" + cuenta1.obtenerSaldo());
		System.out.println("Dolares" + cuenta2.obtenerSaldo());
//		cuenta1.agregarMovimiento("CompraDeDolares", -montoEnPesos, cuenta1.obtenerSaldo());
//		cuenta2.agregarMovimiento("CompraDeDolares", montoEnDolares, cuenta2.obtenerSaldo());
		
	}
}
