package spotify.media;
/**
 * Static class Globals
 * @author hector
 */
public class Globals {
    public static final String hello_banner ="Welcome to \n" +
            "░██████╗██████╗░░█████╗░████████╗██╗███████╗██╗░░░██╗\n" +
            "██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝╚██╗░██╔╝\n" +
            "╚█████╗░██████╔╝██║░░██║░░░██║░░░██║█████╗░░░╚████╔╝░\n" +
            "░╚═══██╗██╔═══╝░██║░░██║░░░██║░░░██║██╔══╝░░░░╚██╔╝░░\n" +
            "██████╔╝██║░░░░░╚█████╔╝░░░██║░░░██║██║░░░░░░░░██║░░░\n" +
            "╚═════╝░╚═╝░░░░░░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░░░░░░░╚═╝░░░";
    // Remote object setup
    public static String remote_name = "RemoteSpotify";
    // Log paths & msgs
    public static String log_path = "./logs/";
    public static String log_extension = ".txt";
    public static String log_listdr = " [LISTDR] List directory requested";
    public static String log_readpl = " [READPL] Pull from playlist Media: ";
    public static String log_peekpl = " [PEEKPL] Peek from playlist Media: ";
    public static String log_errorm = " [ERRORM] Error Media not found: ";
    public static String log_stream = " [STREAM] Streaming to: ";
    public static String log_stream_end = " [STREAM] Stream finished. Tx Bytes: ";
    // Streaming paths
    public static String server_host = "localhost";
    public static int server_port = 20099;
    public static String path_origin = "./mp3files/origin/";
    public static String path_destination = "./mp3files/destination/";
    // Streaming media player config
    public static int player_delay_ms = 3000;
    public static String file_extension = ".mp3";
    public static String player_command = "firefox";
    //public static String player_command = "vlc";
    public static String player_abs_filepath = "./mp3files/destination/";
    // WIN CONFIGS
//public static String player_command = "wmplayer";
//public static String player_abs_filepath = "E:\\spotifyL4\\mp3files\\destination\\";
// Streaming setup
    public static int tx_packet_size_bytes = 32752;
    public static int delivery_delay_ms = 50;
}
