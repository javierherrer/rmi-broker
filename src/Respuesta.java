import java.io.Serializable;

public class Respuesta implements Serializable {
    private Object respuesta;

    public Respuesta() {
        respuesta = null;
    }

    public Respuesta(Object resp) {
        respuesta = resp;
    }

    @Override
    public String toString() {
        if (respuesta == null) {
            return "Respuesta{"+ "null" +'}';
        } else {
            return respuesta.toString();
        }
    }
}
