package sdis.spotify.common;

import sdis.spotify.media.Media;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Spotify extends Remote {
    String hello() throws RemoteException;
    String auth(String username, String password) throws RemoteException;
    void add2L(Media cancion) throws RemoteException;
    void add2L(String nombrePlaylist, Media cancion) throws RemoteException;
    Media readL() throws RemoteException;
    Media readL(String nombrePlaylist) throws RemoteException;
    Media peekL() throws RemoteException;
    Media peekL(String nombrePlaylist) throws RemoteException;
    String deleteL(String nombrePlaylist) throws RemoteException;
    String getDirectoryList() throws RemoteException;
    Media retrieveMedia(String nombreCancion) throws RemoteException;

    //En el enunciado no pasa el String de nombreCancion
    String setCover(String nombreCancion, Media imagen) throws RemoteException;

    String addScore(String nombreCancion, double puntuacion) throws RemoteException;
    String addComment(String nombreCancion, String comentario) throws RemoteException;
}
