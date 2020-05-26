

import java.util.Arrays;
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
	
	public int[] getBilletes() {
		
		return cantidadDeBilletes;
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

	
	
//	public String obtenerUltimosMovimientos(String alias) {
//		return null;
//	}

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
	
	private void recargarBilletes() {
		
		for (int i = 0; i < cantidadDeBilletes.length; i++) {
			
			cantidadDeBilletes[i] += 500;
		}
	}
	
}
