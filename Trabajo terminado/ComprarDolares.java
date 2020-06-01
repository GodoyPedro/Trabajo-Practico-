public class ComprarDolares extends Operacion {
	
	//Extrae dinero en pesos desde una cuenta corriente o caja de ahorro en pesos, y lo deposita como dólares en una caja de ahorro en dólares.
	//El usuario ingresa el monto en dólares que quiere depositar en su caja de ahorro en dólares.
	public void comprarDolares(Cuenta cuenta1, Cuenta cuenta2, int montoEnDolares) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo { 
		
		double montoEnPesos = montoEnDolares / 0.015; //Se hace el pasaje de pesos a dólares. 1 dólar = 66.66 pesos (Dato extraído según
		                                              //Google. Actualizado a fecha de 23/5/2020.
		cuenta1.retirarSaldo(montoEnPesos * 1.3); //Se aplica el impuesto de 30% a la compra de dólares.
		cuenta2.depositarSaldo(montoEnDolares);
		
	}
}
