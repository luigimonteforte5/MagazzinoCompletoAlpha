package Users;
import Exceptions.ProdottoNonTrovatoException;
import Management.Magazzino;
import Products.ProdottoElettronico;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Magazziniere extends Utente {

    private final Magazzino magazzino = Magazzino.getInstance();
    private Roles role;

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public Magazziniere() {}

    public Magazziniere( String nome, String cognome, int age, String email, String password) {
        super(nome,cognome, age,email, password);
        role = Roles.MAGAZZINIERE;


    }
    public void addProductToMagazzino(ProdottoElettronico prodotto) {
        magazzino.addProductToMagazzino(prodotto);
    }

    public void removeProductFromMagazzino(int id) throws ProdottoNonTrovatoException {
        magazzino.removeProductFromMagazzino(id);
    }

    public Set<ProdottoElettronico> filtredByWhareHousePurchasePrice(double prezzo) {
        return magazzino.filtredByWhareHousePurchasePrice(prezzo);
    }

    public Set<ProdottoElettronico>filtredBytype(String tipo){
        return magazzino.filtredBytype(tipo);
    }

    public Set<ProdottoElettronico>filtredByModel(String modello){
        return magazzino.filtredByModel(modello);
    }
    public Set<ProdottoElettronico>filtredByProducer(String marca){
        return magazzino.filtredByProducer(marca);
    }

    public Set<ProdottoElettronico>filtredBySellPrice(double prezzo){
        return magazzino.filtredBySellPrice(prezzo);
    }

    public Set<ProdottoElettronico>filtredByRangePrice(double prezzo1, double prezzo2){
        return magazzino.filtredByRangePrice(prezzo1,prezzo2);
    }

    public ProdottoElettronico filteredById(int iD) throws ProdottoNonTrovatoException{
        return magazzino.filteredById(iD);
    }

    public static void aggiungiMagazziniereAlFile(Magazziniere magazziniere) throws IOException {
        List < Utente> utenti = leggiUtentiDaFile();

        if (utenti == null) {
            utenti = new ArrayList<>();
        }

        utenti.add(magazziniere);

        // Scrivi la lista aggiornata nel file
         FileWriter writer = new FileWriter("src/Users/Users.json"); {
            Gson gson = new Gson();
            gson.toJson(utenti, writer);
            System.out.println("Nuovo magazziniere aggiunto con successo!");

        }
    }

}









