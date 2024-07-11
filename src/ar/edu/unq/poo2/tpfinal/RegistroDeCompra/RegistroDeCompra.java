package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;


public abstract class RegistroDeCompra {
	
	int numeroDeControl;
	
	LocalDateTime fechaYHora;
	
	PuntoDeVenta puntoDeVenta;
	
	public int getNumeroDeControl() {
		return this.numeroDeControl;
	}
	
	public LocalDateTime getFechaYHora() {
		return this.fechaYHora;
	}
	
	public PuntoDeVenta getPuntoDeVenta() {
		return this.puntoDeVenta;
	}

}
