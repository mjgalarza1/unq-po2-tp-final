package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistroDeRecargaTest {
	
	@Test
	public void testConstructorYGettersDeRegistroDeRecarga() {
		// Setup
		int unNumControl = 001;
		double unMonto = 200.0;
		int numCel = 1165982468;
		LocalDateTime fechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		PuntoDeVenta unPuntoDeVenta = mock(PuntoDeVenta.class);
		
		// Exercise
		RegistroDeRecarga regRecarga = new RegistroDeRecarga(unNumControl, unMonto, numCel, fechaYHora, unPuntoDeVenta);
		
		// Verify
		assertEquals(regRecarga.getNumeroDeControl(), unNumControl);
		assertEquals(regRecarga.getMonto(), unMonto);
		assertEquals(regRecarga.getNumeroDeCelular(), numCel);
		assertEquals(regRecarga.getFechaYHora(), fechaYHora);
		assertEquals(regRecarga.getPuntoDeVenta(), unPuntoDeVenta);
	}
	
}
