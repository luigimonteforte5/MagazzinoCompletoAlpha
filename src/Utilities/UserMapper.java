package Utilities;

import Users.Cliente;
import Users.Magazziniere;
import Users.Utente;

public class UserMapper {
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

    public static Magazziniere toMagazziniere(Utente utente){
        Magazziniere magazziniere = new Magazziniere();
        magazziniere.setIdUtente(utente.getIdUtente());
        magazziniere.setAge(utente.getAge());
        magazziniere.setNome(utente.getNome());
        magazziniere.setCognome(utente.getCognome());
        magazziniere.setEmail(utente.getEmail());
        magazziniere.setPassword(utente.getPassword());

        return magazziniere;
    }

}
