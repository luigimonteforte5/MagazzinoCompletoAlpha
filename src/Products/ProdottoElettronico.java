package Products;

public class ProdottoElettronico extends Prodotto {
    private TipoElettronico tipoElettronico;
    private float dimSchermo;

   public ProdottoElettronico( ProductBuilder productBuilder ){
	   super(productBuilder);
       this.tipoElettronico = productBuilder.getTipoElettronico();
       this.dimSchermo = productBuilder.getDimSchermo();
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

    public ProdottoElettronicoUtente toProdottoUtente(){
        return new ProdottoElettronicoUtente(marca, modello, descrizione, prezzoVendita, id, tipoElettronico, dimSchermo);
    }

    public static class ProductBuilder extends AbstractBuilder{

        private TipoElettronico tipoElettronico;
        private float dimSchermo;

        public ProductBuilder( String marca, String modello, double prezzoAcquisto, double prezzoVendita, int id, String tipoElettronico, float dimSchermo  ) {
            super(marca, modello, prezzoAcquisto, prezzoVendita, id);
            this.tipoElettronico = TipoElettronico.valueOf(tipoElettronico);
            this.dimSchermo = dimSchermo;
        }

        public ProductBuilder setTipoElettronico( TipoElettronico tipoElettronico ) {
            this.tipoElettronico = tipoElettronico;
            return this;
        }

        public ProductBuilder setDimSchermo( float dimSchermo ) {
            this.dimSchermo = dimSchermo;
            return this;
        }

        public TipoElettronico getTipoElettronico() {
            return tipoElettronico;
        }

        public float getDimSchermo() {
            return dimSchermo;
        }

        @Override
        public ProdottoElettronico build() {
            return new ProdottoElettronico(this) ;
        }
    }
}
