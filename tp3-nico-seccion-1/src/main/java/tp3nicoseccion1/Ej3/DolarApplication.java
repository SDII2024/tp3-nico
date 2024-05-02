package tp3nicoseccion1.Ej3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"tp3nicoseccion1.Ej3"})
public class DolarApplication {
    public static void main(String... args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(tp3nicoseccion1.Ej3.DolarApplication.class, args);
    }
}
