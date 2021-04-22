package vibe.camara.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import vibe.camara.db.SQLiteJDBC;
import vibe.camara.model.AgrupamentoDespesa;
import vibe.camara.model.Despesa;
import vibe.camara.model.Parlamentar;
import vibe.camara.service.ServiceClient;

@Named
@SessionScoped
public class ParlamentarMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Parlamentar parlamentar;
	private List<AgrupamentoDespesa> agrupamentos = new ArrayList<AgrupamentoDespesa>();
	private ServiceClient clientRS;
	
	private List<Despesa> detalhamento;

	public void init(int id) {

		SQLiteJDBC.updateVisualizacao(id);

		clientRS = new ServiceClient();

		setParlamentar(clientRS.consultarParlamentar(id));

	}

	public ParlamentarMB() {

	}

	public Parlamentar getParlamentar() {
		return parlamentar;
	}

	public void setParlamentar(Parlamentar parlamentar) {
		agrupamentos.clear();
		Calendar calendar = new GregorianCalendar();
		if (parlamentar.getDespesasUltimoMes().size() != 0) {
			AgrupamentoDespesa despesasUltimoMes = new AgrupamentoDespesa(calendar.get(Calendar.MONTH) - 1);
			for (Despesa despesa : parlamentar.getDespesasUltimoMes()) {
				despesasUltimoMes.setValorLiquido(despesa.getValorLiquido());
			}
			agrupamentos.add(despesasUltimoMes);
		}
		if (parlamentar.getDespesasPenultimoMes().size() != 0) {
			AgrupamentoDespesa despesasPenultimoMes = new AgrupamentoDespesa(calendar.get(Calendar.MONTH) - 2);
			for (Despesa despesa : parlamentar.getDespesasPenultimoMes()) {
				despesasPenultimoMes.setValorLiquido(despesa.getValorLiquido());
			}
			agrupamentos.add(despesasPenultimoMes);
		}
		this.parlamentar = parlamentar;
	}

	public List<AgrupamentoDespesa> getAgrupamentos() {
		return agrupamentos;
	}

	public void setAgrupamentos(List<AgrupamentoDespesa> agrupamentos) {
		this.agrupamentos = agrupamentos;
	}

	public String getValorTotalLiquido() {
		Integer mes = Integer
				.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
		if (mes == agrupamentos.get(0).getMes())
			return agrupamentos.get(0).getValorLiquidoFormatado();
		else
			return agrupamentos.get(1).getValorLiquidoFormatado();
	}

	public String getMesDetalhamento() {
		Integer mes = Integer
				.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
		if (mes == agrupamentos.get(0).getMes())
			return agrupamentos.get(0).getDescricaoMes();
		else
			return agrupamentos.get(1).getDescricaoMes();
	}

	public String detalhar() {
		return "despesas";
	}
	
	public void detalharListener(ActionEvent event) {
		Integer mes = Integer
				.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes"));
		if (mes == new GregorianCalendar().get(Calendar.MONTH) - 1)
			detalhamento = getParlamentar().getDespesasUltimoMes();
		else
			detalhamento = getParlamentar().getDespesasPenultimoMes();

		Comparator<Despesa> dataDocumento = new Comparator<Despesa>() {
			public int compare(Despesa d1, Despesa d2) {
				return Long.valueOf(d2.getDataDocumento().getTime()).compareTo(d1.getDataDocumento().getTime());
			}
		};
		Collections.sort(detalhamento, dataDocumento);
	}

	public List<Despesa> getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(List<Despesa> detalhamento) {
		this.detalhamento = detalhamento;
	}
	
}