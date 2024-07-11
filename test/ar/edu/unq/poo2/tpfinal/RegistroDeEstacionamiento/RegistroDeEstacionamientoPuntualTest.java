package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

import static org.mockito.Mockito.*;

public class RegistroDeEstacionamientoPuntualTest {

	@Test
	public void testConstructoresYGettersDeRegistroDeEstacionamientoPuntual() {
		// Setup
		String patentePuntual = "MAX000";
		int horasEstacionamiento = 2;
		LocalDateTime unaFechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class);
		
		// Exercise
		RegistroDeEstacionamientoPuntual regPuntual = new RegistroDeEstacionamientoPuntual(patentePuntual, horasEstacionamiento, unaFechaYHora, zonaA);
		
		// Verify
		assertEquals(regPuntual.getPatente(), patentePuntual);
		assertEquals(regPuntual.getHorasEstacionamiento(), horasEstacionamiento);
		assertEquals(regPuntual.getFechaYHoraDeInicio(), unaFechaYHora);
		assertEquals(regPuntual.getZonaDeEstacionamiento(), zonaA);
	}
}
