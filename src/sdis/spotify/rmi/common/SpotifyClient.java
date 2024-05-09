package sdis.spotify.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyClient extends Remote {
    public boolean launchMediaPlayer(Media cancion);
    public boolean isMediaPlayerActive() throws RemoteException; //Lanza excepción pero no se cual
    public void startStream(Media cancion, String ipServerSocket, int puertoServer) throws RemoteException;
}
