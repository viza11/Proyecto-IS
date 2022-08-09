package Ingenieria.Software.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ControllerError implements ErrorController {
	
	
	

	public String getErrorPath() {
		return "/error";
	}


}
