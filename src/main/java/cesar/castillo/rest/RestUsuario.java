package cesar.castillo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cesar.castillo.service.UsuarioService;
import cesar.castillo.vo.ActivarUsuario;
import cesar.castillo.vo.EstadoUsuario;
import cesar.castillo.vo.Mensaje;
import cesar.castillo.vo.ResponseUsuario;
import cesar.castillo.vo.User;

@RestController
public class RestUsuario {

	private static final Logger LOGGER = Logger.getLogger(RestUsuario.class.getName());
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> registrarUsuario(@RequestBody User user) {
		try {
			ResponseUsuario responseUsuario = usuarioService.registrarUsuario(user);
			return ResponseEntity.ok().body(responseUsuario);
			
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE ,  exception.getMessage() , exception );
			Mensaje mensaje = new Mensaje();
			mensaje.setMensaje( exception.getMessage());
			return new ResponseEntity<Mensaje>(mensaje, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/obtenerUsuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> obtenerUsuarios() {
		List<ResponseUsuario> responseUsuarios = new ArrayList<ResponseUsuario>();
		responseUsuarios =	usuarioService.obtenerUsuarios();
		return ResponseEntity.ok().body(responseUsuarios);
	}	
	
	
	@RequestMapping(value = "/activarUsuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> activarUsuario(@RequestBody ActivarUsuario activarUsuario) {
		try {
			Boolean status = usuarioService.validateToken(activarUsuario.getEmail(), activarUsuario.getToken());
			String msj = String.format("El usuario %s cambia el estado a %s", activarUsuario.getEmail() , status ? EstadoUsuario.ACTIVO.name() : EstadoUsuario.INACTIVO.name());
			Mensaje mensaje = new Mensaje();
			mensaje.setMensaje( msj );
			return ResponseEntity.ok().body(mensaje);
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
			Mensaje mensaje = new Mensaje();
			mensaje.setMensaje(exception.getMessage());
			return new ResponseEntity<Mensaje>(mensaje, HttpStatus.NOT_FOUND);
		}
	}
	
}
