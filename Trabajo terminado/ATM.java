import java.util.Scanner;
/**
 * Al instanciar esta clase, inicializamos el programa
 */
public class ATM {
	
	public static void main(String[] args){
		
		new ATM().inicializarCajero();
	}
	
	private void inicializarCajero() {
		
		boolean numeroEncontrado = false;
		String[] datos = null;
		Scanner numeroTarjeta = null;
	
		BaseDeDatos base = new BaseDeDatos();
		System.out.println("INGRESE EL NUMERO DE LA TARJETA");
		
		while(!numeroEncontrado) {
			
			numeroTarjeta = new Scanner(System.in);
			
			String numeroUsuario = numeroTarjeta.nextLine();
			
			datos = base.buscarNumeroTarjeta(numeroUsuario);
			
			numeroEncontrado = !(datos == null);
		}
		
		Tarjeta tarjeta = new Tarjeta(datos[1],datos[2]);
		System.out.println("INGRESE EL PIN DE LA TARJETA");
		tarjeta.validarPinTarjeta(tarjeta);
		new Cajero(tarjeta);
	}
}
