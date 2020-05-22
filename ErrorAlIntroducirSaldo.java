public class ErrorAlIntroducirSaldo extends Exception {

	public ErrorAlIntroducirSaldo(){
		super("El saldo a introducir debe ser positivo");
	}
}