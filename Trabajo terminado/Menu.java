import java.util.Arrays;
import java.util.Scanner;

public class Menu {

	private Cajero cajero;

	public Menu(Cajero cajero) {

		this.cajero = cajero;

	}

	public void desplegarInterfaz(){

		Scanner escaner = new Scanner(System.in);
		generarSaltosDeLinea();
		System.out.println("          BIENVENIDO AL CAJERO COVIDENTE\n");
		System.out.println("         SELECCIONE EL  TIPO DE OPERACION\n                QUE DESEA EFECTUAR\n");
		System.out.println("1 EXTRACCIONES               6 REVERTIR TRANSFERENCIA");
		System.out.println("2 COMPRAR DOLARES            7 CONSULTAR MOVIMIENTOS");
		System.out.println("3 DEPOSITOS                  8 CONSULTAR ALIAS");
		System.out.println("4 TRANSFERENCIAS             9 CERRAR SESION");
		System.out.println("5 CONSULTAR SALDO");

		String eleccion = escaner.nextLine();
		
		if (eleccion.equals("1")) {

			imprimirMenuExtracciones(escaner);

		} else if (eleccion.equals("2")) {

			imprimirMenuCompraDolares(escaner);

		} else if (eleccion.equals("3")) {

			imprimirMenuDepositos(escaner);
		
		} else if (eleccion.equals("4")) {

			imprimirMenuTransferencia(escaner);

		} else if (eleccion.equals("5")) {
			
			imprimirMenuConsultarSaldo(escaner);
			
		} else if (eleccion.equals("6")) {
	
			imprimirMenuRevertirTransferencia(); 
			
		} else if (eleccion.equals("7")) {

			imprimirMenuMovimientos(escaner); 
		
		} else if (eleccion.equals("8")) {

			imprimirMenuAlias();
		
		} else if(eleccion.equals("9")) {
		
			imprimirMenuCerrarSesion();
		} else {
			
			System.err.println("Ingrese una opcion valida");
		}
		
		generarSaltosDeLinea();

	}

	private void imprimirMenuCerrarSesion() {
		
		System.out.println("                   MUCHAS GRACIAS\n"
				+ "            NO OLVIDE RETIRAR SU TARJETA");
		cajero.cerrarSesion();
		
		
	}	

	private void imprimirMenuAlias() {
	
		System.out.println("Sus cuentas son:\n");
		
		for(Cuenta cuenta: cajero.obtenerAlias()) {
			
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
		
		
		if(cajero.comprobarExistenciaDeCuenta(alias)) {
				
			if(!cajero.obtenerMovimientos(alias).isEmpty()) {
				
				System.out.println("Los movimientos de esta cuenta son:\n");
				
				for(String movimiento: cajero.obtenerMovimientos(alias)) {
					
					String[] datosArray = movimiento.split(",");
					
					System.out.println("-----------------------------------------------");
					
					if(datosArray.length == 6) {
						
						movimiento = String.format("Fecha:         Hora:\n%s    %s\n"
								+ "\nTipo de operacion:\t%s\nAlias de la cuenta:\t%s\nImporte involucrado:\t%.2f\nSaldo final:\t\t%.2f"
								
								,datosArray[0],datosArray[1],datosArray[3],datosArray[2],Double.valueOf(datosArray[4]),Double.valueOf(datosArray[5]));
					}
					
					else {
						
						movimiento = String.format("Fecha:         Hora:\n%s    %s\n"
								+ "\nTipo de operacion:\t%s\nAlias de la cuenta a debitar:\t%s\nAlias de la cuenta a acreditar:\t%s\nImporte involucrado:\t%.2f\n"
								+ "Saldo final cuenta debitada:\t\t%.2f\nSaldo final cuenta acreditada:\t\t%.2f"
								
								,datosArray[0],datosArray[1],datosArray[4],datosArray[2],datosArray[3],Double.valueOf(datosArray[5]),Double.valueOf(datosArray[6]),Double.valueOf(datosArray[7]));
					}
					
					System.out.println(movimiento);
				}
			}
			else {
				
				System.out.println("No se realizaron movimientos en esta cuenta todavia");
			}
			
		}
	}

	private void imprimirMenuConsultarSaldo(Scanner escaner){

		
		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE DESEA CONSULTAR EL SALDO");
		String alias = escaner.nextLine();
		
		String moneda = cajero.obtenerCliente().obtenerCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")?"Dolares":"Pesos";
		
		if(cajero.comprobarExistenciaDeCuenta(alias)) {
		
			System.out.println(String.format("El saldo de su cuenta es:\n%.2f %s", cajero.consultarSaldo(alias),moneda));
		}	
	}

	private void imprimirMenuTransferencia(Scanner escaner){

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE SE DEBITARA");
		String alias = escaner.nextLine();
		
		if(cajero.comprobarExistenciaDeCuenta(alias) && cajero.noEsCajaDeAhorroDolares(alias)) {
			
			System.out.println("INGRESE EL ALIAS DE LA CUENTA A TRANSFERIR");
			String aliasATransferir = escaner.nextLine();
			
			if(alias.equals(aliasATransferir)) {
				
				System.err.println("NO PODES TRANSFERIR A LA MISMA CUENTA");
			}	
			
			else if (cajero.comprobarExistenciaDeCuenta(aliasATransferir) && cajero.noEsCajaDeAhorroDolares(aliasATransferir)){
				
				int monto = imprimirMenuMontos(escaner,"Transferencia");
				cajero.realizarTransferencia(alias, aliasATransferir, monto);		
			}
		}
	}

	private void imprimirMenuDepositos(Scanner escaner){

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA A DEPOSITAR");

		String alias = escaner.nextLine();
		
		if(cajero.comprobarExistenciaDeCuenta(alias)) {
			
			int monto = imprimirMenuMontos(escaner,"Deposito");
			cajero.depositarFondos(alias, monto);
		}
	}

	private void imprimirMenuCompraDolares(Scanner escaner){

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA LA CUAL SE DEBITARA");
		String alias = escaner.nextLine();
		
		if(cajero.comprobarExistenciaDeCuenta(alias) && cajero.noEsCajaDeAhorroDolares(alias)) {
			
			System.out.println("INGRESE EL ALIAS DE LA CAJA DE AHORRO EN DOLARES");
			String aliasDolares = escaner.nextLine();
			
			if(cajero.comprobarExistenciaDeCuenta(aliasDolares) && cajero.esCajaDeAhorroDolares(aliasDolares)) {
			
				System.out.println("ELIJA EL MONTO DE DOLARES A COMPRAR\n");
				int monto = imprimirMenuMontos(escaner,"ComprarDolares");
				cajero.comprarDolares(alias, aliasDolares, monto);
			}
		}	
	}

	private void imprimirMenuExtracciones(Scanner escaner){

		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA A EXTRAER");

		String alias = escaner.nextLine();
		
		if(cajero.comprobarExistenciaDeCuenta(alias) && cajero.noEsCajaDeAhorroDolares(alias)) {

			int monto = imprimirMenuMontos(escaner,"Extraccion");
			int estado = cajero.retirarEfectivo(alias, monto);
			
			if(estado == 1) {
				
				System.err.println("El cajero no dispone de esa cantidad de efectivo");
			}
			else if(estado == 2) {
				
				System.err.println("El saldo a introducir debe ser positivo");
			}
			
			System.out.println(Arrays.toString(cajero.obtenerBilletes()));
			
			String[] leyendasBilletes = {"Billetes de 1000: ","Billetes de 500: ","Billetes de 100: "};
			String[] billetes = cajero.obtenerBilletesAExtraer();
			
			for (int i = 0; i < billetes.length; i++) {
				
				System.out.println(leyendasBilletes[i]+billetes[i]);
			}
			
		}
	}

	private int imprimirMenuMontos(Scanner escaner, String operacion) {
		
		boolean corte = true;
		
		int monto = 0;
		
		while(corte) {
			
			monto = imprimirMenuMontos(escaner);
			
			if(monto > 0) {
				
				if(operacion.equals("ComprarDolares") || operacion.equals("Transferencia")) {
					
					corte = !(monto > 0);
				}
				else if (operacion.equals("Deposito")){
					
					corte = !(monto % 10 == 0);
				}
				else if (operacion.equals("Extraccion")){
					
					if(monto % 100 != 0) {
						
						System.err.println("Solo se pueden extraer multiplos de 100");
					}
					corte = !(monto % 100 == 0);
				}
			}
		}
		
		return monto;
	}
	
	private void generarSaltosDeLinea() {

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	private int imprimirMenuMontos(Scanner escaner) {
		
		int monto = 0;
		
		System.out.println("     SELECCIONE EL MONTO\n");
		System.out.println("1 $ 5000          5     $ 300");
		System.out.println("2 $ 2000          6     $ 200");
		System.out.println("3 $ 1000          7     $ 100");
		System.out.println("4 $ 500           8   OTRO MONTO");
		
		String eleccion = escaner.nextLine();

		if (eleccion.equals("1")) {

			monto = 5000;
					
		} else if (eleccion.equals("2")) {

			monto = 2000;
			
		} else if (eleccion.equals("3")) {

			monto = 1000;

		} else if (eleccion.equals("4")) {

			monto = 500;

		} else if (eleccion.equals("5")) {

			monto = 300;
			
		} else if (eleccion.equals("6")) {

			monto = 200;
			
		} else if (eleccion.equals("7")) {

			monto = 100;
			
		} else if (eleccion.equals("8")) {
			
			System.out.println("INGRESE EL MONTO");

			String valor = escaner.nextLine();	
			
			try {
				
				monto = Integer.valueOf(valor);
				
			} catch (NumberFormatException error) {
				
				System.err.println("Debe ingresar un numero como valor");
			}
		} else {
			
			System.err.println("Elija una opcion valida");
		}
	
		return monto;
	}
}
