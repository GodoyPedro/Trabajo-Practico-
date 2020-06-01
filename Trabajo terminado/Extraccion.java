

public class Extraccion extends Operacion {

	/**
	 * pre: El monto ingresado debe ser mayor a cero.
	 * post: Extrae el monto ingresado desde la cuenta indicada.
	 * 
	 * @param cuenta
	 * @param monto
	 * @throws ErrorSaldoInsuficiente
	 * @throws ErrorAlIntroducirSaldo
	 */
	public void extraerFondos(Cuenta cuenta, int monto) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {

		// Si el monto no es un múltiplo de 100, imprime el mensaje de error. Esto se
		// debe a que el cajero solo dispensa billetes de 1000, de 500 y de 100.
		if (monto % 100 != 0) {

			System.err.println("No se puede seleccionar montos que no sean multiplos de 100");
		}

		cuenta.retirarSaldo(monto);

	}
}
