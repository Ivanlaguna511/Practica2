package spotify.rmi.common;

import spotify.media.Media;

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
    public String setCover(Media imagen) throws RemoteException;
    public  String addScore(String nombreCancion, double puntuacion) throws RemoteException;
    public  String addComment(String nombreCancion, String comentario) throws RemoteException;
    public  void addLike(String nombreCancion) throws RemoteException;
    public  void tagAdultContent(String nombreCancion, boolean flag) throws RemoteException;
}
