import java.util.Scanner;

public class Tarjeta {

	private String cuit;
	private String pin;

	/**
	 * pre: El pin y el cuit deben ser validos segun los archivos "Validacion de
	 * tarjetas" y "Clientes" post: Se crea una Tarjeta.
	 * 
	 * @param pin
	 * @param cuit
	 */
	public Tarjeta(String pin, String cuit) {

		this.pin = pin;
		this.cuit = cuit;
	}

	/**
	 * post: Devuelve el cuit del cliente
	 * 
	 * @return cuit
	 */
	public String obtenerCuit() {

		return cuit;
	}

	/**
	 * post: Devuelve el pin de la tarjeta
	 * 
	 * @return pin
	 */
	public String obtenerPin() {

		return pin;
	}

	/**
	 * post: Valida que el pin ingresado para la tarjeta sea correcto, en caso de
	 * equivocarse 3 veces la cuenta sera bloqueada.
	 * 
	 * @param tarjeta
	 */
	public void validarPinTarjeta(Tarjeta tarjeta) {

		Scanner pinTarjeta = new Scanner(System.in);

		int intentos = 3;

		while (intentos > 0) {

			if (pinTarjeta.nextLine().equals(tarjeta.obtenerPin())) {

				intentos = -1;
			}

			else {

				intentos--;

				if (intentos > 0) {
					System.out.println("Contraseña incorrecta " + "quedan " + intentos + " intentos");
				}

				else {
					System.out.println("No quedan mas intentos, bloqueando cuenta...");
					System.exit(1);
				}
			}
		}
	}

}
