package Ingenieria.Software.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.Anuncio;
import Ingenieria.Software.model.Categoria;
import Ingenieria.Software.repository.RepositoryCategoria;

@Service
public class ServiceCategoria {
	
	@Autowired 
	RepositoryCategoria repositoryCategoria;
	
	//Funciones
	
	public void crearCategoria(Categoria categoria) {
		this.repositoryCategoria.save(categoria);
		
	}
	
	public void BuscarCategoria(int idCategoria) {
		this.repositoryCategoria.findById(idCategoria);
		
	}
	
	public void eliminarCategoria(int idCategoria) {
		this.repositoryCategoria.deleteById(idCategoria);
		
	}
	

	public List<Categoria> obtenerTodasCategoria(){
		return this.repositoryCategoria.findAll();
	}
	
	
}
