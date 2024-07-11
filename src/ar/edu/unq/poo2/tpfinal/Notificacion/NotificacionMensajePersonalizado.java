package ar.edu.unq.poo2.tpfinal.Notificacion;

public class NotificacionMensajePersonalizado extends Notificacion {

	String mensajeDeNotificacion;
	
	public NotificacionMensajePersonalizado(String mensajeDeNotificacion) {
		this.mensajeDeNotificacion = mensajeDeNotificacion;
	}
	
	public String getMensaje() {
		return mensajeDeNotificacion;
	}

}
