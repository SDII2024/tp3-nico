package tp3nicoseccion1.Ej1.Service;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Producto {
    private @Id
    @GeneratedValue Long id;
    private String name;
    private Long price;

    Producto(){}

    Producto(Long id, String name, Long price){
        this.id=id;
        this.name=name;
        this.price=price;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Producto))
            return false;
        Producto producto = (Producto) o;
        return Objects.equals(this.id, producto.id) && Objects.equals(this.name, producto.name)
                && Objects.equals(this.price, producto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.price);
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + this.id + ", name='" + this.name + '\'' + ", price='" + this.price + '\'' + '}';
    }
}
