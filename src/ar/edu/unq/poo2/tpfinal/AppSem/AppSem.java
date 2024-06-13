package ar.edu.unq.poo2.tpfinal.AppSem;

import ar.edu.unq.poo2.tpfinal.Modo.Modo;
import ar.edu.unq.poo2.tpfinal.Modo.ModoAutomatico;
import ar.edu.unq.poo2.tpfinal.Modo.ModoManual;
import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class AppSem {
	
	private String patente;
	private int numCel;
	private boolean deteccionMovActiva;
	private boolean estaManejando;
	private GPS gps;
	private Modo modo;
	private SEM sem;
	private IPantalla pantalla;
	
	public AppSem(IPantalla pantalla, String patente, int numCel,
	boolean deteccionMovActiva, boolean estaManejando, GPS unGps, Modo unModo, SEM unSem) 
	{
		this.pantalla = pantalla;
		this.patente = patente;
		this.gps = unGps;
		this.modo = unModo;
		this.sem = unSem;
		this.numCel = numCel;
		this.estaManejando = estaManejando;
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
	
	public IPantalla getPantalla() {
		return this.pantalla;
	}
	
	public boolean getEstaManejando() {
		return this.estaManejando;
	}

	public boolean getDeteccionMovActiva() {
		return this.deteccionMovActiva;
	}

	public void setModo(Modo unModo) {
		this.modo = unModo;
	}
	
	public void setEstaManejando(boolean estaManejando) {
		this.estaManejando = estaManejando;
	}

	public void registrarVehiculo(ZonaDeEstacionamiento unaZona) {
		this.getModo().registrarVehiculo(this.getPatente(), unaZona, this.getSem(), this.getNumCel(), this.getPantalla());
		
	}

	public void finalizarEstacionamiento() {
		this.getModo().finalizarEstacionamientoPara(this.getPatente(), this.getSem());
	}

	public void activarDeteccionDesplazamiento() {
		this.deteccionMovActiva = true;
	}
	
	public void desactivarDeteccionDesplazamiento() {
		this.deteccionMovActiva = false;
	}


	public void driving() {
		if (this.getDeteccionMovActiva()) {
			this.getModo().driving(this.getEstaManejando(), this.getGps(), patente, sem);
			this.setEstaManejando(true);
		}
	}

	public void walking() {
		if (this.getDeteccionMovActiva()) {
			this.getModo().walking(this.getEstaManejando(), patente, sem);
			this.setEstaManejando(false);
		}
	}
	
}
