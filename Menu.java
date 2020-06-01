import java.util.Scanner;
/**
 * Esta clase se encarga de todo lo relacionado con imprimir a la pantalla.
 * Todo lo que el usuario puede ver por consola, sale de esta clase
 */
public class Menu {

	private Cajero cajero;

	public Menu(Cajero cajero) {

		this.cajero = cajero;

	}
	
	/**
	 * Muestra el menu de opciones para que el usuario pueda operar
	 */
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

	/**
	 * Menu de cerrar sesion, cierra la sesion en el cajero
	 */
	private void imprimirMenuCerrarSesion() {
		
		System.out.println("                   MUCHAS GRACIAS\n"
				+ "            NO OLVIDE RETIRAR SU TARJETA");
		cajero.cerrarSesion();	
	}	

	/**
	 * Muestra los alias de las cuentas del usuario
	 */
	private void imprimirMenuAlias() {
	
		System.out.println("Sus cuentas son:\n");
		
		for(Cuenta cuenta: cajero.obtenerAlias()) {
			
			System.out.println(String.format("Tipo: %s\t\tAlias: %s", cuenta.getClass().getName(),cuenta.obtenerAlias()));
		}
	
	}
	
	/**
	 * Se muestra una leyenda dependiendo de si se pudo realizar la operacion o no
	 */
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

	/**
	 * Muestra como maximo los ultimos 10 movimientos de la cuenta solicitada por el usuario
	 * @param escaner
	 */
	private void imprimirMenuMovimientos(Scanner escaner) {
		
		System.out.println("INGRESE EL ALIAS DE LA CUENTA DE LA CUAL DESEA CONSULTAR SUS MOVIMIENTOS");
		
		String alias = escaner.nextLine();
		
		if(cajero.comprobarExistenciaDeCuenta(alias) && !cajero.obtenerMovimientos(alias).isEmpty()) {

			System.out.println("Los movimientos de esta cuenta son:\n");
			
			for(String movimiento: cajero.obtenerMovimientos(alias)) {
				
				String[] datosArray = movimiento.split(",");
								
				if(datosArray.length == 6) {
					
					movimiento = String.format("-----------------------------------------------\nFecha:         Hora:\n%s    %s\n"
							+ "\nTipo de operacion:\t%s\nAlias de la cuenta:\t%s\nImporte involucrado:\t%.2f\nSaldo final:\t\t%.2f\n"
							+ "-----------------------------------------------"
							
							,datosArray[0],datosArray[1],datosArray[3],datosArray[2],Double.valueOf(datosArray[4]),Double.valueOf(datosArray[5]));
				}			 /*fecha          hora    tipo de operacion  alias                 importe                     saldo final*/
				
				else {
					
					movimiento = String.format("-----------------------------------------------\nFecha:         Hora:\n%s    %s\n"
							+ "\nTipo de operacion:\t%s\nAlias de la cuenta a debitar:\t%s\nAlias de la cuenta a acreditar:\t%s\nImporte involucrado:\t%.2f\n"
							+ "Saldo final cuenta debitada:\t\t%.2f\nSaldo final cuenta acreditada:\t\t%.2f\n"
							+ "-----------------------------------------------"
							
							,datosArray[0],datosArray[1],datosArray[4],datosArray[2],datosArray[3],Double.valueOf(datosArray[5]),Double.valueOf(datosArray[6]),Double.valueOf(datosArray[7]));
				}			 /*fecha          hora    tipo de operacion.aliasdebitado.aliasacreditado          importe                     saldo final debitada        saldo final acreditado*/
				
				System.out.println(movimiento);
			}
		}
		else {
			
			System.out.println("No se realizaron movimientos en esta cuenta todavia");
		}
		
		
	}

	/**
	 * Muestra el saldo de la cuenta pedida por el usuario
	 * @param escaner
	 */
	private void imprimirMenuConsultarSaldo(Scanner escaner){
	
		generarSaltosDeLinea();

		System.out.println("INGRESE EL ALIAS DE LA CUENTA QUE DESEA CONSULTAR EL SALDO");
		String alias = escaner.nextLine();
		
		/*Si el usuario trata de consultar un alias que no existe en su cuenta se lo informo*/
		try {
			
			String moneda = cajero.obtenerCliente().obtenerCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")?"Dolares":"Pesos";
			
			if(cajero.comprobarExistenciaDeCuenta(alias)) {
				
				System.out.println(String.format("El saldo de su cuenta es:\n%.2f %s", cajero.consultarSaldo(alias),moneda));
			}	
			
		} catch(NullPointerException e) {
			
			System.err.println("El alias de la cuenta ingresada no pertenece a tu tarjeta");
		}	
	}

	/**
	 * Muestra un menu para realizar una transferencia, preguntando la cuenta a debitar y a acreditar
	 * @param escaner
	 */
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
	
	/**
	 * Muestra un menu preguntando la cuenta a la que se desea depositar saldo
	 * @param escaner
	 */
	private void imprimirMenuDepositos(Scanner escaner){

		generarSaltosDeLinea();
		System.out.println("INGRESE EL ALIAS DE LA CUENTA A DEPOSITAR");

		String alias = escaner.nextLine();
		
		if(cajero.comprobarExistenciaDeCuenta(alias)) {
			
			int monto = imprimirMenuMontos(escaner,"Deposito");
			cajero.depositarFondos(alias, monto);
		}
	}
	
	/**
	 * Muestra un menu para realizar una compra de dolares, preguntando la cuenta a debitar y a acreditar
	 * @param escaner
	 */
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

	/**
	 * Muestra un menu preguntando la cuenta a la que se desea extraer un monto
	 * @param escaner
	 */
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
			
			/*Muestro en consola cuantos billetes de cada denominacion se estan devolviendo*/
			String[] billetes = cajero.obtenerBilletesAExtraer();
						
			if(billetes != null) {
				
				String[] leyendasBilletes = {"Billetes de 1000: ","Billetes de 500: ","Billetes de 100: "};
				
				for (int i = 0; i < billetes.length; i++) {
					
					System.out.println(leyendasBilletes[i]+billetes[i]);
				}
			}	
		}
	}
	
	/**
	 * Este metodo se llama siempre que quiero hacer una operacion como:
	 * extraccion
	 * deposito
	 * compra de dolares
	 * transferencia
	 * 
	 * Y se va a comportar de distinta manera segun la operacion que este realizando
	 * Intenamente va a llamar a un metodo que me muestra opciones de montos para elegir
	 * @param escaner
	 * @param operacion
	 * @return
	 */
	private int imprimirMenuMontos(Scanner escaner, String operacion) {
		
		boolean corte = true;
		
		int monto = 0;

		/*Imprimo el menu de los montos hasta que el usuario ingrese un monto correcto dependiendo de la operacion que esta realizando*/
		 
		while(corte) {
			
			monto = imprimirMenuMontos(escaner);
			
			if(monto > 0) {
				
				/*Solo me fijo si el monto ingresado es mayor a 0*/
				if(operacion.equals("ComprarDolares") || operacion.equals("Transferencia")) {
					
					corte = !(monto > 0);
				}
				/*Solo puedo ingresar billetes mayores a 10 pesos*/
				else if (operacion.equals("Deposito")){
					
					if(monto % 10 != 0) {
						
						System.err.println("Solo se pueden depositar multiplos de 10");
					}
					corte = !(monto % 10 == 0);
				}
				/*Solo puedo extraer multiplos de 100*/
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
	
	/**
	 * Imprimo un espacio entre las llamadas de metodos para que no quede todo junto
	 */
	private void generarSaltosDeLinea() {

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	/**
	 * Muestra opciones de montos a elegir, tambien permitiendo que el usuario elija un monto personalizado
	 * (siempre dentro de los paramtros posibles)
	 * @param escaner
	 * @return
	 */
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
