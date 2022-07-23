package Ingenieria.Software.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import Ingenieria.Software.model.Usuario;

public interface RepositoryUsuario extends JpaRepository<Usuario,Integer> {
	public Usuario findById(int idUsuario);
}
