package cesar.castillo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cesar.castillo.dao.UsuarioDao;
import cesar.castillo.entity.Usuario;
import cesar.castillo.exception.BussinesException;
import cesar.castillo.vo.EstadoUsuario;
import cesar.castillo.vo.ResponseUsuario;
import cesar.castillo.vo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		
		String token = getTokenJWT(user.getEmail());
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
	
	@Override
	public String getTokenJWT(String email) {
		String base64EncodedSecretKey = Base64.getEncoder().encodeToString(email.getBytes());
		Long tiempo = System.currentTimeMillis();
		String jwt = Jwts.builder()
						.setId(UUID.randomUUID().toString())
						.signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
						.setSubject("Usuario Por Autorizar")
						.setIssuedAt( new Date( tiempo ))
						.setExpiration(new Date( tiempo + 6000000))
						.claim("email", "genesiscastillo@hotmail.com")
						.compact();
		return jwt;
	}
	
	@Override
	public void validateTokenJWT(String email , String jwt) throws BussinesException {
		Optional<Usuario> optional = usuarioDao.findByEmail(email);
		if(!optional.isPresent()) {
			new BussinesException("correo no registrado");
		}

		String base64EncodedSecretKey = Base64.getEncoder().encodeToString(email.getBytes());
		try {
		    Jwts.parser()         
		       .setSigningKey(base64EncodedSecretKey)
		       .parseClaimsJws(jwt).getBody();
		    
		    usuarioDao.activarUsuario(email);
		}catch(Exception exception) {
			throw new BussinesException("token invalido");
		}
	}
}