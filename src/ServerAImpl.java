
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class ServerAImpl extends UnicastRemoteObject implements ServerInteface {
    private static final String DAR_HORA = "dar_hora";
    private static final String DAR_FECHA = "dar_fecha";
    private static final String BROKER_HOSTNAME = "localhost:32001";
    private static final String SERVER_NAME = "ServerA";
    private static final String SERVER_HOSTNAME = "localhost:32001";

    public ServerAImpl() throws RemoteException {
        super();
    }

    private int dar_hora() {
        Calendar rightNow = Calendar.getInstance();
        int hora = rightNow.get(Calendar.HOUR_OF_DAY);
        return hora;  
    }

    private String dar_fecha() {
        java.util.Date date =  Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        return dateFormat.format(date);  
    }

    @Override
    public Respuesta ejecutar_servicio(String nom_servicio,
                                       Vector parametros_servicio)
            throws RemoteException {
        Respuesta respuesta = new Respuesta();
        switch (nom_servicio) {
            case DAR_FECHA:
                String fecha = dar_fecha();
                respuesta =  new Respuesta(fecha);
                break;
            case DAR_HORA:
                int hora = dar_hora();
                respuesta = new Respuesta(hora);
                break;
            default:
                System.out.println("Servicio \"" + nom_servicio + "\" no existe.");
        }
        return respuesta;
    }

//    @Override
//    public void ejecutar_servicio_asinc(String nom_servicio, Vector parametros_servicio) {
//        // TODO
//    }
//
//    @Override
//    public Respuesta obtener_respuesta_asinc(String nom_servicio) {
//        // TODO
//        return null;
//    }

    public static void main (String [] args) {
        System.setProperty("java.security.policy", "src/java.policy");
        System.setSecurityManager(new SecurityManager());

        try {
            ServerAImpl obj = new ServerAImpl();
            System.out.println("Creado!");

            Naming.rebind("//" + SERVER_HOSTNAME + "/MyServices", obj);
            System.out.println("Estoy registrado!");

            Broker broker =
                    (Broker) Naming.lookup("//" + BROKER_HOSTNAME + "/Broker");
            broker.registrar_servidor(SERVER_NAME, SERVER_HOSTNAME);
            broker.registrar_servicio(SERVER_NAME, DAR_FECHA, new Vector(), DAR_FECHA.getClass().getSimpleName());
            broker.registrar_servicio(SERVER_NAME, DAR_HORA, new Vector(), DAR_HORA.getClass().getSimpleName());

            Respuesta respuesta = broker.ejecutar_servicio(DAR_FECHA, new Vector());
            System.out.println(respuesta);

            respuesta = broker.ejecutar_servicio(DAR_HORA, new Vector());
            System.out.println(respuesta);


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
