package cesar.castillo.service;

import java.util.List;

import cesar.castillo.exception.BussinesException;
import cesar.castillo.vo.ResponseUsuario;
import cesar.castillo.vo.User;

public interface UsuarioService {

	ResponseUsuario registrarUsuario(User user) throws BussinesException ;
	String validatePaswoord(String password) throws BussinesException ;
	String validateEmail(String email) throws BussinesException ;
	List<ResponseUsuario> obtenerUsuarios();
	void validateTokenJWT(String email , String jwt) throws BussinesException ;
	String getTokenJWT(String email);
	
}
