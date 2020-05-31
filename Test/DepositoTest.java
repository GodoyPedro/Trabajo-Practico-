package pruebas;

import org.junit.Assert;
import org.junit.Test;

import predeterminado.CajaDeAhorroDolares;
import predeterminado.CajaDeAhorroPesos;
import predeterminado.Cuenta;
import predeterminado.CuentaCorriente;
import predeterminado.ErrorAlIntroducirSaldo;
import predeterminado.Deposito;


public class DepositoTest {

	private Deposito deposito = new Deposito();
	private Cuenta cuentaCorriente;
	private Cuenta cajaDeAhorroPesos;
	private Cuenta cajaDeAhorroDolares;
	
	
	@Test
	public void depositar100PesosEnUnaCuentaCorriente() throws ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		deposito.depositar(cuentaCorriente, 1000);
		Assert.assertEquals(-21.90,cuentaCorriente.obtenerSaldo(),0.1);
	}
	
	@Test
	public void depositarUnaCantidadGrandeEnUnaCuentaCorriente() throws ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		deposito.depositar(cuentaCorriente, 1000000);
		Assert.assertEquals(998978.1,cuentaCorriente.obtenerSaldo(),0.1);
	}
	
	
	@Test
	public void depositar100PesosEnUnaCajaDeAhorroEnPesos() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		deposito.depositar(cajaDeAhorroPesos, 1000);
		Assert.assertEquals(13000.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
	}
	
	@Test
	public void depositarUnaCantidadGrandeEnUnaCajaDeAhorroEnPesos() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		deposito.depositar(cajaDeAhorroPesos, 1000000);
		Assert.assertEquals(1012000.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
	}
	
	@Test
	public void depositar100DolaresEnUnaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		deposito.depositar(cajaDeAhorroDolares, 1000);
		Assert.assertEquals(1200,cajaDeAhorroDolares.obtenerSaldo(),0.1);
	}
	
	@Test
	public void depositarUnaCantidadGrandeEnUnaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		deposito.depositar(cajaDeAhorroDolares, 1000000);
		Assert.assertEquals(1000200,cajaDeAhorroDolares.obtenerSaldo(),0.1);
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarDepositarUnSaldoNegativoEnUnaCuentaCorriente() throws ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		deposito.depositar(cuentaCorriente, -1000);
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarDepositarUnSaldoNegativoEnUnaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo {
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		deposito.depositar(cajaDeAhorroDolares, -1000);
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarDepositarUnSaldoNegativoEnUnaCajaDeAhorroEnPesos() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		deposito.depositar(cajaDeAhorroPesos, -1000);
	}
	
	@Test
	public void depositar2VecesEnUnaCuentaCorriente() throws ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		deposito.depositar(cuentaCorriente, 1000);
		deposito.depositar(cuentaCorriente, 1000);		
		Assert.assertEquals(978.1,cuentaCorriente.obtenerSaldo(),0.1);
	}
	
	@Test
	public void depositar2VecesEnUnaCajaDeAhorroEnPesos() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		deposito.depositar(cajaDeAhorroPesos, 1000);
		deposito.depositar(cajaDeAhorroPesos, 1000);
		Assert.assertEquals(14000.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
	}
	
	@Test
	public void depositar2VecesEnUnaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		deposito.depositar(cajaDeAhorroDolares, 1);
		deposito.depositar(cajaDeAhorroDolares, 1);
		Assert.assertEquals(202,cajaDeAhorroDolares.obtenerSaldo(),0.1);
	}
	
}
