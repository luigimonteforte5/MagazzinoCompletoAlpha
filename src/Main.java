import Exceptions.CarrelloVuotoException;
import Exceptions.LoginFailedException;
import Exceptions.ProdottoNonTrovatoException;
import Management.Magazzino;
import Products.ProdottoElettronico;
import Products.ProdottoElettronicoUtente;
import Products.TipoElettronico;
import Users.Cliente;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws CarrelloVuotoException {
		List<Cliente> clienti = Cliente.leggiUtentiDaFile();
		Magazzino magazzino = inizializzaMagazzino();
		Cliente clienteLoggato;

		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				clienteLoggato = gestisciLogin(clienti, sc);
				gestisciMenuPrincipale(clienteLoggato, magazzino, sc);
			}
		}
	}

	private static Magazzino inizializzaMagazzino() {
		Magazzino magazzino = new Magazzino();
		ProdottoElettronico prd1 = new ProdottoElettronico("Samsung", "Galaxys24", 700.0, 1300, 0, 2, 6, TipoElettronico.SMARTPHONE);
		magazzino.addProductToMagazzino(prd1);
		return magazzino;
	}

	private static Cliente gestisciLogin(List<Cliente> clienti, Scanner sc) {
		Cliente clienteLoggato = null;
		while (clienteLoggato == null) {
			try {
				clienteLoggato = logInCliente(clienti, sc);
			} catch (LoginFailedException e) {
				System.err.println(e.getMessage());
			}
		}
		return clienteLoggato;
	}

	private static Cliente logInCliente(List<Cliente> clienti, Scanner sc) throws LoginFailedException {
		System.out.println("Inserisci l'id");
		String userRead = sc.nextLine();
		System.out.println("Inserisci la password");
		String passRead = sc.nextLine();

		if (clienti.stream().noneMatch(c -> c.getEmail().equalsIgnoreCase(userRead))) {
			throw new LoginFailedException("Utente non presente");
		}

		return clienti.stream()
				.filter(cliente -> cliente.login(userRead, passRead))
				.findFirst()
				.orElseThrow(() -> new LoginFailedException("UserName o Password errati"));
	}

	private static void gestisciMenuPrincipale(Cliente clienteLoggato, Magazzino magazzino, Scanner sc) {
		while (clienteLoggato != null) {
			mostraMenu(clienteLoggato);
			int selezione = sc.nextInt();
			sc.nextLine(); // consume newline
			clienteLoggato = gestisciSelezioneMenu(selezione, clienteLoggato, magazzino, sc);
		}
	}

	private static void mostraMenu(Cliente cliente) {
		System.out.println("\n--- Menu Magazzino ---");
		System.out.println("1. Aggiungi prodotto al carrello");
		System.out.println("2. Rimuovi prodotto dal carrello");
		System.out.println("3. Visualizza prodotti nel carrello");
		System.out.println("4. Visualizza totale del carrello");
		System.out.println("5. Ricerca");
		System.out.println("6. Svuota il carrello");
		System.out.println("7. Concludi l'acquisto");
		System.out.println("0. LogOut");
		System.out.println("Utente loggato: " + cliente.getNome() + " " + cliente.getCognome());
	}

	private static Cliente gestisciSelezioneMenu(int selezione, Cliente clienteLoggato, Magazzino magazzino, Scanner sc) {
		switch (selezione) {
			case 0:
				return null;
			case 1:
				aggiuntaID(sc, clienteLoggato, magazzino);
				break;
			case 2:
				rimozioneID(sc, clienteLoggato, magazzino);
				break;
			case 3:
				clienteLoggato.stampaCarrelloProdotti();
				break;
			case 4:
				calcoloTotaleCarrello(clienteLoggato);
				break;
			case 5:
				menuRicerca(sc, clienteLoggato);
				break;
			case 6:
				clienteLoggato.svuotaCarrelloProdotti();
				break;
			case 7:
				concludiAcquisto(sc, clienteLoggato);
				break;
			default:
				System.err.println("Comando non riconosciuto");
		}
		return clienteLoggato;
	}

	public static void aggiuntaID(Scanner sc, Cliente cliente, Magazzino magazzino) throws ProdottoNonTrovatoException {

		//Done: aggiungere possibilità di inserire quantità prodotti maggiori di 1

		System.out.println("Inserisci l'id del prodotto da aggiungere");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Inserisci la quantità di prodotti che desideri aggiungere al carrello");
		int quantita = sc.nextInt();


		ProdottoElettronico toAdd = magazzino.filteredById(id)
				.stream()
				.findFirst()
				.orElseThrow(() -> new ProdottoNonTrovatoException("Products.Prodotto non presente nel magazzino"));

		int quantitaProdotto = toAdd.getQuantita();

		if(quantitaProdotto == 0 || quantita>quantitaProdotto) throw new ProdottoNonTrovatoException("Non ci sono sufficienti quantità in magazzino");

		ProdottoElettronicoUtente prodottoTmp = toAdd.toDTO();

		cliente.aggiungiProdottoAlCarrello(prodottoTmp, quantita);
		prodottoTmp.setQuantitaCarrello(quantita);
		System.out.println("Products.Prodotto aggiunto con successo");

		magazzino.decrementaQuantita(id, quantita);

	}

	public static void rimozioneID(Scanner sc, Cliente cliente, Magazzino magazzino) throws ProdottoNonTrovatoException {

		System.out.println("Inserisci l'id del prodotto da rimuovere");

		int id = sc.nextInt();
		sc.nextLine();

		System.out.println("Inserire la quantità di prodotti da rimuovere");

		int quantita = sc.nextInt();
		sc.nextLine();
		try{
			cliente.rimuoviProdottoTramiteId(id, quantita);

			magazzino.incrementaQuantita(id, quantita);

			System.out.println("Products.Prodotto rimosso con successo");
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void calcoloTotaleCarrello(Cliente cliente) {
		try {
			System.out.println(cliente.calcoloTotaleCarrello());
		} catch (CarrelloVuotoException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void concludiAcquisto(Scanner sc, Cliente cliente) {
		try {
			cliente.concludiAcquistoProdotti(sc);
		} catch (CarrelloVuotoException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void menuRicerca(Scanner sc, Cliente cliente) {
		System.out.println("\n--- Menu Ricerca ---");
		System.out.println("1. Ricerca per marca");
		System.out.println("2. Ricerca per modello");
		System.out.println("3. Ricerca per prezzo");
		System.out.println("4. Ricerca per range di prezzo");
		System.out.println("5. Ricerca per tipo");
		System.out.println("6. Ricerca tramite id");
		System.out.println("0. Torna indietro");
		sceltaRicerca(sc, cliente);
	}

	private static void sceltaRicerca(Scanner sc, Cliente cliente) {
		System.out.println("Seleziona il tipo di ricerca da effettuare");
		int ricercaSel = sc.nextInt();
		sc.nextLine();

		switch (ricercaSel) {
			case 1:
				eseguiRicerca(() -> ricercaMarca(cliente, sc));
				break;
			case 2:
				eseguiRicerca(() -> ricercaModello(cliente, sc));
				break;
			case 3:
				eseguiRicerca(() -> ricercaPrezzo(cliente, sc));
				break;
			case 4:
				eseguiRicerca(() -> ricercaRangePrezzo(cliente, sc));
				break;
			case 5:
				eseguiRicerca(() -> ricercaTipo(cliente, sc));
				break;
			case 6:
				eseguiRicerca(() -> ricercaId(cliente, sc));
				break;
			case 0:
				break;
			default:
				System.err.println("Comando non riconosciuto");
		}
	}

	@FunctionalInterface
	private interface Ricerca {
		Set<ProdottoElettronicoUtente> esegui() throws ProdottoNonTrovatoException;
	}

	private static void eseguiRicerca(Ricerca ricerca) {
		try {
			Set<ProdottoElettronicoUtente> found = ricerca.esegui();
			System.out.println("Dispositivi trovati: " + found);
		} catch (ProdottoNonTrovatoException e) {
			System.err.println(e.getMessage());
		}
	}

	private static Set<ProdottoElettronicoUtente> ricercaMarca(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci la marca");
		String marca = sc.nextLine();
		return cliente.ricercaProdottoPerMarca(marca);
	}

	private static Set<ProdottoElettronicoUtente> ricercaModello(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il modello");
		String modello = sc.nextLine();
		return cliente.ricercaProdottoPerModello(modello);
	}

	private static Set<ProdottoElettronicoUtente> ricercaPrezzo(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il prezzo:");
		double prezzo = sc.nextDouble();
		sc.nextLine();
		return cliente.ricercaProdottoPerPrezzoDiVendita(prezzo);
	}

	private static Set<ProdottoElettronicoUtente> ricercaRangePrezzo(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il prezzo minore e poi il prezzo maggiore");
		double prezzoMin = sc.nextDouble();
		double prezzoMag = sc.nextDouble();
		sc.nextLine();
		return cliente.ricercaProdottoPerRange(prezzoMin, prezzoMag);
	}

	private static Set<ProdottoElettronicoUtente> ricercaTipo(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il tipo di dispositivo da cercare");
		String tipo = sc.nextLine();
		return cliente.ricercaProdottoPerTIpo(tipo);
	}

	private static Set<ProdottoElettronicoUtente> ricercaId(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci l'id da ricercare:");
		int id = sc.nextInt();
		sc.nextLine();
		return cliente.ricercaTramiteId(id);
	}
}