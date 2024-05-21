package spotify.utils;
import java.util.*;

import spotify.media.Media;

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
    public Media getValue(int indice) {
        List<Map.Entry<String, Media>> entryList = new ArrayList<>(directorio.entrySet());

        // Acceder a un valor mediante índice
        int index = 1; // Índice que deseas acceder (por ejemplo, 1)
        if (index >= 0 && index < entryList.size()) {
            Map.Entry<String, Media> entry = entryList.get(index);
            return entry.getValue();

        }
        return null;
    }
    public boolean contieneClave(String clave) {
        return this.directorio.containsKey(clave);
    }
}

