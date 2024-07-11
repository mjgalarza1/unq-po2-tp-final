package ar.edu.unq.poo2.tpfinal.Celular;

public class Celular {
	
	double credito;
	
	int numCel;
	
	public Celular(int numCel) {
		
		this.numCel = numCel;
		
		this.credito = 0;
	}
	
	public int getNumCel() {
		return this.numCel;
	}
	
	public double getCredito(){
		return this.credito;
	}

	public void incrementarCredito(double montoACargar) {
		this.credito = credito+montoACargar;		
	}

	public void decrementarCredito(double montoACobrar) {
		this.credito = credito-montoACobrar;		
		
	}
	
	
	
}
