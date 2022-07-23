package Ingenieria.Software.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Ingenieria.Software.model.Producto;

public interface RepositoryProducto extends JpaRepository<Producto, Integer>{
	public Producto findById(int idProducto);
	public Producto deleteById(int idProducto);
}
