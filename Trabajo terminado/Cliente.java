import java.util.LinkedList;
import java.util.List;

public class Cliente {
	
	private List<Cuenta> listaCuentas;
	private List<String> movimientos;
	private BaseDeDatos baseDatos;
	
	
	/**
	 * pre: El cuit debe coincidir con el archivo "Clientes"
	 * 
	 * post: Se crea un Cliente
	 * @param cuit
	 */
	public Cliente(String cuit) {
	
		baseDatos = new BaseDeDatos();
		movimientos = new LinkedList<String>();
		listaCuentas = baseDatos.analizarArchivoClientes(cuit);	
	}
	
	
	/**
	 * pre: El alias ingresado debe coincidir con el archivo "Clientes"
	 * post: Devuelve la cuenta que corresponde al alias ingresado
	 * @param alias
	 * @return
	 */
	public Cuenta obtenerCuenta(String alias){
	
		for(Cuenta cuenta: listaCuentas) {
			
			if(cuenta.obtenerAlias().equals(alias)) {
				
				return cuenta;
			}
		}
		
		return null;
	}
	
	/**
	 * post: Devuelve la lista de movimientos general
	 * 
	 * @return movimientos
	 */
	public List<String> obtenerListaMovimientos(){
		
		return movimientos;
	}
	
	
	/**
	 * post: Devuelve la lista de cuentas que posea el Cliente
	 * @return listaCuentas
	 */
	public List<Cuenta> obtenerListaCuentas(){
		
		return listaCuentas;
	}

}
