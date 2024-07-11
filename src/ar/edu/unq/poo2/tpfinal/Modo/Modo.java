package ar.edu.unq.poo2.tpfinal.Modo;

import ar.edu.unq.poo2.tpfinal.AppSem.AppSem;

public interface Modo {

	public abstract void finalizarSiCorrespondeYNotificar(AppSem app);

	public abstract void registrarSiCorrespondeYNotificar(AppSem app);
}