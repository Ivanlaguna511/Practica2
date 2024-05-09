package sdis.spotify.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyClient extends Remote {
     boolean launchMediaPlayer(Media cancion);
     boolean isMediaPlayerActive() throws RemoteException; //Lanza excepción pero no se cual
     void startStream(Media cancion, String ipServerSocket, int puertoServer) throws RemoteException;
}
