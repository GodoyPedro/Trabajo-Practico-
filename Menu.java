
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private Cajero cajero;
	private Operacion[] operaciones;

	public Menu(Cajero cajero) {

		this.cajero = cajero;
		operaciones = new Operacion[6];

		operaciones[0] = new Extraccion();
		operaciones[1] = new Deposito();
		operaciones[2] = new Transferencia();
		operaciones[3] = new ComprarDolares();
		operaciones[4] = new ConsultarSaldo();
		operaciones[5] = new HistorialMovimientos();
	}

	public static void main(String[] args) throws IOException {

		System.out.println("INGRESE EL PIN DE LA TARJETA");
		Tarjeta tarjeta = new Tarjeta("12345678");
		Cajero cajero = new Cajero(tarjeta);

		Menu menu = new Menu(cajero);
		while (true) {
			menu.desplegarInterfaz();
		}
	}

	public void desplegarInterfaz() {

		Scanner escaner = new Scanner(System.in);

		System.out.println("        BIENVENIDO");
		System.out.println("SELECCIONE EL TIPO DE OPERACION\n     QUE DESEA EFECTUAR\n");
		System.out.println("01 EXTRACCIONES");
		System.out.println("02 COMPRAR DOLARES");
		System.out.println("03 DEPOSITOS");
		System.out.println("04 TRANSFERENCIAS");
		System.out.println("05 CONSULTAR SALDO");
		System.out.println("06 REVERTIR TRANSFERENCIA");
		System.out.println("07 CERRAR SESION");

		String eleccion;

		eleccion = escaner.nextLine();

		if (eleccion.equals("01")) {

			imprimirMenuExtracciones(escaner);

		} else if (eleccion.equals("02")) {

			imprimirMenuCompraDolares(escaner);

		} else if (eleccion.equals("03")) {

			imprimirMenuDepositos(escaner);

		}

		else if (eleccion.equals("04")) {

			imprimirMenuTransferencia(escaner);

		} else if (eleccion.equals("05")) {

			imprimirMenuConsultas(escaner);
			
		} else if (eleccion.equals("06")) {

			revertirTransferencia();
			
		} else if(eleccion.equals("07")) {
			
			cerrarSesion();
		}

	}

	private void cerrarSesion() {
	
		System.out.println("       MUCHAS GRACIAS\n"
				+ "NO OLVIDE RETIRAR SU TARJETA");
		
//		cajero.finalizarMovimientos();
		
	}

	private void imprimirMenuConsultas(Scanner escaner) {

		String alias;
		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE DESEA CONSULTAR EL SALDO");
		alias = escaner.nextLine();

		consultarSaldo(alias);
	}

	private void imprimirMenuTransferencia(Scanner escaner) {

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE SE DEBITARA");
		String alias = escaner.nextLine();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA A TRANSFERIR");
		String aliasATransferir = escaner.nextLine();
		double monto = mostrarMontos(escaner);
		realizarTransferencia(alias, aliasATransferir, monto);
	}

	private void imprimirMenuDepositos(Scanner escaner) {

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA A DEPOSITAR");

		String alias = escaner.nextLine();
		double monto = mostrarMontos(escaner);
		depositarFondos(alias, monto);

	}

	private void imprimirMenuCompraDolares(Scanner escaner) {

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA LA CUAL SE DEBITARA");
		String alias = escaner.nextLine();
		System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
		String aliasDolares = escaner.nextLine();
		System.out.println("ELIJA EL MONTO DE DOLARES A COMPRAR\n");
		double monto = mostrarMontos(escaner);
		comprarDolares(alias, aliasDolares, monto);
		;

	}

	private void imprimirMenuExtracciones(Scanner escaner) {

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA A EXTRAER");

		String alias = escaner.nextLine();

		double monto = mostrarMontos(escaner);
		retirarEfectivo(alias, monto);

	}

	private void generarSaltosDeLinea() {

		for (int i = 0; i < 10; i++) {

			System.out.println("");

		}

	}

	private double mostrarMontos(Scanner escaner) {

		System.out.println("      SELECCIONE EL MONTO\n");
		System.out.println("01 $ 5000          05     $ 300");
		System.out.println("02 $ 2000          06     $ 200");
		System.out.println("03 $ 1000          07     $ 100");
		System.out.println("04 $ 500           08   OTRO MONTO");

		double monto = 0;
		String eleccion = escaner.nextLine();

		if (eleccion.equals("08")) {

			System.out.println("INGRESE EL MONTO");
			monto = escaner.nextDouble();
		}

		else if (eleccion.equals("01")) {

			monto = 5000;
		} else if (eleccion.equals("02")) {

			monto = 2000;
		} else if (eleccion.equals("03")) {

			monto = 1000;

		}

		else if (eleccion.equals("04")) {

			monto = 500;

		} else if (eleccion.equals("05")) {

			monto = 300;
		}

		else if (eleccion.equals("06")) {

			monto = 200;

		} else if (eleccion.equals("07")) {

			monto = 100;
		} else {

			System.err.println("INGRESE UN MONTO VALIDO\n");
			mostrarMontos(escaner);
		}

		return monto;

	}

	private void retirarEfectivo(String alias, double monto) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		((Extraccion) operaciones[0]).extraerFondos(cuenta, monto);

		String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
				+ "Extraccion" + "," + monto + "," + cuenta.obtenerSaldo();

		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
				movimiento);

//		cajero.imprimirTicket(movimiento);
	}

	private void comprarDolares(String alias, String aliasDolares, double dolares) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		Cuenta cuentaDolares = cajero.getCliente().devolverCuenta(aliasDolares);

		((ComprarDolares) operaciones[3]).comprarDolares(cuenta, cuentaDolares, dolares);

		String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
				+ "Compra Dolares" + "," + dolares + "," + cuenta.obtenerSaldo();

		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuentaDolares.obtenerListaMovimientos(), movimiento);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
				movimiento);

//
//		cajero.imprimirTicket(movimiento);
//		
	}

	private void depositarFondos(String alias, double monto) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		((Deposito) operaciones[1]).depositar(cuenta, monto);

		String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
				+ "Deposito" + "," + monto + "," + cuenta.obtenerSaldo();

		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
				movimiento);

//		cajero.imprimirTicket(movimiento);
//		
//		

	}

	private void realizarTransferencia(String alias, String aliasATransferir, double monto) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		Cuenta cuentaATransferir = cajero.getCliente().devolverCuenta(aliasATransferir);

		((Transferencia) operaciones[2]).transferir(cuenta, cuentaATransferir, monto);

		String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
				+ "Transferencia" + "," + (-monto) + "," + cuenta.obtenerSaldo();
		String movimientoTransferido = cajero.obtenerFechaYHora().devolverFechaYHora() + ","
				+ cuentaATransferir.obtenerAlias() + "," + "Transferencia" + "," + monto + ","
				+ cuentaATransferir.obtenerSaldo();

		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuentaATransferir.obtenerListaMovimientos(),
				movimientoTransferido);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(), movimiento);
		((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
				movimientoTransferido);
//
//		cajero.imprimirTicket(movimiento);
	}

	private void consultarSaldo(String alias) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);

		((ConsultarSaldo) operaciones[4]).consultarSaldo(cuenta);

	}

	private void revertirTransferencia() {

		List<String> movimientos = cajero.getCliente().devolverListaMovimientos();

		String[] lineaDeMovimiento = movimientos.get(movimientos.size() - 1).split(",");

		if (lineaDeMovimiento[3].equals("Transferencia")) {

			String[] lineaTransferencia = movimientos.get(movimientos.size() - 2).split(",");

			Cuenta cuenta = cajero.getCliente().devolverCuenta(lineaTransferencia[2]);
			Cuenta cuentaTransferida = cajero.getCliente().devolverCuenta(lineaDeMovimiento[2]);

			((Transferencia) operaciones[2]).revertirMovimiento(cuenta, cuentaTransferida);

		}
		else {
			
			System.err.println("LA TRANSFERENCIA YA NO PUEDE SER REVERTIDA");
		}

	}
}
