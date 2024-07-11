package ar.edu.unq.poo2.tpfinal.Notificacion;

public class AlertaDeInicio extends Notificacion {
	
	public String getMensaje() {
		return "AVISO: Su estacionamiento no fue registrado."
				+"\nDebe registrar el estacionamiento.";
	}
}
