package sdis.spotify.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Spotify extends Remote {
    String hello() throws RemoteException;
    String auth(String username, String password) throws RemoteException;
    public void add2L(Media cancion) throws RemoteException;
    public void add2L(String nombrePlaylist, Media cancion) throws RemoteException;
    public Media readL() throws RemoteException;
    public Media readL(String nombrePlaylist) throws RemoteException;
    public  Media peekL() throws RemoteException;
    public  Media peekL(String nombrePlaylist) throws RemoteException;
    public  String deleteL(String nombrePlaylist) throws RemoteException;
    public  String getDirectoryList() throws RemoteException;
    public  Media retrieveMedia(String nombreCancion) throws RemoteException;

    //En el enunciado no pasa el String de nombreCancion
    String setCover(String nombreCancion, Media imagen) throws RemoteException;

    public  String addScore(String nombreCancion, double puntuacion) throws RemoteException;
    public  String addComment(String nombreCancion, String comentario) throws RemoteException;
}
