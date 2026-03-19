package spotify.rmi.client;

import spotify.media.Globals;
import spotify.media.Media;
import spotify.media.MediaPlayer;
import spotify.rmi.common.SpotifyClient;
import spotify.stream.ClientStream;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SpotifyClientImpl extends UnicastRemoteObject implements SpotifyClient {
    private transient Thread playerThread;

    public SpotifyClientImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean launchMediaPlayer(Media m) throws RemoteException {
        try {
            String fullPath = Globals.PLAYER_ABS_FILEPATH + m.getName() + Globals.FILE_EXTENSION;
            MediaPlayer mediaPlayer = new MediaPlayer(Globals.PLAYER_COMMAND, fullPath, Globals.PLAYER_DELAY_MS);
            playerThread = new Thread(mediaPlayer);
            playerThread.start();
            return true;
        } catch (Exception e) {
            System.err.println("CLIENT: Failed to launch media player.");
            return false;
        }
    }

    @Override
    public boolean isMediaPlayerActive() throws RemoteException {
        return playerThread != null && playerThread.isAlive();
    }

    @Override
    public void startStream(Media song, String serverIp, int serverPort) throws RemoteException {
        ClientStream cs = new ClientStream(song, serverIp, serverPort, playerThread);
        new Thread(cs, "ClientStreamThread").start();
    }
}