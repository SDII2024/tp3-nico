package tp3nicoseccion1.Ej1;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp3nicoseccion1.Ej1.Cliente.LectorUI;

/**
 * Ejercicio 1 -  Consume servicio web "Lector"
 */
@Component
public class ConsumoLector {
    private final LectorUI uiLector;

    @Autowired
    public ConsumoLector(LectorUI uiLector) {
        this.uiLector = uiLector;
    }

    @PostConstruct
    public void iniciarInterfaz() {
        uiLector.crearVentana();
    }

}
