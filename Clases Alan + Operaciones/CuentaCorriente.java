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
			throw new RuntimeException("No posee el saldo necesario para realizar esta operacion");
		}
		saldoPrevio = this.saldo;
		this.saldo -= saldo;
	}
}
/*
public void comprarDolares(CajaDeAhorroDolares cuenta, double cantidadDeDolares){
	if(cantidadDeDolares <= 0){
		throw new RuntimeException("La cantidad ingresada debe ser mayor a 0");
	}
	//Actualizar segun el precio del dolar(ahora es 80 dolares). el *30 es el impuesto PAIS
	double dolaresAPesos = ((cantidadDeDolares*80)*30)/100;
	
	if((-1)*descubierto > saldo - dolaresAPesos){
		throw new RuntimeException("No posee el saldo necesario para realizar esta operacion");
	}
	saldo -= dolaresAPesos;
	cuenta.depositar(cantidadDeDolares);
	agregarMovimiento("comprarDolares(" + cantidadDeDolares + ")");
}
*
public void retirar(double cantidad){
	if((-1)*descubierto > saldo - cantidad){
		throw new RuntimeException("No posee el saldo necesario para realizar esta operacion");
	}
	saldo -= cantidad;
	agregarMovimiento("retirar(" + cantidad + ")");
}

public void revertirMovimiento() {
	saldo = saldoPrevio;
}

public void transferir(Cuenta cuenta, double cantidad) {
	if((-1)*descubierto > saldo - cantidad){
		throw new RuntimeException("No posee el saldo necesario para realizar esta operacion");
	}
	else if(cantidad <= 0){
		throw new RuntimeException("La cantidad ingresada debe ser mayor a 0");
	}
	//Crear otro if para comprobar que la cuenta no sea en dolares
	guardarSaldoPrevio();
	cuenta.guardarSaldoPrevio();
	saldo -= cantidad;
	cuenta.depositar(cantidad);
	agregarMovimiento("transferir(" + cuenta + ", " + cantidad + ")");
}*/