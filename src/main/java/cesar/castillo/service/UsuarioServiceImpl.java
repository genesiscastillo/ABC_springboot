package cesar.castillo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cesar.castillo.dao.UsuarioDaoImpl;
import cesar.castillo.entity.Phone;
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

	@Autowired
	UsuarioDaoImpl usuarioDao;

	@Override
	public ResponseUsuario registrarUsuario(User user) throws BussinesException {
		String email = validateEmail(user.getEmail());

		Optional<Usuario> optional1 = usuarioDao.findByEmail(email);
		if (optional1.isPresent()) {
			throw new BussinesException("El correo ya registrado");
		}

		String id = UUID.randomUUID().toString();
		String name = user.getName();
		String password = validatePaswoord(user.getPassword());

		String token = getTokenJWT(user.getEmail());
		EstadoUsuario estadoUsuario = EstadoUsuario.INACTIVO;
		
		Usuario usuario = new Usuario();
		usuario.setEmail( email );
		usuario.setEstadoUsuario( estadoUsuario );
		usuario.setId( id);
		usuario.setLastLogin( null );
		usuario.setName( name );
		usuario.setPassword( password );
		usuario.setToken( token );

		usuarioDao.create(usuario);

		user.getPhones().forEach(phone ->{
				Phone phone2 = new Phone();
				phone2.setCodigoCity( phone.getCitycode());
				phone2.setCodigoPais( phone.getContrycode());
				phone2.setNumero( phone.getNumber());
				phone2.setId( id );
				phone2.setItem( UUID.randomUUID().toString());
				usuarioDao.createPhone(phone2);
			}
		);

		ResponseUsuario responseUsuario = new ResponseUsuario(usuario.getName(), usuario.getEmail(), usuario.getPassword(), usuario.getId(),
				usuario.getFechaCreacion(), usuario.getFechaModificacion(), usuario.getLastLogin(), usuario.getToken(),
				usuario.getEstadoUsuario() );
		responseUsuario.setPhones(user.getPhones());
		return responseUsuario;
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
		List<ResponseUsuario> responseUsuarios = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.getAll();
		usuarios.forEach(usuario ->{
				ResponseUsuario responseUsuario = new ResponseUsuario(usuario.getName(), usuario.getEmail(), usuario.getPassword() , usuario.getId() , usuario.getFechaCreacion(), usuario.getFechaModificacion(), usuario.getLastLogin(), usuario.getToken() ,usuario.getEstadoUsuario());
				usuarioDao.findAllPhoneById(usuario.getId()).forEach(phone ->{
					cesar.castillo.vo.Phone phone2 = new cesar.castillo.vo.Phone();
					phone2.setCitycode(phone.getCodigoCity());
					phone2.setContrycode(phone.getCodigoPais());
					phone2.setNumber(phone.getNumero());
					responseUsuario.add(phone2);
				});
				responseUsuarios.add(responseUsuario);
		});
		return responseUsuarios;
	}
	
	@Override
	public String getTokenJWT(String email) {
		String base64EncodedSecretKey = Base64.getEncoder().encodeToString(email.getBytes());
		Long tiempo = System.currentTimeMillis();
		Long minuto = Duration.ofMinutes(1).toMillis();
		String jwt = Jwts.builder()
						.setId(UUID.randomUUID().toString())
						.signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
						.setSubject("Usuario Por Autorizar")
						.setIssuedAt( new Date( tiempo ))
						.setExpiration(new Date( tiempo + minuto ))
						.claim("email", "genesiscastillo@hotmail.com")
						.compact();
		return jwt;
	}
	
	@Override
	public void validateEmailTokenJWT(String email, String jwt) throws BussinesException {
		Optional<Usuario> optional = usuarioDao.findByEmail(email);
		if (!optional.isPresent()) {
			new BussinesException("correo no registrado");
		}
		validateTokenJWT(email, jwt);
		usuarioDao.activarUsuario(email);
	}

	private void validateTokenJWT(String email, String jwt) throws BussinesException {
		String base64EncodedSecretKey = Base64.getEncoder().encodeToString(email.getBytes());
		try {
			Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(jwt).getBody();
		} catch (Exception exception) {
			throw new BussinesException("token invalido");
		}
	}
}