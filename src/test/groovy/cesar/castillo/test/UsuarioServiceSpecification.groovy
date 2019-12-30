package cesar.castillo.test

import cesar.castillo.repository.UsuarioRepository
import cesar.castillo.service.UsuarioService
import cesar.castillo.vo.ResponseUsuario
import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class UsuarioServiceSpecification extends Specification {
	
	UsuarioService usuarioService;
	UsuarioRepository usuarioRepository;
   
   def setupSpec(){
	   log.info("setupSpec() - Runs once per Specification");
   }
   def setup(){
	   log.info ("setup() - Runs before every feature method");
	   ResponseUsuario responseUsuario = new ResponseUsuario();
	   
	   usuarioService = null;
	   usuarioRepository = Stub(UsuarioRepository);
   }
   def cleanup(){
	   log.info ("Cleanup method - Runs  after every feature method.");
   }
   def cleanupSpec(){
		
	   log.info ("cleanupSpec() - Runs only once per specification");
   }
				
}