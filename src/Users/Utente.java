package Users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class Utente {
    protected String nome;
    protected String cognome;
    protected int age;
    protected String email;
    protected int idUtente = 0;
    protected String password;
    protected Roles role;

    public  Utente(){}

    public Utente(String nome, String cognome, int age, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.age = age;
        this.email = email;
        this.idUtente++;
        this.password = password;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public String getPassword() {
        return password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean login(String email, String password) {
        return email.equals(getEmail()) && password.equals(getPassword());
    }

    public static List< Utente > leggiUtentiDaFile ( ) {
        try ( FileReader lettore = new FileReader("src/Users/Users.json") ) {
            Gson gson = new Gson();
            Type tipoListaUtenti = new TypeToken< List < Utente > >() {}.getType();
            return gson.fromJson(lettore, tipoListaUtenti);
        } catch ( IOException e ) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Roles getRole() {
        return role;
    }
}
