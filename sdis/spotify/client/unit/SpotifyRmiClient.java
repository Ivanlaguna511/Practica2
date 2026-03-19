package sdis.spotify.client.unit;

import sdis.spotify.common.Spotify;
import sdis.spotify.media.Media;

import java.rmi.Naming;
import java.util.Scanner;

public class SpotifyRmiClient {
    private static final String DEFAULT_HOST = "localhost";
    private static final String SERVICE_URL = "rmi://%s/id1";

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : DEFAULT_HOST;
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("CLIENT: Looking up RMI registry at " + host + "...");
            Spotify server = (Spotify) Naming.lookup(String.format(SERVICE_URL, host));
            System.out.println("SERVER: " + server.hello());

            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- RMI SPOTIFY MENU ---");
                System.out.println("1. Login (Auth)");
                System.out.println("2. Add Song to Playlist (Add2L)");
                System.out.println("3. Read Song from Playlist (ReadL)");
                System.out.println("4. Delete Playlist (DeleteL)");
                System.out.println("5. Continuous Read (Drain Playlist)");
                System.out.println("6. Explore Metadata (Add Comments/Score)");
                System.out.println("7. View Master Directory");
                System.out.println("8. Exit");
                System.out.print("Select an option: ");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        System.out.print("User: "); String user = scanner.nextLine();
                        System.out.print("Pass: "); String pass = scanner.nextLine();
                        System.out.println("Result: " + server.auth(user, pass));
                        break;
                    case "2":
                        System.out.print("Playlist Name: "); String pList = scanner.nextLine();
                        System.out.print("Song Name: "); String songName = scanner.nextLine();
                        server.add2L(pList, new Media(songName));
                        System.out.println("Song successfully pushed to remote queue.");
                        break;
                    case "3":
                        System.out.print("Playlist Name: ");
                        Media m = server.readL(scanner.nextLine());
                        System.out.println(m != null ? m.toString() : "Playlist is empty.");
                        if (m != null) m.showCover();
                        break;
                    case "4":
                        System.out.print("Playlist Name: ");
                        System.out.println("Result: " + server.deleteL(scanner.nextLine()));
                        break;
                    case "5":
                        System.out.print("Playlist Name to drain: "); String targetPl = scanner.nextLine();
                        Media popped;
                        int count = 0;
                        while ((popped = server.readL(targetPl)) != null) {
                            System.out.println("Popped: " + popped.getName());
                            count++;
                        }
                        System.out.println("Playlist drained. Total elements read: " + count);
                        break;
                    case "6":
                        System.out.print("Target Internal Song Name (e.g., bohemian_rhapsody): "); 
                        String targetSong = scanner.nextLine();
                        System.out.print("Add Comment: ");
                        System.out.println("Server: " + server.addComment(targetSong, scanner.nextLine()));
                        System.out.print("Add Score (1-10): ");
                        System.out.println("Server: " + server.addScore(targetSong, Double.parseDouble(scanner.nextLine())));
                        break;
                    case "7":
                        System.out.println("Master Directory Keys: " + server.getDirectoryList());
                        break;
                    case "8":
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (Exception e) {
            System.err.println("CLIENT FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}