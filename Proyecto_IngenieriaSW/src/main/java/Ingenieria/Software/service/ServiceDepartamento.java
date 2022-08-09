package Ingenieria.Software.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.Departamento;
import Ingenieria.Software.repository.RepositoryDepartamento;

@Service
public class ServiceDepartamento {
	@Autowired 
	RepositoryDepartamento repositoryDepartamento;
	
	
	public void crearDepartamentO(Departamento departamento) {
		
		this.repositoryDepartamento.save(departamento);
		
	}
	
	public List<Departamento> obtenerTodosLosDepartamentos(){
		return this.repositoryDepartamento.findAll();
	}

}
