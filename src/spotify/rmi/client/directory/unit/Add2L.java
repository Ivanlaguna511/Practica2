package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class Add2L {
    public static void main(String[] args) {
        try {
            Spotify or = (Spotify) Naming.lookup("rmi://localhost/id1");
            if (args.length == 1) {
                String cancion = args[0];
                // Añadir a la playlist DEFAULT
                Media media = new Media(cancion);
                or.add2L(media);
                System.out.println("Cancion " + cancion + " añadida a la playlist DEFAULT.");
            } else if (args.length == 2) {
                // Añadir a una playlist específica
                String playlist = args[0];
                String cancion = args[1];
                Media media = new Media(cancion);

                or.add2L(playlist, media);
                System.out.println("Cancion " + cancion + " añadida a la playlist " + playlist + ".");
            } else {
                System.out.println("Uso incorrecto: Add2L [nombre] [playlist]  ó  Add2L [nombre].");
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