package sdis.spotify.client.unit;
/*
● tener un cliente “ContinuousReadL” que nos permita leer destructivamente de
manera sencilla e interactiva para comprobar el contenido de una playlist y llegar a
agotar el contenido de ésta. Después se ha de comprobar con “ClientRetrieveMedia”
que los elementos, aunque no siguen en la playlist, si están en el directorio.
 */
public class ClientRetrieveMedia {
    //Ejemplo
    public static void main(String [ ] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Hello or = (Hello) Naming.lookup("rmi://"+host+"/id1");
            String respuesta = or.sayHello();
            System.out.println("[Respuesta:"+respuesta+"]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:"+re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: "+e);
            e.printStackTrace();
        }
    }
}
