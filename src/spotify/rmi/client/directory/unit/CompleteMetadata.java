package spotify.rmi.client.directory.unit;

import spotify.rmi.common.Spotify;
import spotify.utils.Directorio;
import spotify.media.Media;

import java.rmi.Naming;
import java.util.Random;

public class CompleteMetadata {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Spotify spotify = (Spotify) Naming.lookup("rmi://" + host + "/id1");

            System.out.println("Enriqueciendo los metadatos de los elementos en el directorio...");
            Directorio directorio = new Directorio();

            // Crear un objeto Random para generar datos aleatorios
            Random random = new Random();

            // Listas de comentarios para elegir aleatoriamente
            String[] comentarios = {"¡Me encanta!", "¡Excelente canción!", "No está mal.", "Podría ser mejor.",
                    "¡La mejor canción de todas!", "Increíble.", "Me hace feliz.", "No es mi estilo."};

            for (String clave : directorio.keySet()) {
                Media media = directorio.obtenerMedia(clave);
                if (media != null) {

                    // Añadir un número aleatorio de likes (0 a 100)
                    int likes = random.nextInt(101);
                    for (int i = 0; i < likes; i++) {
                        spotify.addLike(clave);
                    }
                    System.out.println(likes + " likes añadidos.");

                    // Añadir un número aleatorio de comentarios (0 a 5)
                    int numComentarios = random.nextInt(6);
                    for (int i = 0; i < numComentarios; i++) {
                        String comentario = comentarios[random.nextInt(comentarios.length)];
                        spotify.addComment(clave, comentario);
                    }
                    System.out.println(numComentarios + " comentarios añadidos.");

                    // Añadir tag adulto aleatoriamente (true o false)
                    boolean isAdult = random.nextBoolean();
                    spotify.tagAdultContent(clave, isAdult);
                    System.out.println("Tag adulto añadido: " + isAdult);

                    // Añadir una puntuación aleatoria (0.0 a 10.0)
                    double score = random.nextDouble() * 10;
                    spotify.addScore(clave, score);
                    System.out.println("Score añadido: " + score);
                }
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
