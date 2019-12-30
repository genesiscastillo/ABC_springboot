package cesar.castillo.dao;

import java.util.List;
import java.util.Optional;

import cesar.castillo.entity.Phone;
import cesar.castillo.entity.Usuario;
import cesar.castillo.exception.BussinesException;
import cesar.castillo.vo.ResponseUsuario;

public interface UsuarioDao {
	
	int createUsuario(ResponseUsuario responseUsuario);
	Phone createPhone(Phone phone); 
	Optional<Usuario> findByEmail(String email);
	List<ResponseUsuario> findAll();
	List<Phone> findAllPhoneById(String id);
	void activarUsuario(String email) throws BussinesException;

}
