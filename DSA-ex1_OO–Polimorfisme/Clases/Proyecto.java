package Clases;

import java.util.*;

public class Proyecto {
    private final String nombre;
    private final Usuario gestor;
    private final Set<Usuario> programadores = new HashSet<>();
    private final List<Tarea> tareas = new ArrayList<>();

    public Proyecto(String nombre, Usuario gestor) {
        this.nombre = nombre;
        this.gestor = gestor;
    }
    public String getNombre() { return nombre; }
    public Usuario getGestor() { return gestor; }
    public Set<Usuario> getProgramadores() { return programadores; }
    public List<Tarea> getTareas() { return tareas; }

    public void asignarProgramador(Usuario u) { programadores.add(u); }
    public void agregarTarea(Tarea t) { tareas.add(t); }

    @Override
    public String toString() {
        return "Proyecto: " + nombre + " (Gestor: " + gestor.getNombre() + ")";
    }
}
