package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class InspectorTest {
	
	Inspector inspector;
	
	@Test
	public void testearDatosDeInspector() {
		Inspector inspector = new Inspector("Carlos");
		
		String nombre = "Carlos";
		
		assertEquals(inspector.nombre,nombre);
	}
	
}
