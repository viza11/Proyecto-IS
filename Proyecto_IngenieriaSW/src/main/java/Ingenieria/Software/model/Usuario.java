package Ingenieria.Software.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity 
@Table(name="usuario")


public class Usuario {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String correoElectronico;
	private String contrasenia;
	private int idDepartamento;
	private int telefono;
	private String denuncia;
	
	private int calificacion;
	private String direccion;
	private String rol;
	private String  suscripcion;
	private String listaDeDeseos;
	private boolean activo;
	
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDepartamento", referencedColumnName = "idDepartamento", insertable = false, updatable = false)
	@JsonBackReference
	private Departamento departamento;
	
	
	
	//Constructor por defecto 
	
	public Usuario() {}
	
	//Constructor
	
	

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}
	public Usuario( String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String correoElectronico, String contrasenia, int idDepartamento, int telefono,
			String direccion,String rol,String listaDeDeseos, boolean activo,int calificacion) {
		super();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.correoElectronico = correoElectronico;
		this.contrasenia = contrasenia;
		this.idDepartamento = idDepartamento;
		this.telefono = telefono;
		this.direccion = direccion;
		this.rol=rol;
		this.listaDeDeseos = listaDeDeseos;
		this.activo = activo;
		this.calificacion = calificacion;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getIdDepartamento() {
		return idDepartamento;
	}

	public String getSuscripcion() {
		return suscripcion;
	}

	public void setSuscripcion(String suscripcion) {
		this.suscripcion = suscripcion;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public String getListaDeDeseos() {
		return listaDeDeseos;
	}

	public void setListaDeDeseos(String listaDeDeseos) {
		this.listaDeDeseos = listaDeDeseos;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getRol() {
		return rol;
	}
	
	public String getDenuncia() {
		return denuncia;
	}

	public void setDenuncia(String denuncia) {
		this.denuncia = denuncia;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	

}
