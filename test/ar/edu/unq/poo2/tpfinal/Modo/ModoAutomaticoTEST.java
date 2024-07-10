package ar.edu.unq.poo2.tpfinal.Modo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.AppSem.GPS;
import ar.edu.unq.poo2.tpfinal.AppSem.INotificable;
import ar.edu.unq.poo2.tpfinal.Notificacion.*;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamiento;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

class ModoAutomaticoTEST {
	private ModoAutomatico unModoAutomatico;
	private INotificable unNotificable;
	private AppSem unaApp;
	
	@BeforeEach
	void setUp() throws Exception {
		unaApp = mock(AppSem.class);
		unNotificable = mock(INotificable.class);
		unModoAutomatico = new ModoAutomatico();
	}

	@Test
	void finalizarSiCorrespondeYNotificarTEST() {
		//setup
		ArgumentCaptor<NotificacionMensajePersonalizado> notificacionCaptor = ArgumentCaptor.forClass(NotificacionMensajePersonalizado.class);
		
		//exercise
		unModoAutomatico.finalizarSiCorrespondeYNotificar(unaApp);
		
		//verify
		verify(unaApp, times(1)).finalizarEstacionamientoConNotificacionExtra(notificacionCaptor.capture());
	}
	
	@Test
	void registrarSiCorrespondeYNotificarTEST() {
		//setup
		ArgumentCaptor<NotificacionMensajePersonalizado> notificacionCaptor = ArgumentCaptor.forClass(NotificacionMensajePersonalizado.class);
				
		//exercise
		unModoAutomatico.registrarSiCorrespondeYNotificar(unaApp);
				
		//verify
		verify(unaApp, times(1)).registrarVehiculoConNotificacionExtra(notificacionCaptor.capture());
	}
}
