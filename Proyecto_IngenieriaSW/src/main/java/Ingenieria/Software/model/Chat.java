package Ingenieria.Software.model;

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
@Table(name="chat")

public class Chat {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int idChat;
	private String MensajeEmisor;
	private String MensajeReceptor;
	private int idUsuarioEmisor;
	private int idUsuarioReceptor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioEmisor", referencedColumnName = "idUsuario", insertable = false, updatable = false)
	@JsonBackReference
	private Usuario usuario;
	
	
	
	public Chat() {
		super();
	}
	

	
	public Chat(String mensajeEmisor, String mensajeReceptor, int idUsuarioEmisor,int idUsuarioReceptor) {
		super();
		this.MensajeEmisor = mensajeEmisor;
		this.MensajeReceptor = mensajeReceptor;
		this.idUsuarioEmisor =idUsuarioEmisor;
		this.idUsuarioReceptor=idUsuarioReceptor;
		
	}
	
	


	public int getIdUsuarioEmisor() {
		return idUsuarioEmisor;
	}



	public void setIdUsuarioEmisor(int idUsuarioEmisor) {
		this.idUsuarioEmisor = idUsuarioEmisor;
	}



	public int getIdUsuarioReceptor() {
		return idUsuarioReceptor;
	}



	public void setIdUsuarioReceptor(int idUsuarioReceptor) {
		this.idUsuarioReceptor = idUsuarioReceptor;
	}



	public String getMensajeEmisor() {
		return MensajeEmisor;
	}

	public void setMensajeEmisor(String mensajeEmisor) {
		MensajeEmisor = mensajeEmisor;
	}

	public String getMensajeReceptor() {
		return MensajeReceptor;
	}

	public void setMensajeReceptor(String mensajeReceptor) {
		MensajeReceptor = mensajeReceptor;
	}
	
	
	
}
