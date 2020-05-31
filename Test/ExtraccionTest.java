
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

import CajaDeAhorroDolares;
import CajaDeAhorroPesos;
import Cajero;
import Cuenta;
import CuentaCorriente;
import Deposito;
import ErrorAlIntroducirSaldo;
import ErrorCuentaInvalida;
import ErrorSaldoInsuficiente;
import Extraccion;
import Tarjeta;


public class ExtraccionTest {

	private Extraccion extraccion = new Extraccion();
	private Deposito deposito = new Deposito();	
	private Cuenta cuentaCorriente;
	private Cuenta cajaDeAhorroPesos;
	private Cuenta cajaDeAhorroDolares;
	
	
	@Test
	public void extraer100PesosDeLaCuentaCorriente() throws IOException, ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		extraccion.extraerFondos(cuentaCorriente, 100);
		Assert.assertEquals(-1121.90,cuentaCorriente.obtenerSaldo(),0.1);
	}
	
	@Test
	public void extraer100PesosDeLaCajaDeAhorro() throws IOException, ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		extraccion.extraerFondos(cajaDeAhorroPesos, 100);
		Assert.assertEquals(11899.97, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	@Test
	public void extraer500PesosDeLaCajaDeAhorro() throws IOException, ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		extraccion.extraerFondos(cajaDeAhorroPesos, 500);
		Assert.assertEquals(11499.97, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void extraer500PesosDeLaCuentaCorriente() throws IOException, ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		extraccion.extraerFondos(cuentaCorriente, 500);
		Assert.assertEquals(-1521.90,cuentaCorriente.obtenerSaldo(),0.1);
	}
	
	@Test(expected = ErrorSaldoInsuficiente.class)
	public void intentarExtraer25000PesosEnCuentaCorrienteYExcederElDescubierto() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		extraccion.extraerFondos(cuentaCorriente, 25000);
	}
	
	@Test(expected = ErrorSaldoInsuficiente.class)
	public void intentarExtraer25000PesosEnCajaDeAhorroYExcederElSaldoDeLaCuenta() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		extraccion.extraerFondos(cajaDeAhorroPesos, 25000);
	}
	
	@Test
	public void extraer100DolaresDeLaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		extraccion.extraerFondos(cajaDeAhorroDolares, 100);
		
		Assert.assertEquals(100, cajaDeAhorroDolares.obtenerSaldo(),0.1);
	}
	
	@Test(expected = ErrorSaldoInsuficiente.class)
	public void intentarExtraer250DolaresDeLaCajaDeAhorroEnDolaresYExcederseElSaldoDeLaCuenta() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		extraccion.extraerFondos(cajaDeAhorroDolares, 250);
	}
	
	@Test
	public void extraer2VecesDeLaCuentaCorriente() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		extraccion.extraerFondos(cuentaCorriente, 500);
		extraccion.extraerFondos(cuentaCorriente, 500);
		
		Assert.assertEquals(-2021.90, cuentaCorriente.obtenerSaldo(),0.1);
	}
	
	@Test
	public void extraer2VecesDeLaCajaDeAhorroEnPesos() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		extraccion.extraerFondos(cajaDeAhorroPesos, 1000);
		extraccion.extraerFondos(cajaDeAhorroPesos, 1000);
		
		Assert.assertEquals(10000.03, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void extraer2VecesDeLaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		extraccion.extraerFondos(cajaDeAhorroDolares, 100);
		extraccion.extraerFondos(cajaDeAhorroDolares, 100);
		
		Assert.assertEquals(0, cajaDeAhorroDolares.obtenerSaldo(),0.1);
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarExtraerDeUnaCuentaCorrienteUnMontoNegativo() throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		extraccion.extraerFondos(cuentaCorriente, -500);
		
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarExtraerDeUnaCajaDeAhorroPesosUnMontoNegativo() throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		extraccion.extraerFondos(cajaDeAhorroPesos, -25000);
		
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarExtraerDeUnaCajaDeAhorroDolaresUnMontoNegativo() throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo, ErrorCuentaInvalida {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		extraccion.extraerFondos(cajaDeAhorroDolares, -100);
		
	}
	
	@Test
	public void extraerDeUnaCuentaCorrienteYDepositarLaMismaCantidad() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		extraccion.extraerFondos(cuentaCorriente, 500);
		deposito.depositar(cuentaCorriente, 500);
		
		Assert.assertEquals(-1021.90, cuentaCorriente.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void extraerDeUnaCajaDeAhorroYDepositarLaMismaCantidad() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		extraccion.extraerFondos(cajaDeAhorroPesos, 500);
		deposito.depositar(cajaDeAhorroPesos, 500);
		
		Assert.assertEquals(12000.03, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void extraerDeUnaCajaDeAhorroDolaresYDepositarLaMismaCantidad() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		extraccion.extraerFondos(cajaDeAhorroDolares, 100);
		deposito.depositar(cajaDeAhorroDolares, 100);
		
		Assert.assertEquals(200, cajaDeAhorroDolares.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void intentarExtraerUnMontoQueNoEsMultiploDe100() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
			
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setErr(new PrintStream(outContent));
     
        cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
  
		extraccion.extraerFondos(cuentaCorriente, 24);
        Assert.assertEquals("No se puede seleccionar montos que no sean multiplos de 100", outContent.toString());
	}

}
