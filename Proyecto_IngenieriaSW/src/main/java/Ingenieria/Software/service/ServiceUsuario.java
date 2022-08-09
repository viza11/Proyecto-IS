package Ingenieria.Software.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Ingenieria.Software.model.Usuario;
import Ingenieria.Software.repository.RepositoryUsuario;

@Service

public class ServiceUsuario {
	
	@Autowired 
	RepositoryUsuario repositoryUsuario;
	
	//Funciones
	 
	public void crear(Usuario usuario) {
		this.repositoryUsuario.save(usuario);
		
	}
	
	public List<Usuario> obtenerTodosUsuarios(){
		return this.repositoryUsuario.findAll();
	}
	
	public void BuscarUsuario(int idUsuario) {
		this.repositoryUsuario.findById(idUsuario);
		
	}
	
	public Usuario buscarUsuario(int idUsuario) {
		return this.repositoryUsuario.findById(idUsuario);
	}

}
