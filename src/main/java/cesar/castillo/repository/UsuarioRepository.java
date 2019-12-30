package cesar.castillo.repository;

import java.util.List;
import java.util.Optional;

import cesar.castillo.entity.Usuario;
import cesar.castillo.vo.Phone;
import cesar.castillo.vo.ResponseUsuario;

public interface UsuarioRepository {

	int createUsuario(ResponseUsuario responseUsuario);
	int createPhone(String id , int orden ,  Phone phone); 
	Optional<ResponseUsuario> findByEmail(String email);
	List<ResponseUsuario> findAll();
	List<Phone> findAllPhoneById(String id);
	
}
