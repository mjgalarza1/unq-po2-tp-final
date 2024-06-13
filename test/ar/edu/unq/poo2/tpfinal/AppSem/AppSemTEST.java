package ar.edu.unq.poo2.tpfinal.AppSem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.Modo.*;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

class AppSemTEST {
	IPantalla unaPantalla;
	GPS unGPS;
	Modo unModoAutomatico;
	ModoManual unModoManual;
	SEM unSem;
	AppSem unaApp;
	
	@BeforeEach
	void setUp() throws Exception{
		unaPantalla = mock(IPantalla.class);
		unGPS = mock(GPS.class);
		unModoAutomatico = mock(ModoAutomatico.class);
		unModoManual = mock(ModoManual.class);
		unSem = mock(SEM.class);
		unaApp = new AppSem(unaPantalla, "abc 123", 1166667777, true, true, unGPS, unModoAutomatico, unSem);
	}
	
	@Test
	void getPantallaTEST() {
		assertEquals(unaApp.getPantalla(), unaPantalla);
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
	void getModoTEST() {
		assertEquals(unaApp.getModo(), unModoAutomatico);
	}
	
	@Test
	void getEstaManejandoTEST() {
		assertEquals(unaApp.getEstaManejando(), true);
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
	void setEstaManejandoTEST() {
		unaApp.setEstaManejando(false);
		assertEquals(unaApp.getEstaManejando(), false);
		unaApp.setEstaManejando(true);
		assertEquals(unaApp.getEstaManejando(), true);
	}
	
	@Test
	void consultarSaldoTEST() {
		when(unSem.getSaldoDe(1166667777)).thenReturn("Su saldo es 0 pesos");
		String saldo = unaApp.consultarSaldo();
		verify(unSem, times(1)).getSaldoDe(1166667777);
		assertEquals("Su saldo es 0 pesos", saldo);
	}
	
	@Test
	void registrarVehiculoTEST() {
		
		ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class);
		unaApp.registrarVehiculo(zonaA);
		verify(unaApp.getModo(),times(1))
		.registrarVehiculo(unaApp.getPatente(), zonaA, unaApp.getSem(), unaApp.getNumCel(), unaApp.getPantalla());
	}
	
	@Test
	void finalizarEstacionamientoTEST() {
		unaApp.finalizarEstacionamiento();
		verify(unaApp.getModo(),times(1))
		.finalizarEstacionamientoPara(unaApp.getPatente(), unaApp.getSem());
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
		boolean estabaManejando = unaApp.getEstaManejando();
		unaApp.driving();
		verify(unaApp.getModo(), times(1)).driving(estabaManejando, unaApp.getGps(), "abc 123", unSem);
	}
	
	@Test
	void drivingCuandoNoEstaActivadaLaDeteccionDeDesplazamientoNoHaceNadaTEST() {
		boolean estabaManejando = unaApp.getEstaManejando();
		unaApp.desactivarDeteccionDesplazamiento();
		unaApp.driving();
		verify(unaApp.getModo(), never()).driving(estabaManejando, unaApp.getGps(), "abc 123", unSem);
	}
	
	@Test
	void walkingTEST() {
		boolean estabaManejando = unaApp.getEstaManejando();
		unaApp.walking();
		verify(unaApp.getModo(), times(1)).walking(estabaManejando, "abc 123", unSem);
	}
	
	@Test
	void walkingCuandoNoEstaActivadaLaDeteccionDeDesplazamientoNoHaceNadaTEST() {
		boolean estabaManejando = unaApp.getEstaManejando();
		unaApp.desactivarDeteccionDesplazamiento();
		unaApp.walking();
		verify(unaApp.getModo(), never()).walking(estabaManejando, "abc 123", unSem);
	}
	
}
