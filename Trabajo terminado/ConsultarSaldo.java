
public class ConsultarSaldo extends Operacion {
	
	//Devuelve el saldo de la cuenta indicada.
	public double consultarSaldo(Cuenta cuenta) {
		
		return cuenta.obtenerSaldo();
		
	}
}
