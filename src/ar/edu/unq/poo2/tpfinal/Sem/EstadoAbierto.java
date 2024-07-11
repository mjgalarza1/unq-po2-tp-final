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
		sem.agregarZonaDeEstacionamiento(unEstacionamiento);
		sem.agregarARegistrosDeEstacionamiento(unEstacionamiento);
		sem.notificar(unEstacionamiento); // Notifica a las entidades observadoras que el estacionamiento fue registrado.
	}

	@Override
	public Notificacion registrarEstacionamientoPorApp(RegistroDeEstacionamientoApp unEstacionamiento, SEM sem, Clock reloj) {
		// Guarda el registro del estacionamiento en el sistema
		// SOLAMENTE si el numero de celular del estacionamiento dado tiene saldo SUFICIENTE y
		// si la patente del estacionamiento dado NO se encuentra ya vigente en el sistema,
		// retornando la notificación correspondiente.
		Notificacion notificacion;
		int numeroDeCelular = unEstacionamiento.getNumeroDeCelular(); 
		Optional<Celular> celular = sem.getCelularDeNumero(numeroDeCelular);
		LocalTime horaActual = LocalTime.now(reloj);
		
		if (celular.isEmpty()) {
			notificacion = new NotificacionMensajePersonalizado(sem.mensajeDeNotificacionNumeroNoRegistrado(numeroDeCelular));
		} else if (celular.get().getCredito() <= 0) {
			notificacion = new NotificacionMensajePersonalizado(sem.mensajeDeNotificacionSaldoInsuficiente());
		} else {
			double saldoDelCliente = celular.get().getCredito();
			sem.agregarZonaDeEstacionamiento(unEstacionamiento);
			sem.agregarARegistrosDeEstacionamiento(unEstacionamiento);
			sem.notificar(unEstacionamiento); // Notifica a las entidades observadoras sobre el registro del estacionamiento.
			notificacion = new NotificacionDeInicioExitoso(horaActual, sem.getHoraMaximaPara(saldoDelCliente, horaActual));
		}
		return notificacion;
	}

	@Override
	public Notificacion finalizarEstacionamiento(String patente, SEM sem) {
		// Busca si existe un estacionamiento vigente que tenga la patente dada,
		// y si existe finalizarlo y retornar la notificacion debida.
		// Si NO existe, retornar una notificacion avisando que no existe
		// (O null para el caso de ser Puntual).
		boolean hayEstacionamientoVigente = sem.esEstacionamientoVigente(patente);
		Notificacion notificacion;
		if(hayEstacionamientoVigente) { // si es vigente se finaliza
			Optional<RegistroDeEstacionamiento> estacionamiento = sem.getEstacionamientoDePatente(patente);
			RegistroDeEstacionamiento estacionamientoModificado = estacionamiento.get();
			estacionamientoModificado.setVigencia(false);
			sem.notificar(estacionamientoModificado); // Notifica a las entidades observadoras sobre la finalizacion del estacionamiento.
			sem.cobrarleSiCorrespondeA(estacionamientoModificado);
			notificacion = sem.notificacionDeFinalizacionQueCorrespondaPara(estacionamientoModificado);
		}
		else { // si no hay estacionamiento vigente, no finaliza
			notificacion = new NotificacionMensajePersonalizado(sem.mensajeDeNotificacionEstacionamientoNoVigente(patente));
		}
		return notificacion;
	}

}
