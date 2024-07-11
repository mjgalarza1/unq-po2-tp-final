package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import java.time.LocalTime;

public class Infraccion {
	
	private LocalTime fechaYHora;
	private String patente;
	private Inspector inspector;
	private ZonaDeEstacionamiento zonaDeEstacionamiento;
	
	public Infraccion(LocalTime fechaYHora, ZonaDeEstacionamiento zonaDeEstacionamiento, Inspector inspector, String patente) {
		this.fechaYHora = fechaYHora;
		this.zonaDeEstacionamiento = zonaDeEstacionamiento;
		this.inspector = inspector;
		this.patente = patente;
	}
	
	public LocalTime getFechaYHora() {
		return this.fechaYHora;
	}
	
	public ZonaDeEstacionamiento getZonaDeEstacionamiento() {
		return this.zonaDeEstacionamiento;
	}
	
	public Inspector getInspector() {
		return this.inspector;
	}
	
	public String getPatente() {
		return this.patente;
	}

}
