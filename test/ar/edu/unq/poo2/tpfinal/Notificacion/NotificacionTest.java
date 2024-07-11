package ar.edu.unq.poo2.tpfinal.Notificacion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class NotificacionTest {
	
	@Test
	public void testNotificacionDeInicioExitoso() {
		LocalTime horaInicio = LocalTime.of(7, 0);
		LocalTime horaMaxima = LocalTime.of(20, 0);
		NotificacionDeInicioExitoso notificacionDeInicio = new NotificacionDeInicioExitoso(horaInicio, horaMaxima);
		
		assertEquals(notificacionDeInicio.getHoraInicio(), horaInicio);
		assertEquals(notificacionDeInicio.getHoraMaxima(), horaMaxima);
		assertEquals(notificacionDeInicio.getMensaje(), "Su estacionamiento ha sido registrado con éxito");
		assertTrue(notificacionDeInicio.esNotificacionDeInicioExitoso());
	}
	
	@Test
	public void testNotificacionMensajePersonalizado() {
		String unMensajePersonalizado = "Hola soy un mensaje personalizado";
		NotificacionMensajePersonalizado notificacionPersonalizada = new NotificacionMensajePersonalizado(unMensajePersonalizado);
		
		assertEquals(notificacionPersonalizada.getMensaje(), unMensajePersonalizado);
		assertFalse(notificacionPersonalizada.esNotificacionDeInicioExitoso());
	}
	
	@Test
	public void testNotificacionDeFin() {
		LocalTime horaInicio = LocalTime.of(7, 0);
		LocalTime horaFin = LocalTime.of(20, 0);
		double cantHoras = 3.0;
		double costoDeEstacionamiento = 160.0;
		NotificacionDeFin notificacionDeFin = new NotificacionDeFin(horaInicio, horaFin, cantHoras, costoDeEstacionamiento);
		
		assertEquals(notificacionDeFin.getHoraInicio(), horaInicio);
		assertEquals(notificacionDeFin.getHoraFin(), horaFin);
		assertEquals(notificacionDeFin.getCantHoras(), cantHoras);
		assertEquals(notificacionDeFin.getCostoDeEstacionamiento(), costoDeEstacionamiento);
		assertEquals(notificacionDeFin.getMensaje(), "Su estacionamiento ha sido finalizado con éxito");
		assertFalse(notificacionDeFin.esNotificacionDeInicioExitoso());
	}
	
	@Test
	public void testAlertaDeInicioYDeFin() {
		AlertaDeInicio alertaDeInicio = new AlertaDeInicio();
		AlertaDeFin alertaDeFin = new AlertaDeFin();
		
		String alertaInicioMsg = "AVISO: Su estacionamiento no fue registrado."
				+"\nDebe registrar el estacionamiento.";
		String alertaFinMsg = "AVISO: Su estacionamiento aún está vigente."
				+"\nSi desea salir de la Zona de Estacionamiento, debe finalizar su estacionamiento.";
		
		assertEquals(alertaDeInicio.getMensaje(), alertaInicioMsg);
		assertEquals(alertaDeFin.getMensaje(), alertaFinMsg);
		assertFalse(alertaDeInicio.esNotificacionDeInicioExitoso());
		assertFalse(alertaDeFin.esNotificacionDeInicioExitoso());
	}
}
