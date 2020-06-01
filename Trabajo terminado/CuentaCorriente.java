public class CuentaCorriente extends Cuenta {

	private double descubierto;

	/**
	 * pre: El saldo descubierto debe ser mayor a cero, el alias debe ser valido
	 * post: Se crea una Cuenta Corriente.
	 * 
	 * @param alias
	 * @param saldo
	 * @param descubierto
	 * @throws ErrorAlIntroducirSaldo
	 */
	public CuentaCorriente(String alias, double saldo, double descubierto) throws ErrorAlIntroducirSaldo {

		super(alias, saldo, descubierto);
		this.descubierto = descubierto;
	}
	

	@Override
	
	/*
	 * pre: El saldo debe ser mayor a cero.
	 * post: Se extrae saldo de la cuenta.
	 * @param saldo
	 */
	public void retirarSaldo(double saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {

		if ((-1) * descubierto > this.saldo - saldo) {

			throw new ErrorSaldoInsuficiente();
		} else if (saldo < 0) {

			throw new ErrorAlIntroducirSaldo();
		}

		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}
