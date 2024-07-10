package ar.edu.unq.poo2.tpfinal.Celular;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class CelularTest {

	Celular celu = new Celular(115544);
	
	@Test
	public void testDeSaldoInicial() {
		assertEquals(celu.credito,0);
	}
	
	@Test
	public void testDeNumDeCelular() {
		int numNuevo = 115544;
		assertEquals(celu.getNumCel(),numNuevo);
	}
	
	@Test
	public void testDeIncrementarSaldo() {
		double saldoNuevo = 100.5;
		celu.incrementarCredito(100.5);
		assertEquals(celu.credito,saldoNuevo);
	}
	
	@Test
	public void testDeRestarSaldo() {
		double saldoNuevo = 50.5;
		celu.incrementarCredito(100.5);
		celu.decrementarCredito(50);
		assertEquals(celu.credito,saldoNuevo);
	}
	
	@Test
	public void testearSaldoNegativo() {
		double saldoNuevo = -50;
		celu.decrementarCredito(50);
		assertEquals(celu.credito,saldoNuevo);
	}
	
}
