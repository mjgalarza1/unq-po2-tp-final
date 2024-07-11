package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

import static org.mockito.Mockito.*;

public class RegistroDeEstacionamientoAppTest {

	@Test
	public void testConstructoresYGettersDeRegistroDeEstacionamientoApp() {
		// Setup
		String patenteApp = "MAX000";
		int numCel = 1154876235;
		LocalDateTime unaFechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class);
		
		// Exercise
		RegistroDeEstacionamientoApp regApp = new RegistroDeEstacionamientoApp(patenteApp, numCel, unaFechaYHora, zonaA);
		
		// Verify
		assertEquals(regApp.getPatente(), patenteApp);
		assertEquals(regApp.getNumeroDeCelular(), numCel);
		assertEquals(regApp.getFechaYHoraDeInicio(), unaFechaYHora);
		assertEquals(regApp.getZonaDeEstacionamiento(), zonaA);
	}
}
