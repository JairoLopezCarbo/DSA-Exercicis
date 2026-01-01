import java.util.*;
import Clases.*;

public class GestorSistema {
    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Proyecto> proyectos = new HashMap<>();

    // Usuarios
    public void crearUsuario(String nombre, Rol rol) {
        if (usuarios.containsKey(nombre)) {
            System.out.println("El usuario ya existe.");
            return;
        }
        usuarios.put(nombre, new Usuario(nombre, rol));
        System.out.println("Usuario creado.");
    }

    public void eliminarUsuario(String nombre) {
        if (!usuarios.containsKey(nombre)) {
            System.out.println("No existe.");
            return;
        }
        if (usuarios.get(nombre).getRol() == Rol.ADMIN) {
            System.out.println("No se puede eliminar el administrador.");
            return;
        }
        usuarios.remove(nombre);
        System.out.println("Usuario eliminado.");
    }

    public Usuario getUsuario(String nombre) { return usuarios.get(nombre); }

    public void listarUsuarios() {
        System.out.println("Usuarios:");
        usuarios.values().forEach(System.out::println);
    }

    // Proyectos
    public void crearProyecto(String nombre, Usuario gestor) {
        if (proyectos.containsKey(nombre)) {
            System.out.println("Proyecto ya existe.");
            return;
        }
        proyectos.put(nombre, new Proyecto(nombre, gestor));
        System.out.println("Proyecto creado.");
    }

    public void listarProyectosDeGestor(Usuario gestor) {
        proyectos.values().stream()
                .filter(p -> p.getGestor().equals(gestor))
                .forEach(System.out::println);
    }

    public void listarProgramadores() {
        usuarios.values().stream()
                .filter(u -> u.getRol() == Rol.PROGRAMADOR)
                .forEach(System.out::println);
    }

    public void asignarProgramadorAProyecto(String proyecto, String programador) {
        Proyecto p = proyectos.get(proyecto);
        Usuario u = usuarios.get(programador);
        if (p == null || u == null || u.getRol() != Rol.PROGRAMADOR) {
            System.out.println("Datos inválidos.");
            return;
        }
        p.asignarProgramador(u);
        System.out.println("Programador asignado.");
    }

    public void listarProgramadoresDeProyecto(String proyecto) {
        Proyecto p = proyectos.get(proyecto);
        if (p == null) { System.out.println("No existe el proyecto."); return; }
        p.getProgramadores().forEach(System.out::println);
    }

    public void crearTareaEnProyecto(String proyecto, String nombreTarea, String programador) {
        Proyecto p = proyectos.get(proyecto);
        if (p == null) { System.out.println("No existe proyecto."); return; }
        Usuario u = usuarios.get(programador);
        if (u == null || u.getRol() != Rol.PROGRAMADOR || !p.getProgramadores().contains(u)) {
            System.out.println("Programador inválido o no asignado.");
            return;
        }
        p.agregarTarea(new Tarea(nombreTarea, u));
        System.out.println("Tarea creada.");
    }

    // Programador
    public void listarProyectosDeProgramador(Usuario prog) {
        proyectos.values().stream()
                .filter(p -> p.getProgramadores().contains(prog))
                .forEach(System.out::println);
    }

    public void listarTareasDeProgramadorEnProyecto(Usuario prog, String proyecto) {
        Proyecto p = proyectos.get(proyecto);
        if (p == null) { System.out.println("Proyecto no existe."); return; }
        p.getTareas().stream()
                .filter(t -> t.getProgramador().equals(prog))
                .forEach(System.out::println);
    }

    public void marcarTareaFinalizada(Usuario prog, String proyecto, String tarea) {
        Proyecto p = proyectos.get(proyecto);
        if (p == null) { System.out.println("Proyecto no existe."); return; }
        for (Tarea t : p.getTareas()) {
            if (t.getNombre().equals(tarea) && t.getProgramador().equals(prog)) {
                t.marcarFinalizada();
                System.out.println("Tarea marcada como FINALIZADA.");
                return;
            }
        }
        System.out.println("Tarea no encontrada o no asignada.");
    }
}
