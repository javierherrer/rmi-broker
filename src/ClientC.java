import java.rmi.Naming;
import java.util.Vector;

/**
 * El programa cliente que invoca de manera remota los métodos del servidor
 *
 */
public class ClientC {
    private static final String BROKER_HOSTNAME = "localhost:32001";
    private static final String DAR_HORA = "dar_hora";
    private static final String DAR_FECHA = "dar_fecha";
    private static final String GET_NUMBER_OF_BOOKS = "number_of_books";
    private static final String GET_NAME_OF_COLLECTION = "name_of_collection";
    private static final String SET_NAME_OF_COLLECTION = "set_name_of_collection";

    /**
     *
     *
     */
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "src/java.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Broker broker =
                    (Broker) Naming.lookup("//" + BROKER_HOSTNAME + "/Broker");

            Servicios servicios = broker.lista_servicios();
            System.out.println(servicios);

            Respuesta respuesta = broker.ejecutar_servicio(DAR_FECHA, new Vector());
            System.out.println(respuesta);

            respuesta = broker.ejecutar_servicio(DAR_HORA, new Vector());
            System.out.println(respuesta);

            Vector argmts = new Vector();
            String newname = "New collection";
            argmts.add(newname);
            respuesta = broker.ejecutar_servicio(SET_NAME_OF_COLLECTION, argmts);
            System.out.println(respuesta);

            respuesta = broker.ejecutar_servicio(GET_NAME_OF_COLLECTION, new Vector());
            System.out.println(respuesta);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
