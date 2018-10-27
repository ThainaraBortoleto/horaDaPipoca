package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.usjt.ads.arqdes.model.entity.Genero;

@Repository
public class GeneroDAO {
	Connection conn;
	
	@Autowired
	public GeneroDAO(DataSource dataSource) throws IOException {
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	public Genero buscarGenero(int id) throws IOException {
		Genero genero = null;
		String sql = "select id, nome from genero where id=?";

		try (PreparedStatement pst = conn.prepareStatement(sql);) {

			pst.setInt(1, id);
			try (ResultSet rs = pst.executeQuery();) {

				if (rs.next()) {
					genero = new Genero();
					genero.setId(id);
					genero.setNome(rs.getString("nome"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return genero;
	}

	public ArrayList<Genero> listarGeneros() throws IOException {
		ArrayList<Genero> generos = new ArrayList<>();
		String sql = "select id, nome from genero order by nome";

		try (PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				Genero genero = new Genero();
				genero.setId(rs.getInt("id"));
				genero.setNome(rs.getString("nome"));
				generos.add(genero);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return generos;
	}
	
	public ArrayList<Genero> buscarGenerosFilmes() throws IOException {
		ArrayList<Genero> generos = new ArrayList<>();
		String sql = "select distinct g.id, g.nome, f.id_genero from genero g, filme f where g.id = f.id_genero;";

		try (PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				Genero genero = new Genero();
				genero.setId(rs.getInt("g.id"));
				genero.setNome(rs.getString("g.nome"));
				generos.add(genero);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return generos;
	}
}
