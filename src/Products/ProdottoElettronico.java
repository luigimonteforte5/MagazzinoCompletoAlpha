package Products;

public class ProdottoElettronico extends Prodotto {
    private TipoElettronico tipoElettronico;
    private float dimSchermo;

   public ProdottoElettronico(){}

	public ProdottoElettronico(String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id, int quantita, float dimSchermo, TipoElettronico tipoElettronico){
       super(marca, modello, prezzoAcquisto, prezzoVendita, id, quantita);
       this.dimSchermo = dimSchermo;
       this.tipoElettronico = tipoElettronico;
   }

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
    public double calcolaSpesaMedia() {
        return 0;
    }

    @Override
    public String toString() {
        return " Products.Prodotto: " + '\'' +
                "Tipo Elettronico: " + tipoElettronico +  '\'' +
                "Dimensione Schermo: " + dimSchermo +
                "Marca: " + marca + '\'' +
                "Modello: " + modello + '\'' +
                "Descrizione: " + descrizione + '\'' +
                "Prezzo: " + prezzoVendita +
                "ID: " + id;

    }

    public ProdottoElettronicoDTO toDTO(){
        return new ProdottoElettronicoDTO(marca, modello, descrizione, prezzoVendita, id, tipoElettronico, dimSchermo);
    }
}
