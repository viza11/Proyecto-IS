package Ingenieria.Software.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.EstadoProducto;
import Ingenieria.Software.repository.RepositoryEstadoProducto;

@Service
public class ServiceEstadoProducto {
	@Autowired 
	RepositoryEstadoProducto repositoryEstadoProducto;
	
	public void crearEstadoProducto(EstadoProducto estadoProducto) {
		this.repositoryEstadoProducto.save(estadoProducto);
	}
}
