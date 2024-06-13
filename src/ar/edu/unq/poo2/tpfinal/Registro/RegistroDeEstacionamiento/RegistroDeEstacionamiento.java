package ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class RegistroDeEstacionamiento {

	protected String patente;
	protected LocalDateTime fechaYHoraDeInicio;
	protected boolean vigencia;
	protected ZonaDeEstacionamiento zonaDeEstacionamiento;
	
	
	public String getPatente() {
		return this.patente;
	}
	
	public LocalDateTime getFechaYHoraDeInicio() {
		return this.fechaYHoraDeInicio;
	}
	
	public boolean getVigencia() {
		return this.vigencia;
	}
	
	public boolean esDeApp() {
		return false;
	}
	
	public void setVigencia(boolean vigencia) {
		this.vigencia = vigencia;
	}

	public ZonaDeEstacionamiento getZonaDeEstacionamiento() {
		return this.getZonaDeEstacionamiento();
	}
}
