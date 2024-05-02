package tp3nicoseccion1.Ej3.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DolarResponse {
    private String precio;
    private String source;

    public DolarResponse(String precio, String source){
        this.precio=precio;
        this.source=source;
    }
}
