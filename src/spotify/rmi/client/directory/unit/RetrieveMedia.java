package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class RetrieveMedia {
    public static void main(String[] args) {
        try {
            if(args.length != 1){
                System.out.println("Uso incorrecto, uso: RetrieveMedia [ID].");
            }
            else {
                String host = "localhost";
                Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");

                String ID = args[0];
                Media respuesta = or.retrieveMedia(ID);
                System.out.println("[Respuesta:" + respuesta + "]");
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
