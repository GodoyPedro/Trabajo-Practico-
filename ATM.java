import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args){
		
		boolean numeroEncontrado = false;
		String[] datos = null;
		Scanner numeroTarjeta = new Scanner(System.in);;
	
		BaseDeDatos base = new BaseDeDatos();
		System.out.println("INGRESE EL NUMERO DE LA TARJETA");
		
		while(!numeroEncontrado) {
			
			String numeroUsuario = numeroTarjeta.nextLine();
			
			datos = base.buscarNumeroTarjeta(numeroUsuario);
			
			numeroEncontrado = !(datos == null);
		}
		
		Tarjeta tarjeta = new Tarjeta(datos[1],datos[2]);
		System.out.println("INGRESE EL PIN DE LA TARJETA");
		tarjeta.validarPinTarjeta(tarjeta);
		Cajero cajero = new Cajero(tarjeta);
	}
}
