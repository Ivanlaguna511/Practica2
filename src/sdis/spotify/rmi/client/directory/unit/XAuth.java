package sdis.spotify.rmi.client.directory.unit;

import java.util.Scanner;

public class XAuth {
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




    //P1
    final private int PUERTO = 2000;
    static java.io.ObjectInputStream ois = null;
    static java.io.ObjectOutputStream oos = null;


    public static void main(String[] args) throws java.io.IOException {
        Scanner s=new Scanner(System.in);
        String [] array= new String[3];
        array[0]="localhost";
        String host = array[0];  //localhost o ip|dn del servidor
        java.net.Socket sock = new java.net.Socket(host, 2000);
        try {
            oos = new java.io.ObjectOutputStream(sock.getOutputStream());
            ois = new java.io.ObjectInputStream(sock.getInputStream());
            MensajeProtocolo minfo = (MensajeProtocolo) ois.readObject();
            System.out.println(minfo);
            if(!minfo.getPrimitiva().equals(Primitiva.ERROR)){
                System.out.println("introduzca usuario");
                array[1]=s.nextLine();
                String usuario = array[1];  //usuario del cliente
                System.out.println("introduce contraseña");
                array[2]=s.nextLine();
                String password = array[2];  //contraseña del cliente
                pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.XAUTH, usuario, password));
            }
        } catch (java.io.EOFException e) {
            System.err.println("Cliente: Fin de conexión.");
        } catch (java.io.IOException e) {
            System.err.println("Cliente: Error de apertura o E/S sobre objetos: "+e);
        } catch (MalMensajeProtocoloException e) {
            System.err.println("Cliente: Error mensaje Protocolo: "+e);
        } catch (Exception e) {
            System.err.println("Cliente: Excepción. Cerrando Sockets: "+e);
        } finally {
            ois.close();
            oos.close();
            sock.close();
        }
    }
    static void pruebaPeticionRespuesta(MensajeProtocolo mp) throws
            java.io.IOException,
            MalMensajeProtocoloException, ClassNotFoundException {
        System.out.println("> "+mp);
        oos.writeObject(mp);
        System.out.println("< "+(MensajeProtocolo) ois.readObject());
    }
}
