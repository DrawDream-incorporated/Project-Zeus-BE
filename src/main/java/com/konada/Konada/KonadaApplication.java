package com.konada.Konada;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KonadaApplication {

	private static final Logger logger = LogManager.getLogger(KonadaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KonadaApplication.class, args);
		logger.info("Application started successfully.");
	}

}
