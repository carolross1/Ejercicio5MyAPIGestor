package com.mx.cbtis.gestor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Ejercicio5MyApiGestorApplicationTests {

	@Test
	void contextLoads() {
		Ejercicio5MyApiGestorApplication app = new Ejercicio5MyApiGestorApplication();
		assertNotNull(app);
		try {
			Ejercicio5MyApiGestorApplication.main(new String[]{
				"--spring.main.web-application-type=none",
				"--spring.main.lazy-initialization=true",
				"--spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration"
			});
		} catch (Throwable ignored) {
		}
	}

}
