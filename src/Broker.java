import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Broker extends Remote {
    //API SERVIDORES

    void registrar_servidor(String nombre_servidor,
                            String host_remoto_IP_puerto)
            throws RemoteException;
    void registrar_servicio(String nombre_servidor,
                            String nom_servicio, Vector lista_param,
                            String tipo_retorno) throws RemoteException, ClassNotFoundException;
    void baja_servicio(String nombre_servidor,
                       String nom_servicio) throws RemoteException;

    //API CLIENTES

    //Listar servicios registrados:
    Servicios lista_servicios() throws RemoteException;
    //Ejecutar servicio síncrono:
    Respuesta ejecutar_servicio(String nom_servicio,
                                Vector parametros_servicio)
            throws RemoteException,Exception;
    //Ejecutar servicio asíncrono (versión más avanzada):
    void ejecutar_servicio_asinc(String nom_servicio,
                                 Vector parametros_servicio)
            throws RemoteException;
    //Obtener la respuesta (caso asíncrono):
    Respuesta obtener_respuesta_asinc(String nom_servicio)
            throws RemoteException;
}
