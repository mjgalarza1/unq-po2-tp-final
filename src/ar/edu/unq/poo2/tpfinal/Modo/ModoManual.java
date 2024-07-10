package ar.edu.unq.poo2.tpfinal.Modo;

import java.time.LocalDateTime;
import java.util.Optional;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.AppSem.GPS;
import ar.edu.unq.poo2.tpfinal.Notificacion.AlertaDeFin;
import ar.edu.unq.poo2.tpfinal.Notificacion.AlertaDeInicio;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Notificacion.NotificacionMensajePersonalizado;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class ModoManual implements Modo{

	@Override
	public void finalizarSiCorrespondeYNotificar(String patente, SEM sem, AppSem app) {
		app.notificar(new AlertaDeFin());
	}
	
	@Override
	public void registrarSiCorrespondeYNotificar(String patente, ZonaDeEstacionamiento zonaActual, SEM sem, int numCel, AppSem app) {
		app.notificar(new AlertaDeInicio());
	}
}
