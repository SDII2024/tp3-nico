package tp3nicoseccion1.Ej3.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class DolarUI extends JFrame {
    private JLabel statusLabel;
    private JTextField askField;
    private JTextArea responseArea;
    private RestTemplate restTemplate;

    @Autowired
    public DolarUI() {
        setTitle("REST Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Status: ");
        topPanel.add(statusLabel);

        JButton statusButton = new JButton("Probar Estado");
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getStatus();
            }
        });
        topPanel.add(statusButton);


        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel askLabel = new JLabel("Ask:");
        askField = new JTextField(10);
        middlePanel.add(askLabel);
        middlePanel.add(askField);
        JButton getPreciosButton = new JButton("Obtener Precios");
        getPreciosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPrecios();
            }
        });
        middlePanel.add(getPreciosButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        responseArea = new JTextArea(10, 30);
        responseArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(responseArea);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
        restTemplate = new RestTemplate();
    }

    private void getPrecios() {
        String ask = askField.getText();
        String url = "http://localhost:8080/precios?ask=" + ask; // Cambia la URL según la ubicación de tu servicio
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String response = responseEntity.getBody();
                responseArea.setText(response);
                statusLabel.setText("Status: Correcto");
            } else {
                responseArea.setText("Error al obtener precios. Código de estado: " + responseEntity.getStatusCodeValue());
                statusLabel.setText("Status: Error");
            }
        } catch (Exception e) {
            responseArea.setText("Error al obtener precios: " + e.getMessage());
            statusLabel.setText("Status: Error");
        }
    }

    private void getStatus() {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/status", String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String response = responseEntity.getBody();
                statusLabel.setText("Status: " + response);
            } else {
                statusLabel.setText("Error al obtener el estado. Código de estado: " + responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
            statusLabel.setText("Error al obtener el estado");
            e.printStackTrace();
        }
    }
}
