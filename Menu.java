import java.util.Scanner;

public class Menu {

	private Cajero cajero;

	public Menu(Cajero cajero) {

		this.cajero = cajero;

	}

	public void desplegarInterfaz(){

		Scanner escaner = new Scanner(System.in);
		generarSaltosDeLinea();
		System.out.println(" BIENVENIDO AL CAJERO COVIDENTE\n");
		System.out.println("SELECCIONE EL  TIPO DE OPERACION\n     QUE DESEA EFECTUAR\n");
		System.out.println("01 EXTRACCIONES");
		System.out.println("02 COMPRAR DOLARES");
		System.out.println("03 DEPOSITOS");
		System.out.println("04 TRANSFERENCIAS");
		System.out.println("05 CONSULTAR SALDO");
		System.out.println("06 REVERTIR TRANSFERENCIA");
		System.out.println("07 CONSULTAR MOVIMIENTOS");
		System.out.println("08 CONSULTAR ALIAS");	
		System.out.println("09 CERRAR SESION");

		String eleccion = escaner.nextLine();
		
		if (eleccion.equals("01")) {

			imprimirMenuExtracciones(escaner);

		} else if (eleccion.equals("02")) {

			imprimirMenuCompraDolares(escaner);

		} else if (eleccion.equals("03")) {

			imprimirMenuDepositos(escaner);
		
		} else if (eleccion.equals("04")) {

			imprimirMenuTransferencia(escaner);

		} else if (eleccion.equals("05")) {
			
			imprimirMenuConsultarSaldo(escaner);
			
		} else if (eleccion.equals("06")) {
	
			imprimirMenuRevertirTransferencia(); 
			
		} else if (eleccion.equals("07")) {

			imprimirMenuMovimientos(escaner); 
		
		} else if (eleccion.equals("08")) {

			imprimirMenuAlias();
		
		} else if(eleccion.equals("09")) {
		
			imprimirMenuCerrarSesion();
		} else {
			
			System.err.println("Ingrese una opcion valida");
		}
		
		generarSaltosDeLinea();

	}

	private void imprimirMenuCerrarSesion() {
		
		System.out.println("       MUCHAS GRACIAS\n"
				+ "NO OLVIDE RETIRAR SU TARJETA");
		cajero.cerrarSesion();
		
		
	}	

	private void imprimirMenuAlias() {
	
		System.out.println("Sus cuentas son:\n");
		
		for(Cuenta cuenta: cajero.mostrarAlias()) {
			
			System.out.println(String.format("Tipo: %s\t\tAlias: %s", cuenta.getClass().getName(),cuenta.obtenerAlias()));
		}
	
	}

	private void imprimirMenuRevertirTransferencia() {
		
		int estado = cajero.revertirTransferencia();
		
		if(estado == 1) {
			
			System.out.println("Se revirtio exitosamente la transferencia");
		}
		
		else if(estado == 2) {
					
			System.err.println("El ultimo movimiento no es una transferencia");		
		}
		
		else if(estado == 3) {
			
			System.err.println("No se realizo ningun movimiento recientemente");
		}
		
	}

	private void imprimirMenuMovimientos(Scanner escaner) {
		
		System.out.println("INGRESE EL ALIAS DE LA CUENTA DE LA CUAL DESEA CONSULTAR SUS MOVIMIENTOS");
		
		String alias = escaner.nextLine();
		
		if(cajero.existeCuenta(alias)) {
			
			for(String movimiento: cajero.mostrarMovimientos(alias)) {
			
				System.out.println(movimiento);
			}
		}
	}

	private void imprimirMenuConsultarSaldo(Scanner escaner){

		
		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE DESEA CONSULTAR EL SALDO");
		String alias = escaner.nextLine();
		
		if(cajero.existeCuenta(alias)) {
		
			System.out.println(cajero.consultarSaldo(alias));
		}	
	}

	private void imprimirMenuTransferencia(Scanner escaner){

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE SE DEBITARA");
		String alias = escaner.nextLine();
		
		if(cajero.existeCuenta(alias) && cajero.noEsCajaDeAhorroDolares(alias)) {
			
			System.out.println("INGRESE EL ALIAS DE LA CUENTA A TRANSFERIR");
			String aliasATransferir = escaner.nextLine();
			
			if(!alias.equals(aliasATransferir) && cajero.existeCuenta(aliasATransferir) && cajero.noEsCajaDeAhorroDolares(aliasATransferir)) {
				
				int monto = mostrarMontos(escaner);
				cajero.realizarTransferencia(alias, aliasATransferir, monto);		
			}	
			
			else {
				
				System.err.println("NO PODES TRANSFERIR A LA MISMA CUENTA");
			}
		}
	}


	private void imprimirMenuDepositos(Scanner escaner){

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA A DEPOSITAR");

		String alias = escaner.nextLine();
		
		if(cajero.existeCuenta(alias)) {
			
			int monto = mostrarMontos(escaner);
			cajero.depositarFondos(alias, monto);
		}
	}

	private void imprimirMenuCompraDolares(Scanner escaner){

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA LA CUAL SE DEBITARA");
		String alias = escaner.nextLine();
		
		if(cajero.existeCuenta(alias) && cajero.noEsCajaDeAhorroDolares(alias)) {
			
			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
			String aliasDolares = escaner.nextLine();
			
			if(cajero.existeCuenta(aliasDolares) && cajero.esCajaDeAhorroDolares(aliasDolares)) {
			
				System.out.println("ELIJA EL MONTO DE DOLARES A COMPRAR\n");
				int monto = mostrarMontos(escaner);
				cajero.comprarDolares(alias, aliasDolares, monto);
			}
		}	
	}

	private void imprimirMenuExtracciones(Scanner escaner){

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA A EXTRAER");

		String alias = escaner.nextLine();
		
		if(cajero.existeCuenta(alias) && cajero.noEsCajaDeAhorroDolares(alias)) {

			int monto = mostrarMontos(escaner);
			cajero.retirarEfectivo(alias, monto);
		}
	}

	private void generarSaltosDeLinea() {

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	private int mostrarMontos(Scanner escaner) {
		
		int monto = 0;
		String eleccion = null;
		boolean importeValido = false;
		
		while(!importeValido) {
			
			System.out.println("      SELECCIONE EL MONTO\n");
			System.out.println("01 $ 5000          05     $ 300");
			System.out.println("02 $ 2000          06     $ 200");
			System.out.println("03 $ 1000          07     $ 100");
			System.out.println("04 $ 500           08   OTRO MONTO");
			
			eleccion = escaner.nextLine();

			if (eleccion.equals("01")) {

				monto = 5000;
				importeValido = true; 
				
			} else if (eleccion.equals("02")) {

				monto = 2000;importeValido = true;
				
			} else if (eleccion.equals("03")) {

				monto = 1000;importeValido = true;

			} else if (eleccion.equals("04")) {

				monto = 500;importeValido = true;

			} else if (eleccion.equals("05")) {

				monto = 300;importeValido = true;
				
			} else if (eleccion.equals("06")) {

				monto = 200;importeValido = true;
				
			} else if (eleccion.equals("07")) {

				monto = 100;importeValido = true;
				
			} else if (eleccion.equals("08")) {
				
				System.out.println("INGRESE EL MONTO");

				String valor = escaner.nextLine();
				
				try {
					
					monto = Integer.valueOf(valor);
					
					if(monto % 100 != 0) {
						
						System.err.println("No se puede seleccionar montos que no sean multiplos de 100");	
						
					}else {
						
						importeValido = true;
					}
					
				} catch (NumberFormatException error) {
					
					System.err.println("Debe ingresar un numero como valor");
				}
				
				
		
			} else{

				System.err.println("Ingrese un monto valido\n");
			}
		}
		
		return monto;
	}
}
