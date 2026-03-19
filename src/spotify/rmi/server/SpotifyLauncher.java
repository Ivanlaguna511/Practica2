package spotify.rmi.server;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SpotifyLauncher {
    public static void main(String[] args) {
        try {
            System.out.println("Launching Spotify server (MODULE 3 - SSL & LOGS)...");
            
            // Set KeyStore for SSL Server Identity
            System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

            System.setProperty("java.rmi.server.hostname", "localhost");
            
            SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
            SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
            
            Registry registry = LocateRegistry.createRegistry(1099, csf, ssf);  
            
            // Instantiate the secure server object
            SpotifyServerImpl serverObj = new SpotifyServerImpl(); 
            
            registry.rebind("id1", serverObj);
            
            System.out.println("==========================================");
            System.out.println("SPOTIFY STREAMING SERVER (SECURE) READY");
            System.out.println("==========================================");
            
        } catch (Exception e) {
            System.err.println("Critical server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}