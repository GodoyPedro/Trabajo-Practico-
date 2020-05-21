package predeterminado;

public class CuentaCorriente extends Cuenta {
	
	//El descubierto tiene que ser si o si positivo
	private double descubierto;
	
	public CuentaCorriente(String alias, double saldo, double descubierto) {
		super(alias, saldo, descubierto);
		this.descubierto = descubierto;
	}

	@Override
	public void quitarSaldo(double saldo) {
		if((-1)*descubierto > this.saldo - saldo){
			throw new RuntimeException("No posee el saldo necesario para realizar esta operación.");
		}
		this.saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}