import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class ServerAImpl extends UnicastRemoteObject implements ServerA {
    private static final String DAR_HORA = "dar_hora";
    private static final String DAR_FECHA = "dar_fecha";
    private static final String BROKER_HOSTNAME = "localhost:32001";
    private static final String SERVER_NAME = "ServerA";
    private static final String SERVER_HOSTNAME = "localhost:32001";

    public ServerAImpl() throws RemoteException {
        super();
    }

    public int dar_hora() throws RemoteException {
        Calendar rightNow = Calendar.getInstance();
        int hora = rightNow.get(Calendar.HOUR_OF_DAY);
        return hora;  
    }

    public String dar_fecha() throws RemoteException {
        java.util.Date date =  Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        return dateFormat.format(date);  
    }

    public static void main (String [] args) {
        System.setProperty("java.security.policy", "src/java.policy");
        System.setSecurityManager(new SecurityManager());

        try {
            ServerAImpl obj = new ServerAImpl();
            System.out.println("Creado!");

            Naming.rebind("//" + SERVER_HOSTNAME + "/" + SERVER_NAME, obj);
            System.out.println("Estoy registrado!");

            Broker broker =
                    (Broker) Naming.lookup("//" + BROKER_HOSTNAME + "/Broker");
            broker.registrar_servidor(SERVER_NAME, SERVER_HOSTNAME);
            broker.registrar_servicio(SERVER_NAME, DAR_FECHA, new Vector(), "java.lang.String");
            broker.registrar_servicio(SERVER_NAME, DAR_HORA, new Vector(), "java.lang.Integer");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
