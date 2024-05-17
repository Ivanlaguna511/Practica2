package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class SetCover {
    public static void main(String[] args) {
        try {
            if(args.length == 2){
                String host = "localhost";
                Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");

                String nombreCancion = args[0];
                String rutaImagen = args[1];
                Media cancion = new Media(nombreCancion);
                cancion.loadCover(rutaImagen);
                String respuesta = or.setCover(nombreCancion, cancion);
                System.out.println("[Respuesta:" + respuesta + "]");
            }
            else {
                System.out.println("Uso incorrecto, uso: SetCover [nombreCancion] [rutaImagen].");
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
