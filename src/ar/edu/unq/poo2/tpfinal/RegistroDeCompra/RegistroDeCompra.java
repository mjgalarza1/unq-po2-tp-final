package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalTime;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;


public abstract class RegistroDeCompra {
	
	int numeroDeControl;
	
	LocalTime fechaYHora;
	
	PuntoDeVenta puntoDeVenta;
	
	public int getNumeroDeControl() {
		return this.numeroDeControl;
	}
	
	public LocalTime getFechaYHora() {
		return this.fechaYHora;
	}
	
	public PuntoDeVenta getPuntoDeVenta() {
		return this.puntoDeVenta;
	}

}
