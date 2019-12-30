package cesar.castillo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cesar.castillo.dao.UsuarioDao;
import cesar.castillo.entity.Usuario;
import cesar.castillo.exception.BussinesException;
import cesar.castillo.vo.EstadoUsuario;
import cesar.castillo.vo.ResponseUsuario;
import cesar.castillo.vo.User;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger LOGGER = Logger.getLogger(UsuarioServiceImpl.class.getName());

	@Autowired
	UsuarioDao usuarioDao;

	@Override
	public ResponseUsuario registrarUsuario(User user) throws BussinesException {
		String email = validateEmail(user.getEmail());

		Optional<Usuario> optional1 = usuarioDao.findByEmail(email);
		if (optional1.isPresent()) {
			throw new BussinesException("El correo ya registrado");
		}
		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
		String id = UUID.randomUUID().toString();
		String name = user.getName();
		String password = validatePaswoord(user.getPassword());
		String fechaCreacion = formattedDate;
		String fechaModificacion = null;
		String lastLogin = formattedDate;
		User user2 = new User();
		user2.setEmail(user.getEmail());
		user2.setName(user.getName());
		user2.setPassword(user.getPassword());
		
		String token = getToken(user2);
		EstadoUsuario estadoUsuario = EstadoUsuario.INACTIVO;
		ResponseUsuario responseUsuario = new ResponseUsuario(name, email, password, id, fechaCreacion,
				fechaModificacion, lastLogin, token, estadoUsuario);

		usuarioDao.createUsuario(responseUsuario);

		Optional<Usuario> optional = usuarioDao.findByEmail(email);
		Usuario usuario = optional.get();
		return new ResponseUsuario(usuario.getName(), usuario.getEmail(), usuario.getPassword(), usuario.getId(),
				usuario.getFechaCreacion(), usuario.getFechaModificacion(), usuario.getLastLogin(), usuario.getToken(),
				usuario.getEstadoUsuario());
	}

	@Override
	public String validatePaswoord(String password) throws BussinesException {
		String patternPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]{2}).{4,20}$";
		Pattern pattern = Pattern.compile(patternPassword);
		if (!pattern.matcher(password).matches()) {
			throw new BussinesException(
					"El password debe seguir una expresión regular para validar que formato sea el correcto. (Una Mayuscula, letras minúsculas, y dos números) maximo de 20 caracteres");
		}
		return password;
	}

	@Override
	public String validateEmail(String email) throws BussinesException {
		String patternEmail = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(patternEmail);
		if (!pattern.matcher(email).matches()) {
			throw new BussinesException(
					"El correo debe seguir una expresión regular para validar que formato sea el correcto. (aaaaaaa@dominio.cl)");
		}
		return email;
	}

	@Override
	public List<ResponseUsuario> obtenerUsuarios() {
		return usuarioDao.findAll();
	}
	
	private String getToken(User user) {
		String signature = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
		    byte[] keyBytes = "secretKey".getBytes("UTF8");
		    SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		    mac.init(signingKey);
		    
		    StringBuilder stringToSign = new StringBuilder(user.toString());
		    
		    byte[] signBytes = mac.doFinal(stringToSign.toString().getBytes("UTF8"));
		    signature = Base64.getEncoder().encodeToString(signBytes);
		    
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage() , e);
		}
		return signature;
	}
	
	@Override
	public Boolean validateToken(String email, String token) throws BussinesException{
		Optional<Usuario> optional = usuarioDao.findByEmail(email);
		if(!optional.isPresent()) {
			new BussinesException("correo no registrado");
		}
		Usuario usuario = optional.get();
		
		User user = new User();
		user.setEmail(usuario.getEmail());
		user.setName(usuario.getName());
		user.setPassword(usuario.getPassword());
		
		String signature = getToken(user); 
		
		byte encode[] = Base64.getEncoder().encode(token.getBytes());
		
		String encodeString = new String(encode);
		String decode = new String(Base64.getDecoder().decode(encodeString.getBytes()));
		
		Boolean status = signature.equals(decode); 
		if(status) {
			usuarioDao.activarUsuario(email);
		}
		return status;
	}
}