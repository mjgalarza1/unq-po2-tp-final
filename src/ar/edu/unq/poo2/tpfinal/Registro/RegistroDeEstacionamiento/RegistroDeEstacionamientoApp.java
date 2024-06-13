package ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class RegistroDeEstacionamientoApp extends RegistroDeEstacionamiento {
	
	private int numeroDeCelular;

	public int getNumeroDeCelular() {
		return this.numeroDeCelular;
	}
	
	@Override
	public boolean esDeApp() {
		return true;
	}

	public RegistroDeEstacionamientoApp(String unaPatente, int unNumCel, LocalDateTime unaFechaYHoraDeInicio, ZonaDeEstacionamiento unaZonaDeEstacionamiento){
		this.patente = unaPatente;
		this.numeroDeCelular = unNumCel;
		this.fechaYHoraDeInicio = unaFechaYHoraDeInicio;
		this.zonaDeEstacionamiento = unaZonaDeEstacionamiento;
	}
}
