package ar.edu.unq.poo2.tpfinal.Modo;

import ar.edu.unq.poo2.tpfinal.AppSem.GPS;
import ar.edu.unq.poo2.tpfinal.AppSem.IPantalla;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public interface Modo {

	Notificacion finalizarEstacionamientoPara(String patente, SEM sem);
	
	Notificacion registrarVehiculo(String patente, ZonaDeEstacionamiento zonaA, SEM sem, int numCel, IPantalla pantalla);
	
	Notificacion driving(boolean estaManejando, GPS unGps, String patente, SEM sem);

	Notificacion walking(boolean estaManejando, String patente, SEM sem);
}