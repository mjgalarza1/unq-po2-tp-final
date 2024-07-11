package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoPuntual;

class ZonaDeEstacionamientoTest {

	private ZonaDeEstacionamiento unaZona;
	private PuntoDeVenta unPuntoDeVenta;
	private PuntoDeVenta otroPuntoDeVenta;
	private List<PuntoDeVenta> unosPuntosDeVenta;
	private Inspector unInspector;
	
	@BeforeEach
	void setUp() throws Exception {
		unPuntoDeVenta = mock(PuntoDeVenta.class);
		otroPuntoDeVenta = mock(PuntoDeVenta.class);
		unosPuntosDeVenta = Arrays.asList(unPuntoDeVenta, otroPuntoDeVenta);
		unaZona = new ZonaDeEstacionamiento(unosPuntosDeVenta, unInspector, "Zona A");
	}

	@Test
	void getNombreTEST() {
		assertEquals(unaZona.getNombre(), "Zona A");
	}
	
	@Test
	void getInspectorTEST() {
		assertEquals(unaZona.getInspector(), unInspector);
	}
	
	@Test
	void getPuntosDeVentaTEST() {
		assertEquals(unaZona.getPuntosDeVenta(), unosPuntosDeVenta);
	}
	
	@Test
	void registrarVehiculoTEST() {
		// Setup
		ArgumentCaptor<RegistroDeEstacionamientoPuntual> registroCaptor = ArgumentCaptor.forClass(RegistroDeEstacionamientoPuntual.class);
		
		// Exercise
		unaZona.registrarVehiculo("abc 123", 4, unPuntoDeVenta);
		
		// Verify
		verify(unPuntoDeVenta).registrarVehiculo(registroCaptor.capture());
	}
}
