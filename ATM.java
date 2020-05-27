import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args) {
		
		System.out.println("INGRESE EL NUMERO DE LA TARJETA");
		Scanner numeroTarjeta = new Scanner(System.in);
		Tarjeta tarjeta = new Tarjeta(numeroTarjeta.nextLine());
		System.out.println("INGRESE EL PIN DE LA TARJETA");
		tarjeta.validarPinTarjeta(tarjeta);
		Cajero cajero = new Cajero(tarjeta);
	}
}
