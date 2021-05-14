import java.rmi.Naming;

import java.util.Vector;

public class Servicio {
    private String nombre; //Nombre del servicio
    private String tipoRetorno; //Tipo del objeto de retorno del servicio
    private Vector parametros; //Array con los parámetros del servicio
    private Servidor servidor;

    public Servicio(String nom, String retorno, Vector param, Servidor server) {
        nombre = nom;
        tipoRetorno = retorno;
        parametros = param;
        servidor = server;
    }

    public String getNombre() {
        return nombre;
    }

    public Respuesta ejecutar_servicio(Vector parametros_servicio) {
        String tipo1 = "";
        String tipo2 = "";
        Object o1 = null;
        Object o2 = null;

        if (parametros.size() != parametros_servicio.size()) {
            return new Respuesta("Longitud de parámetros incorrecta.");
        }

        for (int i = 0; i < parametros_servicio.size(); i++) {
            o1 = parametros_servicio.get(i);
            tipo1 = o1.getClass().getSimpleName();
            o2 = parametros.get(i);
            tipo2 = o2.getClass().getSimpleName();

            if (! tipo1.equals(tipo2)) {
                return new Respuesta("Tipos de parámetro no concuerdan");
            }
        }

        Respuesta respuesta = new Respuesta();
        try {
            ServerInteface server =
                    (ServerInteface) Naming.lookup("//" +
                            servidor.getHostname() + "/MyServices");
            respuesta = server.ejecutar_servicio(nombre, parametros_servicio);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            return respuesta;
        }
    }
}
