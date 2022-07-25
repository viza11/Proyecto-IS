package Ingenieria.Software.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ingenieria.Software.model.Producto;
import Ingenieria.Software.repository.RepositoryProducto;
import Ingenieria.Software.service.ServiceProducto;
import Ingenieria.Software.utils.GeneradorPDF;

@RestController
@RequestMapping("/api/pdf")



public class ControllerProucto {
	
	@Autowired
	RepositoryProducto repositoryProducto;
	
	@Autowired
	ServiceProducto serviceProducto;
	
	@GetMapping(value="/PdfProducto",produces = MediaType.APPLICATION_PDF_VALUE)

	public ResponseEntity<InputStreamResource> customersReport() throws IOException {
		 List<Producto> costumers = this.serviceProducto.obtenerTodosProductos();
		 
		
		 
		  ByteArrayInputStream bis = GeneradorPDF.customerPDFReport(costumers);
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.add("Content-Disposition", "inline: filename-Cliente.pdf");
		  
		  return ResponseEntity 
				  .ok()
				  .headers(headers)
				  .contentType(MediaType.APPLICATION_PDF)
				  .body(new InputStreamResource(bis));
	}
	
	}
	
	
	
	


