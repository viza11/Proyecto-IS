package Ingenieria.Software.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Ingenieria.Software.model.Anuncio;
import Ingenieria.Software.model.Categoria;
import Ingenieria.Software.model.Departamento;
import Ingenieria.Software.model.Producto;
import Ingenieria.Software.model.Usuario;
import Ingenieria.Software.repository.RepositoryAnuncio;
import Ingenieria.Software.repository.RepositoryProducto;
import Ingenieria.Software.service.EmailSenderService;
import Ingenieria.Software.service.MailService;
import Ingenieria.Software.service.ServiceAnuncio;
import Ingenieria.Software.service.ServiceCategoria;
import Ingenieria.Software.service.ServiceDepartamento;
import Ingenieria.Software.service.ServiceProducto;
import Ingenieria.Software.service.ServiceUsuario;
import Ingenieria.Software.utils.GeneradorPDF;
import Ingenieria.Software.utils.RenderizadorPaginas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component

@Controller
public class Controlador {
	
	@Autowired
	ServiceUsuario serviceUsuario;
	
	@Autowired
	ServiceDepartamento serviceDepartamento;
	
	@Autowired
	ServiceProducto serviceProducto;
	
	@Autowired
	ServiceAnuncio serviceAnuncio;
	
	@Autowired
	ServiceCategoria serviceCategoria;
	
	RepositoryProducto repositoryProducto;
	
	@Autowired
    private MailService mailService;
	
	@Autowired
	private EmailSenderService service;
	
	//============================================================================================
	//Seguridad
	//============================================================================================
	@GetMapping(value="/PdfProducto",produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> customersReport() throws IOException, MessagingException {
		
		 List<Producto> costumers = this.serviceProducto.obtenerTodosProductos();
		 List<Usuario> usuarios = this.serviceUsuario.obtenerTodosUsuarios();
		 List<Producto> productosCategoria = this.serviceProducto.obtenerTodosProductos();
		 List<Categoria> Categorias = this.serviceCategoria.obtenerTodasCategoria();
		 List<Producto> ListaVacia = new ArrayList<Producto>();
		 ArrayList<String> arreglo1 = new ArrayList<String>();
		 ArrayList<Integer> arreglo2 = new ArrayList<Integer>();
		 String empty_str = "";
		 Path directorioImagenes=Paths.get("src//main//resources//static/pdf");
		 String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		 Path rutaCompleta = Paths.get(rutaAbsoluta+"//");
		
		 /* 
		 Path directorioImagenes=Paths.get("src//main//resources//static/pdf");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			try {
				
				byte[] bystesImg = fotografias.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+fotografias.getOriginalFilename());
				Files.write(rutaCompleta, bystesImg);
				System.out.println(rutaCompleta.getFileName());
			}catch(IOException e) {
				e.printStackTrace();
			}

			service.sendEmailWithAttachment(mail,
					body,
					subject,
					"src//main//resources//static/pdf//"+fotografias.getOriginalFilename());
		 
		 */
		 
		 for(Categoria c : Categorias) {
				 arreglo1.add(c.getNombre()); }
		 
		 
		 for(Categoria c : Categorias) {
			 arreglo2.add(c.getIdCategoria());}
	 
		 
		 for(Usuario u : usuarios) {
	    
			 empty_str = u.getSuscripcion();
		        String[] split = empty_str.split(",");
		        for (int i=0; i<split.length; i++)
		            System.out.println(split[i]);
		            
		        System.out.println("Done");
		     
		   	 for(Producto p : productosCategoria) {
		   		 
				 if(p.getIdCategoria()==Integer.parseInt(u.getSuscripcion()))
					 
					 ListaVacia.add(p);
		   	 }
		   	
		   	
		   	GeneradorPDF.customerPDFReport(ListaVacia);
		   	
		   	
		 }
		 triggerMail2("hebermeza0@gmail.com");
		 
		 
			
		 
		 
		  ByteArrayInputStream bis = GeneradorPDF.customerPDFReport(ListaVacia);
		  HttpHeaders headers = new HttpHeaders();
		  headers.add("Content-Disposition", "inline: filename-Cliente.pdf");
		  
		  return ResponseEntity 
				  .ok()
				  .headers(headers)
				  .contentType(MediaType.APPLICATION_PDF)
				  .body(new InputStreamResource(bis));
	}
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@RequestMapping(value="/encriptar"  ,method=RequestMethod.GET)
	public String encriptarContrasenia(){
		List<Usuario> lista = this.serviceUsuario.obtenerTodosUsuarios();
		String contrasenia;
		for (Usuario e: lista) {
			if(e.getContrasenia().length()<16) {
			contrasenia = passwordEncoder.encode(e.getContrasenia());
			System.out.print("heber"+"pasa a"+contrasenia);
			e.setContrasenia(contrasenia);
			this.serviceUsuario.crear(e);
			}
			
		}
		return"login";
	}
	
	
	
	
	/***************************************************************USUARIO**********************************************************************/
	@GetMapping("/inicio")
	public String registrarCompania(){
		return "index";
	}
	@GetMapping("/")
	public String registrarCompani(){
		return "inicio";
	}
	
	
	@GetMapping("/terminos")
	public String terminos(){
		return "terminos";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	//============================================================================================
	//Usuarios
	//============================================================================================
	
	@RequestMapping(value= "/usuarios/iniciarSesion",method=RequestMethod.POST)
	public String crearUsuario(
								  @RequestParam(name = "primerNombre") String primerNombre,
								  @RequestParam(name = "segundoNombre") String segundoNombre, 
								  @RequestParam(name = "primerApellido") String primerApellido,
								  @RequestParam(name = "segundoApellido") String segundoApellido,
								  @RequestParam(name = "correoElectronico") String correoElectronico,
								  @RequestParam(name = "contrasenia") String contrasenia,
								  @RequestParam(name = "idDepartamento") int idDepartamento,
								  @RequestParam(name = "telefono") int telefono,
								  @RequestParam(name = "direccion") String direccion){
		/*int id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			String correoElectronico, String direccion, String rol, int idDepartamento*/
		try {
			String aux = "*";
			String aux2 ="usuario";
			boolean aux3 = true;
			Usuario usuario= new Usuario(primerNombre,segundoNombre,primerApellido,segundoApellido,correoElectronico,contrasenia,idDepartamento,telefono,direccion,aux2,aux,aux3);
			this.serviceUsuario.crear(usuario);
			return "redirect:/encriptar";
		}catch(Exception e) {
			return "/";
		}
	}

	
	
	/***************************************************************PRODUCTO**********************************************************************/

	@GetMapping("/productos/crearProductos")
    public String registrarProductos(Model model){
		List<Categoria> categoria = this.serviceCategoria.obtenerTodasCategoria();
	    model.addAttribute("categoria", categoria);
        return "test2";
    }
	
	
	
	@GetMapping("/productos/busqueda")
    public String busquedaProductos(){
        return "busqueda";
    }
	
	@GetMapping("/productos/inicio")
    public String inicioProductos(){
        return "inicio";
    }
	
	@GetMapping("/productos/producto")
    public String productoProductos(){
        return "producto";
    }
	
	@GetMapping("/productos/autenticacion")
    public String autenticacionProductos(){
        return "autenticacion";
    }
	
	@GetMapping("/busqueda")
    public String busqueda(){
        return "busqueda";
    }
	

    @RequestMapping(value= "/productos/ingresoProductos",method=RequestMethod.POST)
    public String crearUsuario(Authentication auth,
                                  @RequestParam(name = "nombre") String nombre,
                                  @RequestParam(name = "precio") int precio, 
                                  @RequestParam(name = "descripcion") String descripcion,
                                  @RequestParam(name = "fechaIngreso") @DateTimeFormat(iso = ISO.DATE) LocalDate fechaIngreso ,
                                  @RequestParam(name = "file") MultipartFile fotografias,
                                  @RequestParam(name = "idCategoria") int idCategoria,
                                  @RequestParam(name = "idEstadoProducto") int idEstadoProducto){
        /*String nombre, int precio, String descripcion, Date fechaIngreso,
            byte[] fotografias, int idCategoria, int idUsuario, String idEstadoProducto*/
    	if(!fotografias.isEmpty()) {
    		Path directorioImagenes=Paths.get("src//main//resources//static/imgt");
    		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
    		try {
    			
    			byte[] bystesImg = fotografias.getBytes();
    			Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+fotografias.getOriginalFilename());
    			Files.write(rutaCompleta, bystesImg);
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    		
    		
    		
    	}

        try {
        	String userName =auth.getName();
    		List<Usuario> lista = this.serviceUsuario.obtenerTodosUsuarios();
    		int  idUsuario =0;
    		for (Usuario e: lista) {
    			if(e.getCorreoElectronico().equals(userName)) {
    				idUsuario = e.getIdUsuario();
    			}
    			
    		}
            Producto producto= new Producto(nombre,precio,descripcion,fechaIngreso,fotografias.getOriginalFilename(),idCategoria,idUsuario,idEstadoProducto);
            this.serviceProducto.crearProducto(producto);
            return "redirect:/productos/listarProducto";
        }catch(Exception e) {
            return "/";
        }

    }
/******USARRRRRRRR ESSSSSSSSTEEEEEEEEEEEEEEEEEEEEEE*****/
    @RequestMapping(value= "/producto/listarProductos",method=RequestMethod.GET)
    public String listaProducto( Model model){
        List<Producto> productos = this.serviceProducto.obtenerTodosProductos();
        model.addAttribute("producto", productos);
        return "inicio";
    }
    
    @RequestMapping(value= "/productos/listarProducto",method=RequestMethod.GET)
    public String listarProducto(Model model){
    	
    	
        List<Producto> productos = this.serviceProducto.obtenerTodosProductos();
        model.addAttribute("producto", productos);
        
        return "autenticacion";
    }
 
    
    //============================================================================================
    //Categorias
    //============================================================================================

  @GetMapping("/categorias/seleccionarCategorias")
  public String seleccionarCategorias(){
      return "seleccion";
  }
  
  @RequestMapping(value= "/categorias/seleccionarCategoria",method=RequestMethod.POST)
	public String seleccionarCategoria(Authentication auth,
								  
								  @RequestParam(name = "suscripcion") String suscripcion){
		/*int id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			String correoElectronico, String direccion, String rol, int idDepartamento*/
		try {
			List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
 
			String c =auth.getName();
			String ss= suscripcion;
			for (Usuario e: usuarios) {
				if(e.getCorreoElectronico().equals(c)) {
					e.setSuscripcion(ss);
					this.serviceUsuario.crear(e);
				}
			}
			return "redirect:/productos/listarProducto";
		}catch(Exception e) {
			return "/";
		}
	}
  	
  @GetMapping("/user")
  public String mostrarUsuario(Authentication auth){
	  String userName =auth.getName();
	  System.out.println("Usuario:"+userName);
      return "userName";
  }
  
  @GetMapping("/productos/listaDeseo")
  public String listaDeseo(Authentication auth){
      return "exito";
  }
  
  public String nP= "";
  
  @PostMapping(value = "/agregar/listaDeseo")
  public String agregarAlCarrito(Authentication auth, @RequestParam(name = "idProducto") String idProducto) {
	  String userName =auth.getName();  
	  
	  try {
			List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
			for (Usuario e: usuarios) {
				String str1=e.getListaDeDeseos();
				String str2= null;
				if(e.getListaDeDeseos().equals("*")) {
					nP = idProducto ;
				}else {
					nP = e.getListaDeDeseos() + ','+ idProducto ;
				}
				
				String s= e.getCorreoElectronico();
				if(e.getCorreoElectronico().equals(userName)) {
					e.setListaDeDeseos(nP);
					this.serviceUsuario.crear(e);
				}
			}
			return "redirect:/productos/listarProducto";
		}catch(Exception e) {
			return"login";
		}
  }
  
	//============================================================================================
	//Administradores
	//============================================================================================
  @RequestMapping(value= "/login/inicio",method=RequestMethod.GET)
  public String seleccionInicio(Authentication auth,Model model){
	  String correo = auth.getName();
	  String retorno= "redirect:/pagina/paginaPrincipal";

	  List<Producto> productos = this.serviceProducto.obtenerTodosProductos();
      model.addAttribute("producto", productos);
	  
			List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
			for (Usuario e: usuarios) {
				if(e.getCorreoElectronico().equals(correo)&&e.getRol().equals("admin")) {
					retorno= "redirect:/administradores/paginaInicio";
				}	
			}
			return retorno;    
  }
  
  
  @GetMapping("/administradores/paginaPrincipal")
  public String adminsitradoresPrincipal(){
      return "desarrolladorPrincipal";
  }
  
  @GetMapping("/pagina/paginaPrincipal")
  public String clientesPrincipal(@RequestParam (name="page",defaultValue="0")int page, Model model){
	  Pageable userPageable = PageRequest.of(page, 3);
	  Page<Producto> producto= this.serviceProducto.obtenerTodosProductos(userPageable);
	  RenderizadorPaginas<Producto> renderizadorPaginas = new RenderizadorPaginas<Producto>("autenticacion",producto);
      model.addAttribute("page", renderizadorPaginas);
      model.addAttribute("producto", producto);
      return "autenticacion";
  }
  
  @GetMapping("/administradores/paginaInicio")
  public String adminsitradoresInicio(Model model){
	 
      return "autenticacionAdministrador";
  }
  
  @GetMapping("/administradores/actualizarPlazo")
  public String adminsitradoresAnuncio(Model model){
	  Anuncio anuncios = this.serviceAnuncio.BuscarAnuncio(1);
      model.addAttribute("anuncio", anuncios);
      return "actualizarPlazo";
  }
  
  @PostMapping(value = "/administradores/actualizarDias")
  public String actualizarPlazo(@RequestParam(name = "diasExpiracion") int diasExpiracion) {
	  List<Anuncio> anuncios=this.serviceAnuncio.obtenerTodosAnuncios();
		for (Anuncio e: anuncios) {
			int expiracionActual=diasExpiracion;
			e.setDiasExpiracion(expiracionActual);
			this.serviceAnuncio.crearAnuncio(e);
			
		}
	  
	  return "redirect:/administradores/actualizarPlazo";
	  
  }
  
  @GetMapping(value = "/administradores/categorias")
  public String adminsitradoresCategoria(Model model){
	  List<Categoria> categoria = this.serviceCategoria.obtenerTodasCategoria();
      model.addAttribute("categoria", categoria);
      return "categoriaAdministracion";
  }
  
  @PostMapping(value = "/administradores/eliminarCategoria")
  public String eliminarCategoria(@RequestParam(name = "idCategoria") int idCategoria) {
	  this.serviceCategoria.eliminarCategoria(idCategoria);
	  
	  return "redirect:/administradores/categorias";
	  
  }
  
  @GetMapping(value ="/administradores/actualizarCategoria")
  public String adminsitradoresActualizarCategoria(Model model){
      List<Categoria> categoria = this.serviceCategoria.obtenerTodasCategoria();
      model.addAttribute("categoria", categoria);
      return "actualizarCategoria";
  }


  @PostMapping(value ="/administradores/actualizarCategoria")
  public String actualizarCategoria(@RequestParam(name = "idCategoria") int idCategoria,
          @RequestParam(name = "nombre") String nombre) {
      List<Categoria> categoria=this.serviceCategoria.obtenerTodasCategoria();
        for (Categoria c: categoria) {
            if(c.getIdCategoria()==idCategoria) {
            String categoriaActual=nombre;
            c.setNombre(categoriaActual);
            this.serviceCategoria.crearCategoria(c);
            
        }
        }
      return "redirect:/administradores/actualizarCategoria";

  }
  
  @PostMapping(value ="/administradores/administrarUsuario")
  public String bannearUsuario(@RequestParam(name = "idUsuario") int idUsuario,
          @RequestParam(name = "activo") boolean activo) {
      List<Usuario> usuario=this.serviceUsuario.obtenerTodosUsuarios();
        for (Usuario u: usuario) {
            if(u.getIdUsuario()==idUsuario) {
            boolean EstadoUsuario=activo;
            u.setActivo(EstadoUsuario);
            this.serviceUsuario.crear(u);
        }
        }
        return "redirect:/administradores/actualizarUsuarios";
  }
@GetMapping(value ="/administradores/actualizarUsuarios")
  public String adminsitrarUsuarios(Model model){
      List<Usuario> usuarios = this.serviceUsuario.obtenerTodosUsuarios();
      model.addAttribute("usuarios", usuarios);
      return "bannearUsuarios";
  }


//CORREO
@GetMapping("/mail")
public String index(){
    return "send_mail_view";
}
/*
@PostMapping("/sendMail")
public String sendMail(@RequestParam("name") String name, @RequestParam("mail") String mail, @RequestParam("subject") String subject, @RequestParam("body") String body){

    String message = body +"\n\n Datos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
    mailService.sendMail("testspringcorrreo@gmail.com", mail, subject, message);
    
    return "send_mail_view";
}*/

@PostMapping("/sendMail")
public String triggerMail(Authentication auth,@RequestParam("name") String name, 
		@RequestParam("mail") String mail, 
		@RequestParam("subject") String subject,
		@RequestParam(name = "file") MultipartFile fotografias,
		@RequestParam("body") String body) throws MessagingException {
	Path directorioImagenes=Paths.get("src//main//resources//static/pdf");
	String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
	try {
		
		byte[] bystesImg = fotografias.getBytes();
		Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+fotografias.getOriginalFilename());
		Files.write(rutaCompleta, bystesImg);
		System.out.println(rutaCompleta.getFileName());
	}catch(IOException e) {
		e.printStackTrace();
	}

	service.sendEmailWithAttachment(mail,
			body,
			subject,
			"src//main//resources//static/pdf//"+fotografias.getOriginalFilename());
	return "send_mail_view";

}

public void triggerMail2(String mail) throws MessagingException {
	

	service.sendEmailWithAttachment("hebermeza0@gmail.com",
			"test",
			"test",
			"src//main//resources//static/pdf//10.pdf");
	System.out.println("entro");
}


public static byte[] toByteArray(InputStream in) throws IOException
{
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];
    int len;

    // lee los bytes del flujo de entrada y los almacena en el búfer
    while ((len = in.read(buffer)) != -1)
    {
        // escribir bytes del búfer en el flujo de salida
        os.write(buffer, 0, len);
    }

    return os.toByteArray();
}

public static void main(String[] args) throws IOException
{
    // flujo de entrada
    InputStream in = new ByteArrayInputStream("Techie Delight"
                                    .getBytes(StandardCharsets.UTF_8));

    // array de bytes
    byte[] bytes = toByteArray(in);
    System.out.println(new String(bytes));
}

//Tareas Autoprogramadas

@Scheduled(cron = "0 * * ? * *")
public void scheduleTaskUsingCronExpression() throws MessagingException, IOException {
	
	
	List<Usuario> usuarios = this.serviceUsuario.obtenerTodosUsuarios();
    List<Producto> productosCategoria = this.serviceProducto.obtenerTodosProductos();
    List<Producto> ListaVacia = new ArrayList<Producto>();
    
	
    
    for(Usuario u : usuarios) {


          for(Producto p : productosCategoria) {
             String empty_str = ""; 
             empty_str = u.getSuscripcion();
             String[] split = empty_str.split(",");
             for (int i=0; i<split.length; i++) {
                 if(p.getIdCategoria()==Integer.parseInt(split[i]))

                     ListaVacia.add(p);
                   System.out.println("Done");
             }

          }

          GeneradorPDF.customerPDFReport(ListaVacia);
          service.sendEmailWithAttachment(u.getCorreoElectronico(),
                  "test",
                  "test",
                  "src//main//resources//static/pdf//10.pdf");
          System.out.println("entro");

          ListaVacia.clear();
   }
}
}


