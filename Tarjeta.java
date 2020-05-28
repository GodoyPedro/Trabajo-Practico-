import java.util.Scanner;

public class Tarjeta {
	
	private String cuit;
	private String pin;

	
	public Tarjeta(String pin, String cuit){
		
		this.pin = pin;
		this.cuit = cuit;
	}
	
	public String getCuit() {
		
		return cuit;
	}
	
	public String obtenerPin() {
		
		return pin;
	}
	
	public void validarPinTarjeta(Tarjeta tarjeta){
		
		Scanner pinTarjeta = new Scanner(System.in);
		
		int intentos = 3;
		
		while(intentos > 0) {
			
			if(pinTarjeta.nextLine().equals(tarjeta.obtenerPin())) {
				
				intentos = -1;
			}
			
			else {
				
				intentos--;
				
				if(intentos > 0) {
					System.out.println("Contraseña incorrecta "+"quedan "+intentos+" intentos");
				}
				
				else {
					System.out.println("No quedan mas intentos, bloqueando cuenta...");
					System.exit(1);
				}
			}
		}		
	}

}
