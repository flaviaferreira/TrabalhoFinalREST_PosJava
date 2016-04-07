package org.posjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@ComponentScan
@Controller
@SpringBootApplication
public class RestTrabalhoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTrabalhoFinalApplication.class, args);
	}
}