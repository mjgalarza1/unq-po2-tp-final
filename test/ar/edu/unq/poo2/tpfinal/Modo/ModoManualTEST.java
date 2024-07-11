package ar.edu.unq.poo2.tpfinal.Modo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;
import ar.edu.unq.poo2.tpfinal.Notificacion.AlertaDeFin;
import ar.edu.unq.poo2.tpfinal.Notificacion.AlertaDeInicio;

class ModoManualTEST {

	private ModoManual unModoManual;
	private AppSem unaApp;
	
	@BeforeEach
	void setUp() throws Exception {
		unModoManual = new ModoManual();
		unaApp = mock(AppSem.class);
	}

	@Test
	void finalizarSiCorrespondeYNotificarTEST() {
		//setup
		ArgumentCaptor<AlertaDeFin> alertaCaptor = ArgumentCaptor.forClass(AlertaDeFin.class);
		
		//exercise
		unModoManual.finalizarSiCorrespondeYNotificar(unaApp);
		
		//verify
		verify(unaApp, times(1)).notificar(alertaCaptor.capture());
	}
	
	@Test
	void registrarSiCorrespondeYNotificarTEST() {
		//setup
		ArgumentCaptor<AlertaDeInicio> alertaCaptor = ArgumentCaptor.forClass(AlertaDeInicio.class);
				
		//exercise
		unModoManual.registrarSiCorrespondeYNotificar(unaApp);
				
		//verify
		verify(unaApp, times(1)).notificar(alertaCaptor.capture());
	}
}
