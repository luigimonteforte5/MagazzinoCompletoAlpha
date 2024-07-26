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

	public Prodotto(AbstractBuilder<?> abstractBuilder ) {
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

	public Prodotto setDescrizione( String descrizione ) {
		this.descrizione = descrizione;
		return this;
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


	public static abstract class AbstractBuilder<T extends AbstractBuilder<T>>{
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
		protected abstract T self();

		public T setModello ( String modello ){
			this.modello = modello;
			return self();
		}

		public T setDescrizione ( String descrizione ){
			this.descrizione = descrizione;
			return self();
		}
		public T setPrezzoAcquisto ( double prezzoAcquisto ){
			this.prezzoAcquisto = prezzoAcquisto;
			return self();
		}
		public T setPrezzoVendita ( double prezzoVendita ){
			this.prezzoVendita = prezzoVendita;
			return self();
		}
		public T setId ( int id ){
			this.id = id;
			return self();
		}

		public T setQuantitaMagazzino ( int quantitaMagazzino ){
			this.quantitaMagazzino = quantitaMagazzino;
			return self();
		}

		public T setNumVendite ( int numVendite ){
			this.numVendite = numVendite;
			return self();
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

