import java.util.LinkedList;
import java.util.List;

public abstract class Cuenta {

	protected double saldo;
	protected String alias;
	protected double saldoPrevio;
	protected List<String> movimientos;

	/**
	 * pre: El constructor es exclusivo de una Cuenta Corriente, el saldo descubierto
	 * debe ser mayor a cero, el alias debe ser valido 
	 * post: Se crea una Cuenta Corriente.
	 * 
	 * @param alias
	 * @param saldo
	 * @param descubierto
	 * @throws ErrorAlIntroducirSaldo
	 */
	public Cuenta(String alias, double saldo, double descubierto) throws ErrorAlIntroducirSaldo {
		if (descubierto < 0 || saldo < -descubierto) {
			throw new ErrorAlIntroducirSaldo();
		}
		movimientos = new LinkedList<String>();
		this.alias = alias;
		this.saldo = saldo;
	}

	/**
	 * pre: El constructor es exclusivo de las cajas de ahorro, el saldo
	 * debe ser mayor a cero, el alias debe ser valido
	 * 
	 * post: Se crea una Caja De Ahorro
	 * @param alias
	 * @param saldo
	 * @throws ErrorAlIntroducirSaldo
	 */
	public Cuenta(String alias, double saldo) throws ErrorAlIntroducirSaldo {
		if (saldo < 0) {
			throw new ErrorAlIntroducirSaldo();
		}
		movimientos = new LinkedList<String>();
		this.alias = alias;
		this.saldo = saldo;
	}

	/**
	 * post: Devuelve el saldo de la cuenta.
	 * 
	 * @return saldo
	 */
	public double obtenerSaldo() {
		return saldo;
	}
	
	/**
	 * post: Devuelve el saldo previo de la cuenta
	 * 
	 * @return saldoPrevio
	 */
	public double obtenerSaldoPrevio() {
		return saldoPrevio;
	}
	
	
	/**
	 * pre: El saldo debe ser mayor a cero.
	 * post: Se reemplaza el saldo que tenga la cuenta.
	 * 
	 * @param saldo
	 */
	public void cambiarSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * pre: El saldo debe ser mayor a cero.
	 * post: Se deposita saldo a la cuenta.
	 * 
	 * @param saldo
	 * @throws ErrorAlIntroducirSaldo
	 */
	public void depositarSaldo(int saldo) throws ErrorAlIntroducirSaldo {
		if (saldo < 0) {
			throw new ErrorAlIntroducirSaldo();
		}
		this.saldoPrevio = this.saldo;
		this.saldo += saldo;
	}
	
	
	/**
	 * post: Devuelve el alias de la cuenta
	 * 
	 * @return alias
	 */
	public String obtenerAlias() {
		return alias;
	}
	
	
	/**
	 * pre: Devuelve la lista de movimientos de la cuenta
	 * 
	 * @return movimientos
	 */
	public List<String> obtenerListaMovimientos() {
		return movimientos;
	}

	/**
	 * pre: El saldo debe ser mayor a cero.
	 * post: Cada cuenta define como extraer saldo
	 * 
	 * @param saldo
	 * @throws ErrorSaldoInsuficiente
	 * @throws ErrorAlIntroducirSaldo
	 */
	public abstract void retirarSaldo(double saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo;
	
}
