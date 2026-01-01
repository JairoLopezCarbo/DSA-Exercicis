import java.util.*;

public class I18NManager {
    private static I18NManager instance;
    private HashMap<String, ResourceBundle> cache;

    private I18NManager() {
        cache = new HashMap<>();
    }

    // Singleton I18NManager
    public static I18NManager getInstance(){
        if (instance==null) instance=new I18NManager();
        return instance;
    }
    public String getText(String language, String key) {
        ResourceBundle resourceBundle = cache.get(language);
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle(language);
            //loads resources as a dictionary, and stores it in cache
            cache.put(language, resourceBundle);
            System.out.println("Cargado desde archivo: " + language);
        } else {
            System.out.println("Cargado desde caché: " + language);
        }
        //get the value associated with the key
        return resourceBundle.getString(key);
    }

}
