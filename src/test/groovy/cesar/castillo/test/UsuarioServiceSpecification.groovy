package cesar.castillo.test

import org.junit.experimental.categories.Category;

import cesar.castillo.service.UnitTest;
import cesar.castillo.service.UsuarioServiceImpl;
import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
@Category(UnitTest.class)
class UsuarioServiceSpecification extends Specification {

	def usuarioService = new UsuarioServiceImpl();

	def setupSpec(){
		log.info("setupSpec() - Runs once per Specification");
	}
	def setup(){
		log.info ("setup() - Runs before every feature method");
	}
	def "valida usuario con token dentro de la expiracion"()	{
		log.info("inicio prueba 1");
		expect :
			log.info("email "+email);
			String token = usuarioService.getTokenJWT(email);
			log.info("token "+token);
			usuarioService.validateTokenJWT(email, token);
		where :
			email << ["genesiscastillo@hotmail.com"];
	}

	def "valida usuario con token invalido de la expiracion"()	{
		log.info("inicio prueba 2 - invalido");
		expect :
			log.info("email "+email);
			String token = usuarioService.getTokenJWT(email);
			String otroToken = 'otroToken';
			log.info("otroToken "+otroToken);
			try {
				usuarioService.validateTokenJWT(email, otroToken );
			}catch(e) {
				log.info("exito en la validacion para el token invalido");
			}
		where :
			email << ["genesiscastillo@hotmail.com"];
	}

	def cleanup(){
		log.info ("Cleanup method - Runs  after every feature method.");
	}
	def cleanupSpec(){

		log.info ("cleanupSpec() - Runs only once per specification");
	}
}