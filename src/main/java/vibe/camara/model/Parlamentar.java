package vibe.camara.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parlamentar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String partido;
	private String uf;
	private Integer visualizacoes;

	private String nomeCivil;
	private Date dataNascimento;
	private Character sexo;
	private String urlFoto;

	private List<Despesa> despesasUltimoMes;
	private List<Despesa> despesasPenultimoMes;

	public Parlamentar() {
		this.despesasUltimoMes = new ArrayList<Despesa>();
		this.despesasPenultimoMes = new ArrayList<Despesa>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNomeCivil() {
		return nomeCivil;
	}

	public void setNomeCivil(String nomeCivil) {
		this.nomeCivil = nomeCivil;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDataNascimentoFormatada() {
		return dataNascimento != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento) : null;
	}

	public Character getSexo() {
		return sexo;
	}

	public String getSexoDescricao() {
		switch (sexo) {
		case 'M':
			return "Masculino";
		case 'F':
			return "Feminino";
		default:
			return "";
		}
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public Integer getVisualizacoes() {
		return visualizacoes;
	}

	public void setVisualizacoes(Integer visualizacoes) {
		this.visualizacoes = visualizacoes;
	}

	public List<Despesa> getDespesasUltimoMes() {
		return despesasUltimoMes;
	}

	public void setDespesasUltimoMes(List<Despesa> despesasUltimoMes) {
		this.despesasUltimoMes = despesasUltimoMes;
	}

	public List<Despesa> getDespesasPenultimoMes() {
		return despesasPenultimoMes;
	}

	public void setDespesasPenultimoMes(List<Despesa> despesasPenultimoMes) {
		this.despesasPenultimoMes = despesasPenultimoMes;
	}
}
