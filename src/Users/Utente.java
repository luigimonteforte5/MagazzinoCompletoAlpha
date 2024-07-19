package Users;

public abstract class Utente {
    protected String nome;
    protected String cognome;
    protected int age;
    protected String email;
    protected int idUtente = 0;
    protected String password;


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



}
