package ar.edu.unq.poo2.tpfinal.Sem;

import java.time.Clock;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionMensajePersonalizado;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoPuntual;

public class EstadoCerrado implements EstadoSEM {

	@Override
	public void registrarEstacionamientoPuntual(RegistroDeEstacionamientoPuntual unEstacionamiento, SEM sem) {

	}

	@Override
	public Notificacion registrarEstacionamientoPorApp(RegistroDeEstacionamientoApp unEstacionamiento, SEM sem, Clock reloj) {
		return new NotificacionMensajePersonalizado(sem.mensajeDeNotificacionServicioCerrado());
	}

	@Override
	public Notificacion finalizarEstacionamiento(String patente, SEM sem) {
		return new NotificacionMensajePersonalizado(sem.mensajeDeNotificacionServicioCerrado());
	}

}
