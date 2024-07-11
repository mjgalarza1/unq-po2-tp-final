package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistroDeCompraPuntualTest {
	
	@Test
	public void testConstructorYGettersDeRegistroDeCompraPuntual() {
		// Setup
		int unNumControl = 001;
		LocalDateTime fechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		PuntoDeVenta unPuntoDeVenta = mock(PuntoDeVenta.class);
		int horasEstacionamiento = 2;
		
		// Exercise
		RegistroDeCompraPuntual regCompraPuntual = new RegistroDeCompraPuntual(unNumControl, horasEstacionamiento, fechaYHora, unPuntoDeVenta);
		
		// Verify
		assertEquals(regCompraPuntual.getNumeroDeControl(), unNumControl);
		assertEquals(regCompraPuntual.getCantidadDeHorasCompradas(), horasEstacionamiento);
		assertEquals(regCompraPuntual.getFechaYHora(), fechaYHora);
		assertEquals(regCompraPuntual.getPuntoDeVenta(), unPuntoDeVenta);
	}
	
}
