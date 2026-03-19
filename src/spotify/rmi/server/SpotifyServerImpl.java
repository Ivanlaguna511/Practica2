package spotify.rmi.server;

import spotify.media.Globals;
import spotify.media.Media;
import spotify.rmi.common.*;
import spotify.stream.ServerStream;
import spotify.utils.ConcurrentMultiMap;
import spotify.utils.MediaDirectory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class SpotifyServerImpl extends UnicastRemoteObject implements Spotify, SpotifyServer {
    private SpotifyClient cliente;
    private final ConcurrentHashMap<String, String> registroUsuarios;
    private final ConcurrentMultiMap<String, Media> mapa;
    private final MediaDirectory directorio;

    public SpotifyServerImpl() throws RemoteException {
        super();
        this.directorio = new MediaDirectory();
        this.mapa = new ConcurrentMultiMap<>();
        this.registroUsuarios = new ConcurrentHashMap<>();
        registroUsuarios.put("hector", "1234");
        registroUsuarios.put("sdis", "asdf");
        
        // El escáner a prueba de balas
        preloadOriginFolder();
    }

    private void preloadOriginFolder() {
        System.out.println("SERVER: Scanning origin folder...");
        java.io.File folder = new java.io.File(Globals.PATH_ORIGIN);
        
        if (folder.exists() && folder.isDirectory()) {
            java.io.File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(Globals.FILE_EXTENSION));
            if (files != null && files.length > 0) {
                for (java.io.File file : files) {
                    String songName = file.getName().replace(Globals.FILE_EXTENSION, "");
                    Media newMedia = new Media(songName);
                    
                    String coverPath = Globals.BASE_DIR + "jpgfiles" + java.io.File.separator + songName + ".jpg";
                    if (new java.io.File(coverPath).exists()) {
                        newMedia.loadCover(coverPath);
                        System.out.println(" -> Added to directory: " + songName + " [With cover]");
                    } else {
                        System.out.println(" -> Added to directory: " + songName + " [Without cover]");
                    }
                    
                    directorio.addMedia(songName, newMedia);
                }
            } else {
                System.out.println("SERVER WARNING: The folder exists but there are no files with extension " + Globals.FILE_EXTENSION);
            }
        } else {
            System.err.println("SERVER ERROR: Windows cannot find the path: " + folder.getPath());
        }
    }

    @Override
    public String hello() throws RemoteException {
        return ServerMessages.WELCOME_MESSAGE;
    }

    @Override
    public String auth(String username, String password) throws RemoteException {
        return (registroUsuarios.containsKey(username) && registroUsuarios.get(username).equals(password)) 
                ? ServerMessages.AUTH_SUCCESS : ServerMessages.AUTH_FAILED;
    }

    @Override
    public void add2L(Media cancion) throws RemoteException {
        add2L("DEFAULT", cancion);
    }

    @Override
    public void add2L(String nombrePlaylist, Media cancion) throws RemoteException {
        if (!directorio.containsMediaKey(cancion.getName())) {
            directorio.addMedia(cancion.getName(), cancion);
        }
        mapa.push(nombrePlaylist, cancion);
    }

    @Override
    public Media readL() throws RemoteException { return mapa.pop("DEFAULT"); }

    @Override
    public Media readL(String nombrePlaylist) throws RemoteException { return mapa.pop(nombrePlaylist); }

    @Override
    public Media peekL() throws RemoteException { return mapa.get("DEFAULT"); }

    @Override
    public Media peekL(String nombrePlaylist) throws RemoteException { return mapa.get(nombrePlaylist); }

    @Override
    public String deleteL(String nombrePlaylist) throws RemoteException {
        if (mapa.pop(nombrePlaylist) == null) return ServerMessages.PLAYLIST_EMPTY;
        while (mapa.pop(nombrePlaylist) != null) { /* Vaciando la cola */ }
        return ServerMessages.PLAYLIST_DELETED;
    }

    @Override
    public String getDirectoryList() throws RemoteException {
        List<String> keys = new ArrayList<>(directorio.keySet());
        return keys.isEmpty() ? "" : String.join(", ", keys);
    }

    @Override
    public Media retrieveMedia(String nombreCancion) throws RemoteException {
        return directorio.getMedia(nombreCancion);
    }

    @Override
    public String setCover(Media imagen) throws RemoteException {
        Media aux = directorio.getMedia(imagen.getName()); // OJO: 'imagen' aquí solo trae el nombre, no la foto en sí.
        if (aux == null) return ServerMessages.MEDIA_NOT_FOUND_ERROR;
        
        String rutaImagen = Globals.BASE_DIR + "jpgfiles" + java.io.File.separator + imagen.getName() + ".jpg";
        java.io.File imgFile = new java.io.File(rutaImagen);
        
        if (imgFile.exists()) {
            aux.loadCover(imgFile.getAbsolutePath());
            return "Cover successfully updated.";
        } else {
            return "Error: Image file not found at " + imgFile.getAbsolutePath();
        }
    }

    @Override
    public String addScore(String nombreCancion, double puntuacion) throws RemoteException {
        Media aux = directorio.getMedia(nombreCancion);
        if (aux == null) return ServerMessages.MEDIA_NOT_FOUND_ERROR;
        aux.addScore(puntuacion);
        return "Score successfully registered.";
    }

    @Override
    public String addComment(String nombreCancion, String comentario) throws RemoteException {
        Media aux = directorio.getMedia(nombreCancion);
        if (aux == null) return ServerMessages.MEDIA_NOT_FOUND_ERROR;
        aux.addComment(comentario);
        return "Comment successfully added.";
    }

    @Override
    public void addLike(String nombreCancion) throws RemoteException {
        Media aux = directorio.getMedia(nombreCancion);
        if (aux != null) aux.addLike();
    }

    @Override
    public void tagAdultContent(String nombreCancion, boolean flag) throws RemoteException {
        Media aux = directorio.getMedia(nombreCancion);
        if (aux != null) aux.tagAdultContent(flag);
    }

    @Override
    public boolean setClientStreamReceptor(SpotifyClient cliente) throws RemoteException {
        this.cliente = cliente;
        return true;
    }

    @Override
    public String randomPlay() throws RemoteException {
        if (directorio.size() == 0) return "Directory is empty";
        Random random = new Random();
        List<String> keys = new ArrayList<>(directorio.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        return startMedia(directorio.getMedia(randomKey));
    }

    @Override
    public String startMedia(Media cancion) throws RemoteException {
        
        if (cancion == null || !directorio.containsMediaKey(cancion.getName())) {
            return "Media is null or does not exist in the directory";
        }
        
        // Vamos a destripar la ruta
        java.io.File archivoReal = new java.io.File(Globals.PATH_ORIGIN, cancion.getName() + Globals.FILE_EXTENSION);

        if (!archivoReal.exists() || archivoReal.length() == 0) {
            return "Error: The file fails in the server.";
        }

        String rutaAbsoluta = archivoReal.getAbsolutePath();
        ServerStream ss = new ServerStream(rutaAbsoluta, this.cliente); 

        try {
            new Thread(ss, "streamserver").start();
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            return "Error preparing server socket for streaming";
        }

        try {
            if (!this.cliente.launchMediaPlayer(cancion)) {
                return "Launcher cannot be triggered";
            }
        } catch (Exception e) {
            return "Error launching Media Player at client";
        }

        try {
            this.cliente.startStream(cancion, Globals.SERVER_HOST, ss.getServerSocketPort()); 
        } catch (RemoteException e) {
            return "Error during streaming at client";
        }
        return "MEDIA " + cancion.getName() + " started";
    }
}