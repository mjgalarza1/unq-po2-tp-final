package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistroDeCompraTest {
	
	int unNumControl;
	double unMonto;
	double otroMonto;
	int numCel;
	LocalDateTime fechaYHora;
	PuntoDeVenta unPuntoDeVenta;
	
	@BeforeEach
	public void setUp() {
		// Setup
		unNumControl = 001;
		unMonto = 200.0;
		otroMonto = 550.0;
		numCel = 1165982468;
		fechaYHora = LocalDateTime.of(2024, 5, 5, 15, 00); // 5 de Mayo de 2024 a las 15:00hs
		unPuntoDeVenta = mock(PuntoDeVenta.class);
	}
	
	@Test
	public void testConstructorYGettersDeRegistroDeRecarga() {
		// Setup - Exercise
		RegistroDeRecarga regRecarga = new RegistroDeRecarga(unNumControl, unMonto, numCel, fechaYHora, unPuntoDeVenta);
		
		// Verify
		assertEquals(regRecarga.getNumeroDeControl(), unNumControl);
		assertEquals(regRecarga.getMonto(), unMonto);
		assertEquals(regRecarga.getNumeroDeCelular(), numCel);
		assertEquals(regRecarga.getFechaYHora(), fechaYHora);
		assertEquals(regRecarga.getPuntoDeVenta(), unPuntoDeVenta);
	}
	
	@Test
	public void testConstructorYGettersDeRegistroDeCompraPuntual() {
		// Setup - Exercise
		int horasEstacionamiento = 2;
		RegistroDeCompraPuntual regCompraPuntual = new RegistroDeCompraPuntual(unNumControl, horasEstacionamiento, fechaYHora, unPuntoDeVenta);
		
		// Verify
		assertEquals(regCompraPuntual.getNumeroDeControl(), unNumControl);
		assertEquals(regCompraPuntual.getCantidadDeHorasCompradas(), horasEstacionamiento);
		assertEquals(regCompraPuntual.getFechaYHora(), fechaYHora);
		assertEquals(regCompraPuntual.getPuntoDeVenta(), unPuntoDeVenta);
	}
	
}
