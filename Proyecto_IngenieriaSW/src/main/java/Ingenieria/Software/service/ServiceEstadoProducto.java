package Ingenieria.Software.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.EstadoProducto;
import Ingenieria.Software.model.Usuario;
import Ingenieria.Software.repository.RepositoryEstadoProducto;

@Service
public class ServiceEstadoProducto {
	@Autowired 
	RepositoryEstadoProducto repositoryEstadoProducto;
	
	public void crearEstadoProducto(EstadoProducto estadoProducto) {
		this.repositoryEstadoProducto.save(estadoProducto);
	}
	
	public List<EstadoProducto> obtenerTodosEstadoProducto(){
		return this.repositoryEstadoProducto.findAll();
	}
	
	public void BuscarEstadoProducto(int idEstado) {
		this.repositoryEstadoProducto.findById(idEstado);
		
	}
}
