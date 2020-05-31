import java.util.List;

public class HistorialMovimientos extends Operacion{
	
	//Mediante este método, guardamos las operaciones realizadas sobre una cuenta en una lista. Se pueden guardar hasta 10 movimientos, si se excede este límite,
	//se elimina el primer movimiento de la lista (es decir, el más viejo) y se agrega uno nuevo.
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
