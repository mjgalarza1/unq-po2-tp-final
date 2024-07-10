package ar.edu.unq.poo2.tpfinal.AppSem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.EstadoDeMovimiento.*;
import ar.edu.unq.poo2.tpfinal.Modo.*;
import ar.edu.unq.poo2.tpfinal.Notificacion.*;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

class AppSemTEST {
	private INotificable unNotificable;
	private GPS unGPS;
	private Modo unModoAutomatico;
	private Modo unModoManual;
	private EstadoDeMovimiento unEstadoManejando;
	private EstadoDeMovimiento unEstadoCaminando;
	private SEM unSem;
	private AppSem unaApp;
	private ZonaDeEstacionamiento unaZona;
	
	@BeforeEach
	void setUp() throws Exception{
		unNotificable = mock(INotificable.class);
		unGPS = mock(GPS.class);
		unModoAutomatico = mock(ModoAutomatico.class);
		unModoManual = mock(ModoManual.class);
		unEstadoManejando = mock(EstadoManejando.class);
		unEstadoCaminando = mock(EstadoCaminando.class);
		unSem = mock(SEM.class);
		unaApp = new AppSem(unNotificable, "abc 123", 1166667777, true, unGPS, unEstadoCaminando, unModoAutomatico, unSem);
		unaZona = mock(ZonaDeEstacionamiento.class);
	}
	
	@Test
	void getNotificableTEST() {
		assertEquals(unaApp.getNotificable(), unNotificable);
	}
	
	@Test
	void getPatenteTEST() {
		assertEquals(unaApp.getPatente(), "abc 123");
	}
	
	@Test
	void getNumCelTEST() {
		assertEquals(unaApp.getNumCel(), 1166667777);
	}
	
	@Test
	void getSemTEST() {
		assertEquals(unaApp.getSem(), unSem);
	}
	
	@Test
	void getGpsTEST() {
		assertEquals(unaApp.getGps(), unGPS);
	}

	
	@Test
	void getModoTEST() {
		assertEquals(unaApp.getModo(), unModoAutomatico);
	}
	
	@Test
	void getZonaActualTEST() {
		when(unGPS.getZonaActual()).thenReturn(unaZona);
		assertEquals(unaApp.getZonaActual(), unaZona);
	}
	
	@Test
	void getEstadoDeMovimientoTEST() {
		assertEquals(unaApp.getEstadoDeMovimiento(), unEstadoCaminando);
	}
	
	@Test
	void getDeteccionMovActivaTEST() {
		assertEquals(unaApp.getDeteccionMovActiva(), true);
	}
	
	@Test
	void setModoTEST() {
		unaApp.setModo(unModoManual);
		assertEquals(unaApp.getModo(), unModoManual);
	}
	
	@Test
	void setEstadoTEST() {
		assertEquals(unaApp.getEstadoDeMovimiento(), unEstadoCaminando);
		unaApp.setEstadoDeMovimiento(unEstadoManejando);
		assertEquals(unaApp.getEstadoDeMovimiento(), unEstadoManejando);
	}
	
	@Test
	void consultarSaldoTEST() {
		when(unSem.getSaldoDe(1166667777)).thenReturn("Su saldo es 0 pesos");
		String saldo = unaApp.consultarSaldo();
		verify(unSem, times(1)).getSaldoDe(1166667777);
		assertEquals("Su saldo es 0 pesos", saldo);
	}
	
	@Test
	void notificarTEST() {
		Notificacion unaNotificacion = mock(Notificacion.class);
		unaApp.notificar(unaNotificacion);
		verify(unNotificable, times(1)).notificar(unaNotificacion);
	}
	
	@Test
	void registrarVehiculoTEST(){
		//setup
		ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class);
        Notificacion unaNotificacion = mock(Notificacion.class);
        ArgumentCaptor<RegistroDeEstacionamientoApp> registroCaptor = ArgumentCaptor.forClass(RegistroDeEstacionamientoApp.class);
		when(unSem.registrarEstacionamiento(registroCaptor.capture())).thenReturn(unaNotificacion);
       
		// exercise
		Notificacion otraNotificacion = unaApp.registrarVehiculo(zonaA);
		
		// verify
		assertEquals(unaNotificacion, otraNotificacion);
		verify(unSem, times(1)).registrarEstacionamiento(registroCaptor.capture());
		verify(unNotificable, times(1)).notificar(unaNotificacion);
	}
	
	@Test
	void finalizarEstacionamientoTEST() {
		unaApp.finalizarEstacionamiento();
		verify(unSem,times(1)).finalizarEstacionamiento(unaApp.getPatente());
	}
	
	
	@Test
	void finalizarSiCorrespondeYNotificarTEST(){
		// exercise
		unaApp.finalizarSiCorrespondeYNotificar();
		
		// verify
		verify(unModoAutomatico, times(1)).finalizarSiCorrespondeYNotificar(unaApp);
	}
	
	@Test
	void registrarSiCorrespondeYNotificarTEST() {
		// setup
		when(unGPS.getZonaActual()).thenReturn(unaZona);
		
		// exercise
		unaApp.registrarSiCorrespondeYNotificar();
		
		// verify
		verify(unModoAutomatico, times(1)).registrarSiCorrespondeYNotificar(unaApp);
	}
	
	
	
	@Test
	void finalizarEstacionamientoConNotificacionExtraTEST() {
		// setup
		Notificacion unaNotificacionExtra = mock(Notificacion.class);
		
		// exercise
		unaApp.finalizarEstacionamientoConNotificacionExtra(unaNotificacionExtra);
		
		// verify
		verify(unSem,times(1)).finalizarEstacionamiento(unaApp.getPatente());
		verify(unaApp.getNotificable(), times(1)).notificar(unaNotificacionExtra);
	}
	
	
	@Test
	void registrarEstacionamientoConNotificacionExtra_CuandoNoSePuedeRegistrarTEST() {
		//setup
		ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class); // dummy
		Notificacion unaNotificacion = mock(Notificacion.class); // dummy
		Notificacion unaNotificacionExtra = mock(Notificacion.class); // dummy
		ArgumentCaptor<RegistroDeEstacionamientoApp> registroCaptor = ArgumentCaptor.forClass(RegistroDeEstacionamientoApp.class);
		when(unSem.registrarEstacionamiento(registroCaptor.capture())).thenReturn(unaNotificacion);
		when(unaNotificacion.esNotificacionDeInicioExitoso()).thenReturn(false);
		       
		// exercise
		unaApp.registrarVehiculoConNotificacionExtra(unaNotificacionExtra);
				
		// verify
		verify(unSem, times(1)).registrarEstacionamiento(registroCaptor.capture());
		verify(unaNotificacion, times(1)).esNotificacionDeInicioExitoso();
		verify(unNotificable, never()).notificar(unaNotificacionExtra);
	}
	
	@Test
	void registrarEstacionamientoConNotificacionExtra_CuandoSePuedeRegistrarTEST() {
		//setup
		ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class); // dummy
		Notificacion unaNotificacion = mock(Notificacion.class); // dummy
		Notificacion unaNotificacionExtra = mock(Notificacion.class); // dummy
		ArgumentCaptor<RegistroDeEstacionamientoApp> registroCaptor = ArgumentCaptor.forClass(RegistroDeEstacionamientoApp.class);
		when(unSem.registrarEstacionamiento(registroCaptor.capture())).thenReturn(unaNotificacion);
		when(unaNotificacion.esNotificacionDeInicioExitoso()).thenReturn(true);
		       
		// exercise
		unaApp.registrarVehiculoConNotificacionExtra(unaNotificacionExtra);
				
		// verify
		verify(unSem, times(1)).registrarEstacionamiento(registroCaptor.capture());
		verify(unaNotificacion, times(1)).esNotificacionDeInicioExitoso();
		verify(unNotificable, times(1)).notificar(unaNotificacionExtra);
	}
	
	@Test
	void activarDeteccionDesplazamientoTEST() {
		unaApp.desactivarDeteccionDesplazamiento();
		unaApp.activarDeteccionDesplazamiento();
		assertEquals(unaApp.getDeteccionMovActiva(), true);
	}
	
	@Test
	void desactivarDeteccionDesplazamientoTEST() {
		unaApp.activarDeteccionDesplazamiento();
		unaApp.desactivarDeteccionDesplazamiento();
		assertEquals(unaApp.getDeteccionMovActiva(), false);
	}
	
	@Test
	void drivingTEST() {
		unaApp.driving();
		verify(unaApp.getEstadoDeMovimiento(), times(1)).driving(true, unaApp, unSem);
	}
	
	@Test
	void walkingTEST() {
		unaApp.walking();
		verify(unaApp.getEstadoDeMovimiento(), times(1)).walking(true, unaApp, unSem);
	}
}
