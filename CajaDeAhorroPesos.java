public class CajaDeAhorroPesos extends Cuenta {

	public CajaDeAhorroPesos(String alias, double saldo) throws ErrorAlIntroducirSaldo {
		super(alias, saldo);
	}

	@Override
	public void quitarSaldo(double saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo{
		if(saldo > this.saldo){
			throw new ErrorSaldoInsuficiente();
		}else if(saldo < 0){
			throw new ErrorAlIntroducirSaldo();
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}