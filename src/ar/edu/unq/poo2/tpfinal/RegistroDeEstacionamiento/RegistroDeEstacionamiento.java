package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.*;


public abstract class RegistroDeEstacionamiento {
	
	protected String patente;
	protected LocalDateTime fechaYHoraDeInicio;
	protected ZonaDeEstacionamiento zonaDeEstacionamiento;
	protected boolean vigencia;
	
	
	protected RegistroDeEstacionamiento() {
		this.vigencia = true;
	}

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
	
	public void setVigencia(boolean vigencia) {
		this.vigencia = vigencia;
	}

	public abstract void serCobradoSiCorrespondePor(SEM unSem);

	public abstract Notificacion notificarFinalizacionPara(SEM sem);

}
