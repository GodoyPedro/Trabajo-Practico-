
package predeterminado;
import java.io.IOException;
import java.util.Scanner;

public class Tarjeta {
	
	private String numeroTarjeta;
	private int intentos = 3;
	private String cuit;
	private String pin;

	
	public Tarjeta(String numeroTarjeta) throws IOException {
		
		leerNumeroTarjeta(numeroTarjeta);
		
		if(!validarPin(pin)) {
			
			throw new Error("Se bloqueo la cuenta");
		}
	}
	
	public String getCuit() {
		
		return cuit;
	}
	
	public void leerNumeroTarjeta(String numeroTarjeta){
		
		String [] datos = new OperadorDeArchivos().analizarArchivoValidacion(numeroTarjeta);

		this.pin = datos[1];
		this.cuit = datos[2];
	}
	
	public boolean validarPin(String pin) {
		
		Scanner entradaUsuario = new Scanner(System.in);
		
		while(intentos > 0) {
			
			if(entradaUsuario.nextLine().equals(pin)) {
				
				intentos = -1;
			}
			
			else {
	
				intentos--;
				System.out.println("Contraseña incorrecta"+"quedan "+intentos+" intentos");
			}
		}
		
		return intentos < 0? true: false;	
	}
}
