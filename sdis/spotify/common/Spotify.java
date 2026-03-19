package sdis.spotify.common;

import sdis.spotify.media.Media;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the Spotify Service.
 * Defines all methods that clients can invoke over the RMI network.
 */
public interface Spotify extends Remote {
    String hello() throws RemoteException;
    String auth(String username, String password) throws RemoteException;
    
    void add2L(Media song) throws RemoteException;
    void add2L(String playlistName, Media song) throws RemoteException;
    
    Media readL() throws RemoteException;
    Media readL(String playlistName) throws RemoteException;
    
    Media peekL() throws RemoteException;
    Media peekL(String playlistName) throws RemoteException;
    
    String deleteL(String playlistName) throws RemoteException;
    String getDirectoryList() throws RemoteException;
    Media retrieveMedia(String songName) throws RemoteException;

    String setCover(String songName, Media imageWrapper) throws RemoteException;
    String addScore(String songName, double score) throws RemoteException;
    String addComment(String songName, String comment) throws RemoteException;
}