
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import BaseDeDatos;
import Tarjeta;

public class TarjetaTest {

	private Tarjeta tarjeta;
	
	
	@Test
	public void crearUnaTarjetaConNumero12345678() {
		
		tarjeta = new Tarjeta("1234","27102551236");

	}
	
	@Test
	public void verificarNumeroDePin12345678() {
		
		tarjeta = new Tarjeta("1234","27102551236");
		Assert.assertEquals("1234",tarjeta.obtenerPin());
	}
	
	@Test
	public void validarCuitDeTarjeta12345678() {
		
		tarjeta = new Tarjeta("1234","27102551236");
		Assert.assertEquals("27102551236",tarjeta.obtenerCuit());
		
	}
	
	@Test
	public void crearTarjetaConNumero23456789() {
		
		tarjeta = new Tarjeta("2345","27102551236");
		
	}
	
	@Test
	public void verificarNumeroDePinTarjeta23456789() {
		
		tarjeta = new Tarjeta("2345","27102551236");
		Assert.assertEquals("2345",tarjeta.obtenerPin());
	}
	
	@Test
	public void validarCuitDeTarjeta23456789() {
		
		tarjeta = new Tarjeta("2345","27102551236");
		Assert.assertEquals("27102551236",tarjeta.obtenerCuit());
		
	}
	
	@Test
	public void crearTarjetaConNumero34567890() {
		
		tarjeta = new Tarjeta("3456","23044303094");
	
		
	}
	
	@Test
	public void verificarNumeroPinTarjeta34567890() {
		
		tarjeta = new Tarjeta("3456","23044303094");
		Assert.assertEquals("3456", tarjeta.obtenerPin());
	}
	
	@Test
	public void validarCuitDeTarjeta34567890() {
		
		tarjeta = new Tarjeta("3456","23044303094");
		Assert.assertEquals("23044303094",tarjeta.obtenerCuit());
		
	}
	
	@Test
	public void crearTarjetaConNumero45678901() {
		
		tarjeta = new Tarjeta("4567","20311573951");
	
		
	}
	
	@Test
	public void verificarNumeroPinTarjeta45678901() {
		
		tarjeta = new Tarjeta("4567","20311573951");
		Assert.assertEquals("4567", tarjeta.obtenerPin());
	}
	
	@Test
	public void validarCuitDeTarjeta45678901() {
		
		tarjeta = new Tarjeta("4567","20311573951");
		Assert.assertEquals("20311573951",tarjeta.obtenerCuit());
		
	}
	
	
	@Test
	public void crearTarjetaConNumero56789012() {
		
		tarjeta = new Tarjeta("5678","20311573951");

		
	}
	
	@Test
	public void verificarNumeroPinTarjeta56789012() {
		
		tarjeta = new Tarjeta("5678","20311573951");
		Assert.assertEquals("5678", tarjeta.obtenerPin());
	}
	
	@Test
	public void validarCuitDeTarjeta56789012() {
		
		tarjeta = new Tarjeta("5678","20311573951");
		Assert.assertEquals("20311573951",tarjeta.obtenerCuit());
		
	}
	
	@Test
    public void ingresarUnaTarjetaInvalida(){
        
        Assert.assertNull(new BaseDeDatos().buscarNumeroTarjeta("123123"));
    }
	
}
