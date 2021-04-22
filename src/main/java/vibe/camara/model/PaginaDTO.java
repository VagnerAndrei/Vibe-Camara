package vibe.camara.model;

import java.io.Serializable;
import java.util.List;

public class PaginaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer total;
	private List<Parlamentar> lista;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<Parlamentar> getLista() {
		return lista;
	}

	public void setLista(List<Parlamentar> lista) {
		this.lista = lista;
	}

}
