import java.util.LinkedList;
import java.util.List;

public class ConsultarAlias extends Operacion {

	public List<String> devolverListaAlias(List<Cuenta> listaCuentas) {
		
		List<String> listaAlias = new LinkedList<String>();
		
		for(Cuenta cuenta: listaCuentas) {
			
			listaAlias.add(cuenta.obtenerAlias());
		}
		
		return listaAlias;
	}	
}
