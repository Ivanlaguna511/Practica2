package spotify.rmi.common;

import spotify.media.Media;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpotifyServer extends Remote {
    public boolean setClientStreamReceptor(SpotifyClient cliente) throws RemoteException;
    public String randomPlay(); //Tiene que lanzar una excepción pero no se cúal
    public String startMedia(Media cancion) throws RemoteException; //Lanza otra excepción pero no se cual
}
