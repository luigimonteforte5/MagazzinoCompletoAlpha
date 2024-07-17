package Products;

public abstract class Prodotto {

	protected String marca;
	protected String modello;
	protected String descrizione;
	protected double prezzoAcquisto;
	protected double prezzoVendita;
	protected int id;
	protected int quantita;
	protected int numVendite;

	public Prodotto(){}

	public Prodotto( String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id, int quantita ) {
		this.marca = marca;
		this.modello = modello;
		this.prezzoAcquisto = prezzoAcquisto;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		this.quantita = quantita;
		numVendite = 0;
	}

	public Prodotto( String marca, String modello, String descrizione, double prezzoAcquisto, double prezzoVendita, int id, int quantita ) {
		this.marca = marca;
		this.modello = modello;
		this.descrizione = descrizione;
		this.prezzoAcquisto = prezzoAcquisto;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		this.quantita = quantita;
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

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita( int quantita ) {
		this.quantita = quantita;
	}

	public void incrementaQuantita() {
		this.quantita ++;
	}

	public void diminuisciQuantita() {
		this.quantita --;
	}

	public void setNumVendite( int numVendite ) {
		this.numVendite = numVendite;
	}

	public abstract double calcolaSpesaMedia();

}
