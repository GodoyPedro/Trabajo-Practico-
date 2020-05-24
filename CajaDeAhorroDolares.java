public class CajaDeAhorroDolares extends Cuenta{

	public CajaDeAhorroDolares(String alias, double saldo) throws ErrorAlIntroducirSaldo {
		super(alias, saldo);
	}

	@Override
	public void quitarSaldo(int saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		if(saldo > this.saldo){
			throw new ErrorSaldoInsuficiente();
		}else if(saldo < 0){
			throw new ErrorAlIntroducirSaldo();
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}
