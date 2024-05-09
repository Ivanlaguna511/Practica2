package sdis.spotify.common;

import sdis.spotify.media.Media;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyServer extends Remote {
    boolean setClientStreamReceptor(SpotifyClient spotifyClient) throws RemoteException;
    String randomPlay() throws RemoteException;
    String startMedia(Media media) throws RemoteException, FileNotFoundException;
}
