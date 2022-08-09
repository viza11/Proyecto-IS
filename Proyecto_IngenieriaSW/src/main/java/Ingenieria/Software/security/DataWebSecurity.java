package Ingenieria.Software.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity 
	
	
	public class DataWebSecurity extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication().dataSource(dataSource)
	.usersByUsernameQuery("SELECT correoElectronico, contrasenia,activo as enabled FROM usuario where correoElectronico=?")
	.authoritiesByUsernameQuery("select correoElectronico, rol from usuario "
	+ "where correoElectronico = ?");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
	
	// Los recursos estáticos no requieren autenticación
	.antMatchers("/images/**").permitAll()
	.antMatchers("/estilos/**").permitAll()
	.antMatchers("/imgt/**").permitAll()
	.antMatchers("/js/**").permitAll()
	.antMatchers("/terminos/**").permitAll()
	.antMatchers("/login/**").permitAll()
	.antMatchers("/usuarios/**").permitAll()
	.antMatchers("/inicio/**").permitAll()
	.antMatchers("/producto/**").permitAll()
	.antMatchers("/error").permitAll()
	.antMatchers("/api/pdf/**").permitAll()
	.antMatchers("/PdfProducto/**").permitAll()
	.antMatchers("/mail/**").permitAll()
	.antMatchers("/sendMail/**").permitAll()
	.antMatchers("/").permitAll()
	.antMatchers("/detalle/").hasAnyAuthority("usuario")
    .antMatchers("/denuncia/").hasAnyAuthority("usuario")
    .antMatchers("/calificar/**").hasAnyAuthority("usuario")
	
	// Las vistas públicas no requieren autenticación
	.antMatchers("/encriptar/**").permitAll()
	// Asignar permisos a URLs por ROLES
	.antMatchers("/productos/**").hasAnyAuthority("usuario")
	.antMatchers("/user/**").hasAnyAuthority("usuario")
	.antMatchers("/user/**").hasAnyAuthority("usuario")
	.antMatchers("/agregar/**").hasAnyAuthority("usuario")
	.antMatchers("/administradores/**").hasAnyAuthority("admin")
	.antMatchers("/login/**").hasAnyAuthority("usuario","admin")
	.antMatchers("/administradores/inicio").hasAnyAuthority("usuario")
	// Todas las demás URLs de la Aplicación requieren autenticación
	.anyRequest().authenticated()
	// El formulario de Login no requiere autenticacion
	.and().formLogin()
	.loginPage("/login")
	.usernameParameter("username")
	.passwordParameter("password")
	.defaultSuccessUrl("/login/inicio",true)
	.permitAll();
	
	


	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	}



	}
 
