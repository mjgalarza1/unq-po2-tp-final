package ar.edu.unq.poo2.tpfinal.Modo;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.AppSem.GPS;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public interface Modo {

	public abstract void finalizarSiCorrespondeYNotificar(String patente, SEM sem, AppSem app);

	public abstract void registrarSiCorrespondeYNotificar(String patente, ZonaDeEstacionamiento zonaActual, SEM sem, int numCel, AppSem app);
}