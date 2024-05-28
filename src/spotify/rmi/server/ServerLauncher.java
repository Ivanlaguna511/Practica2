package spotify.rmi.server;

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
            SpotifyServerImpl oRemoto = new SpotifyServerImpl();
            /**
             * TO CRETAE & LAUNCH THE RMI-REGISTRY VIA SOFT
             */
            Registry registro =

                    LocateRegistry.createRegistry(1099);
            /**
             * TO GET THE RMI-REGISTRY REF OF IP-ADDRESS
             */
            //Registry registro = LocateRegistry.getRegistry("localhost");
            registro.rebind("id1", oRemoto);
            System.err.println("Servidor preparado");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: "+e.toString());
            e.printStackTrace();
        }
    }
}
