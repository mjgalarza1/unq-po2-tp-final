package ar.edu.unq.poo2.tpfinal.EstadoDeMovimiento;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;

class EstadoManejandoTEST {

	private EstadoManejando unEstadoManejando;
	private AppSem unaApp;
	private SEM unSem;
	private RegistroDeEstacionamientoApp unRegistroDeEstacionamientoApp;
	private ArgumentCaptor<EstadoCaminando> estadoCaminandoCaptor;
	private Optional<RegistroDeEstacionamiento> opcionalEstacionamiento;
	
	@BeforeEach
	void setUp(){
		unEstadoManejando = new EstadoManejando();
		unaApp = mock(AppSem.class);
		unSem  = mock(SEM.class);
		unRegistroDeEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		estadoCaminandoCaptor = ArgumentCaptor.forClass(EstadoCaminando.class);
		opcionalEstacionamiento = mock(Optional.class);
		when(unaApp.getPatente()).thenReturn("abc 123");
		when(unSem.getEstacionamientoDePatente(unaApp.getPatente())).thenReturn(opcionalEstacionamiento);
	}
	
	private void verificacionesInvariantes() {
		verify(unSem,times(1)).getEstacionamientoDePatente("abc 123");
		verify(unaApp,times(1)).setEstadoDeMovimiento(estadoCaminandoCaptor.capture());
	}
	
	@Test
	void drivingNoHaceNadaTEST() {
		unEstadoManejando.driving(unaApp, unSem);
	}

	@Test
	void walking_CuandoEstaElEstacionamientoYSeEncuentraVigente_SoloSeteaElNuevoEstadoTEST() { // true - true
		// setup
	    when(opcionalEstacionamiento.isEmpty()).thenReturn(false);
	    when(opcionalEstacionamiento.get()).thenReturn(unRegistroDeEstacionamientoApp);
		when(unRegistroDeEstacionamientoApp.getVigencia()).thenReturn(true);
		
		// exercise
		unEstadoManejando.walking(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,never()).registrarSiCorrespondeYNotificar();
	}
	
	@Test
	void walking_CuandoEstaElEstacionamientoYNOSeEncuentraVigente_RegistraYSeteaElNuevoEstadoTEST() { // true - false
		// setup
	    when(opcionalEstacionamiento.isEmpty()).thenReturn(false);
	    when(opcionalEstacionamiento.get()).thenReturn(unRegistroDeEstacionamientoApp);
		when(unRegistroDeEstacionamientoApp.getVigencia()).thenReturn(false);
		
		// exercise
		unEstadoManejando.walking(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,times(1)).registrarSiCorrespondeYNotificar();
	}
	
	@Test
	void walking_CuandoNOEstaElEstacionamientoSeteaElNuevoEstadoTEST() { // false - any
		// setup
	    when(opcionalEstacionamiento.isEmpty()).thenReturn(true);
		
		// exercise
		unEstadoManejando.walking(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,times(1)).registrarSiCorrespondeYNotificar();
	}
}
