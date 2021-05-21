import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class ServerAImpl extends UnicastRemoteObject implements ServerA {
    private static final String DAR_HORA = "dar_hora";
    private static final String DAR_FECHA = "dar_fecha";
    private static final String GUARDAR_VARIABLE = "guardar_variable";
    private static final String COGER_VARIABLE = "coger_variable";
    private static final String BROKER_HOSTNAME = "localhost:32001";
    private static final String SERVER_NAME = "ServerA";
    private static final String SERVER_HOSTNAME = "localhost:32001";
    private Integer variable;
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
    public Integer guardar_variable(Integer v) throws RemoteException {  
        variable=v;
        return variable;  
    }
    public Integer coger_variable() throws RemoteException {
        return variable;  
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
            //broker.registrar_servicio(SERVER_NAME, DAR_FECHA, new Vector(), "java.lang.String");
            //broker.registrar_servicio(SERVER_NAME, DAR_HORA, new Vector(), "java.lang.Integer");

            ArrayList<Servicio> servicios=new ArrayList<Servicio>();
            ArrayList<Boolean> serviciosUp=new ArrayList<Boolean>();
            servicios.add(new Servicio(DAR_FECHA, "java.lang.String", new Vector(), null));
            servicios.add(new Servicio(DAR_HORA, "java.lang.Integer", new Vector(), null));
            servicios.add(new Servicio(COGER_VARIABLE, "java.lang.Integer", new Vector(), null));
            Vector methodparam= new Vector();
            methodparam.add(Integer.class);
            servicios.add(new Servicio(GUARDAR_VARIABLE, "java.lang.Integer",methodparam, null));
            //Registramos todos los servicios
            for(int i = 0; i < servicios.size(); i++){
                Servicio s=servicios.get(i); 
                Vector param= new Vector();
                Collections.addAll(param,s.getPartypes());
                broker.registrar_servicio(SERVER_NAME,s.getNombre(),param , s.getTipoRetorno().getName());
                serviciosUp.add(true);
            }

            Boolean fin=false;
            while(!fin){
                System.out.println("Servicios disponibles. Escribe un número para registrarlo/darlo de baja:");
                for(int i = 0; i < servicios.size(); i++){
                    System.out.printf(i+". ");
                    if (serviciosUp.get(i)==true){
                        System.out.printf("[UP]   ");
                    }else{
                        System.out.printf("[DOWN] ");
                    }
                    System.out.println(servicios.get(i).getNombre());
                }
                //Seleccionamos el servicio
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if(input.equals("fin")){//Si escribe fin, acaba el bucle
                    fin=true;
                }else{
                    int seleccion = Integer.parseInt(input.trim());
                    if(seleccion>=servicios.size()){
                        System.out.println("opción no válida");
                    }else{
                        Servicio s=servicios.get(seleccion); 
                        if(serviciosUp.get(seleccion)==true){
                            //El servicio está registrado, lo damos de baja
                            broker.baja_servicio(SERVER_NAME,s.getNombre());
                            serviciosUp.set(seleccion,false);
                        }else{
                            Vector param= new Vector();
                            Collections.addAll(param,s.getPartypes());
                            broker.registrar_servicio(SERVER_NAME,s.getNombre(), param, s.getTipoRetorno().getName());
                            serviciosUp.set(seleccion,true);
                        }
                    }
                }
                //limpiamos pantalla
                System.out.print("\033[H\033[2J");  
                System.out.flush();  

            }


            

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
