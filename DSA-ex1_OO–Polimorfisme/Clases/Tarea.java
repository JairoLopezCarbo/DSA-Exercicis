package Clases;

public class Tarea {
    private final String nombre;
    private final Usuario programador;
    private boolean finalizada;

    public Tarea(String nombre, Usuario programador) {
        this.nombre = nombre;
        this.programador = programador;
        this.finalizada = false;
    }
    public String getNombre() { return nombre; }
    public Usuario getProgramador() { return programador; }
    public boolean isFinalizada() { return finalizada; }

    public void marcarFinalizada() { this.finalizada = true; }

    @Override
    public String toString() {
        return nombre + " - Asignada a: " + programador.getNombre() +
                " - Estado: " + (finalizada ? "FINALIZADA" : "PENDIENTE");
    }
}
