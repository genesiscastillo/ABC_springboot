package cesar.castillo;

import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application implements CommandLineRunner {
	
	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	public void run(String... args) throws Exception {
		LOGGER.info("Server Ready...!!!! - Nicole Tapia 1.0");
	}
}	