package spotify.rmi.client.stream;

import spotify.media.Globals;
import spotify.media.Media;
import spotify.media.MediaPlayer;
import spotify.rmi.common.SpotifyClient;
import spotify.stream.ClientStream;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SpotifyClientImpl extends UnicastRemoteObject implements SpotifyClient {
    private Thread playerThread;
    public boolean launchMediaPlayer(Media cancion) throws RemoteException {
        try {
            MediaPlayer mediaplayer = new MediaPlayer(
                    Globals.player_command,
                    Globals.player_abs_filepath+cancion.getName()+
                            Globals.file_extension,
                    Globals.player_delay_ms
            );
            playerThread = new Thread(mediaplayer); // 1. Se pasa la instancia de MediaPlayer al constructor de Thread
            playerThread.start(); // 3. Se inicia el hilo playerThread
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isMediaPlayerActive() throws RemoteException {
        return playerThread.isAlive(); // 2. Se usa el método isAlive() de la clase Thread para verificar si el hilo está vivo
    }

    public void startStream(Media cancion, String ipServerSocket, int puertoServer) throws RemoteException {
        ClientStream cs = new ClientStream(cancion,ipServerSocket,puertoServer,playerThread); // 1. Se crean los objetos necesarios según el constructor de ClientStream
        new Thread(cs, "clientstream").start(); // 2. Se inicia un nuevo hilo para ejecutar ClientStream
    }
}