
package predeterminado;

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
	
	public String obtenerTicket() {
		return null;
	}

//	public String obtenerUltimosMovimientos(String alias) {
//		return null;
//	}

	private void finalizarMovimientos() {
		
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
