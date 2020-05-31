public class Transferencia extends Operacion implements Reversible {
	
	//Transfiere un monto desde una cuenta a otra. No se pueden realizar transferencias que involucren una cuenta en dólares.
	public void transferir(Cuenta cuenta1, Cuenta cuenta2, int montoATransferir) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		
		//El usuario no puede realizar una transferencia ingresando el mismo alias para ambas cuentas.
		if(cuenta1.obtenerAlias().equals(cuenta2.obtenerAlias())){
			
			System.err.println("NO PODES TRANSFERIR A LA MISMA CUENTA");
		}
		
		//Si el usuario ingresa un alias que pertenece a una caja de ahorro en dólares, se imprime un mensaje de error.
		else if(cuenta1.getClass().getName().equals("CajaDeAhorroDolares") || 
				cuenta2.getClass().getName().equals("CajaDeAhorroDolares")) {
			
			System.err.println("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES");
		}
		
		cuenta1.retirarSaldo(montoATransferir);
		cuenta2.depositarSaldo(montoATransferir);

	}
	//Este método permite revertir la última transferencia realizada. Cuando se lo llama, las dos cuentas involucradas en la transferencia vuelven a su estado previo a realizar el
	//movimiento.
	public void revertirMovimiento(Cuenta...cuentas) {

		for(Cuenta cuenta : cuentas) {
			
			cuenta.cambiarSaldo(cuenta.obtenerSaldoPrevio());
			
		}

	}
}
