package Ingenieria.Software.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="departamento")

public class Departamento {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDepartamento;
	private String nombre;
	
	//Constructor por defecto 
	
	public Departamento() {}
	
	
	//Constructor
	
	public Departamento(String nombre) {
		super();
		this.nombre = nombre;
	}

	//Getters and Setters




	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
}

