package Ingenieria.Software.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import Ingenieria.Software.model.Chat;
import Ingenieria.Software.model.Departamento;
import Ingenieria.Software.model.Estadistica;
import Ingenieria.Software.model.Estadistica2;
import Ingenieria.Software.model.EstadisticaGeneral;
import Ingenieria.Software.model.EstadoProducto;
import Ingenieria.Software.model.Producto;
import Ingenieria.Software.model.Usuario;
import Ingenieria.Software.model.idAux;
import Ingenieria.Software.model.msgEmisor;
import Ingenieria.Software.model.msgReceptor;
import Ingenieria.Software.repository.RepositoryAnuncio;
import Ingenieria.Software.repository.RepositoryProducto;
import Ingenieria.Software.repository.RepositoryDepartamento;
import Ingenieria.Software.service.EmailSenderService;
import Ingenieria.Software.service.MailService;
import Ingenieria.Software.service.ServiceAnuncio;
import Ingenieria.Software.service.ServiceCategoria;
import Ingenieria.Software.service.ServiceChat;
import Ingenieria.Software.service.ServiceDepartamento;
import Ingenieria.Software.service.ServiceEstadoProducto;
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
	
	@Autowired
	ServiceEstadoProducto serviceEstadoProducto;
	
	@Autowired
	ServiceChat serviceChat;
	
	RepositoryProducto repositoryProducto;
	

	
	@Autowired
	private EmailSenderService service;
	
	//============================================================================================
	//Seguridad
	//============================================================================================
	@GetMapping(value="/PdfProducto",produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> customersReport() throws IOException, MessagingException {
		

		 List<Usuario> usuarios = this.serviceUsuario.obtenerTodosUsuarios();
		 List<Producto> productosCategoria = this.serviceProducto.obtenerTodosProductos();
		 List<Categoria> Categorias = this.serviceCategoria.obtenerTodasCategoria();
		 List<Producto> ListaVacia = new ArrayList<Producto>();
		 ArrayList<String> arreglo1 = new ArrayList<String>();
		 ArrayList<Integer> arreglo2 = new ArrayList<Integer>();
		 String empty_str = "";
		
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
	public String registrarCompani(@RequestParam (name="page",defaultValue="0")int page,Model model){
		Pageable userPageable = PageRequest.of(page, 6);
		  Page<Producto> producto= this.serviceProducto.obtenerTodosProductos(userPageable);
		  RenderizadorPaginas<Producto> renderizadorPaginas = new RenderizadorPaginas<Producto>("/",producto);
	      model.addAttribute("page", renderizadorPaginas);
	      model.addAttribute("producto", producto);
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
			int aux4=0;
			Usuario usuario= new Usuario(primerNombre,segundoNombre,primerApellido,segundoApellido,correoElectronico,contrasenia,idDepartamento,telefono,direccion,aux2,aux,aux3,aux4);
			this.serviceUsuario.crear(usuario);
			return "redirect:/encriptar";
		}catch(Exception e) {
			return "/";
		}
	}

	
	
	/***************************************************************PRODUCTO**********************************************************************/

	@GetMapping("/productos/crearProductos")
    public String registrarProductos(Model model,Model model2){
		List<Categoria> categoria = this.serviceCategoria.obtenerTodasCategoria();
		List<Departamento> departamento = this.serviceDepartamento.obtenerTodosLosDepartamentos();
	    model.addAttribute("categoria", categoria);
	    model.addAttribute("departamento",departamento);
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
                                  @RequestParam(name = "file") MultipartFile fotografias,
                                  @RequestParam(name = "idCategoria") int idCategoria,
                                  @RequestParam(name = "idEstadoProducto") int idEstadoProducto,
                                  @RequestParam(name = "idDepartamento") int idDepartamento){
    	
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
    		LocalDate fechaIngreso = LocalDate.now();
            Producto producto= new Producto(nombre,precio,descripcion,fechaIngreso,fotografias.getOriginalFilename(),idCategoria,idUsuario,idEstadoProducto,idDepartamento);
            this.serviceProducto.crearProducto(producto);
            return "redirect:/pagina/paginaPrincipal";
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
    	
    	
        List<Producto> producto = this.serviceProducto.obtenerTodosProductos();
        model.addAttribute("producto", producto);
        
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
			return "redirect:/pagina/paginaPrincipal";
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

				if(e.getListaDeDeseos().equals("*")) {
					nP = idProducto ;
				}else {
					nP = e.getListaDeDeseos() + ','+ idProducto ;
				}

				if(e.getCorreoElectronico().equals(userName)) {
					e.setListaDeDeseos(nP);
					this.serviceUsuario.crear(e);
				}
			}
			return "redirect:/pagina/paginaPrincipal";
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
	  Pageable userPageable = PageRequest.of(page, 6);
	  Page<Producto> producto= this.serviceProducto.obtenerTodosProductos(userPageable);
	  RenderizadorPaginas<Producto> renderizadorPaginas = new RenderizadorPaginas<Producto>("/pagina/paginaPrincipal",producto);
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
      List<Usuario> usuariosDenunciados = new ArrayList<Usuario>();
      for (Usuario u: usuarios) {
    	  if(u.getDenuncia() != null) {
    		  usuariosDenunciados.add(u);
    	  }
      }
      model.addAttribute("usuarios", usuariosDenunciados);
      return "bannearUsuarios";
}


//CORREO
@GetMapping("/mail")
public String index(){
    return "send_mail_view";
}




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
	

	service.sendEmailWithAttachment(mail,
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
/*
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
*/

// ****************************************RECUPERACION DE CONTRASEÑA********************************************************
@GetMapping("/usuarios/recuperarCredenciales")
public String recuperacion1(){
    return "recuperarCredenciales";
}

@GetMapping("/usuarios/r")
public String r(){
    return "reestablecerContrasenia";
}

@PostMapping("/usuarios/reinicioContraseña")
public String reinicioContraseña(@RequestParam("correo") String correo) throws MessagingException {
	List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
	for (Usuario e: usuarios) {
		if(e.getCorreoElectronico().equals(correo)) {
			int contraseniaAux = generateRandomIntIntRange(1000,3000);
			e.setContrasenia(String.valueOf(contraseniaAux));
			this.serviceUsuario.crear(e);
			service.sendEmailWithAttachment(e.getCorreoElectronico(),
					String.valueOf(contraseniaAux),
	                  "Clave para reinicio de Contraseña",
	                  "src//main//resources//static/images//logo-group.jpg");
			
		}
	}
	
	return "codigo";

}

public static int generateRandomIntIntRange(int min, int max) {
    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
}

@PostMapping("/usuarios/restablecerContraseña")
public String restablecerContraseña(@RequestParam("clave") String clave){
	List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
	for (Usuario e: usuarios) {
		if(e.getContrasenia().equals(clave)) {
			e.setContrasenia("*");
			this.serviceUsuario.crear(e);
			return "reestablecerContrasenia";
		}
	}
	return "recuperarCredenciales";
}
	
	@PostMapping("/usuarios/nuevaContraseña")
	public String nuevaContraseña(@RequestParam("contrasenia") String contrasenia) throws MessagingException {
		List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
		for (Usuario e: usuarios) {
			if(e.getContrasenia().equals("*")) {
				e.setContrasenia(contrasenia);
				this.serviceUsuario.crear(e);
				service.sendEmailWithAttachment(e.getCorreoElectronico(),
			              "Restablecimiento de Contraseña",
			              "Se restablecio su contraseña exitosamente",
			              "src//main//resources//static/images//carita-feliz.jpg");
		}
		
		
		
		

		}
		return "redirect:/encriptar";
	
	
	}
	
	//Estadisticas
	@GetMapping("/administradores/estadistica")
	public String estadistica(Model model,Model model2,Model model3){
		List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
		List<Producto> productos=this.serviceProducto.obtenerTodosProductos();
		List<Categoria> categorias=this.serviceCategoria.obtenerTodasCategoria();
		
		
		List<EstadisticaGeneral> estadisticaGeneral = new ArrayList<EstadisticaGeneral>();
		List<Estadistica> productosXestado = new ArrayList<Estadistica>();
		List<Estadistica2> productosXcategoria = new ArrayList<Estadistica2>();
		
		EstadisticaGeneral e1=new EstadisticaGeneral();
		EstadisticaGeneral e2=new EstadisticaGeneral();
		EstadisticaGeneral e3=new EstadisticaGeneral();
		e1.setTotalUsuariosN("Usuarios");
		e1.setCantidadesTotal(usuarios.size());
		e2.setTotalUsuariosN("Productos");
		e2.setCantidadesTotal(productos.size());
		e3.setTotalUsuariosN("Categorias");
		e3.setCantidadesTotal(categorias.size());
		
		estadisticaGeneral.add(e1);
		estadisticaGeneral.add(e2);
		estadisticaGeneral.add(e3);
		
		
		
		
		
		
		int[] contadores = new int[categorias.size()];
		int estado=0;
		int estado2=0;
		int estado3=0;
		int contadorActual=0;
		int aux=0;
		
		
		
		for(Producto p:productos) {
			if(p.getIdEstadoProducto()==1) {
				estado+=1;
			}else if(p.getIdEstadoProducto()==2) {
				estado2+=1;
			}else if(p.getIdEstadoProducto()==3) {
				estado3+=1;
			}
		}
		
		Estadistica PxE1=new Estadistica();
		Estadistica PxE2=new Estadistica();
		Estadistica PxE3=new Estadistica();
		PxE1.setDescripcion("Nuevo");
		PxE1.setCantidadEstado(estado);
		PxE2.setDescripcion("Semi Nuevo");
		PxE2.setCantidadEstado(estado2);
		PxE3.setDescripcion("Usuado");
		PxE3.setCantidadEstado(estado3);
		
		productosXestado.add(PxE1);
		productosXestado.add(PxE2);
		productosXestado.add(PxE3);
		
		
		
		
		for(Categoria c:categorias) {
			
			for(Producto p:productos) {
				
				if(c.getIdCategoria()== p.getIdCategoria()) {
				 aux+=1;
				}
				
			}
			contadores[contadorActual]=aux;
			aux=0;
			contadorActual+=1;
		}
		int i =0;
			for(Categoria c:categorias) {
				
				Estadistica2 PxC=new Estadistica2();
				PxC.setCategoria(c.getNombre());
				PxC.setCantidadCategoria(contadores[i]);
				productosXcategoria.add(PxC);
				i+=1;
				}

		
		model.addAttribute("estadisticaGeneral", estadisticaGeneral);
		model.addAttribute("productosXestado", productosXestado);
		model.addAttribute("productosXcategoria", productosXcategoria);
		
		
		
		
		
		
	    return "estadisticas.html";
	    
	}
	
	public String denuncias = "";
	 
	 @PostMapping(value ="/usuarios/agregarDenuncia")
	  public String agregarDenuncia(@RequestParam(name = "denuncia") String denuncia, 
			  @RequestParam(name = "idUsuario") int idUsuario) {
		 
		 try {
	      List<Usuario> usuario=this.serviceUsuario.obtenerTodosUsuarios();
	        for (Usuario u: usuario) {
	            if(u.getIdUsuario() == idUsuario) {
	            	if(u.getDenuncia() == null) {
	            		 u.setDenuncia(String.valueOf(denuncia));
	            	} else {
	            		denuncias = u.getDenuncia() + ',' + denuncia;
	            		 u.setDenuncia(String.valueOf(denuncias));
	            	}
	            	this.serviceUsuario.crear(u);
	           
	            }
	        }
	      return "redirect:/pagina/paginaPrincipal";
		 } catch(Exception e) {
			 return "/";
		 }

	  }
	  
	 
	 @GetMapping("/prueba")
	 public String denuncia(){
	     return "denuncia";
	 }
	 
	 @GetMapping("/producto")
	 public String producto(){
		 return "producto";
	 }
	 
	 @GetMapping("/vendedor")
	 public String vendedor(){
		 return "vendedor";
	 }
	 
	 @GetMapping("/detalle/{id}")
	    public String detalleProducto(Model model, @PathVariable("id") int id) {
	        try {
	            Producto producto = this.serviceProducto.buscarProducto(id);
	            model.addAttribute("producto",producto);
	            return "/producto";
	        } catch (Exception e) {
	            model.addAttribute("error", e.getMessage());
	            return "error";
	        }
	    }
	 
	 @GetMapping("/denuncia/{id}")
	    public String denunciarUsuario(Model model, @PathVariable("id") int id) {
	        try {
	        	Usuario usuario = this.serviceUsuario.buscarUsuario(id);
	            model.addAttribute("usuario",usuario);
	            return "denuncia";
	        } catch (Exception e) {
	            model.addAttribute("error", e.getMessage());
	            return "error";
	        }
	    }
	 
	 
	 /*---------------------------------------Calificacion de Vendedores------------------------------------------*/
	 
	 
	 
	 @RequestMapping(value="/calificar", method=RequestMethod.POST)
		public String calificarVendedores(@RequestParam(name = "calificar") int calificacion,
				@RequestParam(name="idUsuario") int idUsuario){
		 try {
		      List<Usuario> usuario=this.serviceUsuario.obtenerTodosUsuarios();
		      Usuario u1 = new Usuario();
		        for (Usuario u: usuario) {
		            if(u.getIdUsuario() == idUsuario) {
		            	if(u.getCalificacion() == 0) {
		            		 u.setCalificacion(calificacion);
		            		 this.serviceUsuario.crear(u);
		            	} else {
		            		u.setCalificacion((u.getCalificacion() + calificacion)/(2));
		            		this.serviceUsuario.crear(u);
		            	}
		            	
		           
		            }
		            
		        }
		        
		        
		      return "redirect:/pagina/paginaPrincipal";
			 } catch(Exception e) {
				 return "/";
			 }
		}
	 
	 @GetMapping(value="/error")
		public String error(){
			return "error.html";
		}
	 
	 //ELIMINAR CUALQUIER ANUNCIO POR USUARIO 
	 @GetMapping(value="/productos/tusProductos")
		public String productosUsuario(Model model,Authentication auth){
		 List<Producto> productos=this.serviceProducto.obtenerTodosProductos();
		 List<Producto> productosXUsuario = new ArrayList<Producto>();
	      String userName =auth.getName();
	      List<Usuario> lista = this.serviceUsuario.obtenerTodosUsuarios();
  			for (Usuario e: lista) {
  				if(e.getCorreoElectronico().equals(userName)) {
  					for(Producto p:productos) {
  						if(p.getIdUsuario()==e.getIdUsuario()) {
  							productosXUsuario.add(p);
  						}
  					}
  				}
  		}
  			
	      
	      
  		model.addAttribute("producto",productosXUsuario);
		 return "productosUsuario.html";
	}
	 
	 
	 @PostMapping(value ="/productos/eliminarProducto")
	    public String borrarProductos(@RequestParam(name = "idProducto") int idProducto) {
		 this.serviceProducto.eliminarProducto(idProducto);
		
	         return "redirect:/productos/tusProductos";

	 }
	 
	 @GetMapping(value="/usuarios/chat")
		public String ChatPrincipal(Model model,Model model2){
		 List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
		 List<idAux> idReceptorM = new ArrayList<idAux>();
		 idAux idReceptor = new idAux();
		 idReceptor.setIdReceptor(0);
		 idReceptorM.add(idReceptor);
		 model.addAttribute("usuarios",usuarios);
		 model2.addAttribute("idReceptorM",idReceptorM);
		return "chat.html";	
	}
	 
	 
	 @GetMapping("/chat/{id}")
	    public String detallesMensajes(Model model,Model model2,Model model3,Model model4,
	    		@PathVariable("id") int id,Authentication auth) {
		 	List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
		 	List<Chat> chats=this.serviceChat.obtenerTodosLosChats();
		 	List<msgEmisor> msgsEmisor = new ArrayList<msgEmisor>();
			msgEmisor msgE = new msgEmisor();
			List<msgReceptor> msgsReceptor = new ArrayList<msgReceptor>();
			msgReceptor msgR = new msgReceptor();
			
		 	List<idAux> idReceptorM = new ArrayList<idAux>();
		 	idAux idReceptor = new idAux();
		 	String userName =auth.getName();
		 	int idEmisor=0;
		 	Chat chatAux = new Chat();
		 	idReceptor.setIdReceptor(id);
 			idReceptorM.add(idReceptor);
		 	for(Usuario u:usuarios) {
		 		if(u.getCorreoElectronico().equals(userName)) {
		 			idEmisor=u.getIdUsuario();
		 		}
		 	}
		 	
	        for(Chat cht:chats) {
	        	if(cht.getIdUsuarioEmisor()==idEmisor&&cht.getIdUsuarioReceptor()==id) {
	        		
	        	}else {
	        		chatAux.setIdUsuarioEmisor(idEmisor);
	        		chatAux.setIdUsuarioReceptor(id);
	        	}
	        }
	        for(Chat cht:chats) {
	        	if(cht.getIdUsuarioEmisor()==idEmisor&&cht.getIdUsuarioReceptor()==id||cht.getIdUsuarioEmisor()==id&&cht.getIdUsuarioReceptor()==idEmisor) {
	     	        	if(!cht.getMensajeEmisor().equals("%")){
	     	        		String empty_str = "";
	     	        		empty_str = cht.getMensajeEmisor();
	     	                String[] split = empty_str.split(",");
	     	                for (int i=0; i<split.length; i++) {
	     	                	msgEmisor msgaux = new msgEmisor();
	     	                	msgaux.setMsg(split[i]);
	     	                	msgsEmisor.add(msgaux);
	     	                }
	     	        	}else {
	     	        		
	     	        	}
	     	        	if(!cht.getMensajeReceptor().equals("%")){
	     	        		String empty_str = "";
	     	        		empty_str = cht.getMensajeReceptor();
	     	                String[] split = empty_str.split(",");
	     	                for (int i=0; i<split.length; i++) {
	     	                	msgReceptor mdgaux = new msgReceptor();
	     	                	mdgaux.setMsg(split[i]);
	     	                	msgsReceptor.add(mdgaux);
	     	                }
	     	        	}else {
	     	        		
	     	        	}
	     	        	
	     	        }
	        	
	        }
	       int aux45=0;
	        for(Chat cht:chats) {
	        	if(cht.getIdUsuarioEmisor()==idEmisor&&cht.getIdUsuarioReceptor()==id||cht.getIdUsuarioEmisor()==id&&cht.getIdUsuarioReceptor()==idEmisor) {
	        		aux45=1;
	        	}
	        }
	        
	        if(aux45==0) {
	        	Chat chatCrear = new Chat();
	        	chatCrear.setIdUsuarioEmisor(idEmisor);
	        	chatCrear.setIdUsuarioReceptor(id);
	        	chatCrear.setMensajeEmisor("%");
	        	chatCrear.setMensajeReceptor("%");
	        	this.serviceChat.crearChat(chatCrear);
	        }
	        
	        if(msgsReceptor.size()<msgsEmisor.size()) {
	        	msgReceptor mdgaux = new msgReceptor();
            	mdgaux.setMsg("");
            	msgsReceptor.add(mdgaux);
	        }else {
	        	if(msgsEmisor.size()<msgsReceptor.size()) {
		        	msgEmisor msgaux = new msgEmisor();
                	msgaux.setMsg("");
                	msgsEmisor.add(msgaux);
		        }
	        }
	        
	        
	        
	        model2.addAttribute("usuarios",usuarios);
	        model.addAttribute("idReceptorM",idReceptorM);
	        model3.addAttribute("receptorMsg",msgsReceptor);
	        model4.addAttribute("emisorMsg",msgsEmisor);
	        for(Chat cht: chats) {
	        	if(cht.getIdUsuarioEmisor()==idEmisor&&cht.getIdUsuarioReceptor()==id) {
	        		return "chat2.html";
	        	}
	        }
	        return "chat.html";
	   }
	 
	 @PostMapping(value ="/chat/mensajes")
	    public String renderizarMensajes(Model model,Model model2,Authentication auth ,
	    			 					 @RequestParam(name = "idReceptor") int idReceptor,
	    								 @RequestParam(name = "msg") String msg) {
		 List<Usuario> usuarios=this.serviceUsuario.obtenerTodosUsuarios();
		 List<Chat> chats=this.serviceChat.obtenerTodosLosChats();
		 List<msgEmisor> msgsEmisor = new ArrayList<msgEmisor>();
		 msgEmisor msgE = new msgEmisor();
		 List<msgReceptor> msgsReceptor = new ArrayList<msgReceptor>();
		 String userName =auth.getName();
		 	int idEmisor=0;
		 	for(Usuario u:usuarios) {
		 		if(u.getCorreoElectronico().equals(userName)) {
		 			idEmisor=u.getIdUsuario();
		 		}
		 	}
		 	
	        for(Chat cht:chats) {
	        	if(cht.getIdUsuarioEmisor()==idEmisor&&cht.getIdUsuarioReceptor()==idReceptor) {
	        			if(cht.getMensajeReceptor().equals("%")) {
	        				cht.setMensajeReceptor(msg+",");
		        			this.serviceChat.crearChat(cht);
	        			}else {
	        				String aux=cht.getMensajeReceptor();
	        				cht.setMensajeReceptor(aux+msg+",");
		        			this.serviceChat.crearChat(cht);
	        			}
	        			
	        		
	        	}else {
	        		if(cht.getIdUsuarioEmisor()==idReceptor&&cht.getIdUsuarioReceptor()==idEmisor) {
	        			if(cht.getMensajeEmisor().equals("%")) {
	        				cht.setMensajeEmisor(msg+",");
		        			this.serviceChat.crearChat(cht);
	        			}else {
	        				String aux=cht.getMensajeEmisor();
	        				cht.setMensajeEmisor(aux+msg+",");
		        			this.serviceChat.crearChat(cht);
	        			}     			
	        			
	        			msgE.setMsg(msg);
	        			msgsEmisor.add(msgE);
	        		}
        			
        		}
	        }
	        
		
	         return "redirect:/chat/"+idReceptor;

	 }
	 
	 
	 ///chat/3


	 @GetMapping(value="/productos/ProductosFiltrados")
		public String productosFiltrados(Model model,Model model2,Model model3,@RequestParam(name = "nombre")Optional<String> nombre,
		 		@RequestParam(name = "idCategoria") Optional<String> idCategoria, Object String){ 
		  		 List<Producto> productos=this.serviceProducto.obtenerTodosProductos();
		  		 List<Categoria> categorias = this.serviceCategoria.obtenerTodasCategoria();
		  		 List<Departamento> departamento = this.serviceDepartamento.obtenerTodosLosDepartamentos(); 
		  		 /*
		 List<Producto> productosFiltrados = new ArrayList<Producto>();
	     List<Departamento> departamentos = this.serviceDepartamento.obtenerTodosLosDepartamentos();
	     List<Categoria> categorias = this.serviceCategoria.obtenerTodasCategoria();
	     

			for (Producto p: productos) {
		       if(p.getIdDepartamento()==idDepartamento);		
		    	   productosFiltrados.add(p);	
				
				}
			
			for (Producto pro: productos) {
				if(pro.getIdCategoria()==idCategoria) {
					productos.add(pro);
					
				}
				
				for(Producto precios: productos) {
					if(precios.getPrecio()<precio) {
						productosFiltrados.add(precios);
					}
						}
			}
		*/
		  		model.addAttribute("productosFiltrados",productos);
		 		model.addAttribute("categoria",categorias);
		  		model.addAttribute("departamento",departamento);
			return "ProductosFiltrados.html";
		}

	

	 @PostMapping(value ="/productos/ProductosFiltradoCategorias")

	 public String productosFiltradosPrecio(Model model,Model model2,Model model3,@RequestParam(name = "nombre")Optional<String> nombre,
	 		@RequestParam(name = "idCategoria") Optional<String> idCategoria, Object String
	 		){
	 	 List<Producto> productosTotales=this.serviceProducto.obtenerTodosProductos();
	 	 List<Departamento> departamentos = this.serviceDepartamento.obtenerTodosLosDepartamentos();
		 List<Categoria> categorias = this.serviceCategoria.obtenerTodasCategoria();
		 List<Departamento> departamento = this.serviceDepartamento.obtenerTodosLosDepartamentos(); 
	 	 List<Producto> productosFiltrados = new ArrayList<Producto>();

	
	 	 if(nombre!=null) {
				for(Departamento d:departamentos) {
					if(d.getNombre().equals(nombre)) 
						for(Producto p:productosTotales)
						 if(p.getIdDepartamento()==d.getIdDepartamento())
							 productosFiltrados.add(p);	
					
				}
				
 }
	 	 
	 	if(idCategoria!=null) 
	 	{
	 		for(Categoria c:categorias)
	 			if(c.getNombre().equals(idCategoria))
 			for(Producto p:productosTotales) {
                  if(c.getIdCategoria()==p.getIdCategoria())
 					productosFiltrados.add(p);			
 		
 		}
	 	}
 			/*	
 			if(precio!=0) {
 				for (Producto p: productosTotales) {
 					if(p.getPrecio()<precio)
 						productosFiltrados.add(p);
 						
 				}
 			}
 			*/
	 	  
	 		model.addAttribute("productosFiltrados",productosFiltrados);
	 		model.addAttribute("categoria",categorias);
	  		model.addAttribute("departamento",departamento);
	 	return "ProductosFiltrados.html";
	 }


				}
			

