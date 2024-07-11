package ar.edu.unq.poo2.tpfinal.RegistroDeCompra;

import java.time.LocalTime;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.*;

public class RegistroDeRecarga extends RegistroDeCompra{
	
	double monto;
	int numeroDeCelular;
	
	public RegistroDeRecarga(int numeroDeRegistroNuevo,double monto,int numeroDeCelular, LocalTime fechaYHora, PuntoDeVenta puntoDeVenta) {
		this.numeroDeControl = numeroDeRegistroNuevo;
		this.monto = monto;
		this.numeroDeCelular= numeroDeCelular;
		this.fechaYHora = fechaYHora;
		this.puntoDeVenta = puntoDeVenta;
	}
	
	public double getMonto() {
		return this.monto;
	}
	
	public int getNumeroDeCelular() {
		return this.numeroDeCelular;
	}
}
