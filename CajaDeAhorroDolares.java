public class CajaDeAhorroDolares extends Cuenta{

	public CajaDeAhorroDolares(String alias, double saldo) throws ErrorAlIntroducirSaldo {
		super(alias, saldo);
	}

	@Override
	public void quitarSaldo(double saldo) throws ErrorSaldoInsuficiente {
		if(saldo > this.saldo){
			throw new ErrorSaldoInsuficiente();
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}