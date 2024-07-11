package ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

import static org.mockito.Mockito.*;

public class RegistroDeEstacionamientoPuntualTest {
	String patentePuntual;
	int horasEstacionamiento;
	LocalDateTime unaFechaYHora; // 5 de Mayo de 2024 a las 15:00hs
	ZonaDeEstacionamiento zonaA;
	
	@BeforeEach
	public void setUp() {
		patentePuntual = "MAX000";
		horasEstacionamiento = 2;
		unaFechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		zonaA = mock(ZonaDeEstacionamiento.class);
	}

	@Test
	public void testConstructoresYGettersDeRegistroDeEstacionamientoPuntual() {
		// Exercise
		RegistroDeEstacionamientoPuntual regPuntual = new RegistroDeEstacionamientoPuntual(patentePuntual, horasEstacionamiento, unaFechaYHora, zonaA);
		
		// Verify
		
		assertEquals(regPuntual.getPatente(), patentePuntual);
		assertEquals(regPuntual.getHorasEstacionamiento(), horasEstacionamiento);
		assertEquals(regPuntual.getFechaYHoraDeInicio(), unaFechaYHora);
		assertEquals(regPuntual.getZonaDeEstacionamiento(), zonaA);
		assertTrue(regPuntual.getVigencia());
		assertFalse(regPuntual.esDeApp());
	}
	
	@Test
	public void testSetVigencia() {
		// Setup
		RegistroDeEstacionamientoPuntual regPuntual = new RegistroDeEstacionamientoPuntual(patentePuntual, horasEstacionamiento, unaFechaYHora, zonaA);
		
		// Exercise
		regPuntual.setVigencia(false);

		// Verify
		assertFalse(regPuntual.getVigencia());
	}
}
