package tp3nicoseccion1.Ej3.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class DolarController {
    static Logger logger = LogManager.getLogger(DolarController.class);

    @GetMapping("/status")
    public String getStatus() {
        try {
            // Crear la URL de la solicitud
            URL url = new URL("https://dolarapi.com/v1/estado");

            // Abrir una conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud GET
            connection.setRequestMethod("GET");

            // Leer la respuesta
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Devolver la respuesta
            return "Dolarapi status: " + response.toString() + ", App status: correcto";
        } catch (Exception e) {
            logger.error("Error al consultar el estado de la aplicación", e);
            return "Error al consultar el estado de la aplicación";
        }
    }

    @GetMapping("/precios")
    public DolarResponse getPrecios(@RequestParam(required = false) String ask) {
        String precios = "Parámetro 'ask' no es válido o no se ha ingresado ";
        String source = "servicio-de-precios-de-dolar";
        try {
            if (ask != null) {
                String urlStr = "https://dolarapi.com/v1/dolares";
                if (ask.equals("BLUE")) {
                    urlStr += "/blue";
                }

                // Crear la URL de la solicitud
                URL url = new URL(urlStr);

                // Abrir una conexión HTTP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Configurar la solicitud GET
                connection.setRequestMethod("GET");

                // Leer la respuesta
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                precios = response.toString();
            }
        } catch (Exception e) {
            logger.error("Error al obtener precios", e);
        }
        return new DolarResponse(precios, source);
    }
}
