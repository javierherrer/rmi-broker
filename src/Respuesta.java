import java.io.Serializable;

public class Respuesta implements Serializable {
    private Object respuesta;

    public Respuesta() {
        respuesta = "No hay respuesta";
    }

    public Respuesta(Object resp) {
        respuesta = resp;
    }

    @Override
    public String toString() {
        if (respuesta == null) {
            return "Respuesta{" +
                    "respuesta='" + "null" + '\'' +
                    '}';
        } else {
            return "Respuesta{" +
                    "respuesta='" + respuesta.toString() + '\'' +
                    '}';
        }
    }
}
