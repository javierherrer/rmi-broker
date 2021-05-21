import java.io.Serializable;

/**
 * Tipo de dato genérico cuya función es almacenar el valor devuelto por la
 *  función ejecutar_servicio.
 */
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
