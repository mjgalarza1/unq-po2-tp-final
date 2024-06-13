package ar.edu.unq.poo2.tpfinal.Notificacion;

import java.time.LocalTime;

public class NotificacionDeFin implements Notificacion {

	private LocalTime horaInicio;
	private LocalTime horaFin;
	private double cantHoras;
	private double costoDeEstacionamiento;
	
	public NotificacionDeFin(LocalTime horaInicio, LocalTime horaFinal, double cantHoras, double costoDeEstacionamiento) {
		this.horaInicio = horaInicio;
		this.horaFin = horaFinal;
		this.cantHoras = cantHoras;
		this.costoDeEstacionamiento = costoDeEstacionamiento;
	}
	
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	
	public LocalTime getHoraFin() {
		return horaFin;
	}
	
	public double getCantHoras() {
		return cantHoras;
	}
	
	public double getCostoDeEstacionamiento() {
		return costoDeEstacionamiento;
	}
	
	@Override
	public String getMensaje() {
		return "Su estacionamiento ha sido finalizado con éxito";
	}

}
