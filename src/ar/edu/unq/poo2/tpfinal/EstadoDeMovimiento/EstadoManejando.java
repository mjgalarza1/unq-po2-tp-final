package ar.edu.unq.poo2.tpfinal.EstadoDeMovimiento;

import java.util.Optional;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionMensajePersonalizado;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class EstadoManejando implements EstadoDeMovimiento {
	
	public void driving(AppSem unaApp, SEM unSem) {
		
	}
	
	public void walking(AppSem unaApp, SEM unSem) {
		Optional<RegistroDeEstacionamiento> opcionalEstacionamiento = unSem.getEstacionamientoDePatente(unaApp.getPatente());	
		if(opcionalEstacionamiento.isEmpty() || !opcionalEstacionamiento.get().getVigencia()){ // si no hay estacionamiento  o  hay y no está vigente
			unaApp.registrarSiCorrespondeYNotificar();
		}
		unaApp.setEstadoDeMovimiento(new EstadoCaminando());
	}
}
