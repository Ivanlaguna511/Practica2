package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import java.rmi.Naming;

public class GetDirectoryList {
    public static void main(String[] args) {
        try {
            if(args.length > 0){
                System.out.println("Uso incorrecto, uso: GetDirectoryList.");
            }
            else {
                String host = "localhost";
                Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");

                String respuesta = or.getDirectoryList();
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
