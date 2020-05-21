package predeterminado;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FechaYHora {

	private String fecha;
	private String hora;
	private TimeZone horario;
	private SimpleDateFormat dia;
	
	/**
	 * post: Se marca la fecha y hora en que se realiza la operacion
	 * @param fecha
	 * @param hora
	 */
	public FechaYHora() {
		
		dia =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		horario = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");
		
		
	}

	/**
	 * 
	 * @return hora
	 */
	
	public String getHora() {
		
	
		return hora;
	}
	
	public String devolverFechaYHora() {
		
		actualizarHora();
		return fecha + "," + hora;
	}
	
	
	/**
	 * @return fecha
	 */
	public String getFecha() {
		
		return fecha;
	}
	
	private void actualizarHora() {
		
		dia.setTimeZone(horario);
		
		String nowAsISO = dia.format(new Date());
		String[] fechaYHora = nowAsISO.split(" ");
		
		this.fecha = fechaYHora[0];
		this.hora = fechaYHora[1];
		
	}
}
