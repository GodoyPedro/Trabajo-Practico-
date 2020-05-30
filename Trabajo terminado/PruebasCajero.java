import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;


public class PruebasCajero {

//	@Test
//    public void ingresarUnaTarjetaInvalida(){
//
//		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//
//		System.setErr(new PrintStream(outContent));
//
//		Tarjeta tarjeta = new Tarjeta();
//
//		assertEquals("El numero de tarjeta ingresado no existe en la base de datos", outContent.toString())
//    }
	
	@Test
    public void ingresarUnaTarjetaInvalida(){

		Assert.assertNull(new BaseDeDatos().buscarNumeroTarjeta("123123"));
    }
	
	@Test
    public void ingresarUnTarjetaValidaYUnPinInvalido(){
		
		BaseDeDatos b = new BaseDeDatos();
		String[] datos = b.buscarNumeroTarjeta("12345678");
		Tarjeta tarjeta = new Tarjeta(datos[1],datos[2]);
		
		Assert.assertEquals("1234",tarjeta.obtenerPin());
    }

}
