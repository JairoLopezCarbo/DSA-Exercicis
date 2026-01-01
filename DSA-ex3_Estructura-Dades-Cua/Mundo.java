import Classes.Usuario;
import Classes.Objeto;

import java.util.*;

/**
 * Clase que gestiona usuarios y sus inventarios.
 * - Usuarios en una LISTA.
 * - Inventarios en un HASHMAP con key = nombre del usuario.
 */
public class Mundo {

    // Lista de usuarios
    private List<Usuario> usuarios = new ArrayList<>();

    // Mapa de inventarios: nombreUsuario -> lista de objetos
    private Map<String, List<Objeto>> inventarios = new HashMap<>();

    /**
     * Crear un nuevo usuario en el sistema.
     * Crea también su inventario vacío en el HashMap.
     * @return true si se crea, false si ya existe ese nombre
     */
    public boolean crearUsuario(Usuario u) {
        // Verificar que no exista un usuario con el mismo nombre
        for (Usuario usr : usuarios) {
            if (usr.nombre.equalsIgnoreCase(u.nombre)) {
                return false;
            }
        }
        usuarios.add(u);
        inventarios.put(u.nombre, new ArrayList<>()); // inventario vacío
        return true;
    }

    /**
     * Eliminar un usuario por nombre y su inventario.
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarUsuario(String nombre) {
        boolean removed = usuarios.removeIf(u -> u.nombre.equalsIgnoreCase(nombre));
        if (removed) {
            inventarios.remove(nombre);
        }
        return removed;
    }

    /**
     * Consultar un usuario por nombre.
     */
    public Usuario consultarUsuario(String nombre) {
        for (Usuario u : usuarios) {
            if (u.nombre.equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Añadir un objeto al inventario de un usuario.
     */
    public void anadirObjetoAUsuario(Usuario u, Objeto o) {
        List<Objeto> inv = inventarios.get(u.nombre);
        if (inv != null) {
            inv.add(o);
        }
    }

    /**
     * Consultar los objetos de un usuario.
     */
    public List<Objeto> consultarObjetosDeUsuario(Usuario u) {
        return inventarios.getOrDefault(u.nombre, Collections.emptyList());
    }

    /**
     * Obtener un objeto por nombre del inventario de un usuario.
     */
    public Objeto consultarObjetoDeUsuario(Usuario u, String nombreObjeto) {
        List<Objeto> inv = inventarios.get(u.nombre);
        if (inv != null) {
            for (Objeto obj : inv) {
                if (obj.nombre.equalsIgnoreCase(nombreObjeto)) {
                    return obj;
                }
            }
        }
        return null;
    }

    /**
     * Eliminar un objeto por nombre del inventario de un usuario.
     */
    public boolean eliminarObjetoDeUsuario(Usuario u, String nombreObjeto) {
        List<Objeto> inv = inventarios.get(u.nombre);
        if (inv != null) {
            return inv.removeIf(o -> o.nombre.equalsIgnoreCase(nombreObjeto));
        }
        return false;
    }

    /**
     * Transferir un objeto de un usuario a otro.
     */
    public void transferirObjetoEntreUsuarios(Usuario origen, Usuario destino, Objeto o) {
        List<Objeto> invOrigen = inventarios.get(origen.nombre);
        List<Objeto> invDestino = inventarios.get(destino.nombre);
        if (invOrigen != null && invDestino != null) {
            if (invOrigen.remove(o)) {
                invDestino.add(o);
            }
        }
    }

    /**
     * Lista todos los usuarios actuales.
     */
    public List<Usuario> listarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }
}
