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
//	   bookRepository.getBook(_) >> { int id ->
//		   if (id == 1)
//		   {
//				Book b = new Book(1, 'Srujana', 'Spock Tut');
//				log.debug(b.toString());
//				return b;
//		   }
//			else if (id == 2)
//		   {
//			   Book b = new Book(2, 'Eugen', 'JUnit Tut');
//				log.debug(b.toString());
//			   return b;
//		   }
//		   else if (id == 3)
//		   {
//			   log.debug("Book with this ID does not exist");
//			   return null;
//		   }
//	   }
   }
//   def "retrieved book object is not null"(){
//	   log.debug ("Feature method 1 - retrieved book object is not null- start");
//	   expect :
////		   bookService.retrieveBookDetails(id) != null
//	   where :
//	   id << [1, 2]
//   }
//	
//   def "retrieved book object is null"(){
//	   log.debug ("Feature method - 2 retrieved book object is null - start");
//	   expect :
////		   bookService.retrieveBookDetails(id) == null
//	   where :
//		
//	   id << 3
//		
//   }
   def cleanup(){
	   log.info ("Cleanup method - Runs  after every feature method.");
   }
   def cleanupSpec(){
		
	   log.info ("cleanupSpec() - Runs only once per specification");
   }
				
}