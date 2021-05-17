import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * La interfaz remota de Java
 *
 */
public interface ServerB extends Remote{
    int number_of_books() throws RemoteException;
    String name_of_collection() throws RemoteException;
    void set_name_of_collection(String _new_value) throws RemoteException;
}