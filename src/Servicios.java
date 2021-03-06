import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Almacena el nombre identificador del servidor, además de la IP y puerto.
 */
public class Servicios implements Serializable {
    private Map<String, Servicio> servicios;

    public Servicios() {
        servicios = new HashMap<>();
    }

    /**
     * Si no existe un servicio con dicho nombre registrado, lo registra y
     *  devuelve true
     * @param s
     * @return
     */
    public boolean registrar_servicio(Servicio s){
        return (servicios.putIfAbsent(s.getNombre(), s) == null);
    }

    public boolean dar_baja_servicio(String nom_servicio){
        return (servicios.remove(nom_servicio)!=null);
    }

    public Servicio obtener_servicio(String nom_servicio) {
        return servicios.get(nom_servicio);
    }

    @Override
    public String toString() {
        return "Servicios{" +
                "servicios=" + servicios.toString() +
                '}';
    }

    public ArrayList<String> obtener_nombres_servicios() {
        return new ArrayList<String>(servicios.keySet());
    }
}
