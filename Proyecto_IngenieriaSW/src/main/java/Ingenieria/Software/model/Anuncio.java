package Ingenieria.Software.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="anuncios")
public class Anuncio {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAnuncio;
	private int diasExpiracion;
	private String anuncio;
	
	
	
	public Anuncio() {
		super();
	}
	
	

	public Anuncio(int diasExpiracion, String anuncio) {
		super();
		this.diasExpiracion = diasExpiracion;
		this.anuncio = anuncio;
	}



	public int getIdAnuncio() {
		return idAnuncio;
	}
	
	public int getDiasExpiracion() {
		return diasExpiracion;
	}
	public void setDiasExpiracion(int diasExpiracion) {
		this.diasExpiracion = diasExpiracion;
	}
	public String getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(String anuncio) {
		this.anuncio = anuncio;
	}
	
	
	
}
