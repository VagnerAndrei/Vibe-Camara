package vibe.camara.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import vibe.camara.db.SQLiteJDBC;
import vibe.camara.model.PaginaDTO;
import vibe.camara.model.Parlamentar;
import vibe.camara.service.ServiceClient;

@Named
@ViewScoped
public class ParlamentaresMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Parlamentar> parlamentares = new ArrayList<Parlamentar>();

	private Integer totalPaginas;
	private Integer paginaAtual;
	private ServiceClient clientRS;

	@Inject
	private ParlamentarMB parlamentarMB;

	public ParlamentaresMB() {

		clientRS = new ServiceClient();

		this.paginaAtual = 1;

		PaginaDTO pagina = clientRS.consultarParlamentares(this.paginaAtual);

		atualizarVisualizacoes(pagina.getLista());

		this.parlamentares.addAll(pagina.getLista());
		this.totalPaginas = pagina.getTotal();
	}

	private void atualizarVisualizacoes(List<Parlamentar> lista) {
		for (Parlamentar parlamentar : lista) {
			parlamentar.setVisualizacoes(SQLiteJDBC.getVisualizacoes(parlamentar.getId()));
		}
	}

	public void pagina(int numero) {
		if (numero > 0 && numero <= totalPaginas) {
			PaginaDTO pagina = clientRS.consultarParlamentares(numero);
			this.parlamentares.clear();
			atualizarVisualizacoes(pagina.getLista());
			this.parlamentares.addAll(pagina.getLista());
			this.totalPaginas = pagina.getTotal();
			this.paginaAtual = numero;
		}
	}

	public void paginaAtualChange(ValueChangeEvent event) {
		try {
			int newValue = (int) event.getNewValue();
			if (newValue < 0 || newValue > totalPaginas)
				throw new Exception();
			this.paginaAtual = newValue;
			pagina(paginaAtual);
		} catch (Exception e) {
			this.paginaAtual = (Integer) event.getOldValue();
		}
	}

	public void proximaPagina(ActionEvent event) {
		this.pagina(this.paginaAtual + 1);
	}

	public void paginaAnterior(ActionEvent event) {
		this.pagina(this.paginaAtual - 1);
	}

	public void primeiraPagina(ActionEvent event) {
		System.out.println("primeira pagina");
		this.pagina(1);
	}

	public void ultimaPagina(ActionEvent event) {
		this.pagina(this.totalPaginas);
	}

	public String detalhar() {
		return "success";
	}

	public void detalharListener(ActionEvent event) {
		parlamentarMB.init(Integer
				.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
	}

	public List<Parlamentar> getParlamentares() {
		return parlamentares;
	}

	public void setParlamentares(List<Parlamentar> parlamentares) {
		this.parlamentares = parlamentares;
	}

	public Integer getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public Integer getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

}
