import java.util.Scanner;
import Clases.*;

public class Main {
    public static void main(String[] args) {
        GestorSistema sistema = new GestorSistema();
        // Creamos el Administrador por defecto
        sistema.crearUsuario("admin", Rol.ADMIN);

        Scanner sc = new Scanner(System.in);
        System.out.println("==== GESTIÓN DE PROYECTOS ====");
        while (true) {
            System.out.print("\nIntroduce nombre de usuario (o 'salir'): ");
            String nombre = sc.nextLine().trim();
            if (nombre.equalsIgnoreCase("salir")) break;

            Usuario usuario = sistema.getUsuario(nombre);
            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                continue;
            }

            switch (usuario.getRol()) {
                case ADMIN -> menuAdmin(sistema, sc);
                case GESTOR -> menuGestor(sistema, sc, usuario);
                case PROGRAMADOR -> menuProgramador(sistema, sc, usuario);
            }
        }
        sc.close();
        System.out.println("Aplicación finalizada.");
    }

    // ---- Menús ----
    private static void menuAdmin(GestorSistema sistema, Scanner sc) {
        while (true) {
            System.out.println("\n[ADMIN] Opciones:");
            System.out.println("1. Crear usuario");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Listar usuarios");
            System.out.println("0. Salir");
            System.out.print("> ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> {
                    System.out.print("Nombre: "); String n = sc.nextLine();
                    System.out.print("Rol (GESTOR/PROGRAMADOR): "); String r = sc.nextLine();
                    Rol rol = Rol.valueOf(r.toUpperCase());
                    sistema.crearUsuario(n, rol);
                }
                case "2" -> {
                    System.out.print("Nombre a eliminar: "); String n = sc.nextLine();
                    sistema.eliminarUsuario(n);
                }
                case "3" -> sistema.listarUsuarios();
                case "0" -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void menuGestor(GestorSistema sistema, Scanner sc, Usuario gestor) {
        while (true) {
            System.out.println("\n[GESTOR] Opciones:");
            System.out.println("1. Crear proyecto");
            System.out.println("2. Listar mis proyectos");
            System.out.println("3. Listar programadores");
            System.out.println("4. Asignar programador a proyecto");
            System.out.println("5. Listar programadores de un proyecto");
            System.out.println("6. Crear tarea en proyecto y asignar programador");
            System.out.println("0. Salir");
            System.out.print("> ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> {
                    System.out.print("Nombre del proyecto: "); String p = sc.nextLine();
                    sistema.crearProyecto(p, gestor);
                }
                case "2" -> sistema.listarProyectosDeGestor(gestor);
                case "3" -> sistema.listarProgramadores();
                case "4" -> {
                    System.out.print("Proyecto: "); String p = sc.nextLine();
                    System.out.print("Programador: "); String pr = sc.nextLine();
                    sistema.asignarProgramadorAProyecto(p, pr);
                }
                case "5" -> {
                    System.out.print("Proyecto: "); String p = sc.nextLine();
                    sistema.listarProgramadoresDeProyecto(p);
                }
                case "6" -> {
                    System.out.print("Proyecto: "); String p = sc.nextLine();
                    System.out.print("Nombre tarea: "); String t = sc.nextLine();
                    System.out.print("Programador asignado: "); String pr = sc.nextLine();
                    sistema.crearTareaEnProyecto(p, t, pr);
                }
                case "0" -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void menuProgramador(GestorSistema sistema, Scanner sc, Usuario prog) {
        while (true) {
            System.out.println("\n[PROGRAMADOR] Opciones:");
            System.out.println("1. Listar mis proyectos");
            System.out.println("2. Listar mis tareas en un proyecto");
            System.out.println("3. Marcar tarea como FINALIZADA");
            System.out.println("0. Salir");
            System.out.print("> ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> sistema.listarProyectosDeProgramador(prog);
                case "2" -> {
                    System.out.print("Proyecto: "); String p = sc.nextLine();
                    sistema.listarTareasDeProgramadorEnProyecto(prog, p);
                }
                case "3" -> {
                    System.out.print("Proyecto: "); String p = sc.nextLine();
                    System.out.print("Tarea: "); String t = sc.nextLine();
                    sistema.marcarTareaFinalizada(prog, p, t);
                }
                case "0" -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }
}
