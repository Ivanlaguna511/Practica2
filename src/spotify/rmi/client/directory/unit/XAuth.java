package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import java.rmi.Naming;

public class XAuth {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host "/id1");

            String username = args[0];
            String password = args[1];

            String respuesta = or.auth(username, password);
            System.out.println("[Respuesta: " + respuesta + "]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: " + e);
            e.printStackTrace();
        }
    }
}
