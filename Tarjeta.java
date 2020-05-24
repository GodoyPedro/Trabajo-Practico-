import java.io.IOException;
import java.util.Scanner;

public class Tarjeta {
	
	private String numeroTarjeta;
	private int intentos = 3;
	private String cuit;
	private String pin;

	
	public Tarjeta(String numeroTarjeta) throws IOException {
		
		leerNumeroTarjeta(numeroTarjeta);
	}
	
	public String getCuit() {
		
		return cuit;
	}
	
	public void leerNumeroTarjeta(String numeroTarjeta){
		
		String [] datos = new OperadorDeArchivos().analizarArchivoValidacion(numeroTarjeta);
		
		this.pin = datos[1];
		this.cuit = datos[2];
	}
	

	public String obtenerPin() {
		
		return pin;
	}
}
