package Utilities;

import Products.ProdottoElettronico;
import Products.ProdottoElettronicoUtente;

public class ProductMapper {

	public static ProdottoElettronicoUtente toProdottoUtente( ProdottoElettronico prd){
		ProdottoElettronicoUtente userPrd = new ProdottoElettronicoUtente();
		userPrd.setMarca(prd.getMarca());
		userPrd.setModello(prd.getModello());
		userPrd.setDescrizione(prd.getDescrizione());
		userPrd.setPrezzoVendita(prd.getPrezzoVendita());
		userPrd.setId(prd.getId());
		userPrd.setTipoElettronico(prd.getTipoElettronico());
		userPrd.setDimSchermo(prd.getDimSchermo());
		return userPrd;
	}
}
