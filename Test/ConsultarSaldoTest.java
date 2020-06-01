
import org.junit.Assert;
import org.junit.Test;


public class ConsultarSaldoTest {

	
	private ConsultarSaldo consulta = new ConsultarSaldo();
	private Extraccion extraccion;
	private Deposito deposito;
	private Transferencia transferencia;
	private ComprarDolares comprar;
	private Cuenta cuentaCorriente;
	private Cuenta cajaDeAhorroPesos;
	private Cuenta cajaDeAhorroDolares;
	
	
	@Test
	public void consultarSaldoDeUnaCuentaCorriente() throws ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 25000);
		Assert.assertEquals(1500, consulta.consultarSaldo(cuentaCorriente),0.1);
		
	}
	
	@Test
	public void consultarSaldoDeUnaCajaDeAhorroPesos() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 1400);
		Assert.assertEquals(1400,consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
		
	}
	
	@Test
	public void consultarSaldoDeUnaCajaDeAhorroDolares() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		Assert.assertEquals(200, consulta.consultarSaldo(cajaDeAhorroDolares),0.1);
	}
	
	@Test
	public void realizarUnaExtraccionDeUnaCuentaCorrienteYLuegoConsultarElSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 20000);
		extraccion = new Extraccion();
		extraccion.extraerFondos(cuentaCorriente, 1500);
		
		Assert.assertEquals(0, consulta.consultarSaldo(cuentaCorriente),0.1);
	}
	
	@Test
	public void realizarUnaExtraccionDeUnaCajaDeAhorroPesosYLuegoConsultarElSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		 cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 5000);
		 extraccion = new Extraccion();
		 extraccion.extraerFondos(cajaDeAhorroPesos, 1500);
		 
		 Assert.assertEquals(3500, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
	}
	
	@Test
	public void realizarUnDepositoDeUnaCuentaCorrienteYLuegoConsultarElSaldo() throws ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 20000);
		deposito = new Deposito();
		deposito.depositar(cuentaCorriente, 1500);
		
		Assert.assertEquals(3000, consulta.consultarSaldo(cuentaCorriente),0.1);
	}
	
	@Test
	public void realizarUnDepositoDeUnaCajaDeAhorroPesosYLuegoConsultarElSaldo() throws ErrorAlIntroducirSaldo {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 5000);
		deposito = new Deposito();
		deposito.depositar(cajaDeAhorroPesos, 1500);
		
		Assert.assertEquals(6500, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
	}
	
	@Test
	public void realizarUnaTransferenciaDeUnaCajaDeAhorroPesosACuentaCorrienteYLuegoConsultarSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 5000);
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 20000);
		
		transferencia = new Transferencia();
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, 1000);
		
		Assert.assertEquals(2500,consulta.consultarSaldo(cuentaCorriente),0.1);
		Assert.assertEquals(4000, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
	}
	
	
	@Test
	public void realizarUnaTransferenciaDeUnaCuentaCorrienteAUnaCajaDeAhorroPesosYLuegoConsultarSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 20000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 5000);
		
		transferencia = new Transferencia();
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 1000);
		
		Assert.assertEquals(500,consulta.consultarSaldo(cuentaCorriente),0.1);
		Assert.assertEquals(6000, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
	}
	
	
	@Test
	public void realizarUnaTransferenciaDeUnaCuentaCorrienteAUnaCajaDeAhorroPesosRevertirlaYLuegoConsultarSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 20000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 5000);
		
		transferencia = new Transferencia();
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 1000);
		
	
		Assert.assertEquals(500,consulta.consultarSaldo(cuentaCorriente),0.1);
		Assert.assertEquals(6000, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
		
		transferencia.revertirMovimiento(cuentaCorriente,cajaDeAhorroPesos);
		
		Assert.assertEquals(1500,consulta.consultarSaldo(cuentaCorriente),0.1);
		Assert.assertEquals(5000, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
		
	} 
	
	@Test
	public void realizarUnaTransferenciaDeUnaCajaDeAhorroPesosACuentaCorrienteRevertirlaYLuegoConsultarSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 5000);
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1500, 20000);
		
		transferencia = new Transferencia();
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, 1000);
		
		Assert.assertEquals(2500,consulta.consultarSaldo(cuentaCorriente),0.1);
		Assert.assertEquals(4000, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
		
		transferencia.revertirMovimiento(cajaDeAhorroPesos,cuentaCorriente);
		
		Assert.assertEquals(5000,consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
		Assert.assertEquals(1500,consulta.consultarSaldo(cuentaCorriente),0.1);
	}
	
	@Test
	public void comprarDolaresDesdeUnaCuentaCorrienteYConsultarElSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 1400, 25000);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		comprar = new ComprarDolares();
		
		comprar.comprarDolares(cuentaCorriente, cajaDeAhorroDolares, 5);
		
		Assert.assertEquals(205, consulta.consultarSaldo(cajaDeAhorroDolares),0.1);
		Assert.assertEquals(966.6, consulta.consultarSaldo(cuentaCorriente),0.1);
	}
	
	@Test
	public void comprarDolaresDesdeUnaCajaDeAhorroPesosYConsultarElSaldo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente, ErrorCuentaInvalida {
		
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 1400);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
		
		comprar = new ComprarDolares();
		
		comprar.comprarDolares(cajaDeAhorroPesos, cajaDeAhorroDolares, 5);
		
		Assert.assertEquals(205, consulta.consultarSaldo(cajaDeAhorroDolares),0.1);
		Assert.assertEquals(966.6, consulta.consultarSaldo(cajaDeAhorroPesos),0.1);
	}
}
