package Ingenieria.Software.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Ingenieria.Software.model.Producto;
import Ingenieria.Software.repository.RepositoryProducto;


@Service
public class ServiceProducto {
	@Autowired 
	RepositoryProducto repositoryProducto;
	
	@Transactional
    public void crearProducto(Producto entity) {  
            this.repositoryProducto.save(entity);
    }
	
	public Producto buscarProducto(int idProducto) {
		return this.repositoryProducto.findById(idProducto);
	}
	
	public void eliminarProducto(int idProducto) {
		this.repositoryProducto.deleteById(idProducto);
	}
	
	public List<Producto> obtenerTodosProductos(){
		return this.repositoryProducto.findAll();
	}
	
	public Page<Producto> obtenerTodosProductos(Pageable pegeable){
		return this.repositoryProducto.findAll(pegeable);
	}
	
	public List<Producto> FiltrarDepartamento (int idDepartamento) {
		return this.repositoryProducto.findByIdDepartamento(idDepartamento);
	}
	
	public List<Producto> FiltrarNombreDepartamento (String nombre) {
		return this.repositoryProducto.findByNombreDepartamento(nombre);
	}
	
	public List<Producto> FiltrarCategoria (String nombre) {
		return this.repositoryProducto.findByCategoria(nombre);
	}
}
