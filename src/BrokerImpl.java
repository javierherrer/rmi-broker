import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BrokerImpl extends UnicastRemoteObject implements Broker {
    private static final String BROKER_HOSTNAME = "localhost:32001";

    Map<String, Servidor> servidores;
    Servicios servicios;

    public BrokerImpl() throws RemoteException {
        super();
        servidores = new HashMap<>();
        servicios = new Servicios();
    }

    /**
     *
     * @param nombre_servidor
     * @param host_remoto_IP_puerto
     */
    @Override
    public void registrar_servidor(String nombre_servidor,
                               String host_remoto_IP_puerto)
            throws RemoteException {
        Servidor nuevoServidor= new Servidor(nombre_servidor,
                host_remoto_IP_puerto);

        if (servidores.putIfAbsent(nombre_servidor, nuevoServidor)==null) {
            //Si no existe un servidor con dicho nombre registrado, lo registra
            // y devuelve true
            System.out.println("Servidor \""+nombre_servidor+ "\" registrado." +
                    " Servidores actuales "+ servidores.toString());
        }else{
            System.out.println("Servidor \""+nombre_servidor+ "\" " +
                    "ya existente. Servidores actuales "+ servidores.toString());
        }
    }

    /**
     *
     * @param nombre_servidor
     * @param nom_servicio
     * @param lista_param
     * @param tipo_retorno
     */
    @Override
    public void registrar_servicio(String nombre_servidor, String nom_servicio,
                                   Vector lista_param, String tipo_retorno)
            throws RemoteException{

        Servidor servidor = servidores.get(nombre_servidor);
        if (servidor != null) {
            Servicio nuevoServicio = new Servicio(nom_servicio, tipo_retorno,
                    lista_param, servidor);

            boolean registrado = servicios.registrar_servicio(nuevoServicio);
            if (registrado) {
                System.out.println("Servicio \""+nom_servicio+ "\" registrado.");
            } else {
                System.out.println("Servicio \""+nom_servicio+ "\" ya existente");
            }
        }else{
            System.out.println("El servidor \""+nombre_servidor+ "\" no esta" +
                    " registrado actualmente. Servidores actuales "+
                    servidores.toString());
        }
    }

    /**
     * Dar de baja servicio
     * @param nombre_servidor
     * @param nom_servicio
     */
    @Override
    public void baja_servicio(String nombre_servidor,String nom_servicio)
            throws RemoteException{
        boolean eliminado = servicios.dar_baja_servicio(nom_servicio);

        if (eliminado) {
            System.out.println("Servicio \""+nom_servicio+ "\" eliminado.");
        } else{
            System.out.println("Servicio \""+nom_servicio+ "\" no existe");
        }
    }

    @Override
    public Servicios lista_servicios() throws RemoteException{
        return servicios;
    }

    @Override
    public Respuesta ejecutar_servicio(String nom_servicio,
                                       Vector parametros_servicio)
            throws RemoteException{
        Servicio servicio = servicios.obtener_servicio(nom_servicio);
        Respuesta respuesta = new Respuesta();
        if (servicio != null) {
            respuesta = servicio.ejecutar_servicio(parametros_servicio);
        } else {
            System.out.println("Servicio \"" + nom_servicio + "\" no existe.");
            respuesta = new Respuesta("Servicio \"" + nom_servicio + "\" no existe.");
        }
        return respuesta;
    }

    @Override
    public void ejecutar_servicio_asinc(String nom_servicio,
                                        Vector parametros_servicio)
            throws RemoteException{
        // TODO
    }

    @Override
    public Respuesta obtener_respuesta_asinc (String nom_servicio)
            throws RemoteException{
        // TODO
        return null;
    }

    public static void main(String args[]) {
        System.setProperty("java.security.policy", "./java.policy");
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }
        String hostName = "localhost:32001";
        try {
            BrokerImpl obj = new BrokerImpl();
            System.out.println("Creado!");

            Naming.rebind("//" + BROKER_HOSTNAME + "/Broker", obj);
            System.out.println("Estoy registrado!");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

