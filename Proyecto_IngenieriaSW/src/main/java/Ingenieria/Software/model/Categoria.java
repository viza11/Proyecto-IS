package Ingenieria.Software.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity 
@Table(name="categoria")

public class Categoria {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)

	private int idCategoria;
	private String nombre;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria", insertable = false, updatable = false)
	@JsonBackReference
	private Producto producto;
	
	
	//Constructor por defecto
	public Categoria(){}
	
	
	
	//Constructor
	public Categoria(String nombre) {
		super();
		this.nombre = nombre;
	}
	

	
	
	//getters and setters
	public int getIdCategoria() {
		return idCategoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
