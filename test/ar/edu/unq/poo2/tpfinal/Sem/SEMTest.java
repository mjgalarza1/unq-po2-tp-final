package ar.edu.unq.poo2.tpfinal.Sem;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.poo2.tpfinal.EntidadObservadora.*;
import ar.edu.unq.poo2.tpfinal.Notificacion.*;

import static org.mockito.Mockito.*;

public class SEMTest {

	SEM sem;
	SEM sem24Horas;
	SEM semCerrado;
	SEM semRelojCustom; 
	ZonaDeEstacionamiento zonaA;
	ZonaDeEstacionamiento zonaB;
	ZonaDeEstacionamiento zonaC;
	List<ZonaDeEstacionamiento> zonasDeEstacionamiento;
	
	RegistroDeEstacionamientoApp unEstacionamientoApp;
	RegistroDeEstacionamientoPuntual unEstacionamientoPuntual;
	
	int unNumCel;
	LocalTime horarioCustom;
	LocalDate fechaCustom;
	
	String alertaInicioMsg;
	String alertaFinMsg;
	String notificacionFinMsg;
	String notificacionInicioExitosoMsg;
	
	@BeforeEach
	public void setUp() {
		// DOC (zonas)
		zonaA = mock(ZonaDeEstacionamiento.class);
		zonaB = mock(ZonaDeEstacionamiento.class);
		zonaC = mock(ZonaDeEstacionamiento.class);
		zonasDeEstacionamiento = new ArrayList<ZonaDeEstacionamiento>();
		zonasDeEstacionamiento.add(zonaA);
		zonasDeEstacionamiento.add(zonaB);
		zonasDeEstacionamiento.add(zonaC);
		
		
		// DOC (registros de estacionamiento)
		unEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		unEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		unNumCel = 1123456789;
		
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
		
		// MENSAJES DE ALGUNAS NOTIFICACIONES (para test)
		alertaInicioMsg = "AVISO: Su estacionamiento no fue registrado."
				+"\nDebe registrar el estacionamiento.";
		alertaFinMsg = "AVISO: Su estacionamiento aún está vigente."
				+"\nSi desea salir de la Zona de Estacionamiento, debe finalizar su estacionamiento.";
		notificacionFinMsg = "Su estacionamiento ha sido finalizado con éxito";
		notificacionInicioExitosoMsg = "Su estacionamiento ha sido registrado con éxito";
		
		// SUT
		sem = new SEM(zonasDeEstacionamiento);
		sem24Horas = new SEM((LocalTime.of(0, 0)), (LocalTime.of(23, 59, 59)), 40.0, zonasDeEstacionamiento);
		semRelojCustom = new SEM((LocalTime.of(7, 0)), (LocalTime.of(20, 0)), 40.0, zonasDeEstacionamiento, relojCustom);
		semCerrado = new SEM(LocalTime.of(5, 26, 59), LocalTime.of(5, 27), 40.0, zonasDeEstacionamiento);
	}
	
	// ------------------- TESTS DE LOS CONSTRUCTORES -------------------
	@Test
	public void testSemEstandarConZonasDeEstacionamientoYGetters() {	
		// Verify
		assertTrue(sem.getZonasDeEstacionamiento().contains(zonaA));
		assertTrue(sem.getZonasDeEstacionamiento().contains(zonaB));
		assertTrue(sem.getZonasDeEstacionamiento().contains(zonaC));
	}
	
	@Test
	public void testSemCustomConZonasDeEstacionamientoYGetters() {
		// Exercise
		SEM semCustom = new SEM((LocalTime.of(6, 30)), (LocalTime.of(22, 30)), 6000.0, zonasDeEstacionamiento);
		
		// Verify
		assertTrue(semCustom.getZonasDeEstacionamiento().contains(zonaA));
		assertTrue(semCustom.getZonasDeEstacionamiento().contains(zonaB));
		assertTrue(semCustom.getZonasDeEstacionamiento().contains(zonaC));
	}
	
	// ------------------- TESTS DE LOS GETTERS -------------------
	@Test
	public void testGetHoraAperturaYCierre() {
		LocalTime horaDeApertura = LocalTime.of(7, 0);
		LocalTime horaDeCierre = LocalTime.of(20, 0);
		assertEquals(sem.getHoraApertura(), horaDeApertura);
		assertEquals(sem.getHoraCierre(), horaDeCierre);
	}
	
	@Test
	public void testGetPrecioPorHora() {
		assertEquals(sem.getPrecioPorHora(), 40.0);
	}
	
	@Test
	public void testGetHoraMaximaPara() {
		// Verify
		assertEquals(sem.getHoraMaximaPara(40.0, LocalTime.of(19, 0)), LocalTime.of(20, 0)); // Solo una hora
		assertEquals(sem.getHoraMaximaPara(80.0, LocalTime.of(10, 0)), LocalTime.of(12, 0)); // Dos horas
		assertEquals(sem.getHoraMaximaPara(80.0, LocalTime.of(19, 0)), LocalTime.of(20, 0)); // A pesar del monto ser 80, el sem cierra a las 20hs, siendo la horaMaxima solo una hora.
	}
	
	// ------------------- TESTS DE SALDO y CARGA DE SALDO CELULAR -------------------
	@Test
	public void testCargaDeSaldoExistoso() {
		// Setup
		int unNumCel = 1187661111;
		int otroNumCel = 1154981654;
		double unCredito = 200.0;
		double otroCredito = 500.0;
		
		// Exercise
		sem.cargarCredito(unCredito, unNumCel);
		sem.cargarCredito(otroCredito, otroNumCel);
		
		// Verify
		assertTrue(sem.getCelularesConCredito().containsKey(unNumCel));
		assertTrue(sem.getCelularesConCredito().containsKey(otroNumCel));
		assertEquals(sem.getCelularesConCredito().get(unNumCel), unCredito);
		assertEquals(sem.getCelularesConCredito().get(otroNumCel), otroCredito);
	}
	
	@Test
	public void test_deIntentarCargarUnCelularQueYaSeEncuentreGuardadoEnElSistema_seRegistraLaSumaEntreElMontoOriginalYElMontoACargar() {
		// Setup
		int unNumCel = 1187661111;
		double montoOriginal = 200.0;
		double montoACargar = 200.0;
		sem.cargarCredito(montoOriginal, unNumCel);
		
		// Exercise
		sem.cargarCredito(montoACargar, unNumCel);
		
		// Verify
		assertTrue(sem.getCelularesConCredito().containsKey(unNumCel));
		assertEquals(sem.getCelularesConCredito().get(unNumCel), (montoOriginal + montoACargar));
	}
	
	@Test
	public void testGetSaldoDe() throws Exception {
		// Setup
		int unNumCel = 1187661111;
		int otroNumCel = 1154981654;
		double unCredito = 200.0;
		double otroCredito = 500.0;
		
		// Exercise
		sem.cargarCredito(unCredito, unNumCel);
		sem.cargarCredito(otroCredito, otroNumCel);
		
		// Verify
		assertEquals(sem.getSaldoDe(unNumCel), unCredito);
		assertEquals(sem.getSaldoDe(otroNumCel), otroCredito);
	}	
	
	@Test
	public void testGetSaldoDeConError() throws Exception {
		// Setup
		int unNumeroNoRegistrado = 567843126;
		
		// Verify
		assertThrows(Exception.class, () -> sem.getSaldoDe(unNumeroNoRegistrado));
	}	
	
	@Test
	public void testGetCelularesConCredito() {
		// Setup
		int unNumCel = 1187661111;
		int otroNumCel = 1154981654;
		double unCredito = 200.0;
		double otroCredito = 500.0;
		
		// Exercise
		sem.cargarCredito(unCredito, unNumCel);
		sem.cargarCredito(otroCredito, otroNumCel);
		
		// Verify
		assertEquals(sem.getCelularesConCredito().size(), 2);
		assertTrue(sem.getCelularesConCredito().containsKey(unNumCel));
		assertTrue(sem.getCelularesConCredito().containsKey(otroNumCel));
		assertEquals(sem.getCelularesConCredito().get(unNumCel), unCredito);
		assertEquals(sem.getCelularesConCredito().get(otroNumCel), otroCredito);
	}	
	
	// ------------------- TEST SERVICIO ABIERTO o CERRADO -------------------
	@Test
	public void testSemAbiertoOCerrado() {
		// Verify
		assertTrue(sem24Horas.elServicioEstaAbierto());
		assertFalse(semCerrado.elServicioEstaAbierto());
	}
	
	// ------------------- TESTS DE LOS REGISTROS DE ESTACIONAMIENTO PUNTUAL -------------------
	@Test
	public void testRegistroDeEstacionamientoPuntualExitoso() {
		// Exercise
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		
		// Verify
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoPuntual));
		verify(unEstacionamientoPuntual, times(1)).getPatente();
		assertEquals(sem24Horas.getRegistrosDeEstacionamiento().get(0).getPatente(), unEstacionamientoPuntual.getPatente());
	}
	
	@Test
	public void testGetRegistrosDeEstacionamiento() {
		// Setup
		double unMonto = 500.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		when(unEstacionamientoPuntual.getPatente()).thenReturn("MAX007");
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertEquals(sem24Horas.getRegistrosDeEstacionamiento().size(), 2);
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoPuntual));
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
	}
	
	@Test
	public void testEsEstacionamientoVigente() {
		// Setup
		String patentePuntual = unEstacionamientoPuntual.getPatente();
		String patenteSinRegistrar = "UnaPatente";
		
		// Exercise
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		
		// Verify
		assertTrue(sem24Horas.esEstacionamientoVigente(patentePuntual));
		assertFalse(sem24Horas.esEstacionamientoVigente(patenteSinRegistrar));
	}
	
	@Test
	public void test_deIntentarRegistrarUnEstacionamientoPuntualDeUnaPatenteQueYaSeEncuentreGuardadaEnElSistema_esteNoSeGuarda() {
		// Setup
		RegistroDeEstacionamientoPuntual elMismoEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		when(unEstacionamientoPuntual.getPatente()).thenReturn("MAX000", "MAX000", "Este es el original");
		when(elMismoEstacionamientoPuntual.getPatente()).thenReturn("MAX000");
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		
		// Exercise
		sem24Horas.registrarEstacionamiento(elMismoEstacionamientoPuntual);
		
		// Verify
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoPuntual));
		verify(unEstacionamientoPuntual, atLeast(2)).getPatente();
		// Una vez para el registro, y la segunda por la búsqueda de estacionamiento vigente.
		assertEquals(sem24Horas.getRegistrosDeEstacionamiento().get(0).getPatente(), "Este es el original");
		assertFalse(sem24Horas.getRegistrosDeEstacionamiento().contains(elMismoEstacionamientoPuntual));
		verify(elMismoEstacionamientoPuntual, times(1)).getPatente();
		// Una única vez por la búsqueda de estacionamiento vigente.
	}
	
	// ------------------- TESTS DE LOS REGISTROS DE ESTACIONAMIENTO POR APP -------------------
	@Test
	public void testRegistroDeEstacionamientoPorAppExitoso() {
		// Setup
		double unMonto = 500.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		
		// Exercise
		Notificacion unaNotificacion = sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertTrue(sem24Horas.getCelularesConCredito().containsKey(unNumCel));
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		verify(unEstacionamientoApp, times(1)).getNumeroDeCelular();
		verify(unEstacionamientoApp, times(1)).getPatente();
		assertEquals(unaNotificacion.getMensaje(), notificacionInicioExitosoMsg);
	}
	
	@Test
	public void test_deRegistrarUnEstacionamientoPorAppDeUnaPatenteQueYaSeEncuentreGuardadaEnElSistema_esteNoSeGuarda() {
		// Setup
		double unMonto = 500.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		RegistroDeEstacionamientoApp elMismoEstacionamientoApp = mock(RegistroDeEstacionamientoApp.class);
		when(elMismoEstacionamientoApp.getNumeroDeCelular()).thenReturn(unNumCel);
		when(elMismoEstacionamientoApp.getPatente()).thenReturn("MAX000");
		when(elMismoEstacionamientoApp.getVigencia()).thenReturn(true);
		when(elMismoEstacionamientoApp.esDeApp()).thenReturn(true);
		
		Notificacion unaNotificacion = sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		// Exercise
		Notificacion otraNotificacion = sem24Horas.registrarEstacionamiento(elMismoEstacionamientoApp);
		
		// Verify
		assertTrue(sem24Horas.getCelularesConCredito().containsKey(unNumCel));
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		verify(unEstacionamientoApp, times(1)).getNumeroDeCelular();
		verify(unEstacionamientoApp, times(2)).getPatente();
		assertFalse(sem24Horas.getRegistrosDeEstacionamiento().contains(elMismoEstacionamientoApp));
		verify(elMismoEstacionamientoApp, never()).getNumeroDeCelular();
		// Al ya existir el estacionamiento, "registrarEstacionamiento" nunca llega a delegar a
		// "registrarEstacionamientoPorApp", el cual usa "getNumeroDeCelular()"
		verify(elMismoEstacionamientoApp, times(1)).getPatente();
		// Una única vez por la búsqueda de estacionamiento vigente.
		assertEquals(unaNotificacion.getMensaje(), notificacionInicioExitosoMsg);
		assertEquals(otraNotificacion.getMensaje(), sem24Horas.mensajeDeNotificacionEstacionamientoYaVigente("MAX000"));
		// Donde el mensaje indica que ya existe un estacionamiento vigente con esa patente.
	}
	
	@Test
	public void test_deRegistrarUnEstacionamientoPorAppDeUnNumeroDeCelularQueNoFueRegistradoPreviamente_esteNoSeGuarda() {
		// Exercise
		Notificacion unaNotificacion = sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertFalse(sem24Horas.getCelularesConCredito().containsKey(unNumCel));
		assertFalse(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		verify(unEstacionamientoApp, times(1)).getNumeroDeCelular();
		verify(unEstacionamientoApp, times(1)).getPatente();
		assertEquals(unaNotificacion.getMensaje(), sem24Horas.mensajeDeNotificacionNumeroNoRegistrado(unNumCel));
		// Donde el mensaje indica que el celular no está registrado.
	}
	
	@Test
	public void test_deRegistrarUnEstacionamientoPorAppDeUnNumeroDeCelularQueNoTieneSaldo_esteNoSeGuarda() {
		// Setup
		double unMonto = 0.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		
		// Exercise
		Notificacion unaNotificacion = sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertTrue(sem24Horas.getCelularesConCredito().containsKey(unNumCel));
		assertEquals(sem24Horas.getCelularesConCredito().get(unNumCel), 0);
		assertFalse(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		verify(unEstacionamientoApp, times(1)).getNumeroDeCelular();
		verify(unEstacionamientoApp, times(1)).getPatente();
		assertEquals(unaNotificacion.getMensaje(), sem24Horas.mensajeDeNotificacionSaldoInsuficiente());
		// Donde el mensaje indica que el saldo no es suficiente.
	}
	
	@Test
	public void test_deIntentarRegistrarUnEstacionamientoPorAppFueraDelHorarioDeAtencion_esteNoSeGuarda() {
		// Setup
		double unMonto = 200.0;
		semCerrado.cargarCredito(unMonto, unNumCel);
		
		// Exercise
		Notificacion unaNotificacion = semCerrado.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertTrue(semCerrado.getCelularesConCredito().containsKey(unNumCel));
		assertFalse(semCerrado.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		verify(unEstacionamientoApp, never()).getNumeroDeCelular();
		// Al registrar mientras el servicio está cerrado, "registrarEstacionamiento" nunca llega a delegar a
		// "registrarEstacionamientoPorApp", el cual usa "getNumeroDeCelular()"
		verify(unEstacionamientoApp, times(1)).getPatente();
		assertEquals(unaNotificacion.getMensaje(), semCerrado.mensajeDeNotificacionServicioCerrado());
		// Donde el mensaje indica que el servicio está cerrado.
	}
	
	// ------------------- TESTS MIXTO ENTRE LOS REGISTROS DE ESTACIONAMIENTO POR APP Y PUNTUALES -------------------
	
	@Test
	public void test_deRegistrarUnaPatentePorEstacionamientoPuntualEInmediatamenteIntentarRegistrarElMismoPorApp_esteNoSeGuarda() {
		// Setup
		double unMonto = 200.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		when(unEstacionamientoApp.getPatente()).thenReturn("MAX001");
		
		// Exercise
		Notificacion notificacionApp = sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertTrue(sem24Horas.getCelularesConCredito().containsKey(unNumCel));
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoPuntual));
		assertFalse(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		verify(unEstacionamientoPuntual, times(2)).getPatente();
		// La primer vez para el registro, la segunda por la busqueda de estacionamiento vigente.
		verify(unEstacionamientoApp, never()).getNumeroDeCelular();
		// Al ya existir el estacionamiento, "registrarEstacionamiento" nunca llega a delegar a
		// "registrarEstacionamientoPorApp", el cual usa "getNumeroDeCelular()"
		verify(unEstacionamientoApp, times(1)).getPatente();
		// Una única vez por la busqueda de estacionamiento vigente.
		assertEquals(notificacionApp.getMensaje(), sem24Horas.mensajeDeNotificacionEstacionamientoYaVigente("MAX001"));
		// Donde el mensaje indica que ya existe un estacionamiento vigente con esa patente.
	}
	
	@Test
	public void test_deRegistrarUnaPatentePorEstacionamientoPorAppEInmediatamenteIntentarRegistrarElMismoPuntual_esteNoSeGuarda() {
		// Setup
		double unMonto = 200.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		Notificacion notificacionApp = sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		
		when(unEstacionamientoPuntual.getPatente()).thenReturn("MAX000");
		
		// Exercise
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		
		// Verify
		assertTrue(sem24Horas.getCelularesConCredito().containsKey(unNumCel));
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp));
		assertFalse(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoPuntual));
		verify(unEstacionamientoApp, times(1)).getNumeroDeCelular();
		verify(unEstacionamientoApp, times(2)).getPatente();
		
		// La primer vez por el registro, y la segunda por la búsqueda de estacionamiento vigente.
		verify(unEstacionamientoPuntual, times(1)).getPatente();
		// Al ya existir el estacionamiento, "registrarEstacionamiento" nunca llega a delegar a
		// "registrarEstacionamientoPorApp", el cual usa "getNumeroDeCelular()"
		assertEquals(notificacionApp.getMensaje(), notificacionInicioExitosoMsg);
	}
	
	@Test
	public void test_esEstacionamientoVigente() {
		// Setup
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(true);
		RegistroDeEstacionamientoPuntual elMismoEstacionamientoPuntual = mock(RegistroDeEstacionamientoPuntual.class);
		when(elMismoEstacionamientoPuntual.getPatente()).thenReturn("MAX001");
		when(elMismoEstacionamientoPuntual.getVigencia()).thenReturn(true);
		when(elMismoEstacionamientoPuntual.esDeApp()).thenReturn(false);
		when(elMismoEstacionamientoPuntual.getZonaDeEstacionamiento()).thenReturn(zonaB);
		String patente = unEstacionamientoPuntual.getPatente();
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		sem24Horas.finalizarEstacionamiento(patente);
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(false);
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		
		// Exercise
		sem24Horas.registrarEstacionamiento(elMismoEstacionamientoPuntual);
		
		// Verify
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(unEstacionamientoPuntual));
		assertTrue(sem24Horas.getRegistrosDeEstacionamiento().contains(elMismoEstacionamientoPuntual));
		assertTrue(sem24Horas.esEstacionamientoVigente("MAX001"));
		
		verify(unEstacionamientoPuntual, times(10)).getPatente();
		// Al ya existir el estacionamiento, "registrarEstacionamiento" nunca llega a delegar a
		// "registrarEstacionamientoPorApp", el cual usa "getNumeroDeCelular()"
	}
	
	@Test
	public void test_siElSemNoTieneLaZonaDeUnRegistroDeEstacionamiento_laMismaSeAgregaALasZonasDeEstacionamientoDelSem() {
		// Setup
		ZonaDeEstacionamiento zonaE = mock(ZonaDeEstacionamiento.class);
		ZonaDeEstacionamiento zonaF = mock(ZonaDeEstacionamiento.class);
		double unMonto = 200.0;
		sem24Horas.cargarCredito(unMonto, unNumCel);
		List<ZonaDeEstacionamiento> zonasDeEstacionamientoOriginal = sem24Horas.getZonasDeEstacionamiento();
		when(unEstacionamientoApp.getZonaDeEstacionamiento()).thenReturn(zonaE);
		when(unEstacionamientoPuntual.getZonaDeEstacionamiento()).thenReturn(zonaF);
		
		// Exercise
		sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		
		// Verify
		assertTrue(sem24Horas.getZonasDeEstacionamiento().containsAll(zonasDeEstacionamientoOriginal));
		assertTrue(sem24Horas.getZonasDeEstacionamiento().contains(zonaE));
		assertTrue(sem24Horas.getZonasDeEstacionamiento().contains(zonaF));
	}
	
	// ------------------- TESTS FINALIZACIÓN DE ESTACIONAMIENTO -------------------
	@Test
	public void testPrecioACobrarPara() {
		// Setup
		// (Instanciacion de un Registro que ocurre a las 13:00hs) 
		LocalDate unaFechaInicio = LocalDate.now();
		LocalTime unHorarioInicio = LocalTime.of(13, 00);
		LocalDateTime unaHoraYFechaCustom = LocalDateTime.of(unaFechaInicio, unHorarioInicio);
		when(unEstacionamientoApp.getVigencia()).thenReturn(false);
		when(unEstacionamientoApp.getFechaYHoraDeInicio()).thenReturn(unaHoraYFechaCustom);
		semRelojCustom.registrarEstacionamiento(unEstacionamientoApp);
		
		// Verify
		assertEquals(semRelojCustom.precioACobrarPara(unEstacionamientoApp), 80.0);
		// Es 80 porque el semRelojCustom cierra a las 15hs, y el registro se hizo a las 13hs
		// haciendo dos horas, y 2 * 40 = 80
	}
	
	@Test
	public void testFinalizacionExitosa_cuandoEnElSemSeIniciaUnEstacionamientoYSeFinalizaDosHorasDespues_elSaldoDelMismoEsDescontado() throws Exception {
		// Setup
		// (Instanciacion de un Registro que ocurre a las 13:00hs) 
		LocalDate unaFechaInicio = LocalDate.now();
		LocalTime unHorarioInicio = LocalTime.of(13, 00);
		LocalDateTime unaHoraYFechaCustom = LocalDateTime.of(unaFechaInicio, unHorarioInicio);
		when(unEstacionamientoApp.getFechaYHoraDeInicio()).thenReturn(unaHoraYFechaCustom);
		
		double montoOriginal = 100.0;
		double precioACobrar = semRelojCustom.precioACobrarPara(unEstacionamientoApp);
		semRelojCustom.cargarCredito(montoOriginal, unNumCel);
		Notificacion notificacionInicio = semRelojCustom.registrarEstacionamiento(unEstacionamientoApp);
		String patente = unEstacionamientoApp.getPatente();
		
		// Exercise
		Notificacion notificacionFin = semRelojCustom.finalizarEstacionamiento(patente);
		
		// Verify
		assertEquals(semRelojCustom.getSaldoDe(unNumCel), (montoOriginal - precioACobrar));
		assertEquals(notificacionInicio.getMensaje(), notificacionInicioExitosoMsg);
		assertEquals(notificacionFin.getMensaje(), notificacionFinMsg);
	}
	
	@Test
	public void test_deFinalizarUnEstacionamientoQueNuncaFueIniciado_esteNoHaceNada() throws Exception {
		// Setup
		String patente = unEstacionamientoApp.getPatente();
		
		// Exercise
		Notificacion notificacionFin = semRelojCustom.finalizarEstacionamiento(patente);
		boolean elEstacionamientoExiste = semRelojCustom.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp);
		
		// Verify
		assertFalse(elEstacionamientoExiste);
		assertEquals(notificacionFin.getMensaje(), semRelojCustom.mensajeDeNotificacionEstacionamientoNoVigente(patente));
		// Donde el mensaje indica que no existe un estacionamiento vigente para esa patente.
	}
	
	@Test
	public void test_deFinalizarUnEstacionamientoMientrasElSemEstaCerrado_esteNoHaceNada() throws Exception {
		// Setup
		String patente = unEstacionamientoApp.getPatente();
		
		// Exercise
		Notificacion notificacionFin = semCerrado.finalizarEstacionamiento(patente);
		boolean elEstacionamientoExiste = semCerrado.getRegistrosDeEstacionamiento().contains(unEstacionamientoApp);
		
		// Verify
		assertFalse(elEstacionamientoExiste);
		assertFalse(semCerrado.elServicioEstaAbierto());
		assertEquals(notificacionFin.getMensaje(), semCerrado.mensajeDeNotificacionServicioCerrado());
		// Donde el mensaje indica que el servicio está cerrado.
	}
	
	@Test
	public void test_deFinalizarUnEstacionamientoPuntualVigentePorMedioDeLaApp_esteSeFinaliza() throws Exception {
		// Setup
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(true);
		semRelojCustom.registrarEstacionamiento(unEstacionamientoPuntual);
		String patente = unEstacionamientoPuntual.getPatente();
		
		// Exercise
		semRelojCustom.finalizarEstacionamiento(patente);
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(false);
		boolean elEstacionamientoExiste = semCerrado.esEstacionamientoVigente(patente);
		
		// Verify
		verify(unEstacionamientoPuntual, times(1)).setVigencia(false);
		assertFalse(elEstacionamientoExiste);
	}
	
	@Test
	public void testFinalizacionDeTodosLosEstacionamientos() throws Exception {
		// Setup
		// (Instanciacion de un Registro que ocurre a las 13:00hs) 
		LocalDate unaFechaInicio = LocalDate.now();
		LocalTime unHorarioInicio = LocalTime.of(13, 00);
		LocalDateTime unaHoraYFechaCustom = LocalDateTime.of(unaFechaInicio, unHorarioInicio);
		when(unEstacionamientoApp.getFechaYHoraDeInicio()).thenReturn(unaHoraYFechaCustom);
		when(unEstacionamientoApp.getPatente()).thenReturn("MAX000");
		when(unEstacionamientoApp.getVigencia()).thenReturn(true);
		when(unEstacionamientoPuntual.getPatente()).thenReturn("MAX001");
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(true);
		semRelojCustom.cargarCredito(100, unNumCel);
		semRelojCustom.registrarEstacionamiento(unEstacionamientoPuntual);
		semRelojCustom.registrarEstacionamiento(unEstacionamientoApp);
		boolean hayEstacionamientosVigentesOriginal = semRelojCustom.getRegistrosDeEstacionamiento().stream()
				.anyMatch(p -> p.getVigencia());
		
		// Exercise
		semRelojCustom.finalizarTodosLosEstacionamientos();
		when(unEstacionamientoApp.getVigencia()).thenReturn(false);
		when(unEstacionamientoPuntual.getVigencia()).thenReturn(false);
		boolean hayEstacionamientosVigentesLuegoDeFinalizar = semRelojCustom.getRegistrosDeEstacionamiento().stream()
				.anyMatch(p -> p.getVigencia());
		
		// Verify
		verify(unEstacionamientoApp, times(1)).setVigencia(false);
		verify(unEstacionamientoPuntual, times(1)).setVigencia(false);
		assertTrue(hayEstacionamientosVigentesOriginal);
		assertFalse(hayEstacionamientosVigentesLuegoDeFinalizar);
	}
	
	// ------------------- TESTS REGISTRO DE COMPRA -------------------
	@Test
	public void testRegistrarCompraYGetterDeLasCompras() {
		// Setup
		RegistroDeCompra unaCompraPuntual = mock(RegistroDeCompraPuntual.class);
		RegistroDeCompra unRegistroDeRecarga = mock(RegistroDeRecarga.class);
		
		// Exercise
		sem.registrarCompra(unaCompraPuntual);
		sem.registrarCompra(unRegistroDeRecarga);
		
		// Verify
		assertEquals(sem.getRegistrosDeCompra().size(), 2);
		assertTrue(sem.getRegistrosDeCompra().contains(unaCompraPuntual));
		assertTrue(sem.getRegistrosDeCompra().contains(unRegistroDeRecarga));
	}
	
	// ------------------- TESTS REGISTRO DE INFRACCIONES -------------------
	@Test
	public void testRegistrarInfraccionesYGetterDeInfracciones() {
		// Setup
		Infraccion unaInfraccion = mock(Infraccion.class);
		Infraccion otraInfraccion = mock(Infraccion.class);
		
		// Exercise
		sem.registrarInfraccion(unaInfraccion);
		sem.registrarInfraccion(otraInfraccion);
		
		// Verify
		assertEquals(sem.getInfracciones().size(), 2);
		assertTrue(sem.getInfracciones().contains(unaInfraccion));
		assertTrue(sem.getInfracciones().contains(otraInfraccion));
	}
	
	// ------------------- TESTS SUSCRIPCION/DESUSCRIPCION/NOTIFICACION -------------------
	@Test
	public void testSuscribirEntidadYGetterDeEntidades() {
		// Setup
		EntidadObservadora unaEntidad = mock(EntidadObservadora.class);
		EntidadObservadora otraEntidad = mock(EntidadObservadora.class);
		
		// Exercise
		sem.suscribir(unaEntidad);
		sem.suscribir(otraEntidad);
		
		// Verify
		assertEquals(sem.getEntidadesObservadoras().size(), 2);
		assertTrue(sem.getEntidadesObservadoras().contains(unaEntidad));
		assertTrue(sem.getEntidadesObservadoras().contains(otraEntidad));
	}
	
	@Test
	public void testDeSuscribirUnaEntidadQueYaExiste_estaNoSeGuarda() {
		// Setup
		EntidadObservadora unaEntidad = mock(EntidadObservadora.class);
		
		// Exercise
		sem.suscribir(unaEntidad);
		sem.suscribir(unaEntidad);
		
		// Verify
		assertEquals(sem.getEntidadesObservadoras().size(), 1);
		assertTrue(sem.getEntidadesObservadoras().contains(unaEntidad));
	}
	
	@Test
	public void testDesuscribirEntidad() {
		// Setup
		EntidadObservadora unaEntidad = mock(EntidadObservadora.class);
		EntidadObservadora otraEntidad = mock(EntidadObservadora.class);
		sem.suscribir(unaEntidad);
		sem.suscribir(otraEntidad);
		
		// Exercise
		sem.desuscribir(unaEntidad);
		sem.desuscribir(otraEntidad);
		
		// Verify
		assertTrue(sem.getEntidadesObservadoras().isEmpty());
		assertFalse(sem.getEntidadesObservadoras().contains(unaEntidad));
		assertFalse(sem.getEntidadesObservadoras().contains(otraEntidad));
	}
	
	@Test
	public void testNotificarEntidad() {
		// Setup
		EntidadObservadora unaEntidad = mock(EntidadObservadora.class);
		EntidadObservadora otraEntidad = mock(EntidadObservadora.class);
		sem.suscribir(unaEntidad);
		sem.suscribir(otraEntidad);
		
		// Exercise
		sem.notificar(sem24Horas);
		
		// Verify
		verify(unaEntidad, times(1)).update(sem24Horas);
		verify(otraEntidad, times(1)).update(sem24Horas);
	}
	
	@Test
	public void testNotificacionesDeLosRegistrosDeInicio_DeFinalizacion_YDeComprasHaciaLasEntidades() {
		// Setup
		// (Instanciacion de un Registro que ocurre a las 13:00hs) 
		LocalDate unaFechaInicio = LocalDate.now();
		LocalTime unHorarioInicio = LocalTime.of(13, 00);
		LocalDateTime unaHoraYFechaCustom = LocalDateTime.of(unaFechaInicio, unHorarioInicio);
		when(unEstacionamientoApp.getFechaYHoraDeInicio()).thenReturn(unaHoraYFechaCustom);
		// REGISTROS DE COMPRA
		RegistroDeCompra unaCompraPuntual = mock(RegistroDeCompraPuntual.class);
		RegistroDeCompra unRegistroDeRecarga = mock(RegistroDeRecarga.class);
		// ENTIDADES OBSERVADORAS
		EntidadObservadora unaEntidad = mock(EntidadObservadora.class);
		EntidadObservadora otraEntidad = mock(EntidadObservadora.class);
		sem24Horas.suscribir(unaEntidad);
		sem24Horas.suscribir(otraEntidad);
		// REGISTROS DE ESTACIONAMIENTO
		sem24Horas.cargarCredito(100, unNumCel);
		String patentePuntual = unEstacionamientoPuntual.getPatente();
		String patenteApp = unEstacionamientoApp.getPatente();
		
		// Exercise
		sem24Horas.registrarEstacionamiento(unEstacionamientoPuntual);
		sem24Horas.registrarEstacionamiento(unEstacionamientoApp);
		sem24Horas.finalizarEstacionamiento(patentePuntual);
		sem24Horas.finalizarEstacionamiento(patenteApp);
		sem24Horas.registrarCompra(unRegistroDeRecarga);
		
		// Verify
		verify(unaEntidad, times(2)).update(unEstacionamientoPuntual); // Una vez por el registro, la segunda vez por su finalizacion.
		verify(unaEntidad, times(2)).update(unEstacionamientoApp); // Una vez por el registro, la segunda vez por su finalizacion.
		verify(unaEntidad, never()).update(unaCompraPuntual);
		verify(unaEntidad, times(1)).update(unRegistroDeRecarga);
		verify(otraEntidad, times(2)).update(unEstacionamientoPuntual); // Una vez por el registro, la segunda vez por su finalizacion.
		verify(otraEntidad, times(2)).update(unEstacionamientoApp); // Una vez por el registro, la segunda vez por su finalizacion.
		verify(otraEntidad, never()).update(unaCompraPuntual);
		verify(otraEntidad, times(1)).update(unRegistroDeRecarga);
	}
}
