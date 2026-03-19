package spotify.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SpotifyLauncher {
    public static void main(String[] args) {
        try {
            SpotifyServerImpl oRemoto = new SpotifyServerImpl();
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("id1", oRemoto);
            System.out.println("==========================================");
            System.out.println("SPOTIFY STREAMING SERVER READY");
            System.out.println("==========================================");
        } catch (Exception e) {
            System.err.println("SERVER ERROR: " + e.toString());
            e.printStackTrace();
        }
    }
}