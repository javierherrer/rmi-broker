import java.io.Serializable;

public class Respuesta implements Serializable {
    private String respuesta;

    public Respuesta() {
        respuesta = "No hay respuesta";
    }

    public Respuesta(String cadena) {
        respuesta = cadena;
    }

    public Respuesta(int num) {
        respuesta = String.valueOf(num);
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "respuesta='" + respuesta + '\'' +
                '}';
    }
}
