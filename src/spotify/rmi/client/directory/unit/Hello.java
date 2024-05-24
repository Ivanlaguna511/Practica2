package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;

import java.rmi.Naming;

public class Hello {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");

            // Invocar el método hello del servidor
            String respuesta = or.hello();
            System.out.println("[Respuesta: " + respuesta + " ]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();
        } catch (Exception e) {
            System.err.println("<Cliente: Excepción: " + e);
            e.printStackTrace();
        }
    }
}
