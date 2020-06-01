public class CajaDeAhorroDolares extends Cuenta{
	/**
	 * 
	 * pre: El saldo debe ser mayor a cero, el alias debe ser valido.
	 * post: Se crea una Caja de ahorro en dolares
	 * 
	 * @param alias
	 * @param saldo
	 * @throws ErrorAlIntroducirSaldo
	 */
	public CajaDeAhorroDolares(String alias, double saldo) throws ErrorAlIntroducirSaldo {
		super(alias, saldo);
	}

	@Override
	/**
	 * pre: El saldo debe ser mayor a cero
	 * post Se extrae saldo de la Caja de Ahorro 
	 */
	public void retirarSaldo(double saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		if(saldo > this.saldo){
			throw new ErrorSaldoInsuficiente();
		}else if(saldo < 0){
			throw new ErrorAlIntroducirSaldo();
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}
