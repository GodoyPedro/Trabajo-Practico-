import java.util.List;

public class BaseDeDatos {

	OperadorDeArchivos operador;

	public BaseDeDatos() {

		operador = new OperadorDeArchivos();
	}

	public String[] buscarNumeroTarjeta(String numero) {

		String[] datos = operador.analizarArchivoValidacion(numero);

		if (datos != null) {

			return datos;
		}

		return null;
	}

	public void escribirArchivoMovimientos(List<String> movimientos) {

		operador.escribirArchivoMovimientos(movimientos);
	}

	public void escribirArchivoTickets(String movimiento) {

		operador.escribirArchivoTickets(movimiento);
	}

	public void escribirArchivoAlias(List<String> listaAlias) {

		operador.escritorArchivoAlias(listaAlias);

	}

	public List<Cuenta> analizarArchivoClientes(String cuit) {

		List<String> listaAlias = operador.analizarArchivoClientes(cuit);

		return operador.analizarArchivoCuentas(listaAlias);
	}

}
