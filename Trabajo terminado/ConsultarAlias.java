import java.util.LinkedList;
import java.util.List;

public class ConsultarAlias extends Operacion {
	
	//Devuelve todos los alias de todas las cuentas del usuario.
	public List<String> obtenerListaAlias(List<Cuenta> listaCuentas) {
		
		List<String> listaAlias = new LinkedList<String>();
		
		for(Cuenta cuenta: listaCuentas) {
			
			listaAlias.add(cuenta.obtenerAlias());
		}
		
		return listaAlias;
	}	
}
