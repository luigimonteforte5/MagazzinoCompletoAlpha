import Users.Cliente;
import Users.Utente;

public class Mapper {
    public static Cliente toUtente(Utente utente){
        Cliente cliente = new Cliente();
        cliente.setIdUtente(utente.getIdUtente());
        cliente.setAge(utente.getAge());
        cliente.setNome(utente.getNome());
        cliente.setCognome(utente.getCognome());
        cliente.setEmail(utente.getEmail());
        cliente.setPassword(utente.getPassword());

        return cliente;
    }
}
