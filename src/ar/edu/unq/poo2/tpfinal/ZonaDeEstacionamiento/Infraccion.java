package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import java.time.LocalTime;

public class Infraccion {
	
	LocalTime fechaYHora;
	String patente;
	Inspector inspector;
	ZonaDeEstacionamiento zonaDeEstacionamiento;
	
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

}
