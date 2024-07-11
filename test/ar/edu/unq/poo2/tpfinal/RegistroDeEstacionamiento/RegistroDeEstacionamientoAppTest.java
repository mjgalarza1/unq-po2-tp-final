package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

import static org.mockito.Mockito.*;

public class RegistroDeEstacionamientoAppTest {
	
	String patenteApp;
	int numCel;
	LocalDateTime unaFechaYHora;
	ZonaDeEstacionamiento zonaA;
	
	@BeforeEach
	public void setUp() {
		patenteApp = "MAX000";
		numCel = 1154876235;
		unaFechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		zonaA = mock(ZonaDeEstacionamiento.class);
	}

	@Test
	public void testConstructoresYGettersDeRegistroDeEstacionamientoApp() {
		// Exercise
		RegistroDeEstacionamientoApp regApp = new RegistroDeEstacionamientoApp(patenteApp, numCel, unaFechaYHora, zonaA);
		
		// Verify
		assertEquals(regApp.getPatente(), patenteApp);
		assertEquals(regApp.getNumeroDeCelular(), numCel);
		assertEquals(regApp.getFechaYHoraDeInicio(), unaFechaYHora);
		assertEquals(regApp.getZonaDeEstacionamiento(), zonaA);
		assertTrue(regApp.getVigencia());
	}
	
	@Test
	public void testSetVigencia() {
		// Setup
		RegistroDeEstacionamientoApp regApp = new RegistroDeEstacionamientoApp(patenteApp, numCel, unaFechaYHora, zonaA);
		
		// Exercise
		regApp.setVigencia(false);

		// Verify
		assertFalse(regApp.getVigencia());
	}
	
	@Test
	public void testSerCobradoSiCorrespondePor() {
		// Setup
		RegistroDeEstacionamientoApp regApp = new RegistroDeEstacionamientoApp(patenteApp, numCel, unaFechaYHora, zonaA);
		SEM unSem = mock(SEM.class);
		
		// Exercise
		regApp.serCobradoSiCorrespondePor(unSem);

		// Verify
		verify(unSem, times(1)).cobrarleA(regApp);
	}
	
	@Test
	public void testNotificarFinalizacionPara() {
		// Setup
		RegistroDeEstacionamientoApp regApp = new RegistroDeEstacionamientoApp(patenteApp, numCel, unaFechaYHora, zonaA);
		SEM unSem = mock(SEM.class);
		
		// Exercise
		regApp.notificarFinalizacionPara(unSem);

		// Verify
		verify(unSem, times(1)).notificacionDeFinalizacionPorAppPara(regApp);
	}
}
