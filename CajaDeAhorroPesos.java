package predeterminado;

public class CajaDeAhorroPesos extends Cuenta {

	public CajaDeAhorroPesos(String alias, double saldo) {
		super(alias, saldo);
	}

	@Override
	public void quitarSaldo(double saldo) {
		if(saldo > this.saldo){
			throw new RuntimeException("No posee el saldo necesario para realizar esta operación.");
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}
