package ar.edu.unq.poo2.tpfinal.Sem;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ar.edu.unq.poo2.tpfinal.Celular.Celular;
import ar.edu.unq.poo2.tpfinal.EntidadObservadora.EntidadObservadora;
import ar.edu.unq.poo2.tpfinal.Notificacion.*;
import ar.edu.unq.poo2.tpfinal.RegistroDeCompra.RegistroDeCompra;
import ar.edu.unq.poo2.tpfinal.RegistroDeCompra.RegistroDeRecarga;
import ar.edu.unq.poo2.tpfinal.RegistroDeEstacionamiento.*;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.Infraccion;
import ar.edu.unq.poo2.tpfinal.ZonaDeEstacionamiento.ZonaDeEstacionamiento;

public class SEM {

	private LocalTime horaApertura;
	private LocalTime horaCierre;
	private double precioPorHora;
	private Set<ZonaDeEstacionamiento> zonasDeEstacionamiento;
	private List<RegistroDeCompra> registrosDeCompra;
	private Set<Celular> celulares;
	private List<RegistroDeEstacionamiento> registrosDeEstacionamiento;
	private List<Infraccion> infracciones;
	private Set<EntidadObservadora> entidadesObservadoras;
	private Clock reloj = Clock.system(ZoneId.systemDefault());
	private EstadoSEM estado;
	
	// CONSTRUCTOR por DEFECTO (el SEM requiere de al menos una zona)
	public SEM(Set<ZonaDeEstacionamiento> zonasDeEstacionamiento) {
		super();
		this.horaApertura = LocalTime.of(7,0);
		this.horaCierre = LocalTime.of(20,0);
		this.precioPorHora = 40.0;
		this.zonasDeEstacionamiento = zonasDeEstacionamiento;
		this.registrosDeCompra = new ArrayList<RegistroDeCompra>();
		this.celulares = new HashSet<Celular>();
		this.registrosDeEstacionamiento = new ArrayList<RegistroDeEstacionamiento>();
		this.infracciones = new ArrayList<Infraccion>();
		this.entidadesObservadoras = new HashSet<EntidadObservadora>();
		this.estado = new EstadoAbierto();
	}
	
	// CONSTRUCTOR para setear los HORARIOS de apertura y cierre además del PRECIO
	public SEM(LocalTime horaApertura, LocalTime horaCierre, double precioPorHora, Set<ZonaDeEstacionamiento> zonasDeEstacionamiento) {
		super();
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.precioPorHora = precioPorHora;
		this.zonasDeEstacionamiento = zonasDeEstacionamiento;
		this.registrosDeCompra = new ArrayList<RegistroDeCompra>();
		this.celulares = new HashSet<Celular>();
		this.registrosDeEstacionamiento = new ArrayList<RegistroDeEstacionamiento>();
		this.infracciones = new ArrayList<Infraccion>();
		this.entidadesObservadoras = new HashSet<EntidadObservadora>();
		this.estado = new EstadoAbierto();
	}
		
	// CONSTRUCTOR para setear los HORARIOS de apertura y cierre, el PRECIO, y un RELOJ para testear.
	protected SEM(LocalTime horaApertura, LocalTime horaCierre, double precioPorHora, Set<ZonaDeEstacionamiento> zonasDeEstacionamiento, Clock reloj) {
		super();
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.precioPorHora = precioPorHora;
		this.zonasDeEstacionamiento = zonasDeEstacionamiento;
		this.registrosDeCompra = new ArrayList<RegistroDeCompra>();
		this.celulares = new HashSet<Celular>();
		this.registrosDeEstacionamiento = new ArrayList<RegistroDeEstacionamiento>();
		this.infracciones = new ArrayList<Infraccion>();
		this.entidadesObservadoras = new HashSet<EntidadObservadora>();
		this.reloj = reloj;
		this.estado = new EstadoAbierto();
	}
	
	
	
	// GETTERS
	public LocalTime getHoraApertura() {
		return this.horaApertura;
	}
	
	public LocalTime getHoraCierre() {
		return this.horaCierre;
	}
	
	public double getPrecioPorHora() {
		return this.precioPorHora;
	}
	
	public Set<Celular> getCelulares() {
		return this.celulares;
	}
	
	public Set<ZonaDeEstacionamiento> getZonasDeEstacionamiento() {
		return this.zonasDeEstacionamiento;
	}
	
	public List<RegistroDeEstacionamiento> getRegistrosDeEstacionamiento() {
		return this.registrosDeEstacionamiento;
	}
	
	public List<RegistroDeCompra> getRegistrosDeCompra() {
		return this.registrosDeCompra;
	}
	
	public List<Infraccion> getInfracciones() {
		return this.infracciones;
	}
	
	public Set<EntidadObservadora> getEntidadesObservadoras() {
		return this.entidadesObservadoras;
	}
	
	protected EstadoSEM getEstado() {
		return this.estado;
	}
	
	// SETTERS
	
	public void setEstado(EstadoSEM unEstado) {
		this.estado = unEstado;
	}
	
	// MÉTODOS
	
	public void registrarEstacionamientoPuntual(RegistroDeEstacionamientoPuntual unEstacionamiento) {
		// Retorna una notificación, a pesar de que el Punto de Venta no hará nada con él.
		// Los registros de estacionamiento puntuales retornan notificaciones vacías (es decir, null).
		this.getEstado().registrarEstacionamientoPuntual(unEstacionamiento, this);
	}

	public Notificacion registrarEstacionamientoPorApp(RegistroDeEstacionamientoApp unEstacionamiento) {
		return this.getEstado().registrarEstacionamientoPorApp(unEstacionamiento, this, this.reloj);
	}
	
	protected void agregarZonaDeEstacionamiento(RegistroDeEstacionamiento unEstacionamiento) {
		ZonaDeEstacionamiento zonaDeEstacionamiento = unEstacionamiento.getZonaDeEstacionamiento();
		zonasDeEstacionamiento.add(zonaDeEstacionamiento);
	}
	
	protected boolean elServicioEstaAbierto() {
		// Valida si el servicio está activo dada la hora actual.
		LocalTime horaActual = LocalTime.now(reloj); 
		return (horaActual.isAfter(this.horaApertura) && horaActual.isBefore(this.horaCierre));
	}
 
	protected LocalTime getHoraMaximaPara(double monto, LocalTime horaActual) {
		// Obtiene la hora máxima que el monto dado permita entre la hora actual dada y la hora de cierre.
		long horasDisponibles = (long) (monto / this.precioPorHora);
		LocalTime tiempoLimite = horaActual.plusHours(horasDisponibles);
		if (tiempoLimite.isAfter(this.horaCierre)) {
            tiempoLimite = this.horaCierre;
        }
		return tiempoLimite;
	}
	
	protected double precioACobrarPara(RegistroDeEstacionamientoApp unEstacionamiento) {
		LocalTime horaInicio = unEstacionamiento.getFechaYHoraDeInicio().toLocalTime();
		LocalTime horaFin = LocalTime.now(reloj);
		double cantHoras = horaInicio.until(horaFin, ChronoUnit.HOURS);
		double precioACobrar = cantHoras * this.precioPorHora;
		return precioACobrar;
	}
	
	public void cobrarleA(RegistroDeEstacionamientoApp unEstacionamiento) {
		int numeroDeCelular = unEstacionamiento.getNumeroDeCelular();
		Celular celular = getCelularDeNumero(numeroDeCelular).get();
		double montoACobrar = this.precioACobrarPara(unEstacionamiento); 
		celular.decrementarCredito(montoACobrar);
	}
	
	public void cargarCredito(double montoACargar, int numeroDeCelular) {
		// Precondición: El celular del número dado debe estar registrado en el SEM.
		Celular celular = this.getCelularDeNumero(numeroDeCelular).get();
		celular.incrementarCredito(montoACargar);
	}
	
	public Notificacion finalizarEstacionamiento(String patente) {
		return this.getEstado().finalizarEstacionamiento(patente, this);
	}
	
	
	public Notificacion notificacionDeFinalizacionPorAppPara(RegistroDeEstacionamientoApp unEstacionamiento) {
		// Retorna la notificación de Finalización del estacionamiento por app dado. 
		LocalTime horaInicio = unEstacionamiento.getFechaYHoraDeInicio().toLocalTime();
		LocalTime horaFin = LocalTime.now(reloj);
		double cantHoras = horaInicio.until(horaFin, ChronoUnit.HOURS);
		double costoDeEstacionamiento = precioACobrarPara(unEstacionamiento);
		Notificacion notificacion = new NotificacionDeFin(horaInicio, horaFin, cantHoras, costoDeEstacionamiento);
		return notificacion;
	}
	
	protected void finalizarTodosLosEstacionamientos() {
		registrosDeEstacionamiento.forEach(r -> finalizarEstacionamiento(r.getPatente()));
	}
	
	public void registrarCompra(RegistroDeCompra registro) {
		registrosDeCompra.add(registro);
		if (registro instanceof RegistroDeRecarga) {
			notificar(registro); // Notifica a las entidades observadoras sobre el registro de recarga.
		}
	}
	
	public boolean esEstacionamientoVigente(String patente) {
		// Valida si ya se encuentra registrado algún estacionamiento vigente que tenga la patente dada.
		boolean existeElEstacionamientoVigenteEnElSistema = registrosDeEstacionamiento.stream()
				.anyMatch(r -> r.getPatente().equals(patente) && r.getVigencia());
		return existeElEstacionamientoVigenteEnElSistema;
	}
	
	public void registrarInfraccion(Infraccion infraccion) {
		infracciones.add(infraccion);
	}
	
	public void suscribir(EntidadObservadora entidadObservadora) {
		entidadesObservadoras.add(entidadObservadora);
	}
	
	public void desuscribir(EntidadObservadora entidadObservadora) {
		entidadesObservadoras.remove(entidadObservadora);
	}
	
	public void notificar(Object notificacion) {
		entidadesObservadoras.forEach(e -> e.update(notificacion));
	}
	
	protected void registrarCliente(Celular unCelular) { // solo para tests
		celulares.add(unCelular);
	}
	
	protected Optional<Celular> getCelularDeNumero(int numCel) {
		Optional<Celular> celular = celulares.stream()
				.filter(c -> c.getNumCel() == numCel)
				.findFirst();
		return celular;
	}
	
	public double getSaldoDe(int numeroDeCelular){
		// PRECONDICIÓN: el celular con numero dado está registrado
		Optional<Celular> celular = this.getCelularDeNumero(numeroDeCelular);
		Celular celularActual = celular.get();
		return celularActual.getCredito();
	}
	
	public Optional<RegistroDeEstacionamiento> getEstacionamientoDePatente(String unaPatente){
		return this.getRegistrosDeEstacionamiento().stream()
			.filter(r -> r.getPatente() == unaPatente)
			.findFirst();
	}
	
	protected String mensajeDeNotificacionServicioCerrado() {
		return "Operacion denegada: El servicio solo está disponible entre las "
				+this.horaApertura.toString()
				+" y las "
				+this.horaCierre.toString()
				+".";
	}
	
	protected String mensajeDeNotificacionEstacionamientoYaVigente(String patente) {
		return "Operación denegada: El vehículo de patente "+patente+" ya se encuentra vigente en el sistema.";
	}
	
	protected String mensajeDeNotificacionEstacionamientoNoVigente(String patente) {
		return "Operación denegada: El vehículo de patente "+patente+" no se encuentra vigente en el sistema.";
	}
	
	protected String mensajeDeNotificacionNumeroNoRegistrado(int numeroDeCelular) {
		return "Operación denegada: El número de teléfono "+numeroDeCelular+" no está registrado en el sistema."
				+"\nDebe registrarlo y cargar crédito en algún Punto de Venta.";
	}
	
	protected String mensajeDeNotificacionSaldoInsuficiente() {
		return "Saldo insuficiente. Estacionamiento no permitido.";
	}

	protected void agregarARegistrosDeEstacionamiento(RegistroDeEstacionamiento unEstacionamiento) {
		this.registrosDeEstacionamiento.add(unEstacionamiento);
	}

	protected void cobrarleSiCorrespondeA(RegistroDeEstacionamiento unEstacionamiento) {
		unEstacionamiento.serCobradoSiCorrespondePor(this);
	}

	public Notificacion notificacionDeFinalizacionQueCorrespondaPara(RegistroDeEstacionamiento unEstacionamiento) {
		return unEstacionamiento.notificarFinalizacionPara(this);
	}
}
