package ar.edu.unq.poo2.tpfinal.PuntoDeVenta;


import java.time.LocalDateTime;

import ar.edu.unq.poo2.tpfinal.Sem.SEM;
import ar.edu.unq.poo2.tpfinal.RegistroDeCompra.*;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.*;

public class PuntoDeVenta {
	
	private SEM sem ;
	public String nombre;
	
	public PuntoDeVenta (SEM sem, String nombre) {
		this.sem = sem;
		this.nombre = nombre;
	}
	
	public SEM getSem() {
		return this.sem;
	}

	public String getNombre() {
		return this.nombre;
	}

	protected int generarNumeroDeRegistro() {
	//Se utiliza para generar un numero de registro de cinco cifras de forma aleatoria
		return Math.toIntExact(Math.round(Math.random()*10000));
	}
	
	public void cargarCreditoSEM(double monto, int numeroDeCelular) {
		this.sem.cargarCredito(monto, numeroDeCelular);
		
		RegistroDeRecarga registro = new RegistroDeRecarga(this.generarNumeroDeRegistro(), monto, numeroDeCelular, LocalDateTime.now(), this);
		
		this.sem.registrarCompra(registro);
	}
	
	
	public void registrarVehiculo(RegistroDeEstacionamientoPuntual registro) {
						
		this.sem.registrarEstacionamientoPuntual(registro);
	
		RegistroDeCompraPuntual registroCompra = new RegistroDeCompraPuntual(this.generarNumeroDeRegistro(), registro.getHorasEstacionamiento(), LocalDateTime.now(), this);
		
		this.sem.registrarCompra(registroCompra);
		
	}
	
	
	

}
