package spotify.rmi.client.directory.unit;
/*
● tener un cliente “CompleteMetadata” que enriquezca los metadatos (dar like, añadir
comentarios, tag, score, etc…), a cada uno de los elementos subidos al directorio
con el objetivo de simular que estamos delante de una plataforma más realista y a
su vez comprobar que los métodos remotos de estas operaciones funcionan.
 */
public class CompleteMetadata {
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
