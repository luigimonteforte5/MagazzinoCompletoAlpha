package Products;

public class ProdottoElettronicoUtente {

	private String marca;
	private String modello;
	private String descrizione;
	private double prezzoVendita;
	private int id;
	private int numVendite;
	private TipoElettronico tipoElettronico;
	private float dimSchermo;
	private int quantitaCarrello;

	public ProdottoElettronicoUtente( String marca, String modello, String descrizione, double prezzoVendita, int id, TipoElettronico tipoElettronico, float dimSchermo ) {
		this.marca = marca;
		this.modello = modello;
		this.descrizione = descrizione;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		this.tipoElettronico = tipoElettronico;
		this.dimSchermo = dimSchermo;
		quantitaCarrello = 1;
	}

	public ProdottoElettronicoUtente( String marca, String modello, double prezzoVendita, int id, TipoElettronico tipoElettronico, float dimSchermo ) {
		this.marca = marca;
		this.modello = modello;
		this.prezzoVendita = prezzoVendita;
		this.id = id;
		this.tipoElettronico = tipoElettronico;
		this.dimSchermo = dimSchermo;
	}

	public ProdottoElettronicoUtente() {}

	public TipoElettronico getTipoElettronico() {
			return tipoElettronico;
	}

	public void setTipoElettronico( TipoElettronico tipoElettronico) {
		this.tipoElettronico = tipoElettronico;
	}

	public float getDimSchermo() {
		return dimSchermo;
	}

	public void setDimSchermo(float dimSchermo) {
		this.dimSchermo = dimSchermo;
	}


	@Override
	public String toString() {
		return " Products.Prodotto: " + '\'' +
				" Tipo Elettronico: " + tipoElettronico +  '\'' +
				" Dimensione Schermo: " + dimSchermo +
				" Marca: " + marca + '\'' +
				" Modello: " + modello + '\'' +
				" Descrizione: " + descrizione + '\'' +
				" Prezzo: " + prezzoVendita +
				" ID: " + id +
				" Quantità: " + quantitaCarrello;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca( String marca ) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello( String modello ) {
		this.modello = modello;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione( String descrizione ) {
		this.descrizione = descrizione;
	}

	public double getPrezzoVendita() {
		return prezzoVendita;
	}

	public void setPrezzoVendita( double prezzoVendita ) {
		this.prezzoVendita = prezzoVendita;
	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public int getNumVendite() {
		return numVendite;
	}

	public void setNumVendite( int numVendite ) {
		this.numVendite = numVendite;
	}

	public int getQuantitaCarrello() {
		return quantitaCarrello;
	}

	public void setQuantitaCarrello( int quantitaCarrello ) {
		this.quantitaCarrello = quantitaCarrello;
	}
}

