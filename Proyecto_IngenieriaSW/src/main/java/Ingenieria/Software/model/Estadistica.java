package Ingenieria.Software.model;

public class Estadistica {
	private String descripcion;
	private int cantidadEstado;
	
	
	
	public Estadistica() {
		super();
	}


	public Estadistica(String descripcion, int estado) {
		super();
		this.descripcion = descripcion;
		this.cantidadEstado = estado;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getCantidadEstado() {
		return cantidadEstado;
	}


	public void setCantidadEstado(int cantidadEstado) {
		this.cantidadEstado = cantidadEstado;
	}


	
			
}
