package co.com.contabilidad.microfocus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="co.com.contabilidad.microfocus.listener")
@SpringBootApplication
public class ContabMicroFocusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContabMicroFocusApplication.class, args);
	}
}
