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


                boolean terminar=false;
                System.out.println(((Spotify) or).hello());
                java.io.BufferedReader tec =
                        new java.io.BufferedReader(
                                new java.io.InputStreamReader(System.in));
                String linea=null;
                while (!terminar){

                    System.out.println("Que desea hacer, 1-Escuchar la cancion,2-pasar a la siguiente, 3 terminar el proceso");
                    linea= tec.readLine();
                    Media media=spotify.readL();
                    if(linea.equals("1")){
                        if(media == null){
                            System.out.println("PlayList vacia");

                        }
                        else {
                            server.startMedia(media);
                            Thread.sleep(5000);
                            System.out.print("ESCUCHANDO");
                            System.out.println(media);

                        }
                    } else if (linea.equals("2")) {
                        System.out.println("CANCION ACTUAL "+spotify.peekL());
                    }
                    else{
                        terminar=true;
                    }

                }
                System.out.println("ADIOS");
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
