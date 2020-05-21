import java.util.LinkedList;
import java.util.List;

public abstract class Cuenta {

    protected double saldo;
    protected String alias;
    protected double saldoPrevio;
    protected List<String> movimientos;

    //Este constructor es exclusivamente para CuentaCorriente
    public Cuenta(String alias, double saldo, double descubierto) {
    	movimientos = new LinkedList<String>();
        this.alias = alias;
        this.saldo = saldo;
    }
    
    //Este constructor sirve para las clases CajaDeAhorroPesos y para CajaDeAhorroDolares
    public Cuenta(String alias, double saldo) {
    	movimientos = new LinkedList<String>();
        this.alias = alias;
        this.saldo = saldo;
    }
    //Este metodo seria el "preguntarSaldo"
    public double obtenerSaldo() {
        return saldo;
    }
    
    public double obtenerSaldoPrevio() {
    	return saldoPrevio;
    }
    
    public void cambiarSaldo(double saldo){
    	this.saldo = saldo;
    }
    
    //Este metodo seria el "depositar"
    public void agregarSaldo(double saldo) {
    	this.saldoPrevio = saldo;
    	this.saldo += saldo;
    }
    
    public String obtenerAlias(){
    	return alias;
    }
    
    public List<String> obtenerListaMovimientos(){
    	return movimientos;
    }
    
    //Este metodo seria el "retirar"
    public abstract void quitarSaldo(double saldo);
    
    public void agregarMovimiento(String operacion, double saldoInvolucrado, double nuevoSaldo) {
        FechaYHora fechaYHora = new FechaYHora();
        String fecha = fechaYHora.getFecha();
        String hora = fechaYHora.getHora();

        String movimiento = fecha + ", " + hora + ", " + alias + ", " + operacion + ", " + saldoInvolucrado + ", " + nuevoSaldo;
        movimientos.add(movimiento);
    }
}