package ar.edu.unq.poo2.tpfinal.Notificacion;

public class NotificacionMensajePersonalizado implements Notificacion {

	String mensajeDeNotificacion;
	
	public NotificacionMensajePersonalizado(String mensajeDeNotificacion) {
		this.mensajeDeNotificacion = mensajeDeNotificacion;
	}
	
	@Override
	public String getMensaje() {
		return mensajeDeNotificacion;
	}

}
