package ar.edu.unq.poo2.tpfinal.Sem;

import java.util.List;
import java.util.Optional;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class SEM {

	public String getSaldoDe(int numCel) {
		return null;
	}

	public Notificacion registrarEstacionamiento(RegistroDeEstacionamiento unEstacionamiento) {
		return null;
	}

	public Notificacion finalizarEstacionamiento(String patente) {
		return null;
	}

	public List<RegistroDeEstacionamiento> getRegistrosDeEstacionamiento() {
		return null;
	}

	public Optional<RegistroDeEstacionamiento> getEstacionamientoDePatente(String patente) {
		return null; // getRegistrosDeEstacionamiento().stream().filter(e -> e.getPatente() == patente).findFirst();
	}
}
