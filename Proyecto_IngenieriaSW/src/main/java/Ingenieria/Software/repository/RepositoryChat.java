package Ingenieria.Software.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import Ingenieria.Software.model.Chat;
import Ingenieria.Software.model.Usuario;

public interface RepositoryChat extends JpaRepository<Chat,Integer>{
	public Chat findById(int idChat);
}
