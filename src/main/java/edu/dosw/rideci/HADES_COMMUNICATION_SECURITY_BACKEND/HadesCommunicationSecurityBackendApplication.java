package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class HadesCommunicationSecurityBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HadesCommunicationSecurityBackendApplication.class, args);
	}

}
