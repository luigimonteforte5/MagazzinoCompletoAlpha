package Management;

import Exceptions.ProdottoNonTrovatoException;
import Products.ProdottoElettronico;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Magazzino {

    //Accetta tipi Products.ProdottoElettronico
    private Set<ProdottoElettronico> magazzino;

    public Magazzino() {
        magazzino = new HashSet<>();
    }
    //Ritorna il totale degli articoli presenti in magazzino.
    public int totaleProdotti(){
        return magazzino.size();
    }


    //Lista filtrata per tipo
    public Set<ProdottoElettronico> filtredBytype(String type){
       return magazzino.stream()
               .filter(d->d.getTipoElettronico().toString().equalsIgnoreCase(type))
               .collect(Collectors.toSet());
    }
    public Set<ProdottoElettronico> filtredByModel(String type){
        return magazzino.stream()
                .filter(d->d.getModello().equalsIgnoreCase(type))
                .collect(Collectors.toSet());
    }
    public Set<ProdottoElettronico> filtredByProducer(String type){
       return magazzino.stream()
               .filter(d->d.getMarca().equalsIgnoreCase(type))
               .collect(Collectors.toSet());
    }
    public Set<ProdottoElettronico> filtredBySellPrice(float price){
        return magazzino.stream()
                .filter(d->d.getPrezzoVendita() == price)
                .collect(Collectors.toSet());
    }
    // Filtrato per prezzo magazzino
    public Set<ProdottoElettronico> filtredByWhareHousePurchasePrice(float price){
        return magazzino.stream()
                .filter(d-> d.getPrezzoAcquisto() == price)
                .collect(Collectors.toSet());
    }
    public Set< ProdottoElettronico > filtredByRangePrice( float price, float secondPrice){
        return magazzino.stream()
                .filter(d->d.getPrezzoVendita() > price && d.getPrezzoVendita() < secondPrice)
                .collect(Collectors.toSet());
    }
    public void addProductToMagazzino(ProdottoElettronico dispositivo){
        boolean found = magazzino.stream().anyMatch(d->d.getId() == dispositivo.getId());
        if(found){
            dispositivo.setQuantita(dispositivo.getQuantita() + 1);
        } else{
            magazzino.add(dispositivo);
        }
    }

    public void removeProductFromMagazzino(int id) throws ProdottoNonTrovatoException {
       boolean isPresent = magazzino.removeIf(d->d.getId() == id);
       if (!isPresent){
           throw new ProdottoNonTrovatoException("Impossibile procedere: prodotto non trovato");
       }
    }

    public Set<ProdottoElettronico> filteredById(int id) {
         return magazzino.stream().filter(d-> d.getId() == id).collect(Collectors.toSet());
    }

    public Set<ProdottoElettronico> getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Set<ProdottoElettronico> magazzino) {
        this.magazzino = magazzino;
    }

    public void decrementaQuantita(int id, int amount) throws ProdottoNonTrovatoException {

        ProdottoElettronico prodotto = filteredById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProdottoNonTrovatoException("Products.Prodotto non presente nel magazzino"));

        int nuovaQuantita = prodotto.getQuantita() - amount;
        if (nuovaQuantita < 0) {
            throw new IllegalArgumentException("Quantità non può essere negativa");
        }

        prodotto.setQuantita(nuovaQuantita);
    }

    public void incrementaQuantita(int id, int amount) throws ProdottoNonTrovatoException {

        ProdottoElettronico prodotto = filteredById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProdottoNonTrovatoException("Products.Prodotto non presente nel magazzino"));

        int nuovaQuantita = prodotto.getQuantita() + amount;
        if (nuovaQuantita < 0) {
            throw new IllegalArgumentException("Quantità non può essere negativa");
        }

        prodotto.setQuantita(nuovaQuantita);
    }

}
