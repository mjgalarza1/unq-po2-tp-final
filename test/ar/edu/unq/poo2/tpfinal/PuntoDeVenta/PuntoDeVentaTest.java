package ar.edu.unq.poo2.tpfinal.PuntoDeVenta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.RegistroDeCompra.RegistroDeCompraPuntual;
import ar.edu.unq.poo2.tpfinal.RegistroDeCompra.RegistroDeRecarga;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoPuntual;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;

class PuntoDeVentaTest {

	private PuntoDeVenta unPuntoDeVenta;
	private SEM unSem;
	private RegistroDeEstacionamientoPuntual unRegistroDeEstacionamientoPuntual;
	
	@BeforeEach
	void setUp() throws Exception {
		unSem = mock(SEM.class);
		unRegistroDeEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		unPuntoDeVenta = new PuntoDeVenta(unSem, "Punto A");
	}

	@Test
	void getNombreTEST() {
		assertEquals(unPuntoDeVenta.getNombre(), "Punto A");
	}

	@Test
	void getSemTEST() {
		assertEquals(unPuntoDeVenta.getSem(), unSem);
	}
	
	@Test
	void generarNumeroDeRegistroTEST() {
		// Exercise
		int num = unPuntoDeVenta.generarNumeroDeRegistro();
		
		// Verify
		assertTrue(num >= 0 && num <= 10000);
	}
	
	@Test
	void cargarCreditoSEMTEST() {
		// Setup
		ArgumentCaptor<RegistroDeRecarga> registroCaptor = ArgumentCaptor.forClass(RegistroDeRecarga.class);
		
		// Exercise
		unPuntoDeVenta.cargarCreditoSEM(100, 1166667777);
		
		// Verify
		verify(unSem).cargarCredito(100, 1166667777);
		verify(unSem).registrarCompra(registroCaptor.capture());
	}
	
	
	@Test
	void registrarVehiculoTEST() {
		// Setup
		when(unRegistroDeEstacionamientoPuntual.getHorasEstacionamiento()).thenReturn(10);
		ArgumentCaptor<RegistroDeCompraPuntual> registroCaptor = ArgumentCaptor.forClass(RegistroDeCompraPuntual.class);
		
		// Exercise
		unPuntoDeVenta.registrarVehiculo(unRegistroDeEstacionamientoPuntual);
		
		// Verify
		verify(unSem).registrarEstacionamiento(unRegistroDeEstacionamientoPuntual);
		verify(unSem).registrarCompra(registroCaptor.capture());
	}
}
	
