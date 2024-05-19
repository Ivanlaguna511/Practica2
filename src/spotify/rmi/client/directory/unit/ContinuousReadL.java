package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;

import java.rmi.Naming;
import java.util.Scanner;

public class ContinuousReadL {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            Scanner scanner = new Scanner(System.in);
            if(args.length == 0) {
                while(true) {
                    Media respuesta = or.readL();
                    if (respuesta == null) {
                        System.out.println("No hay más canciones en la playlist DEFAULT");
                        break;
                    }
                    System.out.println("[Respuesta: " + respuesta + " ]");
                    System.out.println(" ");
                    System.out.println("Presione Enter para leer la siguiente canción...");
                    scanner.nextLine();
                }
            }
            else if(args.length == 1){
                String playlist = args[0];
                while(true) {
                    Media respuesta = or.readL(playlist);
                    if (respuesta == null) {
                        System.out.println("No hay más canciones en la playlist " + playlist);
                        break;
                    }
                    System.out.println("[Respuesta: " + respuesta + " ]");
                    System.out.println(" ");
                    System.out.println("Presione Enter para leer la siguiente canción...");
                    scanner.nextLine();
                }
            }
            else{
                System.out.println("Uso incorrecto: ContinuousReadL [playlist]  ó  ContinuousReadL.");
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
