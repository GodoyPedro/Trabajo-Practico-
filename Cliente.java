
import java.util.LinkedList;
import java.util.List;

public class Cliente {
	
	private List<Cuenta> listaCuentas;
	private List<String> movimientos;
	private BaseDeDatos baseDatos;
	
	public Cliente(String cuit) {
	
		baseDatos = new BaseDeDatos();
		movimientos = new LinkedList<String>();
		listaCuentas = baseDatos.analizarArchivoClientes(cuit);	
	}

	public Cuenta devolverCuenta(String alias){
	
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
	
	public List<Cuenta> devolverListaCuentas(){
		
		return listaCuentas;
	}

}
