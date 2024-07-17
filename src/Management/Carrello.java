package Management;

import Exceptions.CarrelloVuotoException;
import Exceptions.ProdottoNonTrovatoException;
import Products.ProdottoElettronicoDTO;

import java.util.*;
import java.util.stream.Collectors;

public class Carrello {

	HashSet < ProdottoElettronicoDTO > carrello;

	public Carrello(){
		carrello = new HashSet <>();
	}

	public void aggiungiProdotto ( ProdottoElettronicoDTO prodotto) throws ProdottoNonTrovatoException {
		Optional <ProdottoElettronicoDTO> toAdd = carrello.stream()
				.filter(p -> p.getId() == prodotto.getId())
				.findFirst();

		if (toAdd.isPresent()) incrementaQuantita(prodotto.getId(), 1);
		else carrello.add(prodotto);
	}

	public Set <ProdottoElettronicoDTO> ricercaPerId (int id){
		return carrello.stream()
				.filter(p -> p.getId() == id)
				.collect(Collectors.toSet());
	}

	public Set <ProdottoElettronicoDTO> ricercaPerMarca ( String marca){
		return carrello.stream()
				.filter(p -> p.getMarca().equals( marca))
				.collect(Collectors.toSet());
	}

	public Set<ProdottoElettronicoDTO> ricercaPerModello (String modello){
		return carrello.stream()
				.filter(p -> Objects.equals(p.getModello(), modello))
				.collect(Collectors.toSet());
	}

	public Set<ProdottoElettronicoDTO> ricercaPerPrezzoVendita (double prezzo){
		return carrello.stream()
				.filter(p -> p.getPrezzoVendita() == prezzo)
				.collect(Collectors.toSet());
	}


	public Set<ProdottoElettronicoDTO> ricercaPerRange (double prezzoMin, double prezzoMax){
		return carrello.stream()
				.filter(p -> p.getPrezzoVendita() > prezzoMin && p.getPrezzoVendita() < prezzoMax )
				.collect(Collectors.toSet());
	}

	public Set<ProdottoElettronicoDTO> ricercaPerTipo (String tipo){
		return carrello.stream()
				.filter(p -> p.getTipoElettronico().name().equals(tipo))
				.collect(Collectors.toSet());
	}

	public void stampaCarrello (){
		System.out.println("Articoli nel carrello: " + carrello);
	}


	public void rimozioneTramiteId(int id) throws ProdottoNonTrovatoException {
		boolean removed = carrello.removeIf(p -> p.getId() == id);
		if(!removed) throw new ProdottoNonTrovatoException("Products.Prodotto non presente nel carrello");
	}

	public double calcoloTot() throws CarrelloVuotoException {
		double prezzoTot = 0.0;

		if ( carrello.isEmpty()) throw new CarrelloVuotoException("Non ci sono articoli nel carrello");

		for( ProdottoElettronicoDTO dispositivo : carrello){
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

	public Set < ProdottoElettronicoDTO > getCarrello() {
		return carrello;
	}

	public void incrementaQuantita(int id, int amount) throws ProdottoNonTrovatoException {

		ProdottoElettronicoDTO prodotto = ricercaPerId(id)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ProdottoNonTrovatoException("Products.Prodotto non presente nel magazzino"));

		int nuovaQuantita = prodotto.getQuantitaCarrello() + amount;
		if (nuovaQuantita < 0) {
			throw new IllegalArgumentException("Quantità non può essere negativa");
		}

		prodotto.setQuantitaCarrello(nuovaQuantita);
	}
}
