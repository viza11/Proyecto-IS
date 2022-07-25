package Ingenieria.Software;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import Ingenieria.Software.service.EmailSenderService;

@SpringBootApplication
public class ProyectoIngenieriaSwApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoIngenieriaSwApplication.class, args);
	}
	
	
}
