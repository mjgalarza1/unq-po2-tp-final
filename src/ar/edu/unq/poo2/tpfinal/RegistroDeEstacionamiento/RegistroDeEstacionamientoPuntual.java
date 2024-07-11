package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.*;

public class RegistroDeEstacionamientoPuntual extends RegistroDeEstacionamiento{
	
	private int horasEstacionamiento;
	
	public RegistroDeEstacionamientoPuntual(String patente, int horasEstacionamiento, LocalDateTime fechaYHoraDeInicio, ZonaDeEstacionamiento zonaDeEstacionamiento) {
		this.patente = patente;
		this.horasEstacionamiento = horasEstacionamiento;
		this.fechaYHoraDeInicio = fechaYHoraDeInicio;
		this.zonaDeEstacionamiento = zonaDeEstacionamiento;
	}
	
	public int getHorasEstacionamiento() {
		return this.horasEstacionamiento;
	}
	
		

}
