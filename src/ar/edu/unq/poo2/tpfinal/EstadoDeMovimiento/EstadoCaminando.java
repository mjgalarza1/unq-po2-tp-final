package ar.edu.unq.poo2.tpfinal.EstadoDeMovimiento;

import java.util.Optional;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionMensajePersonalizado;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class EstadoCaminando implements EstadoDeMovimiento{

	public void driving(AppSem unaApp, SEM unSem) {
		ZonaDeEstacionamiento zonaActual = unaApp.getZonaActual();
		Optional<RegistroDeEstacionamiento> opcionalEstacionamiento = unSem.getEstacionamientoDePatente(unaApp.getPatente());
		if(opcionalEstacionamiento.isPresent() 
			&& opcionalEstacionamiento.get().getVigencia()
			&& zonaActual == opcionalEstacionamiento.get().getZonaDeEstacionamiento()){
			unaApp.finalizarSiCorrespondeYNotificar();
		}
		unaApp.setEstadoDeMovimiento(new EstadoManejando());
	}
	
	public void walking(AppSem unaApp, SEM unSem) {
		
	}
}
