

import java.util.List;

public class HistorialMovimientos extends Operacion{
	
	public void agregarMovimiento(List<String> lista, String movimiento) {
		
		if(lista.size() == 10) {
			
			lista.remove(0);
			lista.add(movimiento);
		}
		else {
			
			lista.add(movimiento);
		}
		
		
	}
}
