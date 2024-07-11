package ar.edu.unq.poo2.tpfinal.PuntoDeVenta;


import java.time.LocalTime;

import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.*;
import ar.edu.unq.poo2.tpfinal.RegistroDeCompra.*;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.*;

public class PuntoDeVenta {
	
	private SEM sem ;
	public String nombre;
	
	public PuntoDeVenta (SEM sem, String nombre) {
		this.sem = sem;
		this.nombre = nombre;
	}
	
	private int generarNumeroDeRegistro() {
	//Se utiliza para generar un numero de registro de cinco cifras de forma aleatoria
        double numeroDeRegistroNuevo = Math.random()*10000;
		
		numeroDeRegistroNuevo = Math.toIntExact(Math.round(numeroDeRegistroNuevo));
		
		return (int) numeroDeRegistroNuevo;

	}
	
	public void cargarCreditoSEM(double monto, int numeroDeCelular) {
		this.sem.cargarCredito(monto, numeroDeCelular);
		
				
		RegistroDeRecarga registro = new RegistroDeRecarga(this.generarNumeroDeRegistro(), monto, numeroDeCelular, LocalTime.now(), this);
		
		this.sem.registrarCompra(registro);
	}
	
	
	public void registrarVehiculo(RegistroDeEstacionamientoPuntual registro) {
						
		this.sem.registrarEstacionamiento(registro);
	
		RegistroDeCompraPuntual registroCompra = new RegistroDeCompraPuntual(this.generarNumeroDeRegistro(), registro.horasEstacionamiento, LocalTime.now(), this);
		
		this.sem.registrarCompra(registroCompra);
		
	}
	
	
	

}
