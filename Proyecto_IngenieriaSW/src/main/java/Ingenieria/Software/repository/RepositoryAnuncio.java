package Ingenieria.Software.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Ingenieria.Software.model.Anuncio;
import Ingenieria.Software.model.Usuario;


public interface RepositoryAnuncio extends JpaRepository<Anuncio,Integer>{
	public Anuncio findById(int idAnuncio);
}
