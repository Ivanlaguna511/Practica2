package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class PeekL {
    public static void main(String [] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            if (args.length == 0) {
                Media respuesta = or.peekL();
                System.out.println("Respuesta: " + respuesta + " ]");
            } else if (args.length == 1) {
                String playlist = args[0];
                Media respuesta = or.peekL(playlist);
                System.out.println("Respuesta: " + respuesta + " ]");
            } else {
                System.out.println("Uso incorrecto: PeekL [playlist]  ó  PeekL.");
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
