package predeterminado;

public class ConsultarSaldo extends Operacion {
	
	public double consultarSaldo(Cuenta cuenta) {
		
		return cuenta.obtenerSaldo();
		
	}
}
