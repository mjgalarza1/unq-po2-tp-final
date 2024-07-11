package ar.edu.unq.poo2.tpfinal.EstadoDeMovimiento;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

class EstadoCaminandoTEST {

	private EstadoCaminando unEstadoCaminando;
	private AppSem unaApp;
	private SEM unSem;
	private ZonaDeEstacionamiento unaZona;
	private RegistroDeEstacionamientoApp unRegistroDeEstacionamientoApp;
	private ArgumentCaptor<EstadoManejando> estadoManejandoCaptor;
	private Optional<RegistroDeEstacionamiento> opcionalEstacionamiento;
	private ZonaDeEstacionamiento otraZona;
	
	@BeforeEach
	void setUp(){
		unEstadoCaminando = new EstadoCaminando();
		unaApp = mock(AppSem.class);
		unSem  = mock(SEM.class);
		unaZona = mock(ZonaDeEstacionamiento.class);
		otraZona = mock(ZonaDeEstacionamiento.class);
		unRegistroDeEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		estadoManejandoCaptor = ArgumentCaptor.forClass(EstadoManejando.class);
		opcionalEstacionamiento = mock(Optional.class);
		when(unaApp.getPatente()).thenReturn("abc 123");
		when(unaApp.getZonaActual()).thenReturn(unaZona);
		when(unSem.getEstacionamientoDePatente(unaApp.getPatente())).thenReturn(opcionalEstacionamiento);
		
	}
	
	void verificacionesInvariantes() {
		verify(unaApp,times(1)).getZonaActual();
		verify(unSem,times(1)).getEstacionamientoDePatente("abc 123");
		verify(unaApp,times(1)).setEstadoDeMovimiento(estadoManejandoCaptor.capture());
	}
	
	@Test
	void walkingNoHaceNadaTEST() {
		unEstadoCaminando.walking(unaApp, unSem);
	}

	@Test
	void driving_CuandoEstaElEstacionamiento_SeEncuentraVigente_YEnLaMismaZona_FinalizaNotificaYSeteaElNuevoEstadoTEST() { // true - true - true
		// setup
	    when(opcionalEstacionamiento.isPresent()).thenReturn(true);
	    when(opcionalEstacionamiento.get()).thenReturn(unRegistroDeEstacionamientoApp);
	    when(unRegistroDeEstacionamientoApp.getZonaDeEstacionamiento()).thenReturn(unaZona);
	    when(unRegistroDeEstacionamientoApp.getVigencia()).thenReturn(true);
		
		// exercise
		unEstadoCaminando.driving(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,times(1)).finalizarSiCorrespondeYNotificar();
	}
	
	@Test
	void driving_CuandoEstaElEstacionamiento_SeEncuentraVigente_YNOEnLaMismaZona_SoloSeteaElNuevoEstadoTEST() { // true - true - false
		// setup
	    when(opcionalEstacionamiento.isPresent()).thenReturn(true);
	    when(opcionalEstacionamiento.get()).thenReturn(unRegistroDeEstacionamientoApp);
	    when(unRegistroDeEstacionamientoApp.getVigencia()).thenReturn(true);
	    when(unRegistroDeEstacionamientoApp.getZonaDeEstacionamiento()).thenReturn(otraZona);
		
		// exercise
		unEstadoCaminando.driving(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,never()).finalizarSiCorrespondeYNotificar();
	}
	
	@Test
	void driving_CuandoEstaElEstacionamiento_YNOSeEncuentraVigente_SoloSeteaElNuevoEstadoTEST() { // true - false - any
		// setup
	    when(opcionalEstacionamiento.isPresent()).thenReturn(true);
	    when(opcionalEstacionamiento.get()).thenReturn(unRegistroDeEstacionamientoApp);
	    when(unRegistroDeEstacionamientoApp.getVigencia()).thenReturn(false);
		
		// exercise
		unEstadoCaminando.driving(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,never()).finalizarSiCorrespondeYNotificar();
	}
	
	@Test
	void driving_CuandoNoEstaElEstacionamiento_SoloSeteaElNuevoEstadoTEST() { // false - any - any
		// setup
	    when(opcionalEstacionamiento.isPresent()).thenReturn(false);
		
		// exercise
		unEstadoCaminando.driving(unaApp, unSem);
		
		// verify
		this.verificacionesInvariantes();
		verify(unaApp,never()).finalizarSiCorrespondeYNotificar();
	}
}
