package predeterminado;

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
		}
		else if(eleccion.equals("06")) {
			
			revertirTransferencia();
		}
	}

	private void imprimirMenuConsultas(Scanner escaner) {

		String alias;
		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE DESEA CONSULTAR EL SALDO");
		alias = escaner.nextLine();

		consultarSaldo(alias);
	}

	private void imprimirMenuTransferencia(Scanner escaner) {
		String eleccion;
		generarSaltosDeLinea();
		System.out.println("ELIJA LA CUENTA A DEBITAR\n");
		System.out.println("01 CUENTA CORRIENTE");
		System.out.println("02 CAJA DE AHORRO EN PESOS");

		eleccion = escaner.nextLine();
		double monto;

		if (eleccion.equals("01")) {
			generarSaltosDeLinea();
			System.out.println("INGRESE EL ALIAS DE LA CUENTA CORRIENTE");
			String alias = escaner.nextLine();
			System.out.println("INGRESE EL ALIAS A TRANSFERIR");
			String aliasATransferir = escaner.nextLine();
			monto = mostrarMontos(escaner);
			realizarTransferencia(alias, aliasATransferir, monto);

		} else if (eleccion.equals("02")) {
			generarSaltosDeLinea();
			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN PESOS");
			String alias = escaner.nextLine();
			System.out.println("INGRESE EL ALIAS A TRANSFERIR");
			String aliasATransferir = escaner.nextLine();
			monto = mostrarMontos(escaner);
			realizarTransferencia(alias, aliasATransferir, monto);
		}
	}

	private void imprimirMenuDepositos(Scanner escaner) {
		String eleccion;
		generarSaltosDeLinea();
		System.out.println("ELIJA LA CUENTA SOBRE LA QUE SE REALIZARA EL DEPOSITO\n");
		System.out.println("01 CUENTA CORRIENTE");
		System.out.println("02 CAJA DE AHORRO EN PESOS");
		System.out.println("03 CAJA DE AHORRO EN DOLARES");

		eleccion = escaner.nextLine();
		double monto;

		if (eleccion.equals("01")) {

			System.out.println("INGRESE EL ALIAS DE LA CUENTA CORRIENTE");
			String alias = escaner.nextLine();
			monto = mostrarMontos(escaner);
			depositarFondos(alias, monto);

		} else if (eleccion.equals("02")) {

			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN PESOS");
			String alias = escaner.nextLine();
			monto = mostrarMontos(escaner);
			depositarFondos(alias, monto);
		} else if (eleccion.equals("03")) {

			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
			String alias = escaner.nextLine();
			monto = mostrarMontos(escaner);
			depositarFondos(alias, monto);
		}
	}

	private void imprimirMenuCompraDolares(Scanner escaner) {
		String eleccion;
		generarSaltosDeLinea();

		System.out.println("ELIJA DE QUE CUENTA SE DEBITARA EL MONTO\n");
		System.out.println("01 CUENTA CORRIENTE");
		System.out.println("02 CAJA DE AHORRO EN PESOS");

		eleccion = escaner.nextLine();

		if (eleccion.equals("01")) {

			System.out.println("INGRESE EL ALIAS DE LA CUENTA CORRIENTE");
			String alias = escaner.nextLine();
			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
			String aliasDolares = escaner.nextLine();
			System.out.println("ELIJA EL MONTO DE DOLARES A COMPRAR\n");
			double monto = mostrarMontos(escaner);
			comprarDolares(alias, aliasDolares, monto);

		} else if (eleccion.equals("02")) {

			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN PESOS");
			String alias = escaner.nextLine();
			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
			String aliasDolares = escaner.nextLine();
			System.out.println("ELIJA EL MONTO DE DOLARES A COMPRAR\n");
			double monto = mostrarMontos(escaner);
			comprarDolares(alias, aliasDolares, monto);
		}
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

//		String movimiento = fecha + hora + cuenta.getAlias() + "Extraccion" + monto + cuenta.obtenerSaldo();


		new HistorialMovimientos().agregarMovimiento(cuenta.obtenerListaMovimientos(),movimiento);
		new HistorialMovimientos().agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),movimiento);

//		imprimirTicket(movimiento);
	}

	private void comprarDolares(String alias, String aliasDolares, double dolares) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		Cuenta cuentaDolares = cajero.getCliente().devolverCuenta(aliasDolares);

		((ComprarDolares) operaciones[3]).comprarDolares(cuenta, cuentaDolares, dolares);
//		
//		String movimiento = fecha + hora + cuenta.getAlias() + "Compra Dolares" + monto + cuenta.obtenerSaldo();
//		
//		new HistorialMovimientos().agregarMovimiento(cuenta.obtenerListaMovimientos(),movimiento);
//		new HistorialMovimientos().agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),movimiento);
//
//		imprimirTicket(movimiento);
//		
	}

	private void depositarFondos(String alias, double monto) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		((Deposito) operaciones[1]).depositar(cuenta, monto);

		String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
				+ "Deposito" + "," + monto + "," + cuenta.obtenerSaldo();

		((HistorialMovimientos)operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
		((HistorialMovimientos)operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(), movimiento);
		System.out.println(cuenta.obtenerListaMovimientos());
		System.out.println(cajero.getCliente().devolverListaMovimientos());
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
		String movimientoTransferido = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuentaATransferir.obtenerAlias() + ","
				+ "Transferencia" + "," + monto + "," + cuentaATransferir.obtenerSaldo();
		
		System.out.println("cuenta " + cuenta.obtenerSaldo());
		System.out.println("Cuenta a transferir " +cuentaATransferir.obtenerSaldo());
		
		new HistorialMovimientos().agregarMovimiento(cuenta.obtenerListaMovimientos(),movimiento);
		new HistorialMovimientos().agregarMovimiento(cuentaATransferir.obtenerListaMovimientos(), movimientoTransferido);
		new HistorialMovimientos().agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),movimiento);
		new HistorialMovimientos().agregarMovimiento(cajero.getCliente().devolverListaMovimientos(), movimientoTransferido);
//
//		imprimirTicket(movimiento);
	}

	private void consultarSaldo(String alias) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);

		System.out.println(((ConsultarSaldo) operaciones[4]).consultarSaldo(cuenta));

	}

	private void revertirTransferencia() {
		
		List <String> movimientos = cajero.getCliente().devolverListaMovimientos();
		System.out.println("Lista completa" + movimientos);
		String [] lineaDeMovimiento = movimientos.get(movimientos.size()-1).split(",");
		for(String linea : lineaDeMovimiento) {
			System.out.print(linea + ",");
		}
		if(lineaDeMovimiento[3].equals("Transferencia")) {
			System.out.println("Entre");
			String [] lineaTransferencia = movimientos.get(movimientos.size()-2).split(",");
			
			Cuenta cuenta = cajero.getCliente().devolverCuenta(lineaTransferencia[2]);
			Cuenta cuentaTransferida = cajero.getCliente().devolverCuenta(lineaDeMovimiento[2]);
			
			((Transferencia)operaciones[2]).revertirMovimiento(cuenta,cuentaTransferida);
			System.out.println("SAldo final " + cuenta.obtenerSaldo()+ " , " + "SAldo 2 " + cuentaTransferida.obtenerSaldo());
		}
		
	}
}
