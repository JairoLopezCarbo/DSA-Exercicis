import Classes.Usuario;
import Classes.Objeto;

import java.util.List;
import java.util.Scanner;

/**
 * Programa principal que interactúa con el usuario por consola
 * y delega toda la lógica en la clase Mundo.
 */
public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Mundo mundo = new Mundo();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== MENÚ =====");
            System.out.println("1. Crear usuario");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Consultar usuario");
            System.out.println("4. Añadir objeto a usuario");
            System.out.println("5. Ver inventario de usuario");
            System.out.println("6. Consultar objeto de usuario");
            System.out.println("7. Eliminar objeto de usuario");
            System.out.println("8. Transferir objeto entre usuarios");
            System.out.println("9. Listar usuarios");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> { // Crear usuario
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    System.out.print("Nivel inicial: ");
                    int nivel = Integer.parseInt(sc.nextLine());
                    System.out.print("Ataque: ");
                    int ataque = Integer.parseInt(sc.nextLine());
                    System.out.print("Defensa: ");
                    int defensa = Integer.parseInt(sc.nextLine());
                    System.out.print("Resistencia: ");
                    int resistencia = Integer.parseInt(sc.nextLine());

                    Usuario u = new Usuario(nombre, password, nivel, ataque, defensa, resistencia);
                    boolean creado = mundo.crearUsuario(u);
                    System.out.println(creado ? "Usuario creado." : "Ya existe un usuario con ese nombre.");
                }

                case 2 -> { // Eliminar usuario
                    System.out.print("Nombre de usuario a eliminar: ");
                    String nombre = sc.nextLine();
                    boolean ok = mundo.eliminarUsuario(nombre);
                    System.out.println(ok ? "Usuario eliminado." : "Usuario no encontrado.");
                }

                case 3 -> { // Consultar usuario
                    System.out.print("Nombre de usuario a consultar: ");
                    String nombre = sc.nextLine();
                    Usuario u = mundo.consultarUsuario(nombre);
                    System.out.println(u != null ? u : "Usuario no encontrado.");
                }

                case 4 -> { // Añadir objeto a usuario
                    System.out.print("Nombre del usuario: ");
                    String nombre = sc.nextLine();
                    Usuario u = mundo.consultarUsuario(nombre);
                    if (u == null) {
                        System.out.println("Usuario no encontrado.");
                        break;
                    }
                    System.out.print("Nombre del objeto: ");
                    String nombreObj = sc.nextLine();
                    System.out.print("Tipo: ");
                    String tipo = sc.nextLine();
                    System.out.print("Descripción: ");
                    String desc = sc.nextLine();
                    System.out.print("Valor: ");
                    int valor = Integer.parseInt(sc.nextLine());
                    System.out.print("Coste: ");
                    int coste = Integer.parseInt(sc.nextLine());

                    Objeto o = new Objeto(nombreObj, tipo, desc, valor, coste);
                    mundo.anadirObjetoAUsuario(u, o);
                    System.out.println("Objeto añadido al inventario de " + u.nombre);
                }

                case 5 -> { // Ver inventario
                    System.out.print("Nombre del usuario: ");
                    String nombre = sc.nextLine();
                    Usuario u = mundo.consultarUsuario(nombre);
                    if (u == null) {
                        System.out.println("Usuario no encontrado.");
                        break;
                    }
                    List<Objeto> inv = mundo.consultarObjetosDeUsuario(u);
                    if (inv.isEmpty()) {
                        System.out.println("Inventario vacío.");
                    } else {
                        for (Objeto obj: inv) {
                            System.out.println(" -"+obj.nombre);
                        }

                    }
                }

                case 6 -> { // Consultar objeto de usuario
                    System.out.print("Nombre del usuario: ");
                    String nombre = sc.nextLine();
                    Usuario u = mundo.consultarUsuario(nombre);
                    if (u == null) {
                        System.out.println("Usuario no encontrado.");
                        break;
                    }
                    System.out.print("Nombre del objeto: ");
                    String nombreObj = sc.nextLine();
                    Objeto obj = mundo.consultarObjetoDeUsuario(u, nombreObj);
                    System.out.println(obj != null ? obj : "Objeto no encontrado.");
                }

                case 7 -> { // Eliminar objeto
                    System.out.print("Nombre del usuario: ");
                    String nombre = sc.nextLine();
                    Usuario u = mundo.consultarUsuario(nombre);
                    if (u == null) {
                        System.out.println("Usuario no encontrado.");
                        break;
                    }
                    System.out.print("Nombre del objeto a eliminar: ");
                    String nombreObj = sc.nextLine();
                    boolean ok = mundo.eliminarObjetoDeUsuario(u, nombreObj);
                    System.out.println(ok ? "Objeto eliminado." : "Objeto no encontrado.");
                }

                case 8 -> { // Transferir objeto
                    System.out.print("Usuario origen: ");
                    String origenNombre = sc.nextLine();
                    Usuario origen = mundo.consultarUsuario(origenNombre);
                    System.out.print("Usuario destino: ");
                    String destinoNombre = sc.nextLine();
                    Usuario destino = mundo.consultarUsuario(destinoNombre);
                    if (origen == null || destino == null) {
                        System.out.println("Usuario origen o destino no existe.");
                        break;
                    }
                    System.out.print("Nombre del objeto a transferir: ");
                    String nombreObj = sc.nextLine();
                    Objeto obj = mundo.consultarObjetoDeUsuario(origen, nombreObj);
                    if (obj == null) {
                        System.out.println("El objeto no existe en el inventario del usuario origen.");
                    } else {
                        mundo.transferirObjetoEntreUsuarios(origen, destino, obj);
                        System.out.println("Objeto transferido.");
                    }
                }

                case 9 -> { // Listar usuarios
                    System.out.println("Usuarios en el sistema:");
                    List<Usuario> usuarios = mundo.listarUsuarios();
                    if  (usuarios.isEmpty()) {
                        System.out.println("Usuarios no encontrado.");
                    } else {
                        for (Usuario u: usuarios) {
                            System.out.println(" -"+u.nombre);
                        }
                    }
                }

                case 0 -> salir = true;

                default -> System.out.println("Opción no válida.");
            }
        }

        sc.close();
        System.out.println("Programa finalizado.");
    }
}