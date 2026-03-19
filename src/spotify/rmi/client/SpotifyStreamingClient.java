package spotify.rmi.client;

import spotify.media.Media;
import spotify.rmi.common.Spotify;
import spotify.rmi.common.SpotifyServer;

import java.rmi.Naming;
import java.util.Scanner;

public class SpotifyStreamingClient {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            System.out.println("CLIENT: Connecting to RMI Registry...");
            
            Object remoteObject = Naming.lookup("rmi://" + host + "/id1");
            Spotify spotify = (Spotify) remoteObject;
            SpotifyServer serverController = (SpotifyServer) remoteObject;
            
            SpotifyClientImpl localClientCallback = new SpotifyClientImpl();
            serverController.setClientStreamReceptor(localClientCallback);

            System.out.println(spotify.hello());
            
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- STREAMING MENU ---");
                System.out.println("1. Play a specific song");
                System.out.println("2. Play a random song");
                System.out.println("3. View Song Info & Cover");
                System.out.println("4. Edit Metadata (Comment, Score, Like, Cover)");
                System.out.println("5. Exit");
                System.out.print("Select an option: ");

                if (!scanner.hasNextLine()) break;
                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        System.out.print("Enter song name: ");
                        while (!scanner.hasNextLine()) { Thread.sleep(100); }
                        String songName = scanner.nextLine();
                        System.out.println("SERVER: " + serverController.startMedia(new Media(songName)));
                        break;
                    case "2":
                        System.out.println("SERVER: " + serverController.randomPlay());
                        break;
                    case "3":
                        System.out.print("Enter song name to inspect: ");
                        while (!scanner.hasNextLine()) { Thread.sleep(100); }
                        String infoName = scanner.nextLine();
                        Media m = spotify.retrieveMedia(infoName);
                        if (m != null) {
                            System.out.println(m.toString()); // Como ahora showCover() está aquí dentro, saltará la foto sola
                        } else {
                            System.out.println("SERVER: Song not found in directory.");
                        }
                        break;
                    case "4":
                        System.out.print("Enter song name to edit: ");
                        while (!scanner.hasNextLine()) { Thread.sleep(100); }
                        String editName = scanner.nextLine();
                        
                        // Verificar que la canción existe antes de empezar a pedir datos
                        if (spotify.retrieveMedia(editName) == null) {
                            System.out.println("SERVER: Song not found in directory.");
                            break;
                        }

                        // COMENTARIOS
                        System.out.print("Add a comment? (Press Enter to skip, or type comment): ");
                        String comment = scanner.nextLine();
                        if (!comment.trim().isEmpty()) {
                            System.out.println("SERVER: " + spotify.addComment(editName, comment));
                        }

                        // PUNTUACIÓN
                        System.out.print("Add a score [0.0 - 10.0]? (Press Enter to skip): ");
                        String scoreStr = scanner.nextLine();
                        if (!scoreStr.trim().isEmpty()) {
                            try {
                                double score = Double.parseDouble(scoreStr.replace(",", "."));
                                System.out.println("SERVER: " + spotify.addScore(editName, score));
                            } catch (NumberFormatException e) {
                                System.out.println("CLIENT: Invalid number format. Score skipped.");
                            }
                        }

                        // LIKES
                        System.out.print("Add a like? (Y/N): ");
                        String likeStr = scanner.nextLine();
                        if (likeStr.trim().equalsIgnoreCase("y")) {
                            spotify.addLike(editName);
                            System.out.println("SERVER: +1 Like added.");
                        }
                        
                        // CONTENIDO ADULTO
                        System.out.print("Tag as Adult Content (+18)? (Y/N): ");
                        String adultStr = scanner.nextLine();
                        if (adultStr.trim().equalsIgnoreCase("y")) {
                            spotify.tagAdultContent(editName, true);
                            System.out.println("SERVER: Tagged as +18.");
                        } else if (adultStr.trim().equalsIgnoreCase("n")) {
                            spotify.tagAdultContent(editName, false);
                        }

                        // CARÁTULA
                        System.out.print("Set a new cover image name? (Press Enter to skip, e.g., 'Pepe' for Pepe.jpg): ");
                        String coverName = scanner.nextLine();
                        if (!coverName.trim().isEmpty()) {
                            Media coverMedia = new Media(coverName); // Creamos un Media dummy solo para mandar el nombre
                            System.out.println("SERVER: " + spotify.setCover(coverMedia));
                        }
                        
                        System.out.println("CLIENT: Edit process finished.");
                        break;
                    case "5":
                        System.out.println("Shutting down client...");
                        System.exit(0);
                        scanner.close();
                        break;
                    default:
                        // Ignoramos el salto de línea fantasma de la consola
                        if (!option.trim().isEmpty()) {
                            System.out.println("Invalid option.");
                        }
                }
            }
        } catch (Exception e) {
            System.err.println("CLIENT CRASH: " + e.getMessage());
            e.printStackTrace();
        }
    }
}