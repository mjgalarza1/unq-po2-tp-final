package ar.edu.unq.poo2.tpfinal.Modo;

import java.time.LocalDateTime;
import java.util.Optional;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.AppSem.GPS;
import ar.edu.unq.poo2.tpfinal.Notificacion.AlertaDeFin;
import ar.edu.unq.poo2.tpfinal.Notificacion.AlertaDeInicio;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionMensajePersonalizado;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class ModoAutomatico implements Modo {
	
	public void finalizarSiCorrespondeYNotificar(AppSem app) {
		Notificacion unaNotificacion = new NotificacionMensajePersonalizado("Se ha realizado el fin del estacionamiento de forma automática.");
		app.finalizarEstacionamientoConNotificacionExtra(unaNotificacion);
	}

	public void registrarSiCorrespondeYNotificar(AppSem app) {
		Notificacion unaNotificacion = new NotificacionMensajePersonalizado("Se ha registrado el estacionamiento del vehiculo de forma automática.");
		app.registrarVehiculoConNotificacionExtra(unaNotificacion);
	}
}
