package cesar.castillo.test;

import org.junit.experimental.categories.Category

import cesar.castillo.service.MessageService
import cesar.castillo.service.UnitTest
import spock.lang.Specification;

@Category(UnitTest.class)
class MessageServiceSpec extends Specification {
 
	def messageService = new MessageService();
	
	def 'Get message'() {
		expect: 'Should return the correct message'
		println 'Should return the correct message'
		messageService.getMessage() == 'Hello World!'
	}
}