import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Clase que implementa la interfaz remota
 *
 */
public class ServerBImpl extends UnicastRemoteObject implements ServerB {
    private static final String GET_NUMBER_OF_BOOKS = "number_of_books";
    private static final String GET_NAME_OF_COLLECTION = "name_of_collection";
    private static final String SET_NAME_OF_COLLECTION = "set_name_of_collection";
    private static final String SERVER_NAME = "ServerB";
    private static String brokerHostName = "localhost:32001";
    private static String serverIP = "localhost";
    private static String serverPort = "32001";

    private int m_number_of_books;
    private String m_name_of_collection;

    /**
     * Define el constructor para el objeto remoto
     *
     */
    protected ServerBImpl() throws RemoteException {
        super();
        m_number_of_books = 0;
        m_name_of_collection = "";
    }

    /**
     * Devuelve el número de libros
     *
     */
    @Override
    public int number_of_books() throws RemoteException {
        return m_number_of_books;
    }

    /**
     * Devuelve el nombre de la colección
     *
     */
    @Override
    public String name_of_collection() throws RemoteException {
        return m_name_of_collection;
    }

    /**
     * Actualiza el nombre de la colección
     *
     */
    @Override
    public void set_name_of_collection(String _new_value) throws RemoteException {
        m_name_of_collection = _new_value;
    }

    /**
     * Crea el administrador de seguridad
     * Crea el objeto remoto
     * Registra el objeto remoto
     *
     *
     */
    // <brokerIP>:<brokerPort> <serverIP> <serverPort>
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "java.policy");
        System.setSecurityManager(new SecurityManager());

        brokerHostName = args[0];
        serverIP = args[1];
        serverPort = args[2];

        try {
            ServerBImpl obj = new ServerBImpl();
            System.out.println("Creado!");

            Naming.rebind("//" + "localhost:" + serverPort + "/" + SERVER_NAME, obj);
            System.out.println("Estoy registrado!");

            Broker broker =
                    (Broker) Naming.lookup("//" + brokerHostName + "/Broker");
            broker.registrar_servidor(SERVER_NAME,serverIP + ":" + serverPort);
            broker.registrar_servicio(SERVER_NAME, GET_NUMBER_OF_BOOKS, new Vector(), "java.lang.Integer");
            broker.registrar_servicio(SERVER_NAME, GET_NAME_OF_COLLECTION, new Vector(), "java.lang.String");

            Vector param = new Vector();
            param.add(String.class);
            broker.registrar_servicio(SERVER_NAME, SET_NAME_OF_COLLECTION, param, "void");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
