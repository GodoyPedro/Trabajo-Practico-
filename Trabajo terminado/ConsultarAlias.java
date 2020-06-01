import java.util.LinkedList;
import java.util.List;

public class ConsultarAlias extends Operacion {

	/**
	 * post: Devuelve todos los alias de todas las cuentas del usuario.
	 * @param listaCuentas
	 * @return
	 */
	public List<String> obtenerListaAlias(List<Cuenta> listaCuentas) {
		
		List<String> listaAlias = new LinkedList<String>();
		
		for(Cuenta cuenta: listaCuentas) {
			
			listaAlias.add(cuenta.obtenerAlias());
		}
		
		return listaAlias;
	}	
}

