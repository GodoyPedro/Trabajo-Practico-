package predeterminado;

import java.util.LinkedList;
import java.util.List;

public class Cliente {
	
	private List<Cuenta> listaCuentas;
	private String cuit;
	private List<String> movimientos;
	
	public Cliente(String cuit) {
		
		this.cuit = cuit;
		List<String> alias = new OperadorDeArchivos().analizarArchivoClientes(cuit);
		movimientos = new LinkedList<String>();
		listaCuentas = new OperadorDeArchivos().analizarArchivoCuentas(alias);
	 	
	}
	
	public Cuenta devolverCuenta(String alias) {
		
		for(Cuenta cuenta: listaCuentas) {
			
			if(cuenta.obtenerAlias().equals(alias)) {
				
				return cuenta;
			}
		}
		
		return null;
	}
	
	public List<String> devolverListaMovimientos(){
		
		return movimientos;
	}

}
