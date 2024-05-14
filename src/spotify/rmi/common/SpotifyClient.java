package spotify.rmi.common;

import spotify.media.Media;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyClient extends Remote {
    public boolean launchMediaPlayer(Media cancion) throws RemoteException;
    public boolean isMediaPlayerActive() throws RemoteException;
    public void startStream(Media cancion, String ipServerSocket, int puertoServer) throws RemoteException;
}
