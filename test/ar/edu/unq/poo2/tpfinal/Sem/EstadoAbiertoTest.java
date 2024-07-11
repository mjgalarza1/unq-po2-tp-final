package ar.edu.unq.poo2.tpfinal.Sem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.RegistroDeEstacionamientoPuntual;

class EstadoAbiertoTest {

	private EstadoAbierto unEstadoAbierto;
	private RegistroDeEstacionamientoApp unEstacionamientoApp;
	private SEM unSem;
	private Clock unReloj;
	private RegistroDeEstacionamientoPuntual unEstacionamientoPuntual;
	private LocalDate fechaCustom;
	private LocalTime horarioCustom;

	@BeforeEach
	void setUp() throws Exception {
		unEstadoAbierto = new EstadoAbierto();
		unEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		unEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		unSem = mock(SEM.class);
		unReloj = mock(Clock.class);
		
		
		when(unEstacionamientoApp.getNumeroDeCelular()).thenReturn(unNumCel);
		when(unEstacionamientoApp.getPatente()).thenReturn("MAX000");
		when(unEstacionamientoApp.getVigencia()).thenReturn(true);
		when(unEstacionamientoApp.esDeApp()).thenReturn(true);
		when(unEstacionamientoApp.getZonaDeEstacionamiento()).thenReturn(zonaA);
		when(unEstacionamientoPuntual.getPatente()).thenReturn("MAX001");
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(true);
		when(unEstacionamientoPuntual.esDeApp()).thenReturn(false);
		when(unEstacionamientoPuntual.getZonaDeEstacionamiento()).thenReturn(zonaB);
		
		// DOC (reloj custom para testear)
		fechaCustom = LocalDate.now();
		horarioCustom = LocalTime.of(15, 0);
		ZonedDateTime NOW = ZonedDateTime.of(fechaCustom, horarioCustom, ZoneId.ofOffset("GMT", ZoneOffset.ofHours(-3)));
		Clock relojCustom = mock(Clock.class);
		
		when(relojCustom.getZone()).thenReturn(NOW.getZone());
		when(relojCustom.instant()).thenReturn(NOW.toInstant());
	}
	
	@Test
	void registrarEstacionamientoPuntualTEST() {
		
		unEstadoAbierto.registrarEstacionamientoPuntual(unEstacionamientoPuntual, unSem);
		
		fail("Not yet implemented");
	}
	
	@Test
	void registrarEstacionamientoPorAppTEST() {
		
		unEstadoAbierto.finalizarEstacionamiento("abc 123", unSem);
		
		fail("Not yet implemented");
	}
	
	@Test
	void finalizarEstacionamientoTEST() {
		
		unEstadoAbierto.finalizarEstacionamiento("abc 123", unSem);
		
		fail("Not yet implemented");
	}

}
