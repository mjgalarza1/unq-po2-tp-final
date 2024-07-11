package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import java.time.LocalTime;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.*;

public class RegistroDeEstacionamientoPuntual extends RegistroDeEstacionamiento{
	
	public int horasEstacionamiento;
	
	public LocalTime fechaYHoraDeInicio;
	
	public RegistroDeEstacionamientoPuntual(String patente, int horasEstacionamiento, LocalTime fechaYHoraDeInicio, ZonaDeEstacionamiento zonaDeEstacionamiento) {
		this.patente = patente;
		this.horasEstacionamiento = horasEstacionamiento;
		this.fechaYHoraDeInicio = fechaYHoraDeInicio;
		this.zonaDeEstacionamiento = zonaDeEstacionamiento;
		this.vigencia = true;
	}
	
	public int getHorasEstacionamiento() {
		return this.horasEstacionamiento;
	}
	
		

}
