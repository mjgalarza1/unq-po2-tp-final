package ar.edu.unq.poo2.tpfinal.Modo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.AppSem.GPS;
import ar.edu.unq.poo2.tpfinal.AppSem.IPantalla;
import ar.edu.unq.poo2.tpfinal.Notificacion.*;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

class ModoAutomaticoTEST {
	ModoAutomatico unModoAutomatico;
	ZonaDeEstacionamiento unaZona;
	SEM unSem;
	GPS unGps;
	IPantalla unaPantalla;
	
	@BeforeEach
	void setUp() throws Exception {
		unSem = mock(SEM.class);
		unaZona = mock(ZonaDeEstacionamiento.class);
		unaPantalla = mock(IPantalla.class);
		unGps = mock(GPS.class);
		unModoAutomatico = new ModoAutomatico();
	}

	@Test
	void registrarVehiculoTEST() {
		ArgumentCaptor<RegistroDeEstacionamientoApp> registroCaptor = ArgumentCaptor.forClass(RegistroDeEstacionamientoApp.class);
		Notificacion miNotificacion = mock(NotificacionMensajePersonalizado.class);
		when(unSem.registrarEstacionamiento(registroCaptor.capture())).thenReturn(miNotificacion);
		when(miNotificacion.getMensaje()).thenReturn("Saldo insuficiente. Estacionamiento no permitido.");
		
		Notificacion unaNotificacion = unModoAutomatico.registrarVehiculo("ABC 123", unaZona, unSem, 1166667777, unaPantalla);
		
		verify(unSem, times(1)).registrarEstacionamiento(registroCaptor.capture());
		assertEquals(unaNotificacion.getMensaje(), "Saldo insuficiente. Estacionamiento no permitido.");
		assertEquals(1166667777, registroCaptor.getValue().getNumeroDeCelular());
	}
	
	@Test
	void finalizarEstacionamientoParaTEST() {
		Notificacion miNotificacion = mock(NotificacionDeFin.class);
		when(miNotificacion.getMensaje()).thenReturn("Su estacionamiento ha sido finalizado de forma automática");
		when(unSem.finalizarEstacionamiento("ABC 123")).thenReturn(miNotificacion);
		
		Notificacion unaNotificacion = unModoAutomatico.finalizarEstacionamientoPara("ABC 123", unSem);
		
		verify(unSem, times(1)).finalizarEstacionamiento("ABC 123");
		assertEquals(unaNotificacion.getMensaje(), "Su estacionamiento ha sido finalizado de forma automática");
	}

	@Test
	void drivingCuandoPreviamenteNoEstabaManejandoTEST() {
		
		
		unModoAutomatico.driving(false, unGps, "ABC 123", unSem);
		
		
	}
	
	@Test
	void walkingTEST() {
		fail("Not yet implemented");
	}

	//driving(boolean, GPS, SEM): void
	//walking(boolean): void
}
