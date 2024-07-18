package Users;
import Exceptions.CarrelloVuotoException;
import Exceptions.ProdottoNonTrovatoException;
import Management.Carrello;
import Products.ProdottoElettronicoUtente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Cliente extends Utente {

    private final Carrello carrelloCliente = new Carrello();

    public Cliente(String nome, String cognome, int age, String email, int idUtente, String password) {
        super(nome, cognome, age, email, idUtente, password);

    }

    public boolean login(String emailCliente, String passwordCliente) {

	    return emailCliente.equals(getEmail()) && passwordCliente.equals(getPassword());
    }

    public void aggiungiProdottoAlCarrello( ProdottoElettronicoUtente prodotto) throws ProdottoNonTrovatoException {
        carrelloCliente.aggiungiProdotto(prodotto);
    }

    public Set< ProdottoElettronicoUtente > ricercaProdottoPerMarca( String marca) throws ProdottoNonTrovatoException  {
        return carrelloCliente.ricercaPerMarca(marca);
    }

    public Set< ProdottoElettronicoUtente > ricercaProdottoPerModello( String modello) throws ProdottoNonTrovatoException  {
        return carrelloCliente.ricercaPerModello(modello);
    }

    public Set< ProdottoElettronicoUtente > ricercaProdottoPerPrezzoDiVendita( double prezzo){
        return carrelloCliente.ricercaPerPrezzoVendita(prezzo);
    }

    public Set< ProdottoElettronicoUtente > ricercaProdottoPerRange( double prezzoMin, double prezzoMax) throws ProdottoNonTrovatoException {
        return carrelloCliente.ricercaPerRange(prezzoMin, prezzoMax);
    }

    public Set< ProdottoElettronicoUtente > ricercaProdottoPerTIpo( String tipo){
        return carrelloCliente.ricercaPerTipo(tipo);
    }

    public Set< ProdottoElettronicoUtente > ricercaTramiteId( int id) throws ProdottoNonTrovatoException {
        return carrelloCliente.ricercaPerId(id);
    }

    public void stampaCarrelloProdotti(){
        carrelloCliente.stampaCarrello();
    }

    public void rimuoviProdottoTramiteId(int id) throws ProdottoNonTrovatoException {
        carrelloCliente.rimozioneTramiteId(id);
    }

    public double calcoloTotaleCarrello() throws CarrelloVuotoException {
        return carrelloCliente.calcoloTot();
    }

    public void svuotaCarrelloProdotti(){
        carrelloCliente.svuotaCarrello();
    }

    public void concludiAcquistoProdotti( Scanner sc ) throws CarrelloVuotoException {
        carrelloCliente.concludiAcquisto(sc);
    }

    public Set < ProdottoElettronicoUtente > getCarrello(){
        return carrelloCliente.getCarrello();
    }

        public static List < Cliente > leggiUtentiDaFile ( ) {
            try ( FileReader lettore = new FileReader("src/Users/Customers.json") ) {
                Gson gson = new Gson();
                Type tipoListaClienti = new TypeToken < List < Cliente > >() {}.getType();
                return gson.fromJson(lettore, tipoListaClienti);
            } catch ( IOException e ) {
                System.err.println(e.getMessage());
            }
            return null;
        }

    @Override
    public String toString() {
        return "Cliente{" +
                "carrelloCliente=" + carrelloCliente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", idUtente=" + idUtente +
                ", password='" + password + '\'' +
                '}';
    }
}


