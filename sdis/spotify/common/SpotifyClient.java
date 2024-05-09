package sdis.spotify.common;

import sdis.spotify.media.Media;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyClient extends Remote {
    boolean launchMediaPlayer(Media cancion) throws RemoteException;
    boolean isMediaPlayerActive() throws RemoteException;
    void startStream(Media cancion, String ipaddres, int puerto) throws RemoteException;
}
