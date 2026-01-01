package Classes;

public class Usuario{
    public String nombre;
    String password;
    int nivel;
    int ataque;
    int defensa;
    int resistencia;

    public Usuario(String nombre, String password, int nivel, int ataque, int defensa, int resistencia){
        this.nombre = nombre;
        this.password = password;
        this.nivel = nivel;
        this.ataque = ataque;
        this.defensa = defensa;
        this.resistencia = resistencia;
    }
}