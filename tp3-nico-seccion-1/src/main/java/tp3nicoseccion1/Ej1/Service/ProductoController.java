package tp3nicoseccion1.Ej1.Service;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProductoController {
    String url = "jdbc:sqlite:codigo.db";
    static Logger logger = Logger.getLogger(ProductoController.class.getName());

    ProductoController() {
        crearTablaBD();
        insertarProducto(111111111,"Carne",3000);
        insertarProducto(123456789,"Agua",1500);
        insertarProducto(987654321,"Pan",1000);
    }

    @GetMapping("/productos")
    Producto getProducto(@RequestParam Long id) {
        Producto res = new Producto();
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("SELECT prod,precio FROM productos WHERE nro = ?")) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                res.setName(rs.getString("prod"));
                res.setPrice((long) rs.getInt("precio"));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return res;
    }

    public void crearTablaBD(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nro INT NOT NULL,"
                + "prod TEXT NOT NULL,"
                + "precio INT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            logger.info("Tabla usuarios creada exitosamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void insertarProducto(int nro,String prod,int precio) {
        String insertUserSQL = "INSERT INTO productos (nro, prod, precio) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {

            // Insertar código en la base de datos
            pstmt.setInt(1, nro);
            pstmt.setString(2, prod);
            pstmt.setInt(3, precio);
            pstmt.executeUpdate();
            logger.info("Codigo insertado correctamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
