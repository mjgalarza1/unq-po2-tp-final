package ar.edu.unq.poo2.tpfinal.Sem;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Optional;

import ar.edu.unq.poo2.tpfinal.Celular.Celular;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionDeInicioExitoso;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionMensajePersonalizado;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoPuntual;

public class EstadoAbierto implements EstadoSEM {

	@Override
	public void registrarEstacionamientoPuntual(RegistroDeEstacionamientoPuntual unEstacionamiento, SEM sem) {
		sem.registrarEstacionamientoPuntualEstandoAbierto(unEstacionamiento);
	}

	@Override
	public Notificacion registrarEstacionamientoPorApp(RegistroDeEstacionamientoApp unEstacionamiento, SEM sem) {
		return sem.registrarEstacionamientoPorAppEstandoAbierto(unEstacionamiento);
	}

	@Override
	public Notificacion finalizarEstacionamiento(String patente, SEM sem) {
		return sem.finalizarEstacionamientoEstandoAbierto(patente);
	}

}
