

public class Extraccion extends Operacion {
	
	public void extraerFondos(Cuenta cuenta, int monto) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo{ 
		
		if(monto % 100 != 0) {
			
			System.err.println("No se puede seleccionar montos que no sean multiplos de 100");	
		}
		
		cuenta.retirarSaldo(monto);
		
	}
}

