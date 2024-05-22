package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.utils.Directorio;
import spotify.media.Media;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompleteMetadata {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify or = (Spotify) Naming.lookup("rmi://" + host + "/id1");

            // Listas de comentarios para elegir aleatoriamente
            String [] comentarios = {
                    "¡Me encanta!",
                    "¡Excelente canción!",
                    "No está mal.",
                    "Podría ser mejor.",
                    "¡La mejor canción de todas!",
                    "Increíble.",
                    "Me hace feliz.",
                    "No es mi estilo."};

            // Crear un objeto Random para generar datos aleatorios
            Random random = new Random();

            // Obtener el conjunto de claves del directorio
            String claves = or.getDirectoryList();
            if (claves.length() == 0){
                System.out.println("No se pudo realizar la acción");
            }
            else {
                System.out.println("Enriqueciendo los metadatos de los elementos en el directorio...");
                String[] palabras = claves.split(", ");

                for (String clave : palabras) {
                    // Añadir un número aleatorio de likes (0 a 100)
                    int likes = random.nextInt(101);
                    for (int i = 0; i < likes; i++) {
                        or.addLike(clave);
                    }

                    // Añadir un número aleatorio de comentarios (0 a 5)
                    int numComentarios = random.nextInt(6);
                    for (int i = 0; i < numComentarios; i++) {
                        String comentario = comentarios[random.nextInt(comentarios.length)];
                        or.addComment(clave, comentario);
                    }

                    // Añadir tag adulto aleatoriamente (true o false)
                    boolean isAdult = random.nextBoolean();
                    or.tagAdultContent(clave, isAdult);

                    // Añadir una puntuación aleatoria (0.0 a 10.0)
                    double score = random.nextDouble() * 10;
                    or.addScore(clave, score);
                }
            }
        } catch (RemoteException re) {
            System.err.println("<Cliente: Excepción RMI:" + re);
            re.printStackTrace();

        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: " + e);
            e.printStackTrace();
        }
    }
}
