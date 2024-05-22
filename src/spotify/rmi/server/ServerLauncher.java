package spotify.rmi.server;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/*
Tareas adicionales que suben nota:
O que el servidor precargue una serie de contenidos Media en la playlist por defecto
(y por lo tanto en el directorio), o se haya implementado un cliente que introduzca de
manera masiva varios de estos elementos para cargarlos rápidamente.
 */
public class ServerLauncher {
    public static void main(String [ ] args) {
        try {
            SslRMIClientSocketFactory rmicsf = new SslRMIClientSocketFactory();
            javax.rmi.ssl.SslRMIServerSocketFactory rmissf = new javax.rmi.ssl.SslRMIServerSocketFactory();
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1100, rmicsf, rmissf);
            SpotifyServerImpl oRemoto = new SpotifyServerImpl(rmicsf, rmissf);
            r.rebind("id1", oRemoto);
            System.err.println("Servidor preparado");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: "+e.toString());
            e.printStackTrace();
        }
    }
}
