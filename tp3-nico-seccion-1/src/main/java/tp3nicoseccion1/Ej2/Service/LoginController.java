package tp3nicoseccion1.Ej2.Service;

import java.sql.*;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    String url = "jdbc:sqlite:login.db";
    static Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController(){
        crearTablaBD();
        insertarUsuario("usuario1", "contrasena1");
        insertarUsuario("usuario2", "contrasena2");
        insertarUsuario("usuario3", "contrasena3");
    }


    @GetMapping("/login")
    boolean loguear(@RequestParam String user, String password) {
        boolean result = false;
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?")) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }

    @PostMapping("/resetPassword")
    void cambiarContraseña(@RequestParam String user, String new_password) {
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE usuarios SET contrasena = ? WHERE nombre = ?")) {
            pstmt.setString(1, new_password);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void crearTablaBD(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "contrasena TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            logger.info("Tabla usuarios creada exitosamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void insertarUsuario(String nombre, String contrasena) {
        String insertUserSQL = "INSERT INTO usuarios (nombre, contrasena) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {

            // Insertar usuario y contraseña en la base de datos
            pstmt.setString(1, nombre);
            pstmt.setString(2, contrasena);
            pstmt.executeUpdate();
            logger.info("Usuario insertado correctamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
