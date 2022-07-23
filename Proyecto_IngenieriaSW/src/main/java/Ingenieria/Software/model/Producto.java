package Ingenieria.Software.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity 
@Table(name="producto")
public class Producto implements Serializable {
	/**
	 * 
	 */
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProducto;
	private String nombre;
	private int precio;
	private String descripcion;
	private LocalDate fechaIngreso;
	private String fotografias;

	private int idCategoria;

	private int idUsuario;

	private int idEstadoProducto;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
	@JsonBackReference
	private Usuario usuario;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoProducto", referencedColumnName = "idEstadoProducto", insertable = false, updatable = false)
	@JsonBackReference
	private EstadoProducto estadoProducto;
	
	
	
	
	
	public Producto() {
		
	}
	
	
	public Producto(String nombre, int precio, String descripcion, LocalDate fechaIngreso,
			String fotografias, int idCategoria, int idUsuario, int idEstadoProducto) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.fechaIngreso = fechaIngreso;
		this.fotografias = fotografias;
		this.idCategoria = idCategoria;
		this.idUsuario = idUsuario;
		this.idEstadoProducto = idEstadoProducto;
	}

	public int getIdProducto() {
		return idProducto;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFotografias() {
		return fotografias;
	}


	public void setFotografias(String fotografias) {
		this.fotografias = fotografias;
	}

	public int getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(int idCategoria) {
		this.idCategoria=idCategoria;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdEstadoProducto() {
		return idEstadoProducto;
	}

	public void setIdEstadoProducto(int idEstadoProducto) {
		this.idEstadoProducto = idEstadoProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	private static final long serialVersionUID = 1L;
	
	
	
}
