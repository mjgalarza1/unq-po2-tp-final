package ar.edu.unq.poo2.tpfinal.Sem;

import java.time.Clock;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoPuntual;

public interface EstadoSEM {

	public void registrarEstacionamientoPuntual(RegistroDeEstacionamientoPuntual unEstacionamiento, SEM sem);

	public Notificacion registrarEstacionamientoPorApp(RegistroDeEstacionamientoApp unEstacionamiento, SEM sem, Clock reloj);

	public Notificacion finalizarEstacionamiento(String patente, SEM sem);

}
