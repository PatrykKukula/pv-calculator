package com.github.PatrykKukula.Photovoltaic.materials.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableCaching
public class PhotovoltaicMaterialsCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotovoltaicMaterialsCalculatorApplication.class, args);
	}

}
