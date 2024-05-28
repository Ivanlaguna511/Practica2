package spotify.rmi.client.stream.unit;

import spotify.rmi.client.stream.SpotifyClientImpl;
import spotify.rmi.common.Spotify;
import spotify.media.Media;
import spotify.rmi.common.SpotifyClient;
import spotify.rmi.common.SpotifyServer;
import spotify.rmi.server.SpotifyServerImpl;
import spotify.utils.Directorio;

import javax.swing.*;
import java.rmi.Naming;

public class PlaySong {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Object or = (Object) Naming.lookup("rmi://" + host + "/id1");
            SpotifyClient cliente=new SpotifyClientImpl();

            if (args.length == 1) {
                Spotify spotify=(Spotify) or;
                SpotifyServer server=(SpotifyServer) or;
                String cancion = args[0];
                Media media = new Media(cancion);
                server.setClientStreamReceptor(cliente);
                System.out.println(server.startMedia(media));

            } else {
                System.out.println("Debes introducir una cancion");
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
