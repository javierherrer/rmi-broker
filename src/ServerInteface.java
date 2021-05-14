import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ServerInteface extends Remote {
    //Ejecutar servicio síncrono:
    Respuesta ejecutar_servicio(String nom_servicio,
                                Vector parametros_servicio) throws RemoteException;
    //Ejecutar servicio asíncrono (versión más avanzada):
//    void ejecutar_servicio_asinc(String nom_servicio,
//                                 Vector parametros_servicio);
//    //Obtener la respuesta (caso asíncrono):
//    Respuesta obtener_respuesta_asinc(String nom_servicio);
}
