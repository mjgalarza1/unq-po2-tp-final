package ar.edu.unq.poo2.tpfinal.Sem;
import static org.junit.jupiter.api.DynamicTest.stream;

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
	}
		
	// CONSTRUCTOR para setear los HORARIOS de apertura y cierre, el PRECIO, y un RELOJ para testear.
	public SEM(LocalTime horaApertura, LocalTime horaCierre, double precioPorHora, Set<ZonaDeEstacionamiento> zonasDeEstacionamiento, Clock reloj) {
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
	}
	
	
	
	// GETTERS
	public LocalTime getHoraApertura() {
		return horaApertura;
	}
	
	public LocalTime getHoraCierre() {
		return horaCierre;
	}
	
	public double getPrecioPorHora() {
		return precioPorHora;
	}
	
	public Set<Celular> getCelulares() {
		return celulares;
	}
	
	public Set<ZonaDeEstacionamiento> getZonasDeEstacionamiento() {
		return zonasDeEstacionamiento;
	}
	
	public List<RegistroDeEstacionamiento> getRegistrosDeEstacionamiento() {
		return registrosDeEstacionamiento;
	}
	
	public List<RegistroDeCompra> getRegistrosDeCompra() {
		return registrosDeCompra;
	}
	
	public List<Infraccion> getInfracciones() {
		return infracciones;
	}
	
	public Set<EntidadObservadora> getEntidadesObservadoras() {
		return entidadesObservadoras;
	}
	
	// MÉTODOS
	public Notificacion registrarEstacionamiento(RegistroDeEstacionamiento unEstacionamiento) {
		// Delega el problema a los casos concretos (si es por app o si es puntual).
		String patente = unEstacionamiento.getPatente();
		boolean yaHayUnEstacionamientoVigente = esEstacionamientoVigente(patente);
		Notificacion notificacion;
		
		if (!elServicioEstaAbierto()) {
			notificacion = new NotificacionMensajePersonalizado(this.mensajeDeNotificacionServicioCerrado());
		} else if (yaHayUnEstacionamientoVigente) {
			notificacion = new NotificacionMensajePersonalizado(this.mensajeDeNotificacionEstacionamientoYaVigente(patente));
		} else if (unEstacionamiento.esDeApp()) {
			RegistroDeEstacionamientoApp unEstacionamientoApp = (RegistroDeEstacionamientoApp) unEstacionamiento; 
			notificacion = registrarEstacionamientoPorApp(unEstacionamientoApp);
		} else {
			RegistroDeEstacionamientoPuntual unEstacionamientoPuntual = (RegistroDeEstacionamientoPuntual) unEstacionamiento;
			notificacion = registrarEstacionamientoPuntual(unEstacionamientoPuntual);
		}
		return notificacion;
	}
	
	private Notificacion registrarEstacionamientoPuntual(RegistroDeEstacionamientoPuntual unEstacionamiento) {
		// Retorna una notificación, a pesar de que el Punto de Venta no hará nada con él.
		// Los registros de estacionamiento puntuales retornan notificaciones vacías (es decir, null).
		agregarZonaDeEstacionamiento(unEstacionamiento);
		registrosDeEstacionamiento.add(unEstacionamiento);
		notificar(unEstacionamiento); // Notifica a las entidades observadoras que el estacionamiento fue registrado.
		return null;
	}
	
	private Notificacion registrarEstacionamientoPorApp(RegistroDeEstacionamientoApp unEstacionamiento) {
		// Guarda el registro del estacionamiento en el sistema
		// SOLAMENTE si el numero de celular del estacionamiento dado tiene saldo SUFICIENTE y
		// si la patente del estacionamiento dado NO se encuentra ya vigente en el sistema,
		// retornando la notificación correspondiente.
		Notificacion notificacion;
		int numeroDeCelular = unEstacionamiento.getNumeroDeCelular(); 
		Optional<Celular> celular = this.getCelularDeNumero(numeroDeCelular);
		LocalTime horaActual = LocalTime.now(reloj);
		
		if (celular.isEmpty()) {
			notificacion = new NotificacionMensajePersonalizado(this.mensajeDeNotificacionNumeroNoRegistrado(numeroDeCelular));
		} else if (celular.get().getCredito() <= 0) {
			notificacion = new NotificacionMensajePersonalizado(this.mensajeDeNotificacionSaldoInsuficiente());
		} else {
			double saldoDelCliente = celular.get().getCredito();
			agregarZonaDeEstacionamiento(unEstacionamiento);
			registrosDeEstacionamiento.add(unEstacionamiento);
			notificar(unEstacionamiento); // Notifica a las entidades observadoras sobre el registro del estacionamiento.
			notificacion = new NotificacionDeInicioExitoso(horaActual, this.getHoraMaximaPara(saldoDelCliente, horaActual));
		}
		return notificacion;
	}
	
	private void agregarZonaDeEstacionamiento(RegistroDeEstacionamiento unEstacionamiento) {
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
	
	private void cobrarleA(RegistroDeEstacionamientoApp unEstacionamiento) {
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
		// Busca si existe un estacionamiento vigente que tenga la patente dada,
		// y si existe finalizarlo y retornar la notificacion debida.
		// Si NO existe, retornar una notificacion avisando que no existe
		// (O una notificacion null para el caso de ser Puntual).
		
		boolean hayUnEstacionamientoVigente = esEstacionamientoVigente(patente);
		Notificacion notificacion = null;
		Optional<RegistroDeEstacionamiento> estacionamiento = registrosDeEstacionamiento.stream()
				.filter(r -> r.getPatente().equals(patente) && r.getVigencia())
				.findFirst();
		
		if (!elServicioEstaAbierto()) {
			notificacion = new NotificacionMensajePersonalizado(this.mensajeDeNotificacionServicioCerrado());
		} else if (!hayUnEstacionamientoVigente) {
			notificacion = new NotificacionMensajePersonalizado(this.mensajeDeNotificacionEstacionamientoNoVigente(patente));
		} else {
			RegistroDeEstacionamiento estacionamientoModificado = estacionamiento.get();
			estacionamientoModificado.setVigencia(false);
			notificar(estacionamientoModificado); // Notifica a las entidades observadoras sobre la finalizacion del estacionamiento.
			if (estacionamientoModificado.esDeApp()) {
				RegistroDeEstacionamientoApp estacionamientoApp = (RegistroDeEstacionamientoApp) estacionamientoModificado;
				cobrarleA(estacionamientoApp);
				notificacion = this.notificacionDeFinalizacionPorAppPara(estacionamientoApp);
			}
		}
		return notificacion;
	}
	
	private Notificacion notificacionDeFinalizacionPorAppPara(RegistroDeEstacionamientoApp unEstacionamiento) {
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
}
