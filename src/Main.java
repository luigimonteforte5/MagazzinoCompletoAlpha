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
	public static void main( String[] args ) throws CarrelloVuotoException {

		//ToDo: aggiungere lettura da file(in progress...)
		//ToDo: Metodo registrazione su file
		//ToDo: Metodo login magazziniere (B&C)
		//ToDo: Ricerca nel magazzino
		//ToDo: chiudere programma
		//ToDo: BuilderPattern Magazzino(B&C)



		List <Cliente> clienti = Cliente.leggiUtentiDaFile();

		ProdottoElettronico prd1 = new ProdottoElettronico("Samsung", "Galaxys24", 700.0, 1300, 0, 2, 6, TipoElettronico.SMARTPHONE);
		Magazzino magazzino1 = new Magazzino();
		magazzino1.addProductToMagazzino(prd1);
		boolean loggedIn = false;
		Scanner sc = new Scanner(System.in);
		Cliente clienteLoggato = null;

	while ( true ) {

		while ( clienteLoggato == null ) {
			try {
				assert clienti != null;
				clienteLoggato = logInCliente(clienti);
				loggedIn = true;
			}catch(LoginFailedException e){
				System.err.println(e.getMessage());
			}
		}

		while ( clienteLoggato != null ) {
			mostraMenu(clienteLoggato);
			System.out.println("Inserisci la selezione");
			int selezione = sc.nextInt();

			switch ( selezione ) {

				case 0 -> clienteLoggato = null;

				case 1 -> {//Aggiunta tramite id

					try {
						aggiuntaID(sc, clienteLoggato, magazzino1);
					} catch ( ProdottoNonTrovatoException e ) {
						System.err.println(e.getMessage());
					}
				}

				case 2 -> {//Rimozione tramite id
					try {
						rimozioneID(sc, clienteLoggato, magazzino1);
					} catch ( ProdottoNonTrovatoException e ) {
						System.err.println(e.getMessage());
					}
				}

				case 3 -> clienteLoggato.stampaCarrelloProdotti(); //VisualizzaCarrello

				case 4 -> {//CalcoloTotale
					try {
						System.out.println(clienteLoggato.calcoloTotaleCarrello());
					} catch ( CarrelloVuotoException e ) {
						System.err.println(e.getMessage());
					}
				}

				case 5 -> menuRicerca(sc, clienteLoggato);//Ricerche

				case 6 -> clienteLoggato.svuotaCarrelloProdotti(); /*ToDo: SvuotaCarrello*/

				case 7 -> {//ConcludiAcquisto
					try {
						clienteLoggato.concludiAcquistoProdotti(sc);
					} catch ( CarrelloVuotoException e ) {
						System.err.println(e.getMessage());
					}
				}

				default -> System.err.println("Comando non riconosciuto");

			}
		}
	}

	}

	private static void mostraMenu ( Cliente cliente ) {
		System.out.println("\n--- Menu Magazzino ---");
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
		System.out.println("Utente loggato: " + cliente.getNome() + " " + cliente.getCognome());
	}

	public static void menuRicerca(Scanner sc, Cliente cliente){
		//ToDo: controllare ricerche

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
				{  try {
					Set < ProdottoElettronicoUtente > found = ricercaMarca(cliente,sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException inf){
					System.err.println(inf.getMessage());
				}

				}

			case 2 ->
			{	try {
				Set < ProdottoElettronicoUtente > found = ricercaModello(cliente,sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException inf){
				System.err.println(inf.getMessage());
			}
			}
			case 3 ->
			{	try{
					Set< ProdottoElettronicoUtente > found = ricercaPrezzo(cliente, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException inf){
					System.err.println(inf.getMessage());
				}
			}

			case 4 ->
			{ 	try{
				Set< ProdottoElettronicoUtente > found = ricercaRangePrezzo(cliente, sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException inf){
				System.err.println(inf.getMessage());
			}}

			case 5 ->
			{	try {
				Set< ProdottoElettronicoUtente > found = ricercaTipo(cliente, sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException inf){
				System.err.println(inf.getMessage());
			}
			}

			case 6 ->
			{	try{
					Set< ProdottoElettronicoUtente > found = ricercaId(cliente, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException inf){
					System.err.println(inf.getMessage());
				}
			}

			case 0 -> {}

			default -> System.err.println("Comando non riconosciuto");
		}
		sc.nextLine();
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

	public static void svuotaCarrello(Cliente cliente, Magazzino magazzino){
		cliente.getCarrello().forEach(p ->
				magazzino.getMagazzino().stream());
	}

	public static Cliente logInCliente(List<Cliente> clienti) throws LoginFailedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci l'id");
		String userRead = sc.nextLine();
		System.out.println("Inserisci la password");
		String passRead = sc.nextLine();

		if(clienti.stream().noneMatch(c -> c.getEmail().equalsIgnoreCase(userRead)) ) throw new LoginFailedException("Utente non presente");

		for( Cliente cliente : clienti ) {
			if ( cliente.login(userRead, passRead) ) {
				return cliente;
			}
		}
		throw new LoginFailedException("UserName o Password errati");
	}

	public static Set< ProdottoElettronicoUtente > ricercaMarca( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci la marca");
		String marca = sc.nextLine();
		return cliente.ricercaProdottoPerMarca(marca);
	}

	public static Set< ProdottoElettronicoUtente > ricercaModello( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il modello");
		String modello = sc.nextLine();
		return cliente.ricercaProdottoPerModello(modello);
	}

	public static Set< ProdottoElettronicoUtente > ricercaPrezzo( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException{
		System.out.println("Inserisci il prezzo:");
		double prezzo = sc.nextDouble();
		return cliente.ricercaProdottoPerPrezzoDiVendita(prezzo);
	}

	public static Set< ProdottoElettronicoUtente > ricercaRangePrezzo( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il prezzo minore e poi il prezzo maggiore");
		double prezzoMin = sc.nextDouble();
		double prezzoMag = sc.nextDouble();
		return cliente.ricercaProdottoPerRange(prezzoMin, prezzoMag);
	}
	public static Set< ProdottoElettronicoUtente > ricercaTipo( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il tipo di dispositivo da cercare");
		String tipo = sc.nextLine();
		return cliente.ricercaProdottoPerTIpo(tipo);
	}
	public static Set< ProdottoElettronicoUtente > ricercaId( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci l'id da ricercare: ");
		int id = sc.nextInt();
		return cliente.ricercaTramiteId(id);
	}
}