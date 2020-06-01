

public interface Reversible {

	/**
	 * pre: Las cuentas ingresadas son las cuales se va revertir el ultimo
	 * movimiento realizado en caso de ser una Transferencia.
	 * 
	 * @param cuentas
	 */
	public void revertirMovimiento(Cuenta... cuentas);

}
