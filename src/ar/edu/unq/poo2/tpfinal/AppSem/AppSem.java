package ar.edu.unq.poo2.tpfinal.AppSem;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.EstadoDeMovimiento.EstadoDeMovimiento;
import ar.edu.unq.poo2.tpfinal.Modo.Modo;
import ar.edu.unq.poo2.tpfinal.Notificacion.Notificacion;
import ar.edu.unq.poo2.tpfinal.Registro.RegistroDeEstacionamiento.RegistroDeEstacionamientoApp;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class AppSem {
	
	private String patente;
	private int numCel;
	private boolean deteccionMovActiva;
	private GPS gps;
	private Modo modo;
	private SEM sem;
	private INotificable notificable;
	private EstadoDeMovimiento estadoDeMovimiento;
	
	public AppSem(INotificable notificable, String patente, int numCel,
	boolean deteccionMovActiva, GPS unGps, EstadoDeMovimiento unEstadoDeMovimiento, Modo unModo, SEM unSem) 
	{
		this.notificable = notificable;
		this.patente = patente;
		this.gps = unGps;
		this.modo = unModo;
		this.estadoDeMovimiento = unEstadoDeMovimiento;
		this.sem = unSem;
		this.numCel = numCel;
		this.deteccionMovActiva = deteccionMovActiva;
	}

	public String consultarSaldo() {
		return this.getSem().getSaldoDe(this.getNumCel());
	}

	public int getNumCel() {
		return this.numCel;
	}

	public SEM getSem() {
		return this.sem;
	}
	
	public GPS getGps() {
		return this.gps;
	}

	public Modo getModo() {
		return this.modo;
	}
	
	public String getPatente() {
		return this.patente;
	}
	
	public INotificable getNotificable() {
		return this.notificable;
	}
	
	public boolean getDeteccionMovActiva() {
		return this.deteccionMovActiva;
	}
	
	protected EstadoDeMovimiento getEstadoDeMovimiento() {
		return this.estadoDeMovimiento;
	}
	
	public ZonaDeEstacionamiento getZonaActual() {
		return this.getGps().getZonaActual();
	}

	public void setModo(Modo unModo) {
		this.modo = unModo;
	}
	
	public void setEstadoDeMovimiento(EstadoDeMovimiento unEstadoDeMovimiento) {
		this.estadoDeMovimiento = unEstadoDeMovimiento;
	}

	public Notificacion registrarVehiculo(ZonaDeEstacionamiento unaZona) {
		RegistroDeEstacionamientoApp miEstacionamiento = new RegistroDeEstacionamientoApp(this.getPatente(), this.getNumCel(), LocalDateTime.now(), unaZona);
		Notificacion unaNotificacion = this.getSem().registrarEstacionamiento(miEstacionamiento);
		this.notificar(unaNotificacion);
		return unaNotificacion;
	}

	public void notificar(Notificacion unaNotificacion) {
		this.getNotificable().notificar(unaNotificacion);
	}

	public void finalizarEstacionamiento() {
		this.getSem().finalizarEstacionamiento(this.getPatente());
	}

	public void activarDeteccionDesplazamiento() {
		this.deteccionMovActiva = true;
	}
	
	public void desactivarDeteccionDesplazamiento() {
		this.deteccionMovActiva = false;
	}

	public void driving() {
		this.getEstadoDeMovimiento().driving(this.getDeteccionMovActiva(), this, this.getSem());
	}

	public void walking() {
		this.getEstadoDeMovimiento().walking(this.getDeteccionMovActiva(), this, this.getSem());
	}

	public void finalizarSiCorrespondeYNotificar() {
		this.getModo().finalizarSiCorrespondeYNotificar(this.getPatente(), this.getSem(), this);
	}

	public void registrarSiCorrespondeYNotificar() {
		this.getModo().registrarSiCorrespondeYNotificar(this.getPatente(), this.getZonaActual(), this.getSem(), this.getNumCel(), this);
	}

	public void finalizarEstacionamientoConNotificacionExtra(Notificacion unaNotificacionExtra) {
		this.finalizarEstacionamiento();
		this.notificar(unaNotificacionExtra);
	}

	public void registrarVehiculoConNotificacionExtra(ZonaDeEstacionamiento unaZona, Notificacion unaNotificacionExtra) {
		Notificacion unaNotificacion = this.registrarVehiculo(unaZona);
		if (unaNotificacion.esNotificacionDeInicioExitoso()) {
			this.notificar(unaNotificacionExtra);
		}
	}
}
