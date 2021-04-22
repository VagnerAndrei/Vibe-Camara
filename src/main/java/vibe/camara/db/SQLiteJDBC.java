package vibe.camara.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteJDBC {

	private static Connection connection = null;

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection("jdbc:sqlite:vibecamara.db");
			connection.createStatement().execute(
					"create table if not exists VISUALIZACOES_PERFIL(PERFIL_ID integer, VISUALIZACOES integer)");
		}
		return connection;
	}

	public static int getVisualizacoes(int idPerfil) {
		try (PreparedStatement psSelect = getConnection()
				.prepareStatement("select VISUALIZACOES from VISUALIZACOES_PERFIL where PERFIL_ID = ?")) {
			psSelect.setInt(1, idPerfil);
			ResultSet resultSet = psSelect.executeQuery();

			if (resultSet.next())
				return resultSet.getInt("VISUALIZACOES");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return 0;
	}

	public static void updateVisualizacao(int id) {
		try (PreparedStatement psSelect = getConnection()
				.prepareStatement("select VISUALIZACOES from VISUALIZACOES_PERFIL where PERFIL_ID = ?")) {

			psSelect.setInt(1, id);
			ResultSet resultSet = psSelect.executeQuery();

			if (resultSet.next()) {

				try (PreparedStatement update = getConnection().prepareStatement(
						"update VISUALIZACOES_PERFIL set VISUALIZACOES = VISUALIZACOES + ? where PERFIL_ID = ?")) {

					update.setInt(1, 1);
					update.setInt(2, id);

					update.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}

			}

			else {
				try (PreparedStatement psInsert = getConnection()
						.prepareStatement("insert into VISUALIZACOES_PERFIL(PERFIL_ID, VISUALIZACOES) values (?,?)")) {

					psInsert.setInt(1, id);
					psInsert.setInt(2, 1);

					psInsert.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

}