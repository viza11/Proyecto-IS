package Ingenieria.Software.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.Chat;
import Ingenieria.Software.model.Usuario;
import Ingenieria.Software.repository.RepositoryChat;


@Service

public class ServiceChat {

	@Autowired 
	RepositoryChat repositoryChat;
	
	public void crearChat(Chat chat) {
		this.repositoryChat.save(chat);
		
	}
	
	public List<Chat> obtenerTodosLosChats(){
		return this.repositoryChat.findAll();
	}
	public Chat buscarChat(int idChat) {
		return this.repositoryChat.findById(idChat);
	}

	public void eliminarChat(int idChat) {
		this.repositoryChat.deleteById(idChat);;
		
	}
	
}
