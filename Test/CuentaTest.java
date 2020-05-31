package pruebas;

import org.junit.Assert;
import org.junit.Test;

import predeterminado.CajaDeAhorroDolares;
import predeterminado.CajaDeAhorroPesos;
import predeterminado.Cuenta;
import predeterminado.CuentaCorriente;
import predeterminado.ErrorAlIntroducirSaldo;

public class CuentaTest {

	private Cuenta cuenta;
	
	@Test
	public void crearUnaCuentaCorriente() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CuentaCorriente("sol.monte.valle",12233,25000);
	}
	
	@Test
	public void crearUnaCuentaCorrienteYVerificarSuAlias() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CuentaCorriente("sol.monte.valle",12233,25000); 
		
		Assert.assertEquals("sol.monte.valle", cuenta.obtenerAlias());
		
	}
	
	@Test
	public void crearUnaCajaDeAhorroPesos() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CajaDeAhorroPesos("isla.pez.arbol", 2600);
	}
	
	@Test
	public void crearUnaCajaDeAhorroPesosYVerificarSuAlias() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CajaDeAhorroPesos("isla.pez.arbol", 2600);
		Assert.assertEquals("isla.pez.arbol", cuenta.obtenerAlias());
	}
	
	@Test
	public void crearUnaCajaDeAhorroDolares() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
	}
	
	@Test
	public void crearUnaCajaDeAhorroDolaresYVerificarSuAlias() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		Assert.assertEquals("lobo.luna.pasto", cuenta.obtenerAlias());
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarCrearUnaCuentaCorrienteConDescubiertoNegativo() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CuentaCorriente("sol.monte.valle",12233,-25000);
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarCrearUnaCuentaCorrienteConSaldoMenorAlDescubierto() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CuentaCorriente("sol.monte.valle",-25001,25000);
	}
	
	
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarCrearUnaCajaDeAhorroPesosConSaldoNegativo() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CajaDeAhorroPesos("isla.pez.arbol", -2600);
	}
	
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarCrearUnaCajaDeAhorroDolaresConSaldoNegativo() throws ErrorAlIntroducirSaldo {
		
		cuenta = new CajaDeAhorroDolares("lobo.luna.pasto", -200);
	}
	
	
}
