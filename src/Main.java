import Exceptions.CarrelloVuotoException;
import Exceptions.LoginFailedException;
import Exceptions.ProdottoNonTrovatoException;
import Management.Magazzino;
import Products.ProdottoElettronico;
import Products.ProdottoElettronicoDTO;
import Products.TipoElettronico;
import Users.Cliente;

import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main( String[] args ) throws CarrelloVuotoException {

		//Clienti di prova
		Cliente cliente = new Cliente ( "Pietro", "Smusi", 34, "petrosmusi@acegamer.com", 0, "petro27");
		ProdottoElettronico prd1 = new ProdottoElettronico("Samsung", "Galaxys24", 700.0, 1300, 0, 10, 6, TipoElettronico.SMARTPHONE);
		Magazzino magazzino1 = new Magazzino();
		magazzino1.addProductToMagazzino(prd1);
		Scanner sc = new Scanner(System.in);
		boolean loggedIn = false;

		while ( !loggedIn ) {

			System.out.println("Inserisci l'id");
			String userRead = sc.nextLine();
			System.out.println("Inserisci la password");
			String passRead = sc.nextLine();

			try {
				loggedIn = cliente.login(userRead, passRead);
			} catch ( LoginFailedException e ) {
				System.err.println(e.getMessage());
			}
		}

		while ( loggedIn ) {
			mostraMenu();
			System.out.println("Inserisci la selezione");
			int selezione = sc.nextInt();

			switch ( selezione ) {

				case 0 -> loggedIn = false;

				case 1 -> {
					System.out.println("Inserisci l'id del prodotto da aggiungere");
					try {
						aggiuntaID(sc.nextInt(), cliente, magazzino1);
					}catch( ProdottoNonTrovatoException e){
						System.err.println(e.getMessage());
					}
				}

				case 2 -> {
					System.out.println("Inserisci l'id del prodotto da rimuovere");
					try {
						rimozioneID(sc.nextInt(), cliente, magazzino1);
					}catch(ProdottoNonTrovatoException e){
						System.err.println(e.getMessage());
					}
				}

				case 3 -> cliente.stampaCarrelloProdotti();

				case 4 -> {
					try {
						System.out.println(cliente.calcoloTotaleCarrello());
					}catch(CarrelloVuotoException e){
						System.err.println(e.getMessage());
					}
				}

				case 5 -> menuRicerca(sc, cliente);

				case 6 -> cliente.svuotaCarrelloProdotti();

				case 7 -> {
					try{
						cliente.concludiAcquistoProdotti();
					}catch ( CarrelloVuotoException e ){
						System.err.println(e.getMessage());
					}
				}

				default -> System.err.println("Comando non riconosciuto");

			}
		}
	}

	private static void mostraMenu ( ) {
		System.out.println("\n--- Menu Management.Magazzino ---");
		System.out.println();
		System.out.println("1. Aggiungi prodotto al carrello");
		System.out.println("2. Rimuovi prodotto dal carrello");
		System.out.println("3. Visualizza prodotti nel carrello");
		System.out.println("4. Visualizza totale del carrello");
		System.out.println("5. Ricerca");
		System.out.println("6. Svuota il carrello");
		System.out.println("7. Concludi l'acquisto");
		System.out.println("0. LogOut");
		System.out.println();
	}

	public static void menuRicerca(Scanner sc, Cliente cliente){
		System.out.println("\n--- Menu Ricerca ---");
		System.out.println();
		System.out.println("1. Ricerca per marca");
		System.out.println("2. Ricerca per modello");
		System.out.println("3. Ricerca per prezzo");
		System.out.println("4. Ricerca per range di prezzo");
		System.out.println("5. Ricerca per tipo");
		System.out.println("6. Ricerca tramite id");
		System.out.println("0. Torna indietro");
		System.out.println();
		sceltaRicerca(sc, cliente);
	}

	public static void sceltaRicerca(Scanner sc, Cliente cliente){

		System.out.println("Selezione il tipo di ricerca da effetuare");
		int ricercaSel = sc.nextInt();

		switch (  ricercaSel ){
			case 1 ->
				{ System.out.println("Inserisci la marca");
					sc.nextLine();
					String marca = sc.nextLine();
					Set < ProdottoElettronicoDTO > found = cliente.ricercaProdottoPerMarca(marca);
					System.out.println("Dispositivi trovati: " + found);
				}

			case 2 ->
			{System.out.println("Inserisci il modello");
				String modello = sc.nextLine();
				System.out.println("Dispositivi trovati: " + cliente.ricercaProdottoPerModello(modello));}

			case 3 ->
			{System.out.println("Inserisci il prezzo minore e poi il prezzo maggiore");
				double prezzoMin = sc.nextDouble();
				double prezzoMag = sc.nextDouble();
				System.out.println("Dispositivi trovati: " + cliente.ricercaProdottoPerRange(prezzoMin, prezzoMag));}

			case 4 ->
			{System.out.println("Inserisci il tipo di dispositivo da cercare");
				String tipo = sc.nextLine();
				System.out.println("Dispositivi trovati: " +  cliente.ricercaProdottoPerTIpo(tipo));}

/*
			case 6:
				System.out.println("Inserisci l'id da ricercare: ");
				System.out.println("Dispositivo trovato: " + cliente.ricercaProdottoTramiteId(sc.nextInt()));
				break;
*/
			case 0 -> {}

			default -> System.err.println("Comando non riconosciuto");
		}
	}

	public static void aggiuntaID(int id, Cliente cliente, Magazzino magazzino) throws ProdottoNonTrovatoException {

		ProdottoElettronico toAdd = magazzino.filteredById(id)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ProdottoNonTrovatoException("Products.Prodotto non presente nel magazzino"));

		int quantitaProdotto = toAdd.getQuantita();

		if(quantitaProdotto == 0) throw new ProdottoNonTrovatoException("Non ci sono sufficienti quantità in magazzino");

		ProdottoElettronicoDTO prodottoTmp = toAdd.toDTO();

		cliente.aggiungiProdottoAlCarrello(prodottoTmp);
		System.out.println("Products.Prodotto aggiunto con successo");
		magazzino.decrementaQuantita(id, 1);

	}

	public static void rimozioneID(int id, Cliente cliente, Magazzino magazzino) throws ProdottoNonTrovatoException {

		cliente.rimuoviProdottoTramiteId(id);
		magazzino.incrementaQuantita(id, 1);
		System.out.println("Products.Prodotto rimosso con successo");
	}

	public static void svuotaCarrello(Cliente cliente, Magazzino magazzino){
		cliente.getCarrello().forEach(p ->
				magazzino.getMagazzino().stream()
				);
	}

}