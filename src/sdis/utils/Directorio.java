package sdis.utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sdis.spotify.rmi.common.Media;

public class Directorio {
    // HashMap que almacena los objetos Media
    private HashMap<String, Media> directorio;

    public Directorio() {
        this.directorio = new HashMap<>();
    }

    // Método para añadir un objeto Media al directorio
    public void anadirMedia(String clave, Media media) {
        this.directorio.put(clave, media);
    }

    // Método para obtener un objeto Media del directorio
    public Media obtenerMedia(String clave) {
        return this.directorio.get(clave);
    }

    // Método para verificar si un objeto Media existe en el directorio
    public boolean existeMedia(String clave) {
        return this.directorio.containsKey(clave);
    }
    public int size(){return this.directorio.size();}
    public boolean existeMedia(Media media){return directorio.containsValue(media);}
    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, Media> entry : this.directorio.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }
}

