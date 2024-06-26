package sdis.spotify.server;
import sdis.spotify.common.Spotify;
import sdis.spotify.media.Media;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import sdis.spotify.common.Strings;
import sdis.utils.Directorio;
import sdis.utils.MultiMap;

public class SpotifyImpl extends UnicastRemoteObject implements Spotify {
    private ConcurrentHashMap<String, String> registroUsuarios;
    private final MultiMap<String, Media> mapa;
    private final Directorio directorio;
    public SpotifyImpl() throws java.rmi.RemoteException {
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
}
