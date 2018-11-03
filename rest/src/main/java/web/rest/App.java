package web.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("beans.xml")
@PropertySource("properties/application.properties")
@ComponentScan("web.rest.user.management")
@ComponentScan("web.rest.security")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
