package web.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("beans.xml")
@ComponentScan("web.rest.user.management")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
