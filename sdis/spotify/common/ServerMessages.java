package sdis.spotify.common;

/**
 * Centralized string constants for the Distributed Spotify RMI Server.
 * Ensures consistency between server responses and client expectations.
 */
public class ServerMessages {
    
    // Welcome
    public static final String WELCOME_MESSAGE = "Welcome to \r\n" + //
                "░██████╗██████╗░░█████╗░████████╗██╗███████╗██╗░░░██╗\r\n" + //
                "██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝╚██╗░██╔╝\r\n" + //
                "╚█████╗░██████╔╝██║░░██║░░░██║░░░██║█████╗░░░╚████╔╝░\r\n" + //
                "░╚═══██╗██╔═══╝░██║░░██║░░░██║░░░██║██╔══╝░░░░╚██╔╝░░\r\n" + //
                "██████╔╝██║░░░░░╚█████╔╝░░░██║░░░██║██║░░░░░░░░██║░░░\r\n" + //
                "╚═════╝░╚═╝░░░░░░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░░░░░░░╚═╝░░░";
    
    // Authentication Status
    public static final String AUTH_SUCCESS = "AUTH";
    public static final String AUTH_FAILED = "NOTAUTH";
    
    // Playlist Operations
    public static final String PLAYLIST_EMPTY = "EMPTY";
    public static final String PLAYLIST_DELETED = "DELETED";
    
    // Metadata Operations (Success)
    public static final String COVER_UPDATED = "Cover successfully updated.";
    public static final String SCORE_ADDED = "Score successfully registered.";
    public static final String COMMENT_ADDED = "Comment successfully added.";
    
    // Metadata Operations (Errors)
    public static final String MEDIA_NOT_FOUND_ERROR = "Error: Media not found in directory.";
}