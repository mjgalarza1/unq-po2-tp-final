package ar.edu.unq.poo2.tpfinal.Notificacion;

import java.time.LocalTime;

public class NotificacionDeInicioExitoso implements Notificacion {

	private LocalTime horaInicio;
	private LocalTime horaMaxima;
	
	public NotificacionDeInicioExitoso(LocalTime horaInicio, LocalTime horaMaxima) {
		this.horaInicio = horaInicio;
		this.horaMaxima = horaMaxima;
	}
	
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	
	public LocalTime getHoraMaxima() {
		return horaMaxima;
	}
	
	@Override
	public String getMensaje() {
		return "Su estacionamiento ha sido registrado con éxito";
	}

}
