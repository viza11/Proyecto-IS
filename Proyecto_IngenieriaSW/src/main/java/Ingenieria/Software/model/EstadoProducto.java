package Ingenieria.Software.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="estadoProducto")
public class EstadoProducto {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEstadoProducto;
	private String nombre;
	
	public EstadoProducto() {
		
	}
	
	public EstadoProducto(int idEstadoProducto, String nombre) {
		super();
		this.idEstadoProducto = idEstadoProducto;
		this.nombre = nombre;
	}

	public int getIdEstadoProducto() {
		return idEstadoProducto;
	}

	public void setIdEstadoProducto(int idEstadoProducto) {
		this.idEstadoProducto = idEstadoProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
