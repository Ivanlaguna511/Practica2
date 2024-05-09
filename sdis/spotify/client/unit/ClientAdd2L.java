package sdis.spotify.client.unit;

import sdis.spotify.common.Spotify;
import sdis.spotify.media.Media;

import java.rmi.Naming;

public class ClientAdd2L {
    //Ejemplo
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Media cancion = new Media("YO LO SOÑE");
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            or.add2L(cancion);
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: " + e);
            e.printStackTrace();

        }
    }
}
