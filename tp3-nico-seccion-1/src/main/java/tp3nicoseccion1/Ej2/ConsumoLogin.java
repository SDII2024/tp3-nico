package tp3nicoseccion1.Ej2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp3nicoseccion1.Ej2.Cliente.LoginUI;

/**
 * Ejercicio 2 -  Consume servicio web "Login"
 */
@Component
public class ConsumoLogin {
    private final LoginUI uiLogin;

    @Autowired
    public ConsumoLogin(LoginUI uiLogin) {
        this.uiLogin = uiLogin;
    }
}
