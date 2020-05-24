

import java.util.LinkedList;
import java.util.List;

public class Cajero {
	
	private Cliente cliente;
	private int[] cantidadDeBilletes = {500,500,500};
	private FechaYHora fechaYHora;
	
	
	public Cajero(Tarjeta tarjeta) {
		
		cliente = leerTarjeta(tarjeta);
		fechaYHora = new FechaYHora();
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
		
		String ticket = String.format("-----------------------------------------------\n"
									+ "Fecha:         Hora:\n%s    %s\n\nTipo de operacion:\t%s\nAlias de la cuenta:\t%s\nImporte involucrado:\t%s\nSaldo final:\t\t%s"
									+ "\n-----------------------------------------------\n"
				,datosArray[0],datosArray[1],datosArray[3],datosArray[2],datosArray[4],datosArray[5]);
		
		return ticket;
	
	}

	
	
//	public String obtenerUltimosMovimientos(String alias) {
//		return null;
//	}

	public void finalizarMovimientos() {
		
		cliente = null;
	}

	private String[] dispensarBilletes(int monto) {
		//metodo que devuelve los billetes
		return null;
	}

	private void recargarBilletes() {
		
		for (int i = 0; i < cantidadDeBilletes.length; i++) {
			
			cantidadDeBilletes[i] += 500;
		}
	}
	
}
