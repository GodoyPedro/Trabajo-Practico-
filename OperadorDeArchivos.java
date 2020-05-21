package predeterminado;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OperadorDeArchivos {
	
	private int busquedaBinaria(List<String> lista, String numeroTarjeta) {
		
		int inicio=0;				
		int fin=lista.size();		
		int medio; 					
		
		while(inicio <= fin) {		
			
			medio = (fin+inicio)/2;
																			
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
			
			indiceTarjeta = busquedaBinaria(contenidoDelArchivo, numeroTarjeta);
			
			if (indiceTarjeta > -1){
				
				datosTarjeta = contenidoDelArchivo.get(indiceTarjeta).split(",");
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
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
					
					if(tipoCuenta.equals("01")) { 
						
						listaCuentas.add(new CajaDeAhorroPesos(aliasCuenta,saldoCuenta));
					}
					
					if(tipoCuenta.equals("02")) {
												
						listaCuentas.add(new CuentaCorriente(aliasCuenta,saldoCuenta,descubiertoCuenta));
					}
					
					if(tipoCuenta.equals("03")) {
						
						listaCuentas.add(new CajaDeAhorroDolares(aliasCuenta,saldoCuenta));
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
	
	
	public void escribirArchivoMovimientos() {
		
	}
	public void escribirArchivoTickets() {
		
	}
}
