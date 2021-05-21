import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.Naming;

import java.util.Arrays;
import java.util.Vector;

/**
 * Función que ofrece un servidor y queda registrada en el Broker. Desde ella,
 *  el Broker ejecuta el servicio realizando un lookup del servidor y,
 *  posteriormente, ejecutando el método de la clase en cuestión utilizando
 *  la reflectividad en Java: getClass, getMethod, invoke. Y obteniendo una
 *  respuesta (tipoRetorno) que será devuelta al cliente.
 */
public class Servicio implements Serializable {
    private String nombre; //Nombre del servicio
    private Class tipoRetorno; //Tipo del objeto de retorno del servicio
    private Class partypes[];
    private Servidor servidor;

    public Servicio(String nom, String retorno, Vector param, Servidor server)
            throws ClassNotFoundException {
        nombre = nom;
        if (retorno.equals("void")) {
            tipoRetorno = null;
        } else {
            tipoRetorno = Class.forName(retorno);
        }
        servidor = server;

        partypes = new Class[param.size()];
        for (int i = 0; i < param.size(); i++) {
            partypes[i] = (Class) param.get(i);
        }
    }

    public String getNombre() {
        return nombre;
    }
    public Class[] getPartypes(){
        return partypes;
    }
    public Class getTipoRetorno(){
        return tipoRetorno;
    }

    public Respuesta ejecutar_servicio(Vector parametros_servicio) throws Exception{
        Object obj = null;
        Class cls = null;
        Object arglist[] = new Object[parametros_servicio.size()];

        if (partypes.length != parametros_servicio.size()) {
            throw new Exception("Longitud de parámetros incorrecta.");
        }

        for (int i = 0; i < parametros_servicio.size(); i++) {
            obj = parametros_servicio.get(i);
            cls = partypes[i];
            if ( ! cls.equals(obj.getClass()) ) {
                throw new Exception("Tipos de parámetro no concuerdan");
            } else {
                arglist[i] = obj;
            }
        }

        Respuesta respuesta = new Respuesta();
        
        Object server =
                (Object) Naming.lookup("//" +
                        servidor.getHostname() + "/" + servidor.getName());
        Class srvrCls = server.getClass();
        Method meth = srvrCls.getMethod(nombre, partypes);
        Object retobj;
        if (parametros_servicio.isEmpty()) {
            retobj = meth.invoke(server);
        } else {
            retobj = meth.invoke(server, arglist);
        }

        if (tipoRetorno == null) {
            respuesta = new Respuesta(null);
        } else if ( ! tipoRetorno.equals(retobj.getClass())) {
            respuesta = new Respuesta();
            throw new Exception("Tipos de respuesta no concuerdan");
        } else {
            respuesta = new Respuesta(retobj);
        }
    
        return respuesta;
        
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "nombre='" + nombre + '\'' +
                ", tipoRetorno=" + tipoRetorno +
                ", partypes=" + Arrays.toString(partypes) +
                ", servidor=" + servidor.toString() +
                '}';
    }
}
