package ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento;

import java.time.LocalTime;
import java.util.List;

import ar.edu.unq.poo2.tpfinal.PuntoDeVenta.PuntoDeVenta;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.*;

public class ZonaDeEstacionamiento {
	
	List<PuntoDeVenta> puntosDeVenta;
	String nombre;
	Inspector inspector;
	
	public ZonaDeEstacionamiento(List<PuntoDeVenta> puntos,Inspector inspector,String nombre) {
		this.puntosDeVenta = puntos;
		this.inspector = inspector;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public List<PuntoDeVenta> getPuntosDeVenta(){
		return this.puntosDeVenta;
	}
	
	public Inspector getInspector() {
		return this.inspector;
	}
	
	
	public void registrarVehiculo(String patente, int cantHoras, PuntoDeVenta unPuntoDeVenta) {
		
		RegistroDeEstacionamientoPuntual estacionamiento = new RegistroDeEstacionamientoPuntual(patente, cantHoras, LocalTime.now(), this);
		
		unPuntoDeVenta.registrarVehiculo(estacionamiento);
	}
	

}
