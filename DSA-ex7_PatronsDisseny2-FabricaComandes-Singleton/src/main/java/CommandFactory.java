import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    // Renombrado para mayor claridad
    private static CommandFactory uniqueInstance;

    // Uso de la interfaz Map en lugar de la implementación específica en la declaración
    private final Map<String, Command> commandMap;

    // Constructor privado
    private CommandFactory() {
        this.commandMap = new HashMap<>();
    }

    // Singleton con Lazy Loading estándar
    public static synchronized CommandFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CommandFactory();
        }
        return uniqueInstance;
    }

    public Command getCommand(String className) {
        // Intento recuperar el comando de la caché primero
        Command cachedCommand = commandMap.get(className);

        // Si existe, lo devolvemos inmediatamente (ahorramos la búsqueda de containsKey)
        if (cachedCommand != null) {
            return cachedCommand;
        }

        // Si no existe, lo creamos
        try {
            Class<?> clazz = Class.forName(className);

            Command newCommand = (Command) clazz.getDeclaredConstructor().newInstance();

            // Guardamos en caché y retornamos
            commandMap.put(className, newCommand);
            return newCommand;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("La clase del comando no fue encontrada: " + className, e);
        } catch (ReflectiveOperationException e) {
            // Captura InstantiationException, IllegalAccessException, etc.
            throw new RuntimeException("Error al instanciar el comando: " + className, e);
        }
    }

    // Método simplificado
    public int numCommands() {
        return commandMap.size();
    }

    public void clear() {
        commandMap.clear();
    }
}