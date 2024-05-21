package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;

public class SetCover {
    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                String host = "localhost";
                Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");

                // Crear un objeto Media con el nombre de la canción
                String nombreCancion = args[0];
                Media media = new Media(nombreCancion);

                // Cargar la carátula desde la ruta proporcionada
                media.loadCover("/home/hecforc/Descargas/DQE"); // Ruta donde tengas la foto

                // Enviar el objeto Media con la carátula cargada al servidor
                String respuesta = or.setCover(media);
                System.out.println("[Respuesta: " + respuesta + " ]");
            } else {
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
