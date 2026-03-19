package spotify.media;

import java.io.File;

public class Globals {
    public static final String HELLO_BANNER = "Welcome to \n" +
            "░██████╗██████╗░░█████╗░████████╗██╗███████╗██╗░░░██╗\n" +
            "██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝╚██╗░██╔╝\n" +
            "╚█████╗░██████╔╝██║░░██║░░░██║░░░██║█████╗░░░╚████╔╝░\n" +
            "░╚═══██╗██╔═══╝░██║░░██║░░░██║░░░██║██╔══╝░░░░╚██╔╝░░\n" +
            "██████╔╝██║░░░░░╚█████╔╝░░░██║░░░██║██║░░░░░░░░██║░░░\n" +
            "╚═════╝░╚═╝░░░░░░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░░░░░░░╚═╝░░░";
    
    private static String findBaseDir() {
        if (new File("Practica2" + File.separator + "mp3files").exists()) {
            return "Practica2" + File.separator; 
        } else if (new File("mp3files").exists()) {
            return ""; 
        } else if (new File(".." + File.separator + "mp3files").exists()) {
            return ".." + File.separator; 
        }
        return ""; 
    }
    
    public static final String BASE_DIR = findBaseDir();
    
    // Dinamic paths
    public static final String PATH_ORIGIN = BASE_DIR + "mp3files" + File.separator + "origin" + File.separator;
    public static final String PATH_DESTINATION = BASE_DIR + "mp3files" + File.separator + "destination" + File.separator;
    public static final String LOG_PATH = BASE_DIR + "logs" + File.separator;
    
    // Streaming config
    public static final String SERVER_HOST = "localhost";
    public static final int PLAYER_DELAY_MS = 1000;
    public static final String FILE_EXTENSION = ".mp3";
    public static final String PLAYER_COMMAND = "wmplayer"; 
    public static final String PLAYER_ABS_FILEPATH = PATH_DESTINATION;
    
    // Network config
    public static final int TX_PACKET_SIZE_BYTES = 32752;
    public static final int DELIVERY_DELAY_MS = 0; 
}