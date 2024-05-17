package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.media.Media;
import java.rmi.Naming;
import java.util.DoubleSummaryStatistics;


public class ReadL {
    public static void main(String [] args) {
        try {
            Spotify or = (Spotify) Naming.lookup("rmi://localhost/id1");
            if(args.length == 0) {
                Media respuesta = or.readL();
                System.out.println("[Respuesta: " + respuesta + " ]");
            }
            else if(args.length == 1){
                String playlist = args[1];
                Media respuesta = or.readL(playlist);
                System.out.println("[Respuesta: " + respuesta + " ]");
            }
            else{
                System.out.println("Uso incorrecto: ReadL [playlist]  ó  ReadL.");
            }
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();
        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: " + e);
            e.printStackTrace();
        }
    }
