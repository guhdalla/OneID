package br.com.fiap.oneid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
@EntityScan(basePackages = {"br.com.fiap.oneid.model"})
@EnableJpaRepositories(basePackages= {"br.com.fiap.oneid.repository"})
@EnableWebMvc
public class OneidApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneidApplication.class, args);
	}

}
