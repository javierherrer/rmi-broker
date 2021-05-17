import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerA extends Remote {
    public String dar_fecha() throws RemoteException;
    public int dar_hora() throws RemoteException;
}
