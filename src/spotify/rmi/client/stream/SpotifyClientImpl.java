package sdis.spotify.client.stream;

import sdis.spotify.common.SpotifyClient;
import sdis.spotify.media.Media;
import sdis.spotify.media.*;
import sdis.spotify.stream.ClientStream;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class SpotifyClientImpl extends UnicastRemoteObject implements SpotifyClient {
    private Thread playerThread;

    protected SpotifyClientImpl() throws RemoteException {
        super();
    }

    public boolean launchMediaPlayer(Media m) throws RemoteException {
        try {
        MediaPlayer mediaplayer = new MediaPlayer(Globals.player_command, Globals.player_abs_filepath+m.getName()+ Globals.file_extension, Globals.player_delay_ms);
            playerThread = new Thread(mediaplayer);
            playerThread.start();
        }catch (Exception e){ e.printStackTrace(); return false; }
        return true;
    }
    public boolean isMediaPlayerActive() throws RemoteException{
        return playerThread.isAlive();
    }
    public void startStream(Media cancion, String ipaddres, int puerto) throws RemoteException {
        ClientStream cs = new ClientStream(cancion, ipaddres,puerto, playerThread);
        new Thread(cs, "clientstream").start();
    }
}
