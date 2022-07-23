package Ingenieria.Software.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.Anuncio;
import Ingenieria.Software.model.Usuario;
import Ingenieria.Software.repository.RepositoryAnuncio;


@Service
public class ServiceAnuncio {
	@Autowired 
	RepositoryAnuncio repositoryAnuncio;
	
	public void crearAnuncio(Anuncio categoria) {
		this.repositoryAnuncio.save(categoria);
		
	}
	
	public Anuncio BuscarAnuncio(int idAnuncio) {
		return this.repositoryAnuncio.findById(idAnuncio);
		
	}
	
	public void eliminarAnuncio(int idCategoria) {
		this.repositoryAnuncio.deleteById(idCategoria);
		
	}
	
	public List<Anuncio> obtenerTodosAnuncios(){
		return this.repositoryAnuncio.findAll();
	}
	
}
