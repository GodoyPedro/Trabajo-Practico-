
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

import CajaDeAhorroDolares;
import CajaDeAhorroPesos;
import Cuenta;
import CuentaCorriente;
import ErrorAlIntroducirSaldo;
import ErrorSaldoInsuficiente;

import Tarjeta;
import Transferencia;

public class TransferenciaTest {

	private Transferencia transferencia = new Transferencia();
	private Cuenta cuentaCorriente;
	private Cuenta cajaDeAhorroPesos;
	private Cuenta cajaDeAhorroDolares;
	
	
	@Test
	public void transferir100PesosDeUnaCuentaCorrienteAUnaCajaDeAhorro() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente{
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 100);
		
		Assert.assertEquals(-1121.90,cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(12100.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void transferir100PesosDeUnaCajaDeAhorroAUnaCuentaCorriente() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, 100);
		
		Assert.assertEquals(-921.90,cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(11900.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarTransferirDeUnaCajaDeAhorroAUnaCuentaCorrienteUnSaldoNegativo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, -100);
	
	}
	
	@Test(expected = ErrorAlIntroducirSaldo.class)
	public void intentarTransferirDeUnaCuentaCorrienteAUnaCajaDeAhorroUnSaldoNegativo() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, -100);
	
	}
	
	@Test
	public void intentarTransferirDeUnaCuentaCorrienteALaMismaCuentaYQueNoSeRealice() throws ErrorSaldoInsuficiente, ErrorAlIntroducirSaldo {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		
		transferencia.transferir(cuentaCorriente, cuentaCorriente, 5000);
		
		Assert.assertEquals(-1021.90,cuentaCorriente.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void intentarTransferirDeUnaCuentaCorrienteAUnaCajaDeAHorroEnDolares() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente{
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setErr(new PrintStream(outContent));
     
        cuentaCorriente = new CuentaCorriente("hormiga.lija.crema", -50.97, 150.00);
		cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);

        transferencia.transferir(cuentaCorriente, cajaDeAhorroDolares, 1);

        Assert.assertEquals("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES", outContent.toString());
		
	}
	
	@Test
	public void intentarTransferirDeUnaCajaDeAhorroPesosAUnaCajaDeAHorroEnDolares() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente{

	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	        System.setErr(new PrintStream(outContent));

	     
	        cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
	        cajaDeAhorroDolares = new CajaDeAhorroDolares("lobo.luna.pasto", 200);
	        transferencia.transferir(cajaDeAhorroPesos, cajaDeAhorroDolares, 10);

	        Assert.assertEquals("NO SE PUEDE OPERAR CON UNA CUENTA EN DOLARES", outContent.toString());
	  }
		
	
	
	@Test
	public void transferirDosVecesDeUnaCajaDeAhorroAUnaCuentaCorriente() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, 100);
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, 100);
		
		Assert.assertEquals(-821.90,cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(11800.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
	}
	
	@Test
	public void transferirDosVecesDeUnaCuentaCorrienteAUnaCajaDeAhorro() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", -1021.90, 25000);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 12000.03);
		
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 100);
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 100);
		
		Assert.assertEquals(-1221.90,cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(12200.03,cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void pruebaRevertirUnaTransferenciaDe100Pesos() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 300, 500);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 600);
		
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 100);
		Assert.assertEquals(200, cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(700, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
		transferencia.revertirMovimiento(cuentaCorriente, cajaDeAhorroPesos);
		Assert.assertEquals(300, cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(600, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void pruebaRevertirUnaTransferenciaDe300Pesos() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 400, 500);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 800);
		
		transferencia.transferir(cajaDeAhorroPesos, cuentaCorriente, 300);
		Assert.assertEquals(700, cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(500, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
		transferencia.revertirMovimiento(cajaDeAhorroPesos, cuentaCorriente);
		Assert.assertEquals(400, cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(800, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	@Test
	public void pruebaRevertirUnaTransferenciaQueUtilizoElDescubierto() throws ErrorAlIntroducirSaldo, ErrorSaldoInsuficiente {
		
		cuentaCorriente = new CuentaCorriente("sol.monte.valle", 100, 500);
		cajaDeAhorroPesos = new CajaDeAhorroPesos("isla.pez.arbol", 600);
		
		transferencia.transferir(cuentaCorriente, cajaDeAhorroPesos, 300);
		Assert.assertEquals(-200, cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(900, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
		transferencia.revertirMovimiento(cajaDeAhorroPesos, cuentaCorriente);
		Assert.assertEquals(100, cuentaCorriente.obtenerSaldo(),0.1);
		Assert.assertEquals(600, cajaDeAhorroPesos.obtenerSaldo(),0.1);
		
	}
	
	
}
