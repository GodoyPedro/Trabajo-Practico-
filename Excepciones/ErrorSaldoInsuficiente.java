public class ErrorSaldoInsuficiente extends Exception {

    public ErrorSaldoInsuficiente()	{
    	
        super("Usted no posee el saldo suficiente para realizar esta operación");
        
    }
}