package spotify.rmi.common;

public class ServerMessages {
    public static final String WELCOME_MESSAGE = "Welcome to \n" +
            "░██████╗██████╗░░█████╗░████████╗██╗███████╗██╗░░░██╗\n" +
            "██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝╚██╗░██╔╝\n" +
            "╚█████╗░██████╔╝██║░░██║░░░██║░░░██║█████╗░░░╚████╔╝░\n" +
            "░╚═══██╗██╔═══╝░██║░░██║░░░██║░░░██║██╔══╝░░░░╚██╔╝░░\n" +
            "██████╔╝██║░░░░░╚█████╔╝░░░██║░░░██║██║░░░░░░░░██║░░░\n" +
            "╚═════╝░╚═╝░░░░░░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░░░░░░░╚═╝░░░";;
    public static final String AUTH_SUCCESS = "AUTH";
    public static final String AUTH_FAILED = "NOTAUTH";
    public static final String PLAYLIST_EMPTY = "EMPTY";
    public static final String PLAYLIST_DELETED = "DELETED";
    public static final String MEDIA_NOT_FOUND_ERROR = "Error: Media not found in directory.";
    public static final String LOGIN_REQUIRED = "User login is required";
    public static final String MAX_CONNECTIONS_REACHED_ERROR = "Err Max Number of connections reached.";
    public static final String MAX_LOGIN_ATTEMPTS_REACHED_ERROR = "Err Max Number of login attempts reached.";
}