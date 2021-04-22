package vibe.camara.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vibe.camara.model.Despesa;
import vibe.camara.model.PaginaDTO;
import vibe.camara.model.Parlamentar;

public class ServiceClient {

	private Client client;

	private WebTarget webTarget;

	private final String URL_SERVICE = "https://dadosabertos.camara.leg.br/api/v2/";

	private final String PATH = "deputados";

	public ServiceClient() {
		this.client = ClientBuilder.newClient();

		this.webTarget = client.target(URL_SERVICE).path(PATH);
	}

	public Parlamentar consultarParlamentar(int idParlamentar) {
		Response response = this.webTarget.path(String.valueOf(idParlamentar)).request(MediaType.APPLICATION_JSON)
				.get();

		JsonObject json = response.readEntity(JsonObject.class);

		JsonObject dados = (JsonObject) json.get("dados");

		Parlamentar parlamentar = new Parlamentar();
		parlamentar.setNomeCivil(dados.getString("nomeCivil"));
		parlamentar.setSexo(dados.getString("sexo").charAt(0));
		try {
			parlamentar.setDataNascimento(new SimpleDateFormat("yyyy-MM-dd").parse(dados.getString("dataNascimento")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JsonObject ultimoStatus = (JsonObject) dados.get("ultimoStatus");
		parlamentar.setNome(ultimoStatus.getString("nome"));
		parlamentar.setPartido(ultimoStatus.getString("siglaPartido"));
		parlamentar.setUrlFoto(ultimoStatus.getString("urlFoto"));

		response = this.webTarget.path(String.valueOf(idParlamentar)).path("despesas")
				.request(MediaType.APPLICATION_JSON).get();

		json = response.readEntity(JsonObject.class);

		JsonArray despesas = json.get("dados").asJsonArray();

		JsonObject item;
		Despesa despesa;
		Calendar calendar = new GregorianCalendar();
		for (int i = 0; i < despesas.size(); i++) {
			item = (JsonObject) despesas.get(i);
			if (item.getInt("ano") == calendar.get(Calendar.YEAR)) {
				int mes = item.getInt("mes");
				if (mes == calendar.get(Calendar.MONTH) || mes == calendar.get(Calendar.MONTH) - 1) {
					despesa = new Despesa();
					despesa.setTipoDespesa(item.getString("tipoDespesa"));
					despesa.setTipoDocumento(item.getString("tipoDocumento"));
					try {
						despesa.setDataDocumento(
								new SimpleDateFormat("yyyy-MM-dd").parse(item.getString("dataDocumento")));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					despesa.setValorLiquido((float) item.getJsonNumber("valorLiquido").doubleValue());

					if (mes == calendar.get(Calendar.MONTH))
						parlamentar.getDespesasUltimoMes().add(despesa);
					else
						parlamentar.getDespesasPenultimoMes().add(despesa);
				}
			}
		}

		return parlamentar;
	}

	public PaginaDTO consultarParlamentares(int numeroPagina) {

		final Response response = this.webTarget.queryParam("itens", "5").queryParam("pagina", numeroPagina)
				.request(MediaType.APPLICATION_JSON).get();

		JsonObject json = response.readEntity(JsonObject.class);
		JsonArray dados = json.get("dados").asJsonArray();
		JsonArray links = json.get("links").asJsonArray();

		// OBTENDO O TOTAL DA LISTA
		PaginaDTO pagina = new PaginaDTO();
		for (int i = 0; i < links.size(); i++) {
			JsonObject item = (JsonObject) links.get(i);
			if (item.getString("rel").equals("last")) {
				pagina.setTotal(Integer.parseInt(item.getString("href").substring(
						item.getString("href").indexOf("pagina=") + 7, item.getString("href").indexOf("&"))));
				break;
			}
		}

		Parlamentar parlamentar;
		List<Parlamentar> parlamentares = new ArrayList<Parlamentar>();

		// OBTENDO OS DADOS DA LISTA
		for (int i = 0; i < dados.size(); i++) {
			JsonObject item = (JsonObject) dados.get(i);
			parlamentar = new Parlamentar();
			parlamentar.setId(item.getInt("id"));
			parlamentar.setNome(item.getString("nome"));
			parlamentar.setPartido(item.getString("siglaPartido"));
			parlamentar.setUf(item.getString("siglaUf"));
			parlamentares.add(parlamentar);
		}

		pagina.setLista(parlamentares);
		return pagina;
	}

}
