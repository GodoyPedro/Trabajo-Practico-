import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OperadorDeArchivos {
	
	private int busquedaBinaria(List<String> lista, String numeroTarjeta) {
		
		int inicio=0;				
		int fin=lista.size();		
		int medio; 					
		
		try {
			
			while(inicio <= fin) {		
				
				medio = (fin+inicio)/2;
				
				if(medio >= lista.size()) {
					
					return -1;
				}
				
				if(Integer.parseInt(lista.get(medio).split(",")[0]) > Integer.parseInt(numeroTarjeta)) {
					
					fin = medio-1;
				}
				
				else if(Integer.parseInt(lista.get(medio).split(",")[0]) < Integer.parseInt(numeroTarjeta)) {
					inicio = medio+1;
				}
				
				else {
					return medio;
				}
			}
		}
		

		catch (NumberFormatException error){
			
			System.err.println("Ingrese un numero valido de tarjeta, intente nuevamente");
			System.exit(1);
		}
		
		return -1;	
	}
	/**
	 * Abro el archivo "Validacion de tarjetas", uso una busqueda binaria para encontrar eficientemente si existe una tarjeta
	 * con el numero solicitado, si existe, devuelvo su pin y su cuit en un array
	 * 
	 * @param numeroTarjeta
	 * @return datosTarjeta
	 * @throws IOException
	 */
	public String[] analizarArchivoValidacion(String numeroTarjeta){
		
		int indiceTarjeta;
		String[] datosTarjeta = null;
		
		String path = "./txt/Validacion de tarjetas.txt";
		Path p = Paths.get(path);
		
		List<String> contenidoDelArchivo;
		
		try {
			
			contenidoDelArchivo = Files.readAllLines(p);
			
			if(contenidoDelArchivo.size() == 0) {
				
				throw new ErrorArchivoVacio("El archivo esta vacio");
			}
			
			indiceTarjeta = busquedaBinaria(contenidoDelArchivo, numeroTarjeta);
			
			if (indiceTarjeta > -1){
				
				datosTarjeta = contenidoDelArchivo.get(indiceTarjeta).split(",");
			}
	
			if(datosTarjeta == null || datosTarjeta.length != 3) {
				
				throw new ErrorDatosInvalidos("El numero de tarjeta ingresado no existe en la base de datos");
			}

		}
		
		catch (ErrorDatosInvalidos error) {
			
			System.err.println(error.getMessage());
			System.exit(1);
		}
		
		catch (ErrorArchivoVacio error) {
			
			System.err.println(error.getMessage());
			System.exit(1);	
		}

		catch (NoSuchFileException error) {
			
			System.err.println("El archivo no existe, no esta en el directorio correcto o no tiene el nombre correcto");
			System.exit(1);
		}
			
		catch (IOException error) {
			
			error.printStackTrace();
		}
		
		return datosTarjeta;
	}
	
	/**
	 * Abro el archivo Clientes, y lo recorro buscando todos los alias asociados al cuit que extraje de la tarjeta
	 * Todos los que encuentre los devuelvo en una lista
	 * 
	 * @param cuit
	 * @return listaAlias
	 */
	public List<String> analizarArchivoClientes(String cuit){

		List<String> listaAlias = new ArrayList<String>();
		BufferedReader lector = null;
		String unaLinea;
		
		try {
			
			lector = new BufferedReader(new FileReader("./txt/Clientes.txt"));
			
			while((unaLinea = lector.readLine()) != null) {
				
				String [] cuitAlias = unaLinea.split(",");
				
				if(cuitAlias[0].equals(cuit)) {
					
					listaAlias.add(cuitAlias[1]);
				}
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				lector.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return listaAlias;	
	}
	
	/**
	 * Abro el archivo Cuentas, y me fijo a que tipo de cuenta corresponden los alias que extraje del archivo Clientes
	 * y creo las cuentas con su alias, saldo y descubierto para la cuenta corriente
	 * 
	 * @param alias
	 * @return listaCuentas
	 */
	public List<Cuenta> analizarArchivoCuentas(List<String> alias) {
		
		
		
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>();
		BufferedReader lector = null;
		String unaLinea;
		
		try {
			
			lector = new BufferedReader(new FileReader("./txt/Cuentas.txt"));
			
			while((unaLinea = lector.readLine()) != null) {
				
				String [] informacionCuenta = unaLinea.split(","); //tipoCuenta,aliasCuenta,saldoCuenta,descubiertoCuenta
		
				if(alias.contains(informacionCuenta[1])) {
					
					String tipoCuenta = informacionCuenta[0];
					String aliasCuenta = informacionCuenta[1];
					double saldoCuenta = Double.parseDouble(informacionCuenta[2]);
					double descubiertoCuenta = 0;
					
					if(informacionCuenta.length == 4) { //osea CC porque tiene descubierto
						
						descubiertoCuenta = Double.parseDouble(informacionCuenta[3]);						
					}
					
					try {
						
						if(tipoCuenta.equals("01")) { 
							
							listaCuentas.add(new CajaDeAhorroPesos(aliasCuenta,saldoCuenta));
						}
						
						else if(tipoCuenta.equals("02")) {
																		
							listaCuentas.add(new CuentaCorriente(aliasCuenta,saldoCuenta,descubiertoCuenta));
						}
						
						else if(tipoCuenta.equals("03")) {
												
							listaCuentas.add(new CajaDeAhorroDolares(aliasCuenta,saldoCuenta));				
						}
						
					}
					catch (ErrorAlIntroducirSaldo e) {

						System.out.println("El saldo introducido es invalido");
					}							
				}			
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				lector.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

		return listaCuentas;
	}
	
	
	public void  escribirArchivoMovimientos(List<String> movimientos){
		
		FileWriter escritor = null;
		
		try {
			
			try {
				
				escritor = new FileWriter("./txt/Movimientos.txt");
				
				for(String movimiento: movimientos) {
					
					escritor.write(movimiento + System.lineSeparator());

				}
				
			} finally {
				
				escritor.close();
			}
				
		} catch (IOException e) {
			
			e.printStackTrace();

		}	
	}
	public void escribirArchivoTickets(String datos) {
		
		FileWriter escritor = null;
		
		try {
			
			try {
				
				escritor = new FileWriter("./txt/Tickets.txt",true);

				escritor.write(datos + System.lineSeparator());	
				
			} finally {
				
				escritor.close();
			}
				
		} catch (IOException e) {
			
			e.printStackTrace();

		}	
		
	}
	
	public void escritorArchivoAlias(List<String> datos) {
		
		FileWriter escritor = null;
		
		try {
			
			try {
				
				escritor = new FileWriter("./txt/Alias.txt",true);
				
				for(String alias: datos) {
				
					escritor.write(alias + System.lineSeparator());	
				}
				
			} finally {
				
				escritor.close();
			}
				
		} catch (IOException e) {
			
			e.printStackTrace();

		}
	}

}
