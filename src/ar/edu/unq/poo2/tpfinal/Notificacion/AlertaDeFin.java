package ar.edu.unq.poo2.tpfinal.Notificacion;

public class AlertaDeFin implements Notificacion {
	
	@Override
	public String getMensaje() {
		return "AVISO: Su estacionamiento aún está vigente."
				+"\nSi desea salir de la Zona de Estacionamiento, debe finalizar su estacionamiento.";
	}
}
