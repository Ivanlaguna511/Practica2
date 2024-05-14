package spotify.rmi.server;
import spotify.media.Globals;
import spotify.media.Media;
import spotify.rmi.common.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

import spotify.stream.ServerStream;
import spotify.utils.Directorio;

public class SpotifyServerImpl extends UnicastRemoteObject implements Spotify, SpotifyServer {
    private SpotifyClient cliente;
    private ConcurrentHashMap<String, String> registroUsuarios;
    private final MultiMap<String, Media> mapa;
    private final Directorio directorio;
    public SpotifyServerImpl() throws java.rmi.RemoteException {
        super();
        this.directorio = new Directorio();
        this.mapa = new MultiMap<>();
        this.registroUsuarios = new ConcurrentHashMap<>();
        registroUsuarios.put("hector", "1234");
        registroUsuarios.put("sdis", "asdf");
    }

    @Override
    public String hello() throws RemoteException {
        return Strings.WELCOME_MESSAGE;
    }

    @Override
    public String auth(String username, String password) throws RemoteException {
        if (registroUsuarios.containsKey(username) && registroUsuarios.get(username).equals(password)) {//Si el usuario y contraseña están en el multimap
            return "AUTH";
        }else {
            return "NOTAUTH";
        }
    }

    @Override
    public void add2L(Media cancion) throws RemoteException {
        if(!directorio.existeMedia(cancion)){
            directorio.anadirMedia("directorio.size()+1", cancion);
        }
        mapa.push("DEFAULT", cancion);
    }

    @Override
    public void add2L(String nombrePlaylist, Media cancion) throws RemoteException {
        if(!directorio.existeMedia(cancion)){
            directorio.anadirMedia("directorio.size()+1", cancion);
        }
        mapa.push(nombrePlaylist, cancion);
    }

    @Override
    public Media readL() throws RemoteException {
        return mapa.pop("DEFAULT");
    }

    @Override
    public Media readL(String nombrePlaylist) throws RemoteException {
        return mapa.pop(nombrePlaylist);
    }

    @Override
    public Media peekL() throws RemoteException {
        return mapa.get("DEFAULT");
    }

    @Override
    public Media peekL(String nombrePlaylist) throws RemoteException {
        return mapa.get(nombrePlaylist);
    }

    @Override
    public String deleteL(String nombrePlaylist) throws RemoteException {
        Media mensaje;
        if (null == (mensaje = mapa.pop(nombrePlaylist))) {
            return "EMPTY";
        } else {
            while(mensaje!= null){
                mensaje = mapa.pop(nombrePlaylist);
            }
            return "DELETED";
        }
    }

    @Override
    public String getDirectoryList() throws RemoteException {
        String result = "";
        for (String clave : directorio.keySet()) {
            result += clave + ", ";
        }
        // Eliminar la última coma y espacio
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    @Override
    public Media retrieveMedia(String nombreCancion) throws RemoteException {
        return directorio.obtenerMedia(nombreCancion);
    }


    //En el enunciado no pasa el string de nombre cancion
    @Override
    public String setCover(String nombreCancion, Media imagen) throws RemoteException {
        Media aux;
        if ((aux = directorio.obtenerMedia(nombreCancion)) == null){
            return "No se ha podido cambiar la carátula";
        }
        aux.setCover(imagen.getCover());
        directorio.anadirMedia(nombreCancion,aux);
        return "Se ha podido cambiar la carátula";
    }

    @Override
    public String addScore(String nombreCancion, double puntuacion) throws RemoteException {
        Media aux;
        if((aux = directorio.obtenerMedia(nombreCancion)) == null){
            return "No se ha podido añadir puntuación";
        }
        aux.addScore(puntuacion);
        directorio.anadirMedia(nombreCancion,aux);
        return "Se ha podido añadir puntuación";
    }

    @Override
    public String addComment(String nombreCancion, String comentario) throws RemoteException {
        Media aux;
        if((aux = directorio.obtenerMedia(nombreCancion)) == null){
            return "No se ha podido añadir comentario";
        }
        aux.addComment(comentario);
        directorio.anadirMedia(nombreCancion,aux);
        return "Se ha podido añadir comentario";
    }

    @Override
    public boolean setClientStreamReceptor(SpotifyClient cliente) throws RemoteException {
        try {
            this.cliente = cliente;
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public String randomPlay() {
        return null;  //No se que tiene que hacer este método
    }

    @Override
    public String startMedia(Media cancion) throws RemoteException {
            // 1. CHECKS
            if (cancion == null || !directorio.contieneClave(cancion.getName())){
                return "Media is null or does not exist in the directory";
            }

            // 2. PREPARE A SERVERSOCKET FOR THE STREAMING
            String pathFile = Globals.path_origin + cancion.getName() + Globals.file_extension;
            ServerStream ss = new ServerStream(cancion.toString(), this.cliente); // Completa los puntos suspensivos con el puerto deseado
            try {
                new Thread(ss, "streamserver").start(); // Ejecuta en un hilo aparte la preparación del ServerSocket
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "Error preparing server socket for streaming";
            }

            // 3. LAUNCH CLIENT MEDIAPLAYER
            System.out.println("- Checking MediaPlayer status...");
            try {
                if (!this.cliente.launchMediaPlayer(cancion)) { // Ejecuta el método launchMediaPlayer del cliente
                    return "Launcher cannot be triggered";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error launching Media Player at client";
            }

            // 4. READY FOR STREAMING, PLEASE CLIENT GO GO GO
            System.out.println("- Sending server streaming ready signal..." + Globals.server_host + ":" + ss.getServerSocketPort());
            try {
                this.cliente.startStream(cancion, Globals.server_host, ss.getServerSocketPort()); // Notifica al cliente que está listo para el streaming
            } catch (RemoteException e) {
                e.printStackTrace();
                return "Error during streaming at client";
            }
            return "MEDIA " + cancion.getName() + " started";
    }
}
