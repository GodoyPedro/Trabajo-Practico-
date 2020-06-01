import java.util.List;
/**
 * Es el intermediario entre el cajero y los datos que almacenamos en distintas clases.
 * Si queremos buscar algun dato que no este en el cajero, usamos esta clase.
 */
public class BaseDeDatos {
	
	OperadorDeArchivos operador;
	
	public BaseDeDatos() {
		
		operador = new OperadorDeArchivos();
	}
	
	/**
	 * Chequeo si existe el numero de la tarjeta que ingreso el usuario
	 * @param numero
	 * @return
	 */
	public String[] buscarNumeroTarjeta(String numero){
		
		String[] datos = operador.analizarArchivoValidacion(numero);
		
		return datos != null? datos:null;
	}
	
	
	/**
	 * Uso el operador de archivos para crear las cuentas 
	 * @param cuit
	 * @return listaDeCuentas
	 */
	public List<Cuenta> analizarArchivoClientes(String cuit) {

		return operador.analizarArchivoCuentas(operador.analizarArchivoClientes(cuit));
	}

	/**
	 * Uso el operador de archivos para escribri el archivo de movimientos con el formato pedido(fecha,concepto,alias,monto)
	 * @param movimientos
	 */
	public void escribirArchivoMovimientos(List<String> movimientos) {
		
		operador.escribirArchivoMovimientos(movimientos);
	}
	
	/**
	 * Uso el operador de archivos para escribri el archivo txt donde voy a guardar todos los tickets que se producen al realizar una operacion
	 * @param movimiento
	 */
	public void escribirArchivoTickets(String movimiento) {

		operador.escribirArchivoTickets(movimiento);
	}
	
	/**
	 * Escribo el archivo txt en donde voy a guardar los alias que posee el usuario
	 * @param listaAlias
	 */
	public void escribirArchivoAlias(List<String> listaAlias) {

		operador.escritorArchivoAlias(listaAlias);	
	}

	
}
