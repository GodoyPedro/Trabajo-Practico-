import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cajero {
	
	private Cliente cliente;
	private int[] cantidadDeBilletes = {500,500,500};
	private FechaYHora fechaYHora;
	private Menu menu;
	private Operacion[] operaciones;
	private boolean operando = true;
	private BaseDeDatos baseDatos;

	public Cajero(Tarjeta tarjeta) {
		
		cliente = leerTarjeta(tarjeta);
		fechaYHora = new FechaYHora();
		operaciones = new Operacion[7];
		operaciones[0] = new Extraccion();
		operaciones[1] = new Deposito();
		operaciones[2] = new Transferencia();
		operaciones[3] = new ComprarDolares();
		operaciones[4] = new ConsultarSaldo();
		operaciones[5] = new HistorialMovimientos();
		operaciones[6] = new ConsultarAlias();
		menu = new Menu(this);
		baseDatos = new BaseDeDatos();
		
		while(operando) {
			
			menu.desplegarInterfaz();
		}
	}
	
	/*
	 * Metodos usados en menu
	 */
	public void retirarEfectivo(String alias, int monto){

		try {
			
			Cuenta cuenta = getCliente().devolverCuenta(alias);
			
			String[] billetes = dispensarBilletes(monto);
			
			String movimiento = null;
			
			if(!billetes[0].equals("-1")) {
							
				((Extraccion) operaciones[0]).extraerFondos(cuenta, monto);

				movimiento = obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
						+ "Extraccion" + "," + monto + "," + cuenta.obtenerSaldo();

				((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
				((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(),
						movimiento);	
				
				
				String[] leyendasBilletes = {"Billetes de 1000: ","Billetes de 500: ","Billetes de 100: "};
				
				for (int i = 0; i < billetes.length; i++) {
					
					System.out.println(leyendasBilletes[i]+billetes[i]);
				}
				
				System.out.println(imprimirTicket(movimiento));
				baseDatos.escribirArchivoMovimientos(getCliente().devolverListaMovimientos());
				baseDatos.escribirArchivoTickets(movimiento);
			}
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo | ErrorFaltanBilletes error) {
			
			System.err.println(error.getMessage());
		}
	}
	
	public void comprarDolares(String alias, String aliasDolares, int dolares) {

		try {

			Cuenta cuenta = getCliente().devolverCuenta(alias);
			Cuenta cuentaDolares = getCliente().devolverCuenta(aliasDolares);

			((ComprarDolares) operaciones[3]).comprarDolares(cuenta, cuentaDolares, dolares);

			String movimiento = obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
					+ "Compra Dolares" + "," + dolares + "," + cuenta.obtenerSaldo();

			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuentaDolares.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(),
					movimiento);
			
			
		
			System.out.println(imprimirTicket(movimiento));		
			baseDatos.escribirArchivoMovimientos(getCliente().devolverListaMovimientos());
			baseDatos.escribirArchivoTickets(movimiento);
		}
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo e) {
			
			System.err.println(e.getMessage());
			
		}
	}
	
	public void depositarFondos(String alias, int monto) {

		try {

			Cuenta cuenta = getCliente().devolverCuenta(alias);
			((Deposito) operaciones[1]).depositar(cuenta, monto);

			String movimiento = obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
					+ "Deposito" + "," + monto + "," + cuenta.obtenerSaldo();

			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(),
					movimiento);

			System.out.println(imprimirTicket(movimiento));
			baseDatos.escribirArchivoMovimientos(getCliente().devolverListaMovimientos());
			baseDatos.escribirArchivoTickets(movimiento);
		}
		catch (ErrorAlIntroducirSaldo e) {

			System.err.println(e.getMessage());
		}
	}
	
    public void realizarTransferencia(String alias, String aliasATransferir, int monto) {
		
		try {

			Cuenta cuenta = getCliente().devolverCuenta(alias);
			Cuenta cuentaATransferir = getCliente().devolverCuenta(aliasATransferir);

			((Transferencia) operaciones[2]).transferir(cuenta, cuentaATransferir, monto);

			String movimiento = obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
					+ "Transferencia" + "," + (-monto) + "," + cuenta.obtenerSaldo();
			String movimientoTransferido = obtenerFechaYHora().devolverFechaYHora() + ","
					+ cuentaATransferir.obtenerAlias() + "," + "Transferencia" + "," + monto + ","
					+ cuentaATransferir.obtenerSaldo();
			String movimientoTicket = obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + "," + cuentaATransferir.obtenerAlias() + ","
					+ "Transferencia" + "," + monto + "," + cuenta.obtenerSaldo() + "," + cuentaATransferir.obtenerSaldo();

			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuentaATransferir.obtenerListaMovimientos(),
					movimientoTransferido);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(), movimiento);
			((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(),
					movimientoTransferido);

			System.out.println(imprimirTicket(movimientoTicket));
			baseDatos.escribirArchivoMovimientos(getCliente().devolverListaMovimientos());
			baseDatos.escribirArchivoTickets(movimientoTicket);
		}
		
		catch (ErrorSaldoInsuficiente | ErrorAlIntroducirSaldo error) {

			System.err.println(error.getMessage());
		}
	}
	
    public double consultarSaldo(String alias) {
		
		Cuenta cuenta = getCliente().devolverCuenta(alias);

		return ((ConsultarSaldo) operaciones[4]).consultarSaldo(cuenta);
	}
	
    public int revertirTransferencia(){
		
		int estado = 0;
    	
		List<String> movimientos = getCliente().devolverListaMovimientos();
		
		
		if(movimientos.size() > 0) {
			
			String[] lineaDeMovimiento = movimientos.get(movimientos.size() - 1).split(",");

			if (lineaDeMovimiento[3].equals("Transferencia")) {

				String[] lineaTransferencia = movimientos.get(movimientos.size() - 2).split(",");

				Cuenta cuenta = getCliente().devolverCuenta(lineaTransferencia[2]);
				Cuenta cuentaTransferida = getCliente().devolverCuenta(lineaDeMovimiento[2]);
				
				int monto = Integer.valueOf(lineaTransferencia[4]);
				
				String movimiento = obtenerFechaYHora().devolverFechaYHora() + "," + cuenta.obtenerAlias() + ","
						+ "RevertirTransferencia" + "," + -monto + "," + cuenta.obtenerSaldo();
				String movimientoTransferido = obtenerFechaYHora().devolverFechaYHora() + ","
						+ cuentaTransferida.obtenerAlias() + "," + "RevertirTransferencia" + "," + monto + ","
						+ cuentaTransferida.obtenerSaldo();
				
				String movimientoTicket = obtenerFechaYHora().devolverFechaYHora() + "," + cuentaTransferida.obtenerAlias() + "," + cuenta.obtenerAlias() + ","
						+ "RevertirTransferencia" + "," + (-monto) + "," + String.format("%.2f", cuentaTransferida.obtenerSaldo())+ "," + String.format("%.2f", cuenta.obtenerSaldo());

				((Transferencia) operaciones[2]).revertirMovimiento(cuenta, cuentaTransferida);
				
				((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuenta.obtenerListaMovimientos(), movimiento);
				((HistorialMovimientos) operaciones[5]).agregarMovimiento(cuentaTransferida.obtenerListaMovimientos(), movimiento);
				((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(),movimiento);
				((HistorialMovimientos) operaciones[5]).agregarMovimiento(getCliente().devolverListaMovimientos(),movimientoTransferido);
				
				System.out.println(imprimirTicket(movimientoTicket));
				
				baseDatos.escribirArchivoMovimientos(getCliente().devolverListaMovimientos());
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
	
	public List<String> mostrarMovimientos(String alias) {
		
		Cuenta cuenta = getCliente().devolverCuenta(alias);
	
		return cuenta.obtenerListaMovimientos();
	}

    public List<Cuenta> mostrarAlias() {
		
    	List<String> listaAlias = ((ConsultarAlias)operaciones[6]).devolverListaAlias(getCliente().devolverListaCuentas());

		baseDatos.escritorArchivoAlias(listaAlias);	
		
		return getCliente().devolverListaCuentas();
	}
	
    public void cerrarSesion() {
    	
		finalizarMovimientos();
		operando = false;
	}
	
    public boolean existeCuenta(String alias){
    	
		if(getCliente().devolverCuenta(alias) == null) {
			
			System.err.println("NO SE ENCONTRO LA CUENTA!");
			return false;
		}
		
		return true;
	}
	
	public boolean noEsCajaDeAhorroDolares(String alias) {
		
		if(!getCliente().devolverCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")) {
			
			return true;
		}
		System.err.println("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES");
		return false;
	}
	
	public boolean esCajaDeAhorroDolares(String alias) {
		
		if(getCliente().devolverCuenta(alias).getClass().getName().equals("CajaDeAhorroDolares")) {
			
			
			return true;
		}
		System.err.println("ESTA CUENTA NO ES EN DOLARES");
		return false;
	}
	
	public FechaYHora obtenerFechaYHora() {
		
		return fechaYHora;
	}

	
	
	private Cliente leerTarjeta(Tarjeta tarjeta) {
		
		return new Cliente(tarjeta.getCuit());
	}
	
	public Cliente getCliente() {
		
		return cliente;
	}
	
	public String imprimirTicket(String datos) {
		
		//fecha,hora,alias,operacion,monto,saldo
	
		String[] datosArray = datos.split(","); 
		String ticket = null;
		
		if(datosArray.length == 6) {
			
			ticket = String.format("-----------------------------------------------\n"
					+ "Fecha:         Hora:\n%s    %s\n\nTipo de operacion:\t%s\nAlias de la cuenta:\t%s\nImporte involucrado:\t%s\nSaldo final:\t\t%s"
					+ "\n-----------------------------------------------\n"
					,datosArray[0],datosArray[1],datosArray[3],datosArray[2],datosArray[4],datosArray[5]);
		}
		
		else {
			
			ticket = String.format("-----------------------------------------------\n"
					+ "Fecha:         Hora:\n%s    %s\n\nTipo de operacion:\t%s\nAlias de la cuenta a debitar:\t%s\nAlias de la cuenta a acreditar:\t%s\nImporte involucrado:\t%s\nSaldo final:\t\t%s"
					+ "\n-----------------------------------------------\n"
					,datosArray[0],datosArray[1],datosArray[4],datosArray[2],datosArray[3],datosArray[5],datosArray[6]);
		}
		
		return ticket;
	}

	public void finalizarMovimientos() {
		
		cliente = null;
	}

	public String[] dispensarBilletes(int monto) throws ErrorFaltanBilletes{
		
		int[] montos = {1000,500,100};
		String[] devolverBilletes = new String[3];
		
		boolean puedo = comprobarDisponibilidad(monto, montos, devolverBilletes);
	
		for (int i = 0; puedo && i < montos.length; i++) {
			
			int cantidadBillete = monto / montos[i];

			if(cantidadDeBilletes[i] >= cantidadBillete) {
				
				devolverBilletes[i] = String.valueOf(cantidadBillete);
				cantidadDeBilletes[i] -= cantidadBillete;
				monto = monto % montos[i];		
			}
			else {
				
				devolverBilletes[i] = "0";
			}
		}
		return puedo?devolverBilletes:new String[]{"-1","-1","-1"};
	}
	
	private boolean comprobarDisponibilidad(int monto, int[] montos, String[] devolverBilletes) {
		
		boolean puedo = true;
	
		int plata = monto;
		
		for (int i = 0; puedo && i< devolverBilletes.length; i++) {
			
			puedo = plata/montos[i] <= cantidadDeBilletes[i];
			
			plata %= montos[i];	
		}
		
		return puedo;
	}
	
	public void recargarBilletes() {

		for (int i = 0; i < cantidadDeBilletes.length; i++) {
			
			cantidadDeBilletes[i] += 500;		
		}
	}

	public int[] getBilletes() {
		
		return cantidadDeBilletes;
	}
}
