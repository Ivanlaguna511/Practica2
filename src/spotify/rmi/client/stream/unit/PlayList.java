package spotify.rmi.client.stream.unit;

import spotify.rmi.client.stream.SpotifyClientImpl;
import spotify.rmi.common.Spotify;
import spotify.media.Media;
import spotify.rmi.common.SpotifyClient;
import spotify.rmi.common.SpotifyServer;
import spotify.rmi.server.SpotifyServerImpl;
import spotify.utils.Directorio;

import java.nio.file.attribute.UserPrincipal;
import java.rmi.Naming;

public class PlayList {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Object or = (Object) Naming.lookup("rmi://" + host + "/id1");
            SpotifyClient cliente=new SpotifyClientImpl();

            if (args.length == 0) {
                //simular meter varias canciones en una playlist
                Spotify spotify=(Spotify) or;
                SpotifyServer server=(SpotifyServer) or;
                server.setClientStreamReceptor(cliente);

                //parte donde se debe añadir canciones para poder reproducirlas
                String cancion1="cancion1";
                String cancion2="cancion1";
                String cancion3="cancion1";
                Media media1=new Media(cancion1);
                Media media2=new Media(cancion2);
                Media media3=new Media(cancion3);
                spotify.add2L(media1);
                spotify.add2L(media2);
                spotify.add2L(media3);

                boolean terminar=false;
                Media media=spotify.readL();
                java.io.BufferedReader tec =
                        new java.io.BufferedReader(
                                new java.io.InputStreamReader(System.in));
                String linea=null;
                while (!terminar){

                    System.out.println("Que desea hacer, 1-Escuchar la cancion,2-pasar a la siguiente, 3 terminar el proceso");
                    linea= tec.readLine();
                    if(linea.equals("1")){
                        server.startMedia(media);
                        spotify.readL();
                        System.out.println("se ha escuchado la cancion " + media.getName());
                    } else if (linea.equals("1")) {
                        System.out.println("la siguiente cancion es "+spotify.peekL());
                        spotify.readL();

                    }
                    else{
                        terminar=true;
                    }

                }

            } else {

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
