package Management;
import Exceptions.ProdottoNonTrovatoException;
import Products.ProdottoElettronico;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
public class Magazzino {

    private static Magazzino instance;
    private Set<ProdottoElettronico> magazzino = new HashSet<>();

    public static synchronized Magazzino getInstance(){
        if(instance == null) instance = new Magazzino();
        return instance;
    }

    private Magazzino() {}


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
    public Set<ProdottoElettronico> filtredBySellPrice(double price){
        return magazzino.stream()
                .filter(d->d.getPrezzoVendita() == price)
                .collect(Collectors.toSet());
    }
    // Filtrato per prezzo magazzino
    public Set<ProdottoElettronico> filtredByWhareHousePurchasePrice(double price){
        return magazzino.stream()
                .filter(d-> d.getPrezzoAcquisto() == price)
                .collect(Collectors.toSet());
    }
    public Set< ProdottoElettronico > filtredByRangePrice( double price, double secondPrice){
        return magazzino.stream()
                .filter(d->d.getPrezzoVendita() >= price && d.getPrezzoVendita() <= secondPrice)
                .collect(Collectors.toSet());
    }
    public void addProductToMagazzino(ProdottoElettronico dispositivo){
        boolean found = magazzino.stream().anyMatch(d->d.getId() == dispositivo.getId());
        if(found){
            dispositivo.setQuantitaMagazzino(dispositivo.getQuantitaMagazzino() + 1);
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

    public ProdottoElettronico filteredById(int id) throws ProdottoNonTrovatoException {
         return magazzino.stream()
                 .filter(d-> d.getId() == id)
                 .findFirst()
                 .orElseThrow(() -> new ProdottoNonTrovatoException("Nessuna corrispondenza nel magazzino"));
    }

    public Set<ProdottoElettronico> getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Set<ProdottoElettronico> magazzino) {
        this.magazzino = magazzino;
    }

    public void decrementaQuantita(int id, int amount) throws ProdottoNonTrovatoException {

        ProdottoElettronico prodotto = filteredById(id);

        int nuovaQuantita = prodotto.getQuantitaMagazzino() - amount;
        if (nuovaQuantita < 0) {
            throw new IllegalArgumentException("Quantità non può essere negativa");
        }

        prodotto.setQuantitaMagazzino(nuovaQuantita);
    }

    public void incrementaQuantita(int id, int amount) throws ProdottoNonTrovatoException {

        ProdottoElettronico prodotto = filteredById(id);

        int nuovaQuantita = prodotto.getQuantitaMagazzino() + amount;
        if (nuovaQuantita < 0) {
            throw new IllegalArgumentException("Quantità non può essere negativa");
        }

        prodotto.setQuantitaMagazzino(nuovaQuantita);
    }

    @Override
    public String toString() {
        return "Magazzino{" +
                "magazzino=" + magazzino +
                '}';
    }
}
