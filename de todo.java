public void leerTarjeta(){

	new LectorDeArchivo.crearCliente();

	//llama al menu
}

public void crearCliente(){



	analizarArchivoValidacion();
	// Cuit
	analizarArchivoCliente();
	//Alias
	analizarArchivoCuentas();
	//tipo
	//monto

	list.add()

	cliente = new Cliente(List<Cuenta>, cuit);



realizarTransferencia(cuenta1,cuenta2,importe){

	cliente.transferencia("pedro","pablo",100,true);

	saldoPrevioPedro = saldo;
	agregarMovimiento(fecha,hora,cuenta,tipoTransaccion,importeInvolucrado,nuevoSaldo)
}


	fecha, hora, cuenta, tipo de transacción, importe involucrado
	en la transacción y el nuevo saldo de la cuenta.


	agregarMovimiento(fecha,hora,cuenta,tipoTransaccion,importeInvolucrado,nuevoSaldo)
							 X                                                 X
++
++
+-
+-
+
+
+
+			

+++++··$$	 
/**/
String cuentaRecibe = 2020-05-01,23-59-59,uva.sandalia.halcon,transferencia,175.00,38902
String cuentaEnvia = 2020-05-01,23-59-59,isla.pez.arbol,transferencia,175.00,825



lista = [CC,CA,CAD,CA]

saldo = [100,1000,100,2000]

revertirUltimoMovimiento(){

	lees stack

	if = transferencia:
		revertirMovimiento(uva.sandalia.halcon);
		revertirMovimiento(isla.pez.arbol);

}

revertirMovimiento(uva.sandalia.halcon);
revertirMovimiento(isla.pez.arbol);

falta ver si podemos no pasarle los alias o pasarlos todos a la Funcion


revertirMovimiento(alias){

	volverSaldo();
}

// if tipoTransaccion == "transferencia":

// 	revertirMovimiento(0);

// if tipoTransaccion == "hipoteca":

// 	revertirMovimiento(1);

// if tipoTransaccion == "hipoteca":

// 	revertirMovimiento(2);

listaDeshacer = [deshacerTransferencia(),deshacerHipoteca()]

class cuentaCorriente implements Reversible{

	deshacerMovimiento(){

		cliente.transferencia("pablo","pedro",100,false);
		agregarMovimiento(fecha,hora,cuenta,"extraerMovimiento",importeInvolucrado,nuevoSaldo)
	}
}

deshacerTransferencia(cuenta2,cuenta1,importe){

	cliente.transferencia("pablo","pedro",100,false);
	agregarMovimiento(fecha,hora,cuenta,"extraerMovimiento",importeInvolucrado,nuevoSaldo)
}




//Stack: fecha,hora,cuenta,tipoTransaccion,importeInvolucrado,nuevoSaldo
//Archivo movimientos: fecha,cuenta,tipoTransaccion,importeInvolucrado
//Ticket: fecha,hora,cuenta,tipoTransaccion,importeInvolucrado,nuevoSaldo	

/* TICKET
Hora y fecha

Se realizo cuentaRecibe[n]:

El importe involucrado fue cuentaEnvia[n]

Desde cuentaRecibe[n]
Hacia cuentaEnvia[n]

El nuevo saldo de la cuenta: cuentaEnvia[n] es cuentaEnvia[n]
El nuevo saldo de la cuenta: cuentaRecibe[n] es cuentaRecibe[n]
*/




depositar(plata){

	saldo += plata
}

new Transaccion.depositar(cuenta,monto){

	cuenta.setSaldo(monto)


}

/*
	

*/


main(){

	Scanner numeroDeTarjeta

	Tarjeta miTarjeta = new Tarjeta(numeroDeTarjeta)

	ATM atm = new ATM()
	atm.setCliente(atm.leerTarjeta(miTarjeta))

	Menu menu = new Menu(atm)

	menu.desplegarIngerfaz()



}	
desplegarIngerfaz(){ 

	menu


	01 compras
	02 depositos
	03 extracciones
	04 transferencias
	05 revertirMovimiento


	Scanner

	obtenerAlias(){


		01 CuentaCorriente aliastanto
		02 CajaDeAhorro 
		03 CajaDeAhorro
	}

	if numero == 01:
		Scanner alias sacamos la plata cc ca
		Scanner alias ponemos la plata cad
		Scanner
		comprarDolares(alias1,alias2,monto);


	if numero == 03:
		Scanner alias

		Scanner
		retirarEfectivo(alias,monto);

	if numero == 02:
		Scanner
		Scanner
		depositarFondos(alias,monto);

	if numero == 04:
		Scanner
		Scanner
		Scanner
		realizarTransferencia(alias1,alias2,monto);
}


//agregar movimiento a la lista movimientos del cliente
//buscar las cuentas de la listaCuentas con el alias desde menu


agregarMovimientoClase{


	agregarMovimiento(Lista,elMovimiento){


	}

}


extraerFondosMenu(cuenta1,monto){

	objetoExtraccion().extraerFondos(cuenta1,monto)
	

	String movimento = fecha + hora + cuenta.getAlias() + "extraccion" + monto + cuenta1.getSaldo()


	HistorialMovimiento().agregarMovimiento(cuenta1.getLista(),movimiento);
	HistorialMovimiento().agregarMovimiento(cajero.getCliente().getLista(),movimiento);

	imprimirTicket(movimiento);
}


agregarMovimiento(lista,movimiento){

	lista.add(movimiento);
}


extraerFondosOperaciones(cuenta1,monto){

	cuenta1.extraerFondos(monto);


	
}
//agregamos el movimiento a la lista (String)
//pedimos el ticket

//Ticket: fecha,hora,cuenta,tipoTransaccion,importeInvolucrado,nuevoSaldo	
lista cuenta1 = 

imprimirTicket(){

	
	movimentos.split
}















revertirMovimiento(){

	// lista de movimentos de cliente

	List<String> movimentos = cajero.getCliente().getMovimientos();

	if(movimentos indice ultimo == transferencia){

		String alias1 = movimentos[ante ultimo].split[","][3]
		String alias2 = movimentos[ultimo].split[","][3]
		
		lista[cuenta que le sacan, cuenta a la que le deposita]

		Cuenta cuenta1 = cajero.getCliente().devolverCuenta(alias1);
		Cuenta cuenta2 = cajero.getCliente().devolverCuenta(alias2);

		revertirMovimientoInterfaz(cuenta1,cuenta2);
	}

}

revertirMovimientoInterfaz(cuenta1,cuenta2){

	cuenta1.setSaldo(cuenta1.getSaldoPrevio());
	cuenta2.setSaldo(cuenta2.getSaldoPrevio());
}



// comprarDolares(cuenta1,cuenta2,monto){


// 	objetoComprarDolares().transferir(cuenta1,cuenta2,montoDolares);

// 	// objetoTransferencia().transferir(cuenta1,cuenta2,monto);
// 	// objetoTransferencia().transferir(cuenta1,cuenta2,monto,objetoExtraccion,objetoDeposito);
// 	// objetoExtraccion
// 	// objetoDeposito
// }






// transfererir(Cuenta cuenta1, Cuenta cuenta2, double montoDolares){

// 	cuenta1.sacarSaldo(cambio de dolares a pesos)
// 	cuenta2.ponerSaldo(montoDolares)
// }
















// transfererir(cuenta1,cuenta2,montoDolares,objetoExtraccion,objetoDeposito){

// 	// cambio de dolar a peso

// 	objetoExtraccion.extraerFondos(cuenta1,montoPesos);
// 	objetoDeposito.despositar(cuenta2.montoDolares);
// }


// transfererir(cuenta1,cuenta2,montoDolares){

// 	cuenta1.sacarSaldo(cambio de dolares a pesos)
// 	cuenta2.ponerSaldo(montoDolares)
// }


// transfererir(cuenta1,cuenta2,monto){

// 	cuenta1.sacarSaldo(pesos)
// 	cuenta2.ponerSaldo(pesos)
// }









// retirarEfectivo{


// 	objetoExtraccion().extraerFondos(cuenta1,monto)
// 	// el usuario ingresa el alias con un Scanner
// 	// buscamos a que cuenta pertenece ese alias
// 	atm.realizarTransaccion().transferencia(cuenta1,cuenta2,monto)
// 	atm.realizarTransaccion().extraccion(cuenta1,monto);

// }

// setSaldo(monto){

// 	saldo += monto;
// 	agregarMovimiento()
// }



// // definido en menu



//dentro de operacion

extraccion(cuenta1,monto){

	// acceso a las cuetnas que le pasamos por parametro
	cuenta1.getSaldo(monto);
	// cuenta1.agregarMovimiento("extraccion",monto,cuenta1.getSaldo());
	//agregar a la lista de movimientos del cliente


}		

//saldo = 900
cuentaCorriente{ // descubierto = 100
 	
 	getSaldo(monto){

 		if(this.saldo+this.descubierto >= monto){

 			saldo -= monto
 		}

 		return monto
 	}
	
}

//saldo = 900
cajaAhorro{
 	
 	getSaldo(monto){

	 	if(saldo <= monto){

	 		return monto

	 	}else{

	 		error
	 	}
	}
}

//saldo = 900
cajaAhorroDolares{
 
	getSaldo(monto){

	 	if(saldo <= monto){

	 		return monto

	 	}else{

	 		error
	 	}
	}
}

//cuenta1 es de tipo cc
atm.realizarTransaccion().extraccion(cuenta1,1000) //esto lo puedo hacer

//cuenta1 es de tipo ca
atm.realizarTransaccion().extraccion(cuenta1,1000) //esto NO lo puedo hacer

//cuenta1 es de tipo cad
atm.realizarTransaccion().extraccion(cuenta1,1000) //esto NO lo puedo hacer









realizarTransferencia(alias1,alias2,monto){

	cuenta1 = atm.getCliente().devolverCuenta(alias1);
	cuenta2 = atm.getCliente().devolverCuenta(alias2);

	Transfrencia objetoTransaccion = new Transfrencia()

	objetoTransaccion.transferir(cuenta1,cuenta2,monto);
	atm.realizarTransaccion().transferir(cuenta1,cuenta2,monto);
	atm.realizarTransaccion().desposito(cuenta1,monto);

	deposito{

		cuenta1.setSaldo (monto)
	}
	// cuenta1.transferir(cuenta2,monto);

}

transferir{

	cuenta1.extraer(monto)
	cuenta2.depositar(monto)
}

	depositarFondos(alias,monto){

		atm.getCliente().devolverCuenta(alias).depositar(100);
	}
	
	// alias, 100

//a que cuenta se refiere el alias
//cliente(atm),listaCuentas(cliente) return objeto
[cc,cc,ca,cad]
getCliente().devolverCuenta(alias).depositar(100);
cliente.cc.depositar(100)

devolverCuenta{

	//buscar en la lista de cuentas y devolver el objeto
}

objeto.depositar(100)

getCliente();
//plata, aliasDeLaCuenta datos que ingresa el usario
atm.retirarEfectivo(aliasDeLaCuenta, plata):

retirarEfectivo{

	//recorrer lista de cuentas
	//chequear a que cuenta corresponde el alias
	//usando el objeto de cuenta hacemos la operacion


	//objeto.retirar(plata)			
}


Cliente leerTarjeta(Tarjeta miTarjeta){

	//cuit
	Cliente miCliente = new Cliente(cuit);

	return miCliente;
}

public Cliente(cuit){

	//setter meter en cuit el cuit que me pasan

	//crear las cuentas con ese cuit
	// O(n)
 	List<String> alias = new LectorDeArchivo().analizarArchivoClientes(cuit);
 	// O(n)
 	List<Cuenta> cuentas = new LectorDeArchivo().analizarArchivoCuentas(alias);
 	//tipo,alias,monto,(descubierto para cc)
 	//setter meto la lista cuentas en el atributo de cliente.
}

//-------------------------

	int intentos = 3;

	public Tarjeta(numeroDeTarjeta){

		leerNumeroTarjeta(numeroDeTarjeta){

		String[2] datos = new LectorDeArchivo().analizarArchivoValidacion(numeroDeTarjeta)
		//[pin,cuit]
		if datos.length != 2:
			corto (error de archivo)

		else:
			
			while intentos > 0{

				Scanner pin
				---------------
				if validarPint(datos[0]): boolean

					//seteamos el cuit (setters)
					intentos = -1;
				}
				else:
					intentos--;
					
			}
		
		if intentos == 0:
			corto (error del usuario)
		// 	crearCuentas(datos[1]){

		// 		analizarArchivoClientes()
		// 		analizarArchivoCuentas()
		// 	}

		// else:
		// 	corto (error del usuario)
	}
//-------------------------

}


Cajero{

	Operacion objetoTra = new Transferencia()
	Operacion objetoDep = new Deposito()
	Operacion objetoCons = new ConsultarSaldo()
	Operacion objetoExt = new Extraccion()

}























}