public class ComprarDolares extends Operacion {
	
	public void comprarDolares(Cuenta cuenta1, Cuenta cuenta2, int montoEnDolares) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo { 
		
		double montoEnPesos = montoEnDolares / 0.015;  //
		cuenta1.quitarSaldo(montoEnPesos * 1.3); //<--- Impuesto
		cuenta2.agregarSaldo(montoEnDolares);
		
	}
}


