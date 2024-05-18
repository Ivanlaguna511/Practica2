package spotify.rmi.client.directory.unit;

import spotify.media.Media;
import spotify.rmi.common.Spotify;
import java.rmi.Naming;

public class AddScore {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");
            if(args.length == 2){
                String ID = args[0];
                double puntuacion;
                try {
                    puntuacion = Double.parseDouble(args[1]);
                    if (puntuacion < 0 || puntuacion > 10) {
                        throw new IllegalArgumentException("La puntuación debe estar entre 0 y 10.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("El segundo argumento debe ser un número.");
                }

                String respuesta = or.addScore(ID, puntuacion);
                System.out.println("[Respuesta: " + respuesta + " ]");
            }
            else {
                System.out.println("Uso incorrecto, uso: AddScore [ID] [puntuación]");
            }
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: " + e);
            e.printStackTrace();
        }
