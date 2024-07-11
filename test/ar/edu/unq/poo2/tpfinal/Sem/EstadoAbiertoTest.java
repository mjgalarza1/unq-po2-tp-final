package ar.edu.unq.poo2.tpfinal.Sem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.*;

class EstadoAbiertoTest {

	private EstadoAbierto unEstadoAbierto;
	private SEM unSem;
	private RegistroDeEstacionamientoApp unEstacionamientoApp;
	private RegistroDeEstacionamientoPuntual unEstacionamientoPuntual;
	private String unaPatente;
	
	@BeforeEach
	public void setUp() {
		unEstadoAbierto = new EstadoAbierto();
		unSem = mock(SEM.class);
		unEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		unEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		unaPatente = "abc123";
	}
	
	@Test
	void registrarEstacionamientoPuntualTEST() {
		// Exercise
		unEstadoAbierto.registrarEstacionamientoPuntual(unEstacionamientoPuntual, unSem);
		
		// Verify
		verify(unSem, times(1)).registrarEstacionamientoPuntualEstandoAbierto(unEstacionamientoPuntual);
	}
	
	@Test
	void registrarEstacionamientoAppTEST() {
		// Setup
		Notificacion unaNotificacion = mock(Notificacion.class);
		when(unSem.registrarEstacionamientoPorAppEstandoAbierto(unEstacionamientoApp)).thenReturn(unaNotificacion);
		
		// Exercise
		Notificacion notificacionRecibida = unEstadoAbierto.registrarEstacionamientoPorApp(unEstacionamientoApp, unSem);
		
		// Verify
		verify(unSem, times(1)).registrarEstacionamientoPorAppEstandoAbierto(unEstacionamientoApp);
		assertEquals(notificacionRecibida, unaNotificacion);
	}
	
	@Test
	void finalizarEstacionamientoTEST() {
		// Setup
		Notificacion unaNotificacion = mock(Notificacion.class);
		when(unSem.finalizarEstacionamientoEstandoAbierto(unaPatente)).thenReturn(unaNotificacion);
		
		// Exercise
		Notificacion notificacionRecibida = unEstadoAbierto.finalizarEstacionamiento(unaPatente, unSem);
		
		// Verify
		verify(unSem, times(1)).finalizarEstacionamientoEstandoAbierto(unaPatente);
		assertEquals(notificacionRecibida, unaNotificacion);
	}

}