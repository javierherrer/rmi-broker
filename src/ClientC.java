import java.rmi.Naming;
import java.util.Vector;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * El programa cliente que puede invocar de manera remota los servicios de los
 *  servidores desde el  Broker.
 *
 */
public class ClientC {
    private static String brokerHostName;

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "java.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        brokerHostName = args[0];

        try {
            Broker broker =
                    (Broker) Naming.lookup("//" + brokerHostName + "/Broker");

            Boolean fin=false;
            while(!fin){//En bucle, muestra los servicios por pantalla y le da a elegir 1 al usuario
                Servicios servicios = broker.lista_servicios();
                ArrayList<String> lista_servicios=servicios.obtener_nombres_servicios();

                System.out.println("Esribe el número del servicio que quieres ejecutar.\n"+
                                    "Escribe \"fin\" para salir.\n"+
                                    "Escribe \"r\" para actualizar el listado de servicios disponibles\n");
                for (int i = 0; i < lista_servicios.size(); i++) {
                    System.out.println(i+" "+lista_servicios.get(i));
                }

                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                

                if(input.equals("fin")){//Si escribe fin, acaba el bucle
                    fin=true;
                }else if(input.equals("r")){
                    broker = (Broker) Naming.lookup("//" + brokerHostName + "/Broker");
                    servicios = broker.lista_servicios();
                    lista_servicios=servicios.obtener_nombres_servicios();
                }else{
                    int seleccion = Integer.parseInt(input.trim());
                    if(seleccion>=lista_servicios.size()){
                        System.out.println("opción no válida");
                    }else{
                        Servicio servicio= servicios.obtener_servicio(lista_servicios.get(seleccion));
                        Class partypes[]=servicio.getPartypes();
                        Vector parametros=new Vector();
                        Boolean parametrosCorrectos=true;
                        //Leemos los parámetros por pantalla
                        for(int i = 0; i < partypes.length&&parametrosCorrectos; i++){
                            System.out.printf("Parametro "+i+" tipo "+partypes[i].getSimpleName()+":");
                            switch(partypes[i].getName()){
                                case "java.lang.String":
                                    parametros.add(scanner.nextLine());
                                break;

                                case "java.lang.Integer":
                                    try{
                                        parametros.add(scanner.nextInt());
                                    }catch(Exception e){
                                        System.out.println("Error al leer el parametro de teclado");
                                        parametrosCorrectos=false;
                                    }
                                break;
                                case "java.lang.Boolean":
                                    parametros.add(Boolean.parseBoolean(scanner.nextLine()));
                                break;
                                default:
                                System.out.println("Los parámetros del tipo "+partypes[i]+" no son admitidos en este cliente");
                                    parametrosCorrectos=false;

                            }
                        }
                        if(parametrosCorrectos){
                            try{
                                Respuesta respuesta=broker.ejecutar_servicio(servicio.getNombre(),parametros);
                                if(servicio.getTipoRetorno()!=null){
                                    System.out.println("\nRespuesta:\n"+respuesta+'\n');
                                }else{
                                    System.out.println("Servicio realizado");
                                }
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                            
                        }else{
                            System.out.println("No se ha podido ejecutar el servicio"+servicio.getNombre());
                        }
                    }
                }

            }
            

            /*Respuesta respuesta = broker.ejecutar_servicio(DAR_FECHA, new Vector());
            System.out.println(respuesta);

            respuesta = broker.ejecutar_servicio(DAR_HORA, new Vector());
            System.out.println(respuesta);

            Vector argmts = new Vector();
            String newname = "New collection";
            argmts.add(newname);
            respuesta = broker.ejecutar_servicio(SET_NAME_OF_COLLECTION, argmts);
            System.out.println(respuesta);

            respuesta = broker.ejecutar_servicio(GET_NAME_OF_COLLECTION, new Vector());
            System.out.println(respuesta);*/

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
