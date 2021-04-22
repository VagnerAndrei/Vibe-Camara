package vibe.camara.model;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;

public class AgrupamentoDespesa {

	public AgrupamentoDespesa(int mes) {
		DateFormatSymbols fd = new DateFormatSymbols();
		String descricaoMes = fd.getMonths()[mes];
		descricaoMes = descricaoMes.substring(0, 1).toUpperCase() + descricaoMes.substring(1);
		this.descricaoMes = descricaoMes;
		this.mes = mes;
		this.valorLiquido = 0F;
	}

	private String descricaoMes;
	private Integer mes;
	private Float valorLiquido;

	public String getDescricaoMes() {
		return descricaoMes;
	}

	public void setDescricaoMes(String descricaoMes) {
		this.descricaoMes = descricaoMes;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Float getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Float valorLiquido) {
		this.valorLiquido += valorLiquido;
	}
	
	public String getValorLiquidoFormatado() {
		return NumberFormat.getCurrencyInstance().format(getValorLiquido());
	}

}