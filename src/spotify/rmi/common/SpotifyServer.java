package spotify.rmi.common;

import spotify.media.Media;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyServer extends Remote {
    boolean setClientStreamReceptor(SpotifyClient cliente) throws RemoteException;
    String randomPlay() throws RemoteException; 
    String startMedia(Media cancion) throws RemoteException; 
}