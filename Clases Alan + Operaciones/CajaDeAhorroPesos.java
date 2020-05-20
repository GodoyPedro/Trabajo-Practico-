public class CajaDeAhorroPesos extends Cuenta {

	public CajaDeAhorroPesos(String alias, double saldo) {
		super(alias, saldo);
	}

	@Override
	public void quitarSaldo(double saldo) {
		if(saldo > this.saldo){
			throw new RuntimeException("No posee el saldo necesario para realizar esta operacion");
		}
		this.saldo -= saldo;
	}
}
/*
public void comprarDolares(CajaDeAhorroDolares cuenta, double cantidadDeDolares){
	if(cantidadDeDolares <= 0){
		throw new RuntimeException("La cantidad ingresada debe ser mayor a 0");
	}
	//actualizar segun el precio del dolar. 30 es el impuesto PAIS
	double dolaresAPesos = ((cantidadDeDolares*80)*30)/100;
	if(saldo - dolaresAPesos < 0){
		throw new RuntimeException("No posee el saldo necesario para comprar dolares");
	}
	saldo -= dolaresAPesos;
	cuenta.depositar(cantidadDeDolares);
	agregarMovimiento("comprarDolares(" + cantidadDeDolares + ")");
}

public void revertirMovimiento() {
	saldo = saldoPrevio;
}

public void transferir(Cuenta cuenta, double cantidad) {
	if(cantidad <= 0){
		throw new RuntimeException("La cantidad ingresada debe ser mayor a 0");
	}else if(saldo - cantidad < 0){
		throw new RuntimeException("No posee el saldo necesario para realizar esta operacion");
	}
	//crear otro if para comprobar que la cuenta no sea en dolares
	guardarSaldoPrevio();
	cuenta.guardarSaldoPrevio();
	saldo -= cantidad;
	cuenta.depositar(cantidad);
	agregarMovimiento("transferir(" + cuenta + ", " + cantidad + ")");
}*/