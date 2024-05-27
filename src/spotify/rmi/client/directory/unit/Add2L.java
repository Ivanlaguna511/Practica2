package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class Add2L {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            if (args.length == 1) {
                String cancion = args[0];
                // Añadir a la playlist DEFAULT
                Media media = new Media(cancion);
                or.add2L(media);
                System.out.println("Canción " + cancion + " añadida a la playlist DEFAULT.");
            } else if (args.length == 2) {
                // Añadir a una playlist específica
                String cancion = args[0];
                Media media = new Media(cancion);
                String playlist = args[1];
                or.add2L(playlist, media);
                System.out.println("Canción " + cancion + " añadida a la playlist " + playlist + ".");
            } else {
                System.out.println("Uso incorrecto: Add2L [canción] [playlist]  ó  Add2L [canción].");
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
