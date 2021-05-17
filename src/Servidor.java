import java.io.Serializable;

public class Servidor implements Serializable {
    private String nombre; //Nombre identificador del servidor
    private String hostname; //IP y puerto del servidor

    public Servidor(String nom, String host) {
        nombre = nom;
        hostname = host;
    }

    public String getHostname() {
        return hostname;
    }

    public String getName() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + " " + hostname;
    }

}
