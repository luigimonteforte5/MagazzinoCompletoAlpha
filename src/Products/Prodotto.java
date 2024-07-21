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

	public Prodotto(AbstractBuilder abstractBuilder ) {
		this.marca = abstractBuilder.getMarca();
		this.modello = abstractBuilder.getModello();
		this.descrizione = abstractBuilder.getDescrizione();
		this.prezzoAcquisto = abstractBuilder.getPrezzoAcquisto();
		this.prezzoVendita = abstractBuilder.getPrezzoVendita();
		this.id = abstractBuilder.getId();
		this.quantitaMagazzino = abstractBuilder.getQuantitaMagazzino();
		this.numVendite = abstractBuilder.getNumVendite();
	}

	public Prodotto( String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id, int quantita ) {
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


	public static abstract class AbstractBuilder {
		private String marca;
		private String modello;
		private String descrizione;
		private double prezzoAcquisto;
		private double prezzoVendita;
		private int id;
		private int quantitaMagazzino;
		private int numVendite;

		public AbstractBuilder( String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id ) {
			this.marca = marca;
			this.modello = modello;
			this.prezzoAcquisto = prezzoAcquisto;
			this.prezzoVendita = prezzoVendita;
			this.id = id;
		}

		public AbstractBuilder setModello ( String modello ){
			this.modello = modello;
			return this;
		}

		public AbstractBuilder setDescrizione ( String descrizione ){
			this.descrizione = descrizione;
			return this;
		}
		public AbstractBuilder setPrezzoAcquisto ( double prezzoAcquisto ){
			this.prezzoAcquisto = prezzoAcquisto;
			return this;
		}
		public AbstractBuilder setPrezzoVendita ( double prezzoVendita ){
			this.prezzoVendita = prezzoVendita;
			return this;
		}
		public AbstractBuilder setId ( int id ){
			this.id = id;
			return this;
		}

		public AbstractBuilder setQuantitaMagazzino ( int quantitaMagazzino ){
			this.quantitaMagazzino = quantitaMagazzino;
			return this;
		}

		public AbstractBuilder setNumVendite ( int numVendite ){
			this.numVendite = numVendite;
			return this;
		}

		public String getMarca ( ) {
			return marca;
		}

		public String getModello ( ) {
			return modello;
		}

		public String getDescrizione ( ) {
			return descrizione;
		}

		public double getPrezzoAcquisto ( ) {
			return prezzoAcquisto;
		}

		public double getPrezzoVendita ( ) {
			return prezzoVendita;
		}

		public int getId ( ) {
			return id;
		}

		public int getQuantitaMagazzino ( ) {
			return quantitaMagazzino;
		}

		public int getNumVendite ( ) {
			return numVendite;
		}

		public abstract Prodotto build();

	}
}

