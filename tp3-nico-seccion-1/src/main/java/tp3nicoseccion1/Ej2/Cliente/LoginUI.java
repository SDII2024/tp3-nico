package tp3nicoseccion1.Ej2.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
public class LoginUI extends JFrame {
    private static final String BASE_URL = "http://localhost:8080";

    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetPasswordButton;


    @Autowired
    public LoginUI() {
        super("REST Client");

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Iniciar sesión");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String password = new String(passwordField.getPassword());
                boolean loggedIn = logIn(user, password);
                if (loggedIn) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Inicio de sesión exitoso");
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Inicio de sesión fallido");
                }
            }
        });

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        tabbedPane.addTab("Inicio de sesión", loginPanel);

        JPanel resetPasswordPanel = new JPanel(new GridLayout(3, 2));

        JLabel resetUserLabel = new JLabel("Usuario:");
        JTextField resetUserField = new JTextField();
        JLabel newPasswordLabel = new JLabel("Nueva Contraseña:");
        JPasswordField newPasswordField = new JPasswordField();
        resetPasswordButton = new JButton("Resetear Contraseña");

        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = resetUserField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                resetPassword(user, newPassword);
            }
        });

        resetPasswordPanel.add(resetUserLabel);
        resetPasswordPanel.add(resetUserField);
        resetPasswordPanel.add(newPasswordLabel);
        resetPasswordPanel.add(newPasswordField);
        resetPasswordPanel.add(resetPasswordButton);

        tabbedPane.addTab("Resetear Contraseña", resetPasswordPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private boolean logIn(String user, String password) {
        try {
            URL url = new URL(BASE_URL + "/login?user=" + user + "&password=" + password);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(url.openStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();
                return Boolean.parseBoolean(response);
            } else {
                System.out.println("Error en la conexión: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void resetPassword(String user, String newPassword) {
        try {
            URL url = new URL(BASE_URL + "/resetPassword?user=" + user + "&new_password=" + newPassword);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(LoginUI.this, "Contraseña restablecida exitosamente");
            } else {
                JOptionPane.showMessageDialog(LoginUI.this, "Error al restablecer la contraseña");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
