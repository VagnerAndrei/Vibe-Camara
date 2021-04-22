package vibe.camara.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Despesa implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tipoDespesa;
	private String tipoDocumento;
	private Date dataDocumento;
	private Float valorLiquido;

	public String getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	
	public String getDataDocumentoFormatada() {
		return dataDocumento != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataDocumento) : null;
	}

	public Float getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Float valorLiquido) {
		this.valorLiquido = valorLiquido;
	}
	
	public String getValorLiquidoFormatado() {
		return NumberFormat.getCurrencyInstance().format(getValorLiquido());
	}

}
