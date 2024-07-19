package Products;

public abstract class Prodotto {

	protected String marca;
	protected String modello;
	protected String descrizione;
	protected double prezzoAcquisto;
	protected double prezzoVendita;
	protected int id;
	protected int quantitaMagazzino;
	protected int numVendite;

	public Prodotto(){}

	public Prodotto( String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id, int quantitaMagazzino ) {
		this.marca = marca;
		this.modello = modello;
		this.prezzoAcquisto = prezzoAcquisto;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		this.quantitaMagazzino = quantitaMagazzino;
		numVendite = 0;
	}

	public Prodotto( String marca, String modello, String descrizione, double prezzoAcquisto, double prezzoVendita, int id, int quantitaMagazzino ) {
		this.marca = marca;
		this.modello = modello;
		this.descrizione = descrizione;
		this.prezzoAcquisto = prezzoAcquisto;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		this.quantitaMagazzino = quantitaMagazzino;
		numVendite = 0;
	}

	public Prodotto( String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id ) {
		this.marca = marca;
		this.modello = modello;
		this.prezzoAcquisto = prezzoAcquisto;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		numVendite = 0;
	}

	public Prodotto( String marca, String modello, String descrizione, double prezzoAcquisto, double prezzoVendita, int id ) {
		this.marca = marca;
		this.modello = modello;
		this.descrizione = descrizione;
		this.prezzoAcquisto = prezzoAcquisto;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		numVendite = 0;
	}


	public String getMarca() {
		return marca;
	}

	public String getModello() {
		return modello;
	}

	public String getDescrizione() {
		return descrizione;
	}


	public double getPrezzoAcquisto() {
		return prezzoAcquisto;
	}

	public double getPrezzoVendita() {
		return prezzoVendita;
	}

	public int getId() {
		return id;
	}

	public int getQuantitaMagazzino() {
		return quantitaMagazzino;
	}

	public void setQuantitaMagazzino( int quantitaMagazzino ) {
		this.quantitaMagazzino = quantitaMagazzino;
	}

	public void incrementaQuantita() {
		this.quantitaMagazzino++;
	}

	public void diminuisciQuantita() {
		this.quantitaMagazzino--;
	}

	public void setNumVendite( int numVendite ) {
		this.numVendite = numVendite;
	}

	public abstract double calcolaSpesaMedia();

}
