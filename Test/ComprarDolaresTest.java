package pruebas;

import org.junit.Assert;
import org.junit.Test;

import predeterminado.CajaDeAhorroDolares;
import predeterminado.CajaDeAhorroPesos;
import predeterminado.ComprarDolares;
import predeterminado.Cuenta;
import predeterminado.CuentaCorriente;
import predeterminado.Deposito;
import predeterminado.ErrorAlIntroducirSaldo;
import predeterminado.ErrorCuentaInvalida;
import predeterminado.ErrorSaldoInsuficiente;

public class ComprarDolaresTest {

	private ComprarDolares compra = new ComprarDolares();
	private Deposito deposito = new Deposito();	
	private Cuenta cuentaCorriente;
	private Cuenta cajaDeAhorroPesos;
	private Cuenta cajaDeAhorroDolares;
	
	@Test
	public void comprar1DolarDesdeUnaCuentaCorriente() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("hormiga.lija.crema", -50.97, 150.00);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cuentaCorriente, cajaDeAhorroDolares, 1);
		
		Assert.assertEquals(-137.63,cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(201,cajaDeAhorroDolares.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void comprar1DolarDesdeUnaCajaDeAhorroEnPesos() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, 1);
		
		Assert.assertEquals(11913.36,cajaDeAhorroPesos.obtenerSaldo(),0.1);
		Assert.assertEquals(201,cajaDeAhorroDolares.obtenerSaldo(),0.1);
		
	}
	
	@Test(expected = ErrorCuentaInvalida.class)
	public void intentarComprarDolaresDesdeUnaCajaDeAhorroEnDolares() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cajaDeAhorroDolares, cajaDeAhorroDolares, 1);

	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarComprarUnaCantidadNegativaDeDolaresDesdeUnaCuentaCorriente() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("hormiga.lija.crema", -50.97, 150.00);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cuentaCorriente, cajaDeAhorroDolares, -1);
		
		
	}
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarComprarUnaCantidadNegativaDeDolaresDesdeUnaCajaDeAhorroPesos() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, -1);
		
		
	}
	
	@Test(expected = ErrorSaldoInsuficiente.class)
	public void intentarComprarUnaCantidadDeDolaresDesdeUnaCajaDeAhorroEnPesosSuperandoElSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, 10000);
		
		
	}
	@Test(expected = ErrorSaldoInsuficiente.class)
	public void intentarComprarUnaCantidadDeDolaresDesdeUnaCuentaCorrienteSuperandoElSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, 1000);
		
		
	}
	
	@Test
	public void comprar1DolarDesdeUnaCuentaCorriente2Veces() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("hormiga.lija.crema", -50.97, 150.00);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cuentaCorriente, cajaDeAhorroDolares, 1);
		deposito.depositar(cuentaCorriente, 100);
		compra.comprarDolares(cuentaCorriente, cajaDeAhorroDolares, 1);
		
		Assert.assertEquals(-124.30,cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(202,cajaDeAhorroDolares.obtenerSaldo(),0.1);
	
	}
	@Test
	public void comprar1DolarDesdeUnaCajaDeAhorroPesos2Veces() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		compra.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, 1);
		compra.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, 1);
		
		Assert.assertEquals(11826.6,cajaDeAhorroPesos.obtenerSaldo(),0.1);
		Assert.assertEquals(202,cajaDeAhorroDolares.obtenerSaldo(),0.1);
		
	}
}
