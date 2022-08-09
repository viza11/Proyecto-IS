package Ingenieria.Software.model;

public class Estadistica2 {
	private String categoria;
	private int cantidadCategoria;
	
	
	
	
	public Estadistica2() {
		super();
	}

	public Estadistica2(String categoria, int cantidadCategoria) {
		super();
		this.categoria = categoria;
		this.cantidadCategoria = cantidadCategoria;
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getCantidadCategoria() {
		return cantidadCategoria;
	}
	public void setCantidadCategoria(int cantidadCategoria) {
		this.cantidadCategoria = cantidadCategoria;
	}
	
	
	
	
}
