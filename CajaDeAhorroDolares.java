package predeterminado;

public class CajaDeAhorroDolares extends Cuenta{

	public CajaDeAhorroDolares(String alias, double saldo) {
		super(alias, saldo);
	}

	@Override
	public void quitarSaldo(double saldo) {
		if(saldo > this.saldo){
			throw new RuntimeException("No posee el saldo necesario para realizar esta operación.");
		}
		this.saldo -= saldo;
	}
}
