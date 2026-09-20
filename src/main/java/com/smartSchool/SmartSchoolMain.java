package com.smartSchool;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Smart API",
				version = "1.0",
				description = "Smart School Authentication and Authorization API with JWT Security",
				contact = @Contact(
						name = "Vipin",
						email = "vipin@example.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Local Development Server"),
				@Server(url = "https://api.example.com", description = "Production Server")
		}
)
public class SmartSchoolMain {

	public static void main(String[] args) {
		SpringApplication.run(SmartSchoolMain.class, args);
	}
}
