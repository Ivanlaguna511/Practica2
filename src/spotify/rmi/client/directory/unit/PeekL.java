package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class PeekL {
    public static void main(String [] args) {
        try {
            Spotify or = (Spotify) Naming.lookup("rmi://localhost/id1");
            if (args.length == 0) {
                Media cancion = or.peekL();
                System.out.println("Canción " + cancion + " consumida de la playlist DEFAULT.");
            } else if (args.length == 1) {
                String playlist = args[1];
                Media cancion = or.peekL(playlist);
                System.out.println("Canción " + cancion + " consumida de la playlist " + playlist + ".");
            } else {
                System.out.println("Uso incorrecto: ReadL [playlist]  ó  ReadL.");
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
