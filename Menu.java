
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private Cajero cajero;
	private Operacion[] operaciones;
	private static boolean operando = true;
	
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

	public static void main(String[] args) throws ErrorFaltanBilletes{
		
		inicializarCajero();
	}

	private static void inicializarCajero() throws ErrorFaltanBilletes {
		
		try {
			
			System.out.println("INGRESE EL NUMERO DE LA TARJETA");
			Scanner numeroTarjeta = new Scanner(System.in);
			Tarjeta tarjeta = new Tarjeta(numeroTarjeta.nextLine());
			System.out.println("INGRESE EL PIN DE LA TARJETA");
			validarPinTarjeta(tarjeta);
			Cajero cajero = new Cajero(tarjeta);
			Menu menu = new Menu(cajero);
	
			while (operando) {
				menu.desplegarInterfaz();
			}
		}
		catch(ErrorLimiteIntentosAlcanzado error) {
			
			System.err.println(error.getMessage());
		}
		catch(IOException error) {
			
			System.err.println(error.getMessage());
		}
		
	}

	private static void validarPinTarjeta(Tarjeta tarjeta) throws ErrorLimiteIntentosAlcanzado {
		
		Scanner entradaUsuario = new Scanner(System.in);
		
		int intentos = 3;
		
		while(intentos > 0) {
			
			if(entradaUsuario.nextLine().equals(tarjeta.obtenerPin())) {
				
				intentos = -1;
			}
			
			else {
				
				intentos--;
				
				if(intentos > 0) {
					System.out.println("Contraseña incorrecta "+"quedan "+intentos+" intentos");
				}
				
				else {
					System.out.println("No quedan mas intentos, bloqueando cuenta...");
				}
			}
		}
		
		if(intentos == 0) {
			
			throw new ErrorLimiteIntentosAlcanzado("Se bloqueo la cuenta");
		}		
	}

	public void desplegarInterfaz() throws ErrorFaltanBilletes {

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
		
		cajero.finalizarMovimientos();
		operando = false;
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
		int monto = mostrarMontos(escaner);
		realizarTransferencia(alias, aliasATransferir, monto);	

	}

	private void imprimirMenuDepositos(Scanner escaner) {

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA A DEPOSITAR");

		String alias = escaner.nextLine();
		int monto = mostrarMontos(escaner);
		depositarFondos(alias, monto);

	}

	private void imprimirMenuCompraDolares(Scanner escaner) {

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA LA CUAL SE DEBITARA");
		String alias = escaner.nextLine();
		System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
		String aliasDolares = escaner.nextLine();
		System.out.println("ELIJA EL MONTO DE DOLARES A COMPRAR\n");
		int monto = mostrarMontos(escaner);
		comprarDolares(alias, aliasDolares, monto);
	}

	private void imprimirMenuExtracciones(Scanner escaner) throws ErrorFaltanBilletes {

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA A EXTRAER");

		String alias = escaner.nextLine();

		int monto = mostrarMontos(escaner);
		retirarEfectivo(alias, monto);

	}

	private void generarSaltosDeLinea() {

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	private int mostrarMontos(Scanner escaner) {

		System.out.println("      SELECCIONE EL MONTO\n");
		System.out.println("01 $ 5000          05     $ 300");
		System.out.println("02 $ 2000          06     $ 200");
		System.out.println("03 $ 1000          07     $ 100");
		System.out.println("04 $ 500           08   OTRO MONTO");

		int monto = 0;
		String eleccion = escaner.nextLine();

		if (eleccion.equals("08")) {

			System.out.println("INGRESE EL MONTO");
			monto = escaner.nextInt();
		} else if (eleccion.equals("01")) {

			monto = 5000;
			
		} else if (eleccion.equals("02")) {

			monto = 2000;
			
		} else if (eleccion.equals("03")) {

			monto = 1000;

		} else if (eleccion.equals("04")) {

			monto = 500;

		} else if (eleccion.equals("05")) {

			monto = 300;
			
		} else if (eleccion.equals("06")) {

			monto = 200;
			
		} else if (eleccion.equals("07")) {

			monto = 100;
			
		} else{

			System.err.println("Ingrese un monto valido\n");
			mostrarMontos(escaner);
		}

		return monto;

	}

	private void retirarEfectivo(String alias, int monto) throws ErrorFaltanBilletes, ErrorCuentaInvalida {

		try {

			Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
		
			String[] billetes = cajero.dispensarBilletes(monto);
			
			String movimiento = null;
			
			if(!billetes[0].equals("-1")) {
				try {
				((Extraccion) operaciones[0]).extraerFondos(cuenta, monto);

				movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
						+ "Extraccion" + "," + monto + "," + cuenta.obtenerSaldo();

				((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
				((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
						movimiento);	
				}
				catch(ErrorCuentaInvalida error) {
					
					System.err.println(error.getMessage());
				}
			}
			
			String[] leyendasBilletes = {"Billetes de 1000: ","Billetes de 500: ","Billetes de 100: "};
			
			for (int i = 0; i < billetes.length; i++) {
				
				System.out.println(leyendasBilletes[i]+billetes[i]);
			}

			System.out.println(cajero.imprimirTicket(movimiento));
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo error) {
			
			System.err.println(error.getMessage());
			
		}
	}

	private void comprarDolares(String alias, String aliasDolares, int dolares) {

		try {

			Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
			Cuenta cuentaDolares = cajero.getCliente().devolverCuenta(aliasDolares);

			((ComprarDolares) operaciones[3]).comprarDolares(cuenta, cuentaDolares, dolares);

			String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
					+ "Compra Dolares" + "," + dolares + "," + cuenta.obtenerSaldo();

			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuentaDolares.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
					movimiento);
			
			
		
			System.out.println(cajero.imprimirTicket(movimiento));
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo | ErrorCuentaInvalida e) {
			
			System.err.println(e.getMessage());
			
		}
	}

	private void depositarFondos(String alias, int monto) {

		try {

			Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);
			((Deposito) operaciones[1]).depositar(cuenta, monto);

			String movimiento = cajero.obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
					+ "Deposito" + "," + monto + "," + cuenta.obtenerSaldo();

			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cajero.getCliente().devolverListaMovimientos(),
					movimiento);

			System.out.println(cajero.imprimirTicket(movimiento));
		}
		catch (ErrorAlIntroducirSaldo e) {

			System.err.println(e.getMessage());
		}
	}

	private void realizarTransferencia(String alias, String aliasATransferir, int monto) {
		
		try {

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

			System.out.println(cajero.imprimirTicket(movimiento));
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo | ErrorTransferencia e) {

			System.err.println(e.getMessage());
		}
	}

	private void consultarSaldo(String alias) {

		Cuenta cuenta = cajero.getCliente().devolverCuenta(alias);

		System.out.println(((ConsultarSaldo) operaciones[4]).consultarSaldo(cuenta));

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
			
			System.err.println("La transferencia no puede ser revertida");
		}

	}
}
