package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.*;

public class RegistroDeCompraPuntual extends RegistroDeCompra{
	
	int cantidadDeHorasCompradas;
	
	public RegistroDeCompraPuntual(int numeroDeControl,int horasEstacionamiento, LocalDateTime fechaYHoraDeInicio, PuntoDeVenta puntoDeVenta) {
		this.numeroDeControl = numeroDeControl;
		this.cantidadDeHorasCompradas = horasEstacionamiento;
		this.fechaYHora = fechaYHoraDeInicio;
		this.puntoDeVenta = puntoDeVenta;
	}
	
	public int getCantidadDeHorasCompradas() {
		return this.cantidadDeHorasCompradas;
	}
	
}