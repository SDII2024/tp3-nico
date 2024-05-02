package tp3nicoseccion1.Ej2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"tp3nicoseccion1.Ej2"})
public class LoginApplication {
    public static void main(String... args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(tp3nicoseccion1.Ej2.LoginApplication.class, args);
    }
}