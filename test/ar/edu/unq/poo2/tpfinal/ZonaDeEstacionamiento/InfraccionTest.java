package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class InfraccionTest {
	
	Infraccion infraccion;
	
	ZonaDeEstacionamiento zonaA = mock(ZonaDeEstacionamiento.class);
	Inspector inspector = mock(Inspector.class);
	
	String patente = "AAA000";
	
	Infraccion infraccionCompleta = new Infraccion(LocalTime.now(),zonaA,inspector,"AAA000");
	
	@Test
	public void testearPatenteEnInfraccion() {
		assertEquals(infraccion.patente,patente);
	}

}