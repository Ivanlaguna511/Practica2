package sdis.spotify.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SpotifyLauncher {
    private static final int RMI_PORT = 1099;
    private static final String SERVICE_NAME = "id1";

    public static void main(String[] args) {
        try {
            SpotifyImpl remoteService = new SpotifyImpl();
            
            // Start the RMI Registry programmatically
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            registry.rebind(SERVICE_NAME, remoteService);
            
            System.out.println("==================================================");
            System.out.println("SPOTIFY RMI SERVER RUNNING ON PORT " + RMI_PORT);
            System.out.println("==================================================");
            System.out.println("Service bound to name: '" + SERVICE_NAME + "'");
            
        } catch (Exception e) {
            System.err.println("SERVER CRASH: Failed to initialize RMI registry.");
            e.printStackTrace();
        }
    }
}