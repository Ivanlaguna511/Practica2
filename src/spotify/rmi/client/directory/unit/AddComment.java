package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import java.rmi.Naming;

public class AddComment {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            if(args.length == 2){
                String ID = args[0];
                String comment = args[1];

                String respuesta = or.addComment(ID, comment);
                System.out.println("[Respuesta: " + respuesta + "]");
            }
            else {
                System.out.println("Uso incorrecto, uso: AddComment [ID] [comentario]");
            }
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: " + e);
            e.printStackTrace();
        }
    }
}
