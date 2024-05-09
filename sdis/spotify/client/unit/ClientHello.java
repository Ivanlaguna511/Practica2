package sdis.spotify.client.unit;

import sdis.spotify.common.Spotify;

import java.rmi.Naming;

public class ClientHello {
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            String respuesta = or.hello();
            System.out.println("[Respuesta: " + respuesta + " ]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI>");
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion>");
            e.printStackTrace();
        }
    }
}
