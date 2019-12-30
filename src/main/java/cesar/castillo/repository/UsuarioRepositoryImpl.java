package cesar.castillo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cesar.castillo.vo.EstadoUsuario;
import cesar.castillo.vo.Phone;
import cesar.castillo.vo.ResponseUsuario;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {//exextends CrudRepository<Dog, Long>{

	JdbcTemplate jdbcTemplate;

	@Autowired
	//@Qualifier("cgrDatasource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int createUsuario(ResponseUsuario responseUsuario) {
		LOGGER.info("create(ResponseUsuario usuario) email = " + responseUsuario.getEmail());
		return jdbcTemplate.update(INSERT_USUARIO, responseUsuario.getId(), responseUsuario.getName(),
				responseUsuario.getEmail(), responseUsuario.getPassword(), responseUsuario.getFechaCreacion(),
				responseUsuario.getLastLogin(), responseUsuario.getFechaModificacion(), 
				responseUsuario.getEstadoUsuario().name(), responseUsuario.getToken());
	}
	
	@Override
	public int createPhone(String id , int orden ,  Phone phone) {
		LOGGER.info("createPhone orden = " + orden++);
		return jdbcTemplate.update(INSERT_PHONES, id , orden , phone.getNumber(), phone.getCitycode(), phone.getContrycode());
	}

	@Override
	public Optional<ResponseUsuario> findByEmail(String email) {
		LOGGER.info("findByEmail(String email) email = " + email);
		ResponseUsuario responseUsuario = null;
		List<ResponseUsuario> responseUsuarios = jdbcTemplate.query(SELECT_USUARIO_BY_EMAIL, this::mapRowToUsuario , email);

		if (!responseUsuarios.isEmpty()) {
			ResponseUsuario responseUsuario2 = responseUsuarios.get(0);
			List<Phone> phones = findAllPhoneById(responseUsuario2.getId());
			phones.forEach(phone  -> responseUsuario2.add(phone));
			responseUsuario = responseUsuario2;
		}
		
		return Optional.ofNullable(responseUsuario);
	}

	@Override
	public List<ResponseUsuario> findAll() {
		List<ResponseUsuario> responseUsuarios = jdbcTemplate.query(SELECT_ALL_USUARIOS_, this::mapRowToUsuario);
		responseUsuarios.forEach(responseUsuario -> responseUsuario.setPhones(findAllPhoneById(responseUsuario.getId())));
		return responseUsuarios;
	}

	@Override
	public List<Phone> findAllPhoneById(String id) {
		return jdbcTemplate.query(SELECT_PHONES_BY_ID, this::mapRowToPhone , id );
	}

	private ResponseUsuario mapRowToUsuario(ResultSet rs, int rowNum) throws SQLException {
		return new ResponseUsuario(rs.getString("NAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"),
				rs.getString("ID"), rs.getString("FECHA_CREACION"), rs.getString("FECHA_MODIFICACION"),
				rs.getString("LAST_LOGIN"), rs.getString("TOKEN"),
				EstadoUsuario.valueOf(rs.getString("ESTADO_USUARIO")));
	}
	

	private Phone mapRowToPhone(ResultSet rs, int rowNum) throws SQLException {
		return new Phone(rs.getString("NUMERO") , rs.getString("CODIGO_CITY") , rs.getString("CODIGO_PAIS"));
	}

	private static final Logger LOGGER = Logger.getLogger(UsuarioRepositoryImpl.class.getName());
	private static final String INSERT_USUARIO = "INSERT INTO TUSUARIO(ID,NAME,EMAIL,PASSWORD,FECHA_CREACION,LAST_LOGIN,FECHA_MODIFICACION,ESTADO_USUARIO,TOKEN) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_USUARIO_BY_EMAIL = "SELECT ID,NAME,EMAIL,PASSWORD,FECHA_CREACION,LAST_LOGIN,FECHA_MODIFICACION,TOKEN,ESTADO_USUARIO FROM TUSUARIO WHERE EMAIL = ?";
	private static final String SELECT_ALL_USUARIOS_ = "SELECT ID,NAME,EMAIL,PASSWORD,FECHA_CREACION,LAST_LOGIN,FECHA_MODIFICACION,TOKEN,ESTADO_USUARIO FROM TUSUARIO";

	private static final String INSERT_PHONES = "INSERT INTO TUSUARIOPHONES(ID,ITEM,NUMERO,CODIGO_CITY,CODIGO_PAIS) VALUES (?,?,?,?,?)";
	private static final String SELECT_PHONES_BY_ID = "SELECT ID, ITEM,NUMERO,CODIGO_CITY,CODIGO_PAIS FROM TUSUARIOPHONES WHERE ID = ?";
	
}
