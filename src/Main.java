import Exceptions.CarrelloVuotoException;
import Exceptions.LoginFailedException;
import Exceptions.ProdottoNonTrovatoException;
import Management.Magazzino;
import Products.ProdottoElettronico;
import Products.ProdottoElettronicoUtente;
import Users.Cliente;
import Users.Magazziniere;
import Users.Roles;
import Users.Utente;
import Utilities.UserMapper;
import Utilities.ProductMapper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

	private static Utente utenteLoggato = null;
	private static Cliente clienteLoggato = null;
	private static Magazziniere magazziniereLoggato = null;
	private static final Magazzino magazzino = Magazzino.getInstance();

	public static void main( String[] args ) {

		//ToDo: Aggiungere lettura da file(in progress... magazziniere)
		//ToDo: Ricerca nel magazzino da implementare in cliente
		//Todo: Metodo per chiudere il programma

		//ToDo: Cancellazione utente
		//ToDo: integrare il DB(sostituire Gson/json)


		//Carica i clienti nella lista leggendoli dal file Json


		//Crea un prodottoElettronico di esempio

		ProdottoElettronico prd1 = new ProdottoElettronico.ProductBuilder("Samsung", "Galaxy S24", 850.0, 1300.0, 0, "Smartphone", 6).setQuantitaMagazzino(3).build();
		//Inizializza il magazzino

		magazzino.addProductToMagazzino(prd1);//aggiunge il prodotto al magazzino
		//boolean loggedIn = false;
		Scanner sc = new Scanner(System.in);//Inizializza lo Scanner

		while ( true ) {
			//Controlla che ci sia un cliente loggato
			while ( Main.utenteLoggato == null) {
				menuAccesso();
			}

			if( Main.utenteLoggato.getRole().equals(Roles.CLIENTE)) clienteLoggato = UserMapper.toUtente(utenteLoggato);
			if( Main.utenteLoggato.getRole().equals(Roles.MAGAZZINIERE)) magazziniereLoggato = UserMapper.toMagazziniere(Main.utenteLoggato);

			while ( Main.utenteLoggato != null ) {
				if(clienteLoggato != null) mostraMenuCliente();
				if(magazziniereLoggato != null) mostraMenuMagazziniere();
			}
		}

	}

	private static void mostraMenuCliente() {
		System.out.println("\n--- Menu Cliente ---");
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
		sceltaCliente();
	}

	private static void sceltaCliente() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci la selezione");
		int selezione = sc.nextInt();

		switch ( selezione ) {

			case 0 -> {
				clienteLoggato = null;
				utenteLoggato = null;
			}	//todo

			case 1 -> {//Aggiunta tramite id

				try {
					aggiuntaIDCarrello(sc, clienteLoggato, magazzino);
				} catch ( ProdottoNonTrovatoException e ) {
					System.err.println(e.getMessage());
				}
			}

			case 2 -> {//Rimozione tramite id
				try {
					rimozioneIDCarrello(sc, clienteLoggato, magazzino);
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

			case 5 -> menuRicercaCliente(sc, clienteLoggato);//Ricerche

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
		//sc.close();
	}

	private static void mostraMenuMagazziniere(){
		System.out.println("\n--- Menu Magazziniere ---");
		System.out.println();
		System.out.println("1. Aggiungi prodotto al magazzino");
		System.out.println("2. Rimuovi prodotto dal magazzino");
		System.out.println("3. Visualizza prodotti nel magazzino");
		System.out.println("4. Ricerca");
		System.out.println("0. LogOut");
		System.out.println();
		sceltaMagazziniere();
	}

	private static void sceltaMagazziniere() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci la selezione");
		int selezione = sc.nextInt();

		switch ( selezione ) {

			case 0 -> {
				clienteLoggato = null;
				magazziniereLoggato = null;
			}	//todo

			case 1 -> aggiuntaMagazzino(magazziniereLoggato, magazzino);

			case 2 -> {//Rimozione tramite id
				try {
					System.out.println("Inserisci l'id del prodotto da rimuovere: ");
					magazziniereLoggato.removeProductFromMagazzino(sc.nextInt());
				} catch ( ProdottoNonTrovatoException e ) {
					System.err.println(e.getMessage());
				}
			}

			case 3 -> System.out.println(magazziniereLoggato.getMagazzino()); //VisualizzaCarrello

			case 4 -> menuRicercaMagazziniere(sc, magazziniereLoggato);

			default -> System.err.println("Comando non riconosciuto");
		}
		//sc.close();
	}

	private static void aggiuntaMagazzino( Magazziniere magazziniere, Magazzino magazzino ) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci la marca: ");
		String marca = sc.nextLine();
		System.out.println("Inserisci il modello: ");
		String modello = sc.nextLine();
		System.out.println("Inserisci il prezzo di acquisto: ");
		double prezzoAcquisto = sc.nextDouble();
		sc.nextLine();
		System.out.println("Inserisci la percentuale di ricarico da applicare: ");
		int ricarico = sc.nextInt();
		sc.nextLine();
		double prezzoVendita = prezzoAcquisto + (prezzoAcquisto * ricarico)/100;
		System.out.println("Inserisci l'id: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Inserisci il tipo di dispositivo (Smartphone, notebook o tablet): ");
		String tipo = sc.nextLine();
		System.out.println("Inserisci la dimensione dello schermo: ");
		float dimSchermo = sc.nextFloat();
		sc.nextLine();
		System.out.println("Inserisci la quantità: ");
		int quantita = sc.nextInt();
		sc.nextLine();
		ProdottoElettronico toAdd = new ProdottoElettronico.ProductBuilder(marca, modello, prezzoAcquisto, prezzoVendita, id, tipo, dimSchermo).setQuantitaMagazzino(quantita).build();
		System.out.println("Vuoi inserire una descrizione? S/N");
		if(sc.nextLine().equalsIgnoreCase("si")){
			System.out.println("Inserisci la decrizione: ");
			String descrizione = sc.nextLine();
			toAdd.setDescrizione(descrizione);
		}

		magazziniere.addProductToMagazzino(toAdd);
	}

	public static void menuRicercaCliente( Scanner sc, Cliente cliente){
		//ToDo: controllare ricerche

		System.out.println("\n--- Menu Ricerca Cliente---");
		System.out.println();
		System.out.println("1. Ricerca per marca");
		System.out.println("2. Ricerca per modello");
		System.out.println("3. Ricerca per prezzo");
		System.out.println("4. Ricerca per range di prezzo");
		System.out.println("5. Ricerca per tipo");
		System.out.println("6. Ricerca tramite id");
		System.out.println("0. Torna indietro");
		System.out.println();
		sceltaRicercaCliente(sc, cliente);
	}

	public static void menuRicercaMagazziniere (Scanner sc, Magazziniere magazziniere){
		System.out.println("\n--- Menu Ricerca Magazziniere---");
		System.out.println();
		System.out.println("1. Ricerca per marca");
		System.out.println("2. Ricerca per modello");
		System.out.println("3. Ricerca per prezzo di vendita");
		System.out.println("4. Ricerca per prezzo di acquisto");
		System.out.println("5. Ricerca per range di prezzo");
		System.out.println("6. Ricerca per tipo");
		System.out.println("7. Ricerca tramite id");
		System.out.println("0. Torna indietro");
		System.out.println();
		sceltaRicercaMagazziniere(sc, magazziniere);
	}

	public static void sceltaRicercaCliente( Scanner sc, Cliente cliente){

		System.out.println("Selezione il tipo di ricerca da effetuare");
		int ricercaSel = sc.nextInt(); //Legge la ricerca da effettuare

		switch (  ricercaSel ){
			case 1 ->
				{  try {//Ricerca per marca
					Set < ProdottoElettronicoUtente > found = ricercaMarcaCarrello(cliente,sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException e){
					System.err.println(e.getMessage());
				}
				}

			case 2 ->
			{	try {//Ricerca per modello
				Set < ProdottoElettronicoUtente > found = ricercaModelloCarrello(cliente,sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException e){
				System.err.println(e.getMessage());
			}
			}
			case 3 ->
			{	try{//Ricerca per prezzo di vendita
					Set< ProdottoElettronicoUtente > found = ricercaPrezzoCarrello(cliente, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException e){
					System.err.println(e.getMessage());
				}
			}

			case 4 ->
			{ 	try{//Ricerca per range di prezzo
				Set< ProdottoElettronicoUtente > found = ricercaRangePrezzoCarrello(cliente, sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException e){
				System.err.println(e.getMessage());
			}
			}

			case 5 ->
			{	try {//Ricerca per tipo elettronico
				Set< ProdottoElettronicoUtente > found = ricercaTipoUtente(cliente, sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException e){
				System.err.println(e.getMessage());
			}
			}

			case 6 ->
			{	try{//Ricerca tramite ID
					ProdottoElettronicoUtente found = ricercaIdCliente(cliente, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException e){
					System.err.println(e.getMessage());
				}
			}

			case 0 -> {}//Torna al menu precedente

			default -> System.err.println("Comando non riconosciuto");
		}
		sc.nextLine();
	}

	public static void sceltaRicercaMagazziniere(Scanner sc, Magazziniere magazziniere) {
		System.out.println("Selezione il tipo di ricerca da effetuare");
		int ricercaSel = sc.nextInt(); //Legge la ricerca da effettuare

		switch ( ricercaSel ) {
			case 1 -> {
				try {
					Set < ProdottoElettronico > found = ricercaMarcaMagazzino(magazziniere, sc);
					System.out.println("Prodotti trovati: " + found);
				} catch ( ProdottoNonTrovatoException e ) {
					System.err.println(e.getMessage());
				}
			}
			case 2 -> {
				try {//Ricerca per modello
					Set < ProdottoElettronico > found = ricercaModelloMagazzino(magazziniere, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch ( ProdottoNonTrovatoException e ) {
					System.err.println(e.getMessage());
				}
			}
			case 3 ->{
				try{//Ricerca per prezzo di vendita
				Set< ProdottoElettronico > found = ricercaPrezzoVenditaMagazzino(magazziniere, sc);
				System.out.println("Dispositivi trovati: " + found);
			} catch (ProdottoNonTrovatoException e){
				System.err.println(e.getMessage());
			}
			}
			case 4 -> {
				try {//Ricerca per prezzo di acquisto
					Set < ProdottoElettronico > found = ricercaPrezzoAcquistoMagazzino(magazziniere, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch ( ProdottoNonTrovatoException e ) {
					System.err.println(e.getMessage());
				}
			}
			case 5 -> {
				try{//Ricerca per range di prezzo
					Set< ProdottoElettronico > found = ricercaRangePrezzoMagazzino(magazziniere, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException e){
					System.err.println(e.getMessage());
				}
			}
			case 6 ->{
				try {//Ricerca per tipo elettronico
					Set< ProdottoElettronico > found = ricercaTipoMagazzino(magazziniere, sc);
					System.out.println("Dispositivi trovati: " + found);
				} catch (ProdottoNonTrovatoException e){
					System.err.println(e.getMessage());
				}
			}
			case 7 -> {
				try {//Ricerca tramite ID
					ProdottoElettronico found = ricercaIdMagazzino(magazziniere, sc);
					System.out.println("Dispositivo trovato: " + found);
				} catch ( ProdottoNonTrovatoException e ) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	//aggiunge prodotti al carrello e ne rimuove la quantità dal magazzino
	public static void aggiuntaIDCarrello( Scanner sc, Cliente cliente, Magazzino magazzino) throws ProdottoNonTrovatoException {

		System.out.println("Inserisci l'id del prodotto da aggiungere");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Inserisci la quantità di prodotti che desideri aggiungere al carrello");
		int quantita = sc.nextInt();

		ProdottoElettronico toAdd = magazzino.filteredById(id);  //trova il prodotto elettronico da aggiungere in base all'id

		int quantitaProdotto = toAdd.getQuantitaMagazzino();

		if(quantitaProdotto == 0 || quantita>quantitaProdotto) throw new ProdottoNonTrovatoException("Non ci sono sufficienti quantità in magazzino"); //Nel caso non ci siano abbastanza prodotti in magazzino, lancia eccezione

		//todo cambiare nome metodo
		ProdottoElettronicoUtente prodottoTmp = ProductMapper.toProdottoUtente(toAdd); //tasforma l'oggetto da prodotto a prodotto utente

		cliente.aggiungiProdottoAlCarrello(prodottoTmp, quantita);
		prodottoTmp.setQuantitaCarrello(quantita);
		System.out.println("Prodotto aggiunto con successo");

		magazzino.decrementaQuantita(id, quantita); //Rimuovi dal magazzino i prodotti aggiunti al carrello

	}

	//rimuove il prodotto dal carrello e lo riaggiunge al magazzino
	public static void rimozioneIDCarrello( Scanner sc, Cliente cliente, Magazzino magazzino) throws ProdottoNonTrovatoException {

		System.out.println("Inserisci l'id del prodotto da rimuovere");

		int id = sc.nextInt();
		sc.nextLine();

		System.out.println("Inserire la quantità di prodotti da rimuovere");

		int quantita = sc.nextInt();
		sc.nextLine();

		cliente.rimuoviProdottoTramiteId(id, quantita);
		magazzino.incrementaQuantita(id, quantita);

		System.out.println("Prodotto rimosso con successo");

	}

	//todo
	public static void svuotaCarrello(Cliente cliente, Magazzino magazzino){
		cliente.getCarrello().forEach(p ->
				magazzino.getMagazzino().stream());
	}

	//Legge i dati inseriti da input, controlla che l'utente sia registrato e che i dat inseriti siano validi
	public static Utente logIn(List<Utente> utenti) throws LoginFailedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci e-mail:");
		String userRead = sc.nextLine();
		if(utenti.stream().noneMatch(c -> c.getEmail().equalsIgnoreCase(userRead)) ) throw new LoginFailedException("Utente non registrato");//Se il cliente non è registrato, lancia un'eccezione

		System.out.println("Inserisci la password");
		String passRead = sc.nextLine();

		return utenti.stream()
				.filter(c ->c.login(userRead, passRead))
				.findFirst()
				.orElseThrow(() -> new LoginFailedException("UserName o Password errati"));
		//Richiama il metodo login e controlla se i dati inseriti sono corretti, in caso non lo siano lancia eccezione
	}

	public static Set< ProdottoElettronico > ricercaMarcaMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci la marca");
		String marca = sc.nextLine();
		return magazziniere.filtredByProducer(marca);
	}

	public static Set< ProdottoElettronico > ricercaModelloMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il modello");
		String modello = sc.nextLine();
		return magazziniere.filtredByModel(modello);
	}

	public static Set< ProdottoElettronico > ricercaPrezzoVenditaMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException{
		System.out.println("Inserisci il prezzo:");
		double prezzo = sc.nextDouble();
		return magazziniere.filtredBySellPrice(prezzo);
	}

	public static Set< ProdottoElettronico > ricercaPrezzoAcquistoMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException{
		System.out.println("Inserisci il prezzo:");
		double prezzo = sc.nextDouble();
		return magazziniere.filtredByWhareHousePurchasePrice(prezzo);
	}

	public static Set< ProdottoElettronico > ricercaRangePrezzoMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il prezzo minore e poi il prezzo maggiore");
		double prezzoMin = sc.nextDouble();
		double prezzoMag = sc.nextDouble();
		return magazziniere.filtredByRangePrice(prezzoMin, prezzoMag);
	}
	public static Set< ProdottoElettronico > ricercaTipoMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il tipo di dispositivo da cercare");
		String tipo = sc.nextLine();
		return magazziniere.filtredBytype(tipo);
	}
	public static ProdottoElettronico ricercaIdMagazzino( Magazziniere magazziniere, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci l'id da ricercare: ");
		int id = sc.nextInt();
		return magazziniere.filteredById(id);
	}

	public static Set< ProdottoElettronicoUtente > ricercaMarcaCarrello(Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci la marca");
		String marca = sc.nextLine();
		return cliente.ricercaProdottoPerMarca(marca);
	}

	public static Set< ProdottoElettronicoUtente > ricercaModelloCarrello( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il modello");
		String modello = sc.nextLine();
		return cliente.ricercaProdottoPerModello(modello);
	}

	public static Set< ProdottoElettronicoUtente > ricercaPrezzoCarrello( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException{
		System.out.println("Inserisci il prezzo:");
		double prezzo = sc.nextDouble();
		return cliente.ricercaProdottoPerPrezzoDiVendita(prezzo);
	}

	public static Set< ProdottoElettronicoUtente > ricercaRangePrezzoCarrello( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il prezzo minore e poi il prezzo maggiore");
		double prezzoMin = sc.nextDouble();
		double prezzoMag = sc.nextDouble();
		return cliente.ricercaProdottoPerRange(prezzoMin, prezzoMag);
	}
	public static Set< ProdottoElettronicoUtente > ricercaTipoUtente( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci il tipo di dispositivo da cercare");
		String tipo = sc.nextLine();
		return cliente.ricercaProdottoPerTIpo(tipo);
	}
	public static ProdottoElettronicoUtente ricercaIdCliente( Cliente cliente, Scanner sc) throws ProdottoNonTrovatoException {
		System.out.println("Inserisci l'id da ricercare: ");
		int id = sc.nextInt();
		return cliente.ricercaTramiteId(id);
	}

	public static void registrazione() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci il nome");
		String nome = sc.nextLine();
		System.out.println("Inserisci il cognome");
		String cognome = sc.nextLine();
		System.out.println("Inserisci l'età");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.println("Inserisci la mail");
		String email = sc.nextLine();
		System.out.println("Inserisci la password");
		String password = sc.nextLine();

		Cliente tmp = new Cliente(nome, cognome, age, email, password);

        try {
            Cliente.aggiungiClienteAlFile(tmp);
        } catch (IOException e) {
			System.err.println("Impossibile accedere al file");
        }
    }

	public static void menuAccesso(){
		Scanner sc = new Scanner(System.in);
		System.out.println("\n--- Login ---");
		System.out.println("Scegli come vuoi accedere");
		System.out.println();
		System.out.println("1. Registrazione cliente");
		System.out.println("2. Login ");
		sceltaAccesso(sc);
	}

	public static void sceltaAccesso(Scanner sc){
		List <Utente> utenti = Utente.leggiUtentiDaFile();
		int scelta = sc.nextInt();

		switch (scelta) {
			case 1 -> registrazione();
			case 2 -> {
				try {
					utenteLoggato = logIn(utenti);
				}catch(LoginFailedException e){
					System.err.println(e.getMessage());
				}
			}
			case 3 -> {}
			default -> System.err.println("Comando non riconosciuto");
		}
	}
}