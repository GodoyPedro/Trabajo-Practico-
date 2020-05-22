public class CuentaCorriente extends Cuenta {
	
	//El descubierto tiene que ser si o si positivo
	private double descubierto;
	
	public CuentaCorriente(String alias, double saldo, double descubierto) throws ErrorAlIntroducirSaldo {
		super(alias, saldo, descubierto);
		this.descubierto = descubierto;
	}

	@Override
	public void quitarSaldo(double saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		if((-1)*descubierto > this.saldo - saldo){
			throw new ErrorSaldoInsuficiente();
		}else if(saldo < 0){
			throw new ErrorAlIntroducirSaldo();
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}