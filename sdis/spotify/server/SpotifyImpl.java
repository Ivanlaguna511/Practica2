package sdis.spotify.server;

import sdis.spotify.common.Spotify;
import sdis.spotify.media.Media;
import sdis.spotify.utils.MediaDirectory;
import sdis.spotify.utils.ConcurrentMultiMap;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server-side implementation of the Spotify Remote interface.
 */
public class SpotifyImpl extends UnicastRemoteObject implements Spotify {
    private static final long serialVersionUID = 1L;

    private final ConcurrentHashMap<String, String> userRegistry;
    private final ConcurrentMultiMap<String, Media> playlists;
    private final MediaDirectory directory;

    public SpotifyImpl() throws RemoteException {
        super();
        this.directory = new MediaDirectory();
        this.playlists = new ConcurrentMultiMap<>();
        this.userRegistry = new ConcurrentHashMap<>();
        
        // Default mock users
        userRegistry.put("hector", "1234");
        userRegistry.put("sdis", "asdf");

        // Preload some default media to satisfy the "extra credit" task
        preloadDefaultContent();
    }

    private void preloadDefaultContent() {
        System.out.println("SERVER: Preloading default content...");
        Media m1 = new Media("Bohemian Rhapsody");
        Media m2 = new Media("Hotel California");
        directory.addMedia(m1.getInternalName(), m1);
        directory.addMedia(m2.getInternalName(), m2);
        playlists.push("DEFAULT", m1);
        playlists.push("DEFAULT", m2);
    }

    @Override
    public String hello() throws RemoteException {
        return "Welcome to \n" +
            "░██████╗██████╗░░█████╗░████████╗██╗███████╗██╗░░░██╗\n" +
            "██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝╚██╗░██╔╝\n" +
            "╚█████╗░██████╔╝██║░░██║░░░██║░░░██║█████╗░░░╚████╔╝░\n" +
            "░╚═══██╗██╔═══╝░██║░░██║░░░██║░░░██║██╔══╝░░░░╚██╔╝░░\n" +
            "██████╔╝██║░░░░░╚█████╔╝░░░██║░░░██║██║░░░░░░░░██║░░░\n" +
            "╚═════╝░╚═╝░░░░░░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░░░░░░░╚═╝░░░";
    }

    @Override
    public String auth(String username, String password) throws RemoteException {
        return (userRegistry.containsKey(username) && userRegistry.get(username).equals(password)) 
               ? "AUTH" : "NOTAUTH";
    }

    @Override
    public void add2L(Media song) throws RemoteException {
        add2L("DEFAULT", song);
    }

    @Override
    public void add2L(String playlistName, Media song) throws RemoteException {
        if (!directory.containsMedia(song)) {
            directory.addMedia(song.getInternalName(), song);
        }
        playlists.push(playlistName, song);
    }

    @Override
    public Media readL() throws RemoteException { return readL("DEFAULT"); }

    @Override
    public Media readL(String playlistName) throws RemoteException {
        return playlists.pop(playlistName);
    }

    @Override
    public Media peekL() throws RemoteException { return peekL("DEFAULT"); }

    @Override
    public Media peekL(String playlistName) throws RemoteException {
        return playlists.get(playlistName);
    }

    @Override
    public String deleteL(String playlistName) throws RemoteException {
        if (playlists.pop(playlistName) == null) return "EMPTY";
        while (playlists.pop(playlistName) != null) { /* Drain the queue */ }
        return "DELETED";
    }

    @Override
    public String getDirectoryList() throws RemoteException {
        return String.join(", ", directory.getKeys());
    }

    @Override
    public Media retrieveMedia(String songName) throws RemoteException {
        return directory.getMedia(songName);
    }

    @Override
    public String setCover(String songName, Media imageWrapper) throws RemoteException {
        Media target = directory.getMedia(songName);
        if (target == null) return "Error: Media not found in directory.";
        target.setCover(imageWrapper.getCover());
        return "Cover successfully updated.";
    }

    @Override
    public String addScore(String songName, double score) throws RemoteException {
        Media target = directory.getMedia(songName);
        if (target == null) return "Error: Media not found in directory.";
        target.addScore(score);
        return "Score successfully registered.";
    }

    @Override
    public String addComment(String songName, String comment) throws RemoteException {
        Media target = directory.getMedia(songName);
        if (target == null) return "Error: Media not found in directory.";
        target.addComment(comment);
        return "Comment successfully added.";
    }
}