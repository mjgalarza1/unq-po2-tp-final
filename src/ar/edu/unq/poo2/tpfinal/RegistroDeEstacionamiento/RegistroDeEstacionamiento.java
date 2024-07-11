package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.*;


public abstract class RegistroDeEstacionamiento {
	
	String patente;
	LocalDateTime fechaYHoraDeInicio;
	ZonaDeEstacionamiento zonaDeEstacionamiento;
	boolean vigencia;
	
	public String getPatente() {
		return this.patente;
	}
	
	public LocalDateTime getFechaYHoraDeInicio() {
		return this.fechaYHoraDeInicio;
	}
	
	public boolean getVigencia() {
		return this.vigencia;
	}
	
	public ZonaDeEstacionamiento getZonaDeEstacionamiento() {
		return this.zonaDeEstacionamiento;
	}
	
	public boolean esDeApp() {
		return false;
	};
	
	public void setVigencia(boolean vigencia) {
		this.vigencia = vigencia;
	}

}
