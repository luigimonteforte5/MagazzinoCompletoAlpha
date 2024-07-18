package Management;

import Exceptions.CarrelloVuotoException;
import Exceptions.ProdottoNonTrovatoException;
import Products.ProdottoElettronicoUtente;

import java.util.*;
import java.util.stream.Collectors;

public class Carrello {

	HashSet < ProdottoElettronicoUtente > carrello;

	public Carrello(){
		carrello = new HashSet <>();
	}

	public void aggiungiProdotto ( ProdottoElettronicoUtente prodotto, int quantita) throws ProdottoNonTrovatoException {

		Optional < ProdottoElettronicoUtente > toAdd = carrello.stream()
				.filter(p -> p.getId() == prodotto.getId())
				.findFirst();

		if (toAdd.isPresent()) incrementaQuantita(prodotto.getId(), quantita);
		else carrello.add(prodotto);
	}

	public Set < ProdottoElettronicoUtente > ricercaPerId ( int id) throws ProdottoNonTrovatoException{
		Set< ProdottoElettronicoUtente > tmp = carrello.stream()
				.filter(p -> p.getId() == id)
				.collect(Collectors.toSet());
		if(tmp.isEmpty()){
			throw new ProdottoNonTrovatoException("Nessuna corrispondenza per ID");
		}
		return tmp;
	}

	public Set < ProdottoElettronicoUtente > ricercaPerMarca ( String marca) throws ProdottoNonTrovatoException{
		Set< ProdottoElettronicoUtente > tmp = carrello.stream()
				.filter(p -> p.getMarca().equalsIgnoreCase( marca))
				.collect(Collectors.toSet());
		if(tmp.isEmpty()){
			throw new ProdottoNonTrovatoException("Nessuna corrispondenza per Marca");
		}
		return tmp;
	}

	public Set< ProdottoElettronicoUtente > ricercaPerModello ( String modello) throws ProdottoNonTrovatoException{
		 Set< ProdottoElettronicoUtente > tmp = carrello.stream()
				.filter(p -> p.getModello().equalsIgnoreCase(modello)).collect(Collectors.toSet());
		 if(tmp.isEmpty()){
			 throw new ProdottoNonTrovatoException("Nessuna corrispondenza per Modello");
		 }
		 return tmp;
	}

	public Set< ProdottoElettronicoUtente > ricercaPerPrezzoVendita ( double prezzo){
		return carrello.stream()
				.filter(p -> p.getPrezzoVendita() == prezzo)
				.collect(Collectors.toSet());
	}


	public Set< ProdottoElettronicoUtente > ricercaPerRange ( double prezzoMin, double prezzoMax) throws ProdottoNonTrovatoException{
		Set< ProdottoElettronicoUtente > tmp = carrello.stream()
				.filter(p -> p.getPrezzoVendita() > prezzoMin && p.getPrezzoVendita() < prezzoMax )
				.collect(Collectors.toSet());
		if(tmp.isEmpty()){
			throw new ProdottoNonTrovatoException("Nessuna corrispondenza trovata per range di prezzo");
		}
		return tmp;
	}

	public Set< ProdottoElettronicoUtente > ricercaPerTipo ( String tipo){
		return carrello.stream()
				.filter(p -> p.getTipoElettronico().name().equals(tipo))
				.collect(Collectors.toSet());
	}

	public void stampaCarrello (){
		System.out.println("Articoli nel carrello: " + carrello);
	}


	public void rimozioneTramiteId(int id, int quantita) throws ProdottoNonTrovatoException {
		 ProdottoElettronicoUtente prdToRemove = carrello.stream()
				 .filter(p->p.getId() == id)
				 .findFirst()
				 .orElseThrow(()-> new  ProdottoNonTrovatoException("Prodotto non presente nel carrello"));
		 decrementaQuantita(id,quantita);

	}

	public double calcoloTot() throws CarrelloVuotoException {
		double prezzoTot = 0.0;

		if ( carrello.isEmpty()) throw new CarrelloVuotoException("Non ci sono articoli nel carrello");

		for( ProdottoElettronicoUtente dispositivo : carrello){
			prezzoTot += dispositivo.getPrezzoVendita() * dispositivo.getQuantitaCarrello();
		}
		return prezzoTot;
	}


	public void svuotaCarrello(){
		carrello.clear();
	}


	public void concludiAcquisto(Scanner scan) throws CarrelloVuotoException {
		if(carrello.isEmpty()) throw new CarrelloVuotoException("Non ci sono articoli nel carrello");
		System.out.println("Si è sicuro di voler concludere l'acquisto?");
		stampaCarrello();
		System.out.println(calcoloTot());
		System.out.println("Inserire si per continuare o no per annullare");
		scan.nextLine();
		String conferma = scan.nextLine();
		if(conferma.equalsIgnoreCase("si") || conferma.equalsIgnoreCase("sì")){
			System.out.println("Acquisto effettuato, torna a trovarci!");
			svuotaCarrello();
		}else if(conferma.equalsIgnoreCase("no")){
			System.out.println("Acquisto annullato");
		}else System.err.println("Comando non riconosciuto");
	}

	public Set < ProdottoElettronicoUtente > getCarrello() {
		return carrello;
	}

	public void incrementaQuantita(int id, int amount) throws ProdottoNonTrovatoException {

		ProdottoElettronicoUtente prodotto = ricercaPerId(id)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ProdottoNonTrovatoException("Prodotto non presente nel carrello"));

		int nuovaQuantita = prodotto.getQuantitaCarrello() + amount;

		prodotto.setQuantitaCarrello(nuovaQuantita);
	}

	public void decrementaQuantita (int id, int amount) throws ProdottoNonTrovatoException{
		ProdottoElettronicoUtente prodotto = ricercaPerId(id)
				.stream()
				.findFirst()
				.orElseThrow(()-> new ProdottoNonTrovatoException("Prodotto non presente nel carrello"));
		int quantita = prodotto.getQuantitaCarrello() - amount;
		if(quantita < 0){
			throw new IllegalArgumentException("Quantità non può essere negativa");
		}
		prodotto.setQuantitaCarrello(quantita);
	}
}
