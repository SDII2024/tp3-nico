package tp3nicoseccion1.Ej1.Cliente;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tp3nicoseccion1.Ej1.Service.Producto;

import javax.swing.*;


@Component
public class LectorUI {
    static Logger logger = LogManager.getLogger(LectorUI.class);
    private JTextField inputA;
    private JLabel labelResultado;

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public LectorUI() {}


    public void crearVentana() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Convertidor Temperatura");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            iniciarComponentes(frame);
            frame.setSize(250, 250);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void iniciarComponentes(JFrame frame) {
        JLabel labelA = new JLabel("Codigo:");
        labelResultado = new JLabel("  ", SwingConstants.LEFT);
        inputA = new JTextField();
        JButton btnConvertir = new JButton("Consultar");

        btnConvertir.addActionListener(e -> consultar());

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelA)
                .addComponent(inputA)
                .addComponent(btnConvertir)
                .addComponent(labelResultado));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(labelA)
                .addComponent(inputA)
                .addComponent(btnConvertir)
                .addComponent(labelResultado));

        frame.add(panel);
    }

    private void consultar() {
        try {
            String id = inputA.getText();

            if (id.isEmpty()) {
                labelResultado.setText("Ingrese el valor");
                return;
            }
            String url = "http://localhost:8080/productos?id=" +id;
            Producto producto = restTemplate.getForObject(url, Producto.class);
            labelResultado.setText("Producto: " + producto.getName()+" Precio: " + producto.getPrice());
        } catch (NumberFormatException ex) {
            labelResultado.setText("El valor debe ser numerico");
        } catch (RuntimeException ex) {
            logger.error("Error al realizar la operacion: {}", ex.getMessage());
            labelResultado.setText("Error al realizar la operacion");
        }
    }
}
