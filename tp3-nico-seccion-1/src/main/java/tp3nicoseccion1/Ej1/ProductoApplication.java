package tp3nicoseccion1.Ej1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"tp3nicoseccion1.Ej1"})
public class ProductoApplication {
    public static void main(String... args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(ProductoApplication.class, args);
    }
}

