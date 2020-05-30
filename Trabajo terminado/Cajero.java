import java.util.List;

public class Cajero {
	
	private Cliente cliente;
	private int[] cantidadDeBilletes = {500,500,500};
	private FechaYHora fechaYHora;
	private Menu menu;
	private Extraccion operacionExtraer;
	private Deposito operacionDepositar;
	private Transferencia operacionTransferir;
	private ComprarDolares operacionComprarDolares;
	private ConsultarSaldo operacionConsultarSaldo;
	private HistorialMovimientos operacionHistorial;
	private ConsultarAlias operacionConsultarAlias;
	private boolean operando = true;
	private BaseDeDatos baseDatos;
	private String[] billetes;

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

	public int retirarEfectivo(String alias, int monto){

		int estado = 0;
		
		try {
			
			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			
			boolean cajeroTieneBilletesSuficientes = comprobarDisponibilidadDeBilletes(monto, cantidadDeBilletes.length);
			
			System.out.println(cajeroTieneBilletesSuficientes+" monto: "+monto);
			
			String movimiento = null;
			
			if(monto > 0) {
				
				if(cajeroTieneBilletesSuficientes) {
					
					billetes = dispensarBilletes(monto);
					
					operacionExtraer.extraerFondos(cuenta, monto);

					movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
							+ "Extraccion" + "," + monto + "," + cuenta.obtenerSaldo();

					operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
					operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),
							movimiento);	
					
					System.out.println(imprimirTicket(movimiento));
					baseDatos.escribirArchivoMovimientos(obtenerCliente().obtenerListaMovimientos());
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
		
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo | ErrorFaltanBilletes error) {
			
			System.err.println(error.getMessage());
		}
		
		return estado;
	}
	
	public void comprarDolares(String alias, String aliasDolares, int dolares) {

		try {

			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			Cuenta cuentaDolares = obtenerCliente().obtenerCuenta(aliasDolares);

			operacionComprarDolares.comprarDolares(cuenta, cuentaDolares, dolares);

			String movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
					+ "Compra Dolares" + "," + (dolares / 0.015)*1.3 + "," + cuenta.obtenerSaldo();
			
			String movimientoTransferido = obtenerFechaYHora().obtenerFechaYHoraActual() + ","
					+ cuentaDolares.obtenerAlias() + "," + "Transferencia" + "," + dolares + ","
					+ cuentaDolares.obtenerSaldo();
			
			String movimientoTicket = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + "," + cuentaDolares.obtenerAlias() + ","
					+ "Transferencia" + "," + dolares+"$" + "," + cuenta.obtenerSaldo() + "," + cuentaDolares.obtenerSaldo();
			
			operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(cuentaDolares.obtenerListaMovimientos(), movimientoTransferido);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),
					movimiento);
			

		
			System.out.println(imprimirTicket(movimientoTicket));		
			baseDatos.escribirArchivoMovimientos(obtenerCliente().obtenerListaMovimientos());
			baseDatos.escribirArchivoTickets(movimiento);
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo e) {
			
			System.err.println(e.getMessage());
			
		}
	}
	
	public void depositarFondos(String alias, int monto) {

		try {

			Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
			operacionDepositar.depositar(cuenta, monto);

			String movimiento = obtenerFechaYHora().obtenerFechaYHoraActual() + "," + cuenta.obtenerAlias() + ","
					+ "Deposito" + "," + monto + "," + cuenta.obtenerSaldo();

			operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),
					movimiento);

			System.out.println(imprimirTicket(movimiento));
			baseDatos.escribirArchivoMovimientos(obtenerCliente().obtenerListaMovimientos());
			baseDatos.escribirArchivoTickets(movimiento);
		}
		catch (ErrorAlIntroducirSaldo e) {

			System.err.println(e.getMessage());
		}
	}
	
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
			operacionHistorial.agregarMovimiento(cuentaATransferir.obtenerListaMovimientos(),
					movimientoTransferido);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(), movimiento);
			operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),
					movimientoTransferido);

			System.out.println(imprimirTicket(movimientoTicket));
			baseDatos.escribirArchivoMovimientos(obtenerCliente().obtenerListaMovimientos());
			baseDatos.escribirArchivoTickets(movimientoTicket);
		}
		
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo error) {

			System.err.println(error.getMessage());
		}
	}
	
    public double consultarSaldo(String alias) {
		
		Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);

		return operacionConsultarSaldo.consultarSaldo(cuenta);
	}
	
    public int revertirTransferencia(){
		
		int estado = 0;
    	
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
						+ "RevertirTransferencia" + "," + (-monto) + "," + String.format("%.2f", cuentaTransferida.obtenerSaldo())+ "," + String.format("%.2f", cuenta.obtenerSaldo());

				operacionTransferir.revertirMovimiento(cuenta, cuentaTransferida);
				
				operacionHistorial.agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
				operacionHistorial.agregarMovimiento(cuentaTransferida.obtenerListaMovimientos(), movimiento);
				operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),movimiento);
				operacionHistorial.agregarMovimiento(obtenerCliente().obtenerListaMovimientos(),movimientoTransferido);
				
				System.out.println(imprimirTicket(movimientoTicket));
				
				baseDatos.escribirArchivoMovimientos(obtenerCliente().obtenerListaMovimientos());
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
    
	public List<String> obtenerMovimientos(String alias) {
		
		Cuenta cuenta = obtenerCliente().obtenerCuenta(alias);
	
		return cuenta.obtenerListaMovimientos();
	}

    public List<Cuenta> obtenerAlias() {
    	
    	List<String> listaAlias = operacionConsultarAlias.obtenerListaAlias(obtenerCliente().obtenerListaCuentas());

		baseDatos.escribirArchivoAlias(listaAlias);	
		
		return obtenerCliente().obtenerListaCuentas();
	}
	
    public void cerrarSesion() {
    	
		finalizarMovimientos();
		operando = false;
	}
	
    public boolean comprobarExistenciaDeCuenta(String alias){
    	
		if(obtenerCliente().obtenerCuenta(alias) == null) {
			
			System.err.println("NO SE ENCONTRO LA CUENTA!");
			return false;
		}
		
		return true;
	}
	
	public boolean noEsCajaDeAhorroDolares(String alias) {
		
		if(!obtenerCliente().obtenerCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")) {
			
			return true;
		}
		System.err.println("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES");
		return false;
	}
	
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
	
	private Cliente leerTarjeta(Tarjeta tarjeta) {
		
		return new Cliente(tarjeta.obtenerCuit());
	}
	
	public Cliente obtenerCliente() {
		
		return cliente;
	}
	
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

	public void finalizarMovimientos() {
		
		cliente = null;
	}

	public String[] dispensarBilletes(int monto) throws ErrorFaltanBilletes{

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
	
	private boolean comprobarDisponibilidadDeBilletes(int monto, int longitud) {
		
		int[] montos = {1000,500,100};
		
		int plataEntregarPosible = 0;
		
		for (int i = 0; i < montos.length; i++) {
			
			plataEntregarPosible += cantidadDeBilletes[i]*montos[i];
		}
		
		return monto <= plataEntregarPosible;
	}
	
	public void recargarBilletes() {

		for (int i = 0; i < cantidadDeBilletes.length; i++) {
			
			cantidadDeBilletes[i] += 500;		
		}
	}

	public int[] obtenerBilletes() {
		
		return cantidadDeBilletes;
	}
	
	public String[] obtenerBilletesAExtraer() {
		
		return billetes;
	}
}
