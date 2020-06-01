import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Es la clase principal de programa, se va a encargar de realizar todas las operaciones que el usuario solicita
 */
public class Cajero {
	/*El cajero almacena un cliente cuando este introduce su tarjeta*/
	private Cliente cliente;
	
	private boolean operando = true;
	private Menu menu;

	private int[] cantidadDeBilletes = {500,500,500};
	private FechaYHora fechaYHora;
	
	/*Tengo todas las operaciones que puedo realizar*/
	private Extraccion operacionExtraer;
	private Deposito operacionDepositar;
	private Transferencia operacionTransferir;
	private ComprarDolares operacionComprarDolares;
	private ConsultarSaldo operacionConsultarSaldo;
	private HistorialMovimientos operacionHistorial;
	private ConsultarAlias operacionConsultarAlias;
	
	/*Voy a utilizar la base de datos cuando necesite buscar algun dato necesario para realizar alguna operacion*/
	private BaseDeDatos baseDatos;
	
	/*Son los billetes que se le van a entregar al usuario cuando realiza una extraccion*/
	private String[] billetes;

	/**
	 * El cajero se crea leyendo una tarjeta y luego procede a indicarle a menu que despliegue la interfaz
	 * @param tarjeta
	 */
	public Cajero(Tarjeta tarjeta) {
		
		cliente = leerTarjeta(tarjeta);
		
		operacionExtraer = new Extraccion();
		operacionDepositar = new Deposito();
		operacionTransferir = new Transferencia();
		operacionComprarDolares = new ComprarDolares();
		operacionConsultarSaldo = new ConsultarSaldo();
		operacionHistorial = new HistorialMovimientos();
		operacionConsultarAlias = new ConsultarAlias();
		
		fechaYHora = new FechaYHora();
		menu = new Menu(this);
		baseDatos = new BaseDeDatos();
		
		while(operando) {
			
			menu.desplegarInterfaz();
		}
	}
	
	/**
	 * Utiliza la operacion de retirar efectivo para realizar la accion
	 * @param alias
	 * @param monto
	 * @return
	 */
	public int retirarEfectivo(String alias, int monto){

		/*En todos los metodos en que la operacion tiene distinto resultado dependiendo de si se pudo realizar o no
		 * voy a devolverle a menu un estado, este me va a indicar si se pudo realizar correctamente, o hubo algun inconveniente
		 * y si lo hubo, dependiendo que estado fue, voy a interpretar en el menu cual fue el problema mostrandole al usuario una leyenda*/
		int estado = 0;
		
		try {
			
			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			
			boolean cajeroTieneBilletesSuficientes = comprobarDisponibilidadDeBilletes(monto, cantidadDeBilletes.length);
			
			/*Esta string la utilizo para guardar en el historial que se realizo la operacion*/
			String movimiento = null;
			
			if(monto > 0 ) {
				
				if(cajeroTieneBilletesSuficientes) {			
					
					operacionExtraer.extraerFondos(cuenta, monto);
					
					/*Si se pudieron extraer fondos de la cuenta, extraigo billetes del cajero y me los guardo aca*/
					billetes = dispensarBilletes(monto);

					movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
					+ "Extraccion" + "," + monto + "," + cuenta.obtenerSaldo();
					
					/*Agrego a la lista de movimiento de cada cuenta y a una lista general (donde se guardan todos los movimientos de todas las cuentas) 
					 * el movimiento realizado*/	
					operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);					
					operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),
							movimiento);	
					
					/* Muestro el ticket en pantalla, y escribo 2 archivos de texto, el archivo de movimientos pedido y un archivo
					 * de tickets que almacena todos los tickets que el usuario produjo */
					System.out.println(imprimirTicket(movimiento));					
					baseDatos.escribirArchivoMovimientos(formatearListaMovimientosGenerales(obtenerCliente().obtenerListaMovimientos()));
					baseDatos.escribirArchivoTickets(movimiento);
				}
				else {
					
					estado = 1;
				}
			}
			else {
				
				estado = 2;
			}	
		}
		
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo error) {
			
			System.err.println(error.getMessage());
		}
		
		return estado;
	}

	/**
	 * Utiliza la operacion de comprar dolares para realizar la accion
	 * @param alias
	 * @param aliasDolares
	 * @param dolares
	 */
	public void comprarDolares(String alias, String aliasDolares, int dolares) {

		try {

			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			Cuenta cuentaDolares = obtenerCliente().obtenerCuenta(aliasDolares);

			operacionComprarDolares.comprarDolares(cuenta, cuentaDolares, dolares);	
			
			String movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
					+ "Compra Dolares" + "," + (dolares / 0.015)*1.3 + "," + cuenta.obtenerSaldo();
			
			String movimientoTransferido = obtenerFechaYHora().obtenerFechaYHoraActual() + ","+ cuentaDolares.obtenerAlias() + "," 
					+ "Transferencia" + "," + dolares + ","+ cuentaDolares.obtenerSaldo();
			
			String movimientoTicket = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + "," + cuentaDolares.obtenerAlias() + ","
					+ "Transferencia" + "," + dolares+"$" + "," + cuenta.obtenerSaldo() + "," + cuentaDolares.obtenerSaldo();
			
			operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(cuentaDolares.obtenerListaMovimientos(), movimientoTransferido);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),
					movimiento);

			System.out.println(imprimirTicket(movimientoTicket));		
			baseDatos.escribirArchivoMovimientos(formatearListaMovimientosGenerales(obtenerCliente().obtenerListaMovimientos()));
			baseDatos.escribirArchivoTickets(movimiento);
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo | ErrorCuentaInvalida e) {
			
			System.err.println(e.getMessage());
			
		}
	}
	/**
	 * Utiliza la operacion de depositar fondos para realizar la accion
	 * @param alias
	 * @param monto
	 */
	public void depositarFondos(String alias, int monto) {

		try {

			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			operacionDepositar.depositar(cuenta, monto);

			String movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
					+ "Deposito" + "," + monto + "," + cuenta.obtenerSaldo();
			
			operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),movimiento);

			System.out.println(imprimirTicket(movimiento));
			baseDatos.escribirArchivoMovimientos(formatearListaMovimientosGenerales(obtenerCliente().obtenerListaMovimientos()));
			baseDatos.escribirArchivoTickets(movimiento);
		}
		catch (ErrorAlIntroducirSaldo e) {

			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Utiliza la operacion de depositar fondos para realizar la accion
	 * @param alias
	 * @param aliasATransferir
	 * @param monto
	 */
    public void realizarTransferencia(String alias, String aliasATransferir, int monto) {
		
		try {

			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			Cuenta cuentaATransferir = obtenerCliente().obtenerCuenta(aliasATransferir);

			operacionTransferir.transferir(cuenta, cuentaATransferir, monto);
						
			String movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
					+ "Transferencia" + "," + (-monto) + "," + cuenta.obtenerSaldo();
			String movimientoTransferido = obtenerFechaYHora().obtenerFechaYHoraActual() + ","
					+ cuentaATransferir.obtenerAlias() + "," + "Transferencia" + "," + monto + ","
					+ cuentaATransferir.obtenerSaldo();
			String movimientoTicket = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + "," + cuentaATransferir.obtenerAlias() + ","
					+ "Transferencia" + "," + monto + "," + cuenta.obtenerSaldo() + "," + cuentaATransferir.obtenerSaldo();
				
			operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(cuentaATransferir.obtenerListaMovimientos(),movimientoTransferido);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),movimientoTransferido);

			System.out.println(imprimirTicket(movimientoTicket));
			baseDatos.escribirArchivoMovimientos(formatearListaMovimientosGenerales(obtenerCliente().obtenerListaMovimientos()));
			baseDatos.escribirArchivoTickets(movimientoTicket);
		}
		
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo error) {

			System.err.println(error.getMessage());
		}
	}
	
    /**
     * Utiliza la operacion consultar saldo para realizar la accion
     * @param alias
     * @return
     */
    public double consultarSaldo(String alias) {
		
		Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);

		return operacionConsultarSaldo.consultarSaldo(cuenta);
	}
	
    /**
     * Utiliza la operacion transferencia, con el metodo revertirMovimiento para realizar la accion
     * @return
     */
    public int revertirTransferencia(){
		
		int estado = 0;
    	
		/*Primero voy a fijarme la lista general de movimientos que tengo guardada, si el ultimo movimiento es una transferencia
		 * lo puedo revertir, sino no. Cuando lo revierto tomo esa string de la lista de movimientos y dependiendo que alias
		 * esten involucrados y el saldo, realiza la accion.
		 */
		List<String> movimientos = obtenerCliente().obtenerListaMovimientos();
			
		if(movimientos.size() > 0) {
			
			String[] lineaDeMovimiento = movimientos.get(movimientos.size() - 1).split(",");

			if (lineaDeMovimiento[3].equals("Transferencia")) {

				String[] lineaTransferencia = movimientos.get(movimientos.size() - 2).split(",");

				Cuenta cuenta = obtenerCliente().obtenerCuenta(lineaTransferencia[2]);
				Cuenta cuentaTransferida = obtenerCliente().obtenerCuenta(lineaDeMovimiento[2]);
				
				int monto = Integer.valueOf(lineaTransferencia[4]);
				
				String movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
						+ "RevertirTransferencia" + "," + -monto + "," + cuenta.obtenerSaldo();
				String movimientoTransferido = obtenerFechaYHora().obtenerFechaYHoraActual() + ","
						+ cuentaTransferida.obtenerAlias() + "," + "RevertirTransferencia" + "," + monto + ","
						+ cuentaTransferida.obtenerSaldo();
				
				String movimientoTicket = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuentaTransferida.obtenerAlias() + "," + cuenta.obtenerAlias() + ","
						+ "RevertirTransferencia" + "," + (-monto) + "," + cuentaTransferida.obtenerSaldo()+ "," + cuenta.obtenerSaldo();
								
				operacionTransferir.revertirMovimiento(cuenta, cuentaTransferida);
				
				operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
				operacionHistorial.agregarMovimiento(cuentaTransferida.obtenerListaMovimientos(), movimiento);
				operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),movimiento);
				operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),movimientoTransferido);
				
				System.out.println(imprimirTicket(movimientoTicket));
				
				baseDatos.escribirArchivoMovimientos(formatearListaMovimientosGenerales(obtenerCliente().obtenerListaMovimientos()));
				baseDatos.escribirArchivoTickets(movimientoTicket);
								
				estado = 1;
			}
			else {		
				
				estado = 2;
			}
		}
		else {
			
			estado = 3;
		}
		
		return estado;
	}
    
    /**
     * Para que el archivo de movimientos este con el formato pedido necesito modificar un poco la string que
     * se alamacena en la lista de movimientos
     * @param movimientosGenerales
     * @return
     */
    private List<String> formatearListaMovimientosGenerales(List<String> movimientosGenerales) {

		List<String> listaMovimientosFormateados = new LinkedList<String>();
		
		for(String movimiento : movimientosGenerales) {
			
			String[] movimientosSeparados = movimiento.split(",");
		
			listaMovimientosFormateados.add(movimientosSeparados[0]+","+movimientosSeparados[3]+","
					+movimientosSeparados[2]+","+movimientosSeparados[4]);
		}
		
		return listaMovimientosFormateados;
	}
    
    /**
     * Retorno la lista de movimientos que corresponde a la cuenta que tiene el alias pasado por parametro
     * @param alias
     * @return
     */
	public List<String> obtenerMovimientos(String alias) {
		
		Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
	
		return cuenta.obtenerListaMovimientos();
	}

	/**
	 * Retorna las cuentas que posee el cliente
	 * @return
	 */
    public List<Cuenta> obtenerListaCuentas() {
    	
    	List<String> listaAlias = operacionConsultarAlias.obtenerListaAlias(obtenerCliente().obtenerListaCuentas());

		baseDatos.escribirArchivoAlias(listaAlias);	
		
		return obtenerCliente().obtenerListaCuentas();
	}
	
    /**
     * Cierra la sesion y finaliza los movimientos dejando de mostrar el menu en consola
     */
    public void cerrarSesion() {
    	
		finalizarMovimientos();
		operando = false;
	}
	
    /**
     * Devuelve si se encuenta la cuenta que tiene el alias pasado por parametro
     * @param alias
     * @return
     */
    public boolean comprobarExistenciaDeCuenta(String alias){
    	
		if(obtenerCliente().obtenerCuenta(alias) == null) {
			
			System.err.println("NO SE ENCONTRO LA CUENTA!");
			return false;
		}
		
		return true;
	}
	
    /**
     * Devuelve si la cuenta que tiene el alias pasado por parametro no es una cuenta en dolares
     * @param alias
     * @return
     */
	public boolean noEsCajaDeAhorroDolares(String alias) {
		
		if(!obtenerCliente().obtenerCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")) {
			
			return true;
		}
		System.err.println("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES");
		return false;
	}
	
	/**
	 * Devuelve si la cuenta que tiene el alias pasado por parametro es una cuenta en dolares
	 * @param alias
	 * @return
	 */
	public boolean esCajaDeAhorroDolares(String alias) {
		
		if(obtenerCliente().obtenerCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")) {

			return true;
		}
		System.err.println("ESTA CUENTA NO ES EN DOLARES");
		return false;
	}
	
	
	public FechaYHora obtenerFechaYHora() {
		
		return fechaYHora;
	}
	
	/**
	 * Al crearse el cajero se lee la tarjeta ingresada y con esa tarjeta se crea un nuevo cliente
	 * @param tarjeta
	 * @return
	 */
	private Cliente leerTarjeta(Tarjeta tarjeta) {
		
		return new Cliente(tarjeta.obtenerCuit());
	}
	
	public Cliente obtenerCliente() {
		
		return cliente;
	}
	
	/**
	 * Le muestro al usuario un ticket conteniendo la informacion de la operacion que acaba de realizar
	 * @param datos
	 * @return
	 */
	public String imprimirTicket(String datos) {
		
		//fecha,hora,alias,operacion,monto,saldo
	
		String[] datosArray = datos.split(","); 
		String ticket = null;
		
		if(datosArray.length == 6) {
			
			ticket = String.format("-----------------------------------------------\n"
					+ "Fecha:         Hora:\n%s    %s\n\nTipo de operacion:\t%s\nAlias de la cuenta:\t%s\nImporte involucrado:\t%s\nSaldo final:\t\t%.2f"
					+ "\n-----------------------------------------------\n"
					,datosArray[0],datosArray[1],datosArray[3],datosArray[2],datosArray[4],Double.valueOf(datosArray[5]));
		}
		
		else {
			
			ticket = String.format("-----------------------------------------------\n"
					+ "Fecha:         Hora:\n%s    %s\n\nTipo de operacion:\t%s\nAlias de la cuenta a debitar:\t%s\nAlias de la cuenta a acreditar:\t%s\nImporte involucrado:\t%s\n"
					+ "Saldo final cuenta debitada:\t\t%.2f\nSaldo final cuenta acreditada:\t\t%.2f"
					+ "\n-----------------------------------------------\n"
					,datosArray[0],datosArray[1],datosArray[4],datosArray[2],datosArray[3],datosArray[5],Double.valueOf(datosArray[6]),Double.valueOf(datosArray[7]));
		}
		
		return ticket;
	}

	/**
	 * Borro el cliente y por lo tanto todas sus cuentas
	 */
	public void finalizarMovimientos() {
		
		cliente = null;
	}

	/**
	 * Devuelvo la cantidad de billetes de cada denominacion que corresponde con el monto pasado por parametro
	 * @param monto
	 * @return
	 */
	public String[] dispensarBilletes(int monto){

		int[] montos = {1000,500,100};
		String[] devolverBilletes = new String[cantidadDeBilletes.length];
		
		for(int i = 0; i<3; i++) {

			int cantidadNDeBilletes = monto / montos[i];

			if(cantidadNDeBilletes - cantidadDeBilletes[i] > 0) {

				devolverBilletes[i] = Integer.toString(cantidadDeBilletes[i]);
				cantidadDeBilletes[i] = 0;				
			}
			else {
				
				devolverBilletes[i] = Integer.toString(cantidadNDeBilletes);
				cantidadDeBilletes[i] -= cantidadNDeBilletes;
			}
			monto -= montos[i] * Integer.parseInt(devolverBilletes[i]);
		}
		return devolverBilletes;
	}
	
	/**
	 * Compruebo si es posible devolver en billetes la cantidad pasada por parametro
	 * @param monto
	 * @param longitud
	 * @return
	 */
	private boolean comprobarDisponibilidadDeBilletes(int monto, int longitud) {
		
		billetes = null;
		
		int[] montos = {1000,500,100};
		
		int plataEntregarPosible = 0;
		
		for (int i = 0; i < montos.length; i++) {
			
			plataEntregarPosible += cantidadDeBilletes[i]*montos[i];
		}
		
		return monto <= plataEntregarPosible;
	}
	
	/**
	 * Recargo los billetes que posee el cajero
	 */
	public void recargarBilletes() {

		for (int i = 0; i < cantidadDeBilletes.length; i++) {
			
			cantidadDeBilletes[i] += 500;		
		}
	}

	
	public int[] obtenerBilletesEnElCajero() {
		
		return cantidadDeBilletes;
	}
	
	/**
	 * Si es que el usuario debe recibir billetes, los devuelvo aca
	 * @return
	 */
	public String[] obtenerBilletesAExtraer() {
		
		return billetes;
	}
}
