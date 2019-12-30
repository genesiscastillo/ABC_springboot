Spring Boot in a Java Container
================================================================================


Para iniciar SpringBoot

	* gradle bootRun
	
Servicios Rest :

	* http://localhost:8081/registrarUsuario  type POST
	{
        "name": "Juan Rodriguez",
        "email": "jjjuan@rodriguez.org",
        "password": "Hhunter42",
        "phones": [
            {
                "number": "1234567",
                "citycode": "1",			
                "contrycode": "57"
            }
        ]
    }
----------------------------------------------------------------------------------------		 
	* http://localhost:8081/obtenerUsuarios   type GET
----------------------------------------------------------------------------------------	
	* http://localhost:8081//activarUsuario   type POST
	{
		"email":"jjjuan@rodriguez.org",
		"token":"KWcJK0vezkJ8tU1Jf00asMQ0TME="
	}
-----------------------------------------------------------------------------------------	

Para testing con Spock Framework (prueba de conceptos)

	* gradle clean test

Documentos:

	* diagrmas y otros    : src/main/resources/Propuestas_Ejercicio_1.docx
	* Proyecto del SOAPui : src/main/resources/REST-Project-2-soapui-project.xml


###############################

Informacion de recursos

	* https://httpstatuses.com/
	* https://www.baeldung.com/exception-handling-for-rest-with-spring
	* https://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
	* http://openjpa.apache.org/builds/3.0.0/apache-openjpa/docs/manual.html
	* https://www.javatips.net/blog/hibernate-jpa-with-derby
	* https://www.mkyong.com/spring/spring-embedded-database-examples/
	* https://www.testwithspring.com/lesson/running-unit-tests-with-gradle-spock-edition/
