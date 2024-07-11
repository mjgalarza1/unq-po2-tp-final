package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class InfraccionTest {
	
	ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class);
	Inspector inspector = mock(Inspector.class);
	
	String patente = "AAA000";
	LocalTime unaFechaYHora = LocalTime.now();
	Infraccion infraccionCompleta = new Infraccion(unaFechaYHora,zonaA,inspector,"AAA000");
	
	@Test
	public void testearPatenteEnInfraccion() {
		assertEquals(infraccionCompleta.getPatente(),patente);
	}
	
	@Test
	public void testGettersDeInfraccion() {
		assertEquals(infraccionCompleta.getFechaYHora(),unaFechaYHora);
		assertEquals(infraccionCompleta.getInspector(),inspector);
		assertEquals(infraccionCompleta.getZonaDeEstacionamiento(),zonaA);
	}

}