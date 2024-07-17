package Users;

import Exceptions.CarrelloVuotoException;
import Exceptions.LoginFailedException;
import Exceptions.ProdottoNonTrovatoException;
import Management.Carrello;
import Products.ProdottoElettronicoDTO;

import java.util.Set;

public class Cliente extends Utente {

    private final Carrello carrelloCliente;

    public Cliente(String nome, String cognome, int age, String email, int idUtente, String password) {
        super(nome, cognome, age, email, idUtente, password);
        this.carrelloCliente = new Carrello();

    }

    public boolean login(String emailCliente, String passwordCliente) throws LoginFailedException {
        if(emailCliente.equals(getEmail()) && passwordCliente.equals(getPassword())) {
            return true;
        } else {
            throw new LoginFailedException("UserId o password errati");
        }
    }

    public void aggiungiProdottoAlCarrello( ProdottoElettronicoDTO prodotto) throws ProdottoNonTrovatoException {
        carrelloCliente.aggiungiProdotto(prodotto);
    }

    public Set< ProdottoElettronicoDTO > ricercaProdottoPerMarca( String marca) {
        return carrelloCliente.ricercaPerMarca(marca);
    }

    public Set< ProdottoElettronicoDTO > ricercaProdottoPerModello( String modello) {
        return carrelloCliente.ricercaPerModello(modello);
    }

    public Set< ProdottoElettronicoDTO > ricercaProdottoPerPrezzoDiVendita( double prezzo){
        return carrelloCliente.ricercaPerPrezzoVendita(prezzo);
    }

    public Set< ProdottoElettronicoDTO > ricercaProdottoPerRange( double prezzoMin, double prezzoMax){
        return carrelloCliente.ricercaPerRange(prezzoMin, prezzoMax);
    }

    public Set< ProdottoElettronicoDTO > ricercaProdottoPerTIpo( String tipo){
        return carrelloCliente.ricercaPerTipo(tipo);
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

    public void concludiAcquistoProdotti() throws CarrelloVuotoException {
        carrelloCliente.concludiAcquisto();
    }

    public Set < ProdottoElettronicoDTO > getCarrello(){
        return carrelloCliente.getCarrello();
    }

}

