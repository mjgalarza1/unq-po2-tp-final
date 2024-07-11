package ar.edu.unq.poo2.tpfinal.Sem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.*;

class EstadoCerradoTest {

	private EstadoCerrado unEstadoCerrado;
	private SEM unSem;
	private RegistroDeEstacionamientoApp unEstacionamientoApp;
	private RegistroDeEstacionamientoPuntual unEstacionamientoPuntual;
	private String unaPatente;
	private String notificacionCerrado;
	
	@BeforeEach
	public void setUp() {
		unEstadoCerrado = new EstadoCerrado();
		unSem = mock(SEM.class);
		unEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		unEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		unaPatente = "abc123";
		notificacionCerrado = "Cerrado";
		when(unSem.mensajeDeNotificacionServicioCerrado()).thenReturn(notificacionCerrado);
	}
	
	@Test
	void registrarEstacionamientoPuntualTEST() {
		// Exercise
		unEstadoCerrado.registrarEstacionamientoPuntual(unEstacionamientoPuntual, unSem);
		
		// Verify
		verify(unSem, never()).registrarEstacionamientoPuntualEstandoAbierto(unEstacionamientoPuntual);
	}
	
	@Test
	void registrarEstacionamientoAppTEST() {
		// Exercise
		Notificacion notificacionRecibida = unEstadoCerrado.registrarEstacionamientoPorApp(unEstacionamientoApp, unSem);
		
		// Verify
		verify(unSem, never()).registrarEstacionamientoPorAppEstandoAbierto(unEstacionamientoApp);
		assertEquals(notificacionRecibida.getMensaje(), notificacionCerrado);
	}
	
	@Test
	void finalizarEstacionamientoTEST() {
		// Exercise
		Notificacion notificacionRecibida = unEstadoCerrado.finalizarEstacionamiento(unaPatente, unSem);
		
		// Verify
		verify(unSem, never()).finalizarEstacionamientoEstandoAbierto(unaPatente);
		assertEquals(notificacionRecibida.getMensaje(), notificacionCerrado);
	}

}
