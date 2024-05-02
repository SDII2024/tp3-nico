package tp3nicoseccion1.Ej3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp3nicoseccion1.Ej3.Cliente.DolarUI;

/**
 * Ejercicio 3 -  Consume servicio web "Dolar"
 */
@Component
public class ConsumoDolar {
    private final DolarUI uiDolar;

    @Autowired
    public ConsumoDolar(DolarUI uiDolar) {
        this.uiDolar = uiDolar;
    }
}
