import java.util.LinkedList;
import java.util.List;

public abstract class Cuenta {

    protected double saldo;
    protected String alias;
    protected double saldoPrevio;
    protected List<String> movimientos;

    //Este constructor es exclusivamente para CuentaCorriente
    public Cuenta(String alias, double saldo, double descubierto) throws ErrorAlIntroducirSaldo {
    	if(descubierto < 0 || saldo < -descubierto){
    		throw new ErrorAlIntroducirSaldo();
    	}
    	movimientos = new LinkedList<String>();
        this.alias = alias;
        this.saldo = saldo;
    }
    
    //Este constructor sirve para las clases CajaDeAhorroPesos y para CajaDeAhorroDolares
    public Cuenta(String alias, double saldo) throws ErrorAlIntroducirSaldo {
    	if(saldo < 0){
    		throw new ErrorAlIntroducirSaldo();
    	}
    	movimientos = new LinkedList<String>();
        this.alias = alias;
        this.saldo = saldo;
    }
    
    //Este metodo seria el "preguntarSaldo"
    public double obtenerSaldo() {
        return saldo;
    }
    
    //Este metodo devuelve el saldo previo del usuario
    public double obtenerSaldoPrevio() {
    	return saldoPrevio;
    }
    
    //Este metodo se utiliza a la hora de revertir la transferencia/futuras operaciones
    public void cambiarSaldo(double saldo){
    	this.saldo = saldo;
    }
    
    //Este metodo seria el "depositar"
    public void agregarSaldo(int saldo) throws ErrorAlIntroducirSaldo {
    	if(saldo < 0){
    		throw new ErrorAlIntroducirSaldo();
    	}
    	this.saldoPrevio = this.saldo;
    	this.saldo += saldo;
    }
    
    //Este metodo devuelte el alias de la cuenta
    public String obtenerAlias(){
    	return alias;
    }
    
    //Este metodo devuelve la lista de movimientos de su cuenta
    public List<String> obtenerListaMovimientos(){
    	return movimientos;
    }
    
    //Este metodo seria el "retirar"
    public abstract void quitarSaldo(int saldo) throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo;
}
