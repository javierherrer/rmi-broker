import java.util.HashMap;
import java.util.Map;

public class Servicios {
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
        return (servicios.remove(nom_servicio)==null);
    }

    public Servicio obtener_servicio(String nom_servicio) {
        return servicios.get(nom_servicio);
    }

}
