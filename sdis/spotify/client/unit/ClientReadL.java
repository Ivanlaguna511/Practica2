package sdis.spotify.client.unit;

import sdis.spotify.common.Spotify;
import sdis.spotify.media.Media;

import java.rmi.Naming;
import java.util.Scanner;

public class ClientReadL {
    //Ejemplo
    public static void main(String [ ] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Spotify or = (Spotify) Naming.lookup("rmi://"+host+"/id1");
            Media respuesta = or.readL();
            System.out.println("[Respuesta: "+respuesta+" ]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:"+re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: "+e);
            e.printStackTrace();
        }
    }
}
