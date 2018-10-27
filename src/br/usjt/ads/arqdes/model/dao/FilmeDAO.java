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

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;

@Repository
public class FilmeDAO {
	Connection conn;

	@Autowired
	public FilmeDAO(DataSource dataSource) throws IOException {
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	public int inserirFilme(Filme filme) throws IOException {
		int id = -1;
		String sql = "insert into Filme (titulo, descricao, diretor, posterpath, "
				+ "popularidade, data_lancamento, id_genero) values (?,?,?,?,?,?,?)";

		try (PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, filme.getTitulo());
			pst.setString(2, filme.getDescricao());
			pst.setString(3, filme.getDiretor());
			pst.setString(4, filme.getPosterPath());
			pst.setDouble(5, filme.getPopularidade());
			if(filme.getDataLancamento() != null) {
				pst.setDate(6, new java.sql.Date(filme.getDataLancamento().getTime()));
			} else {
				pst.setDate(6,  null);
			}
			pst.setInt(7, filme.getGenero().getId());			
			pst.execute();
			
			//obter o id criado
			String query = "select LAST_INSERT_ID()";
			try(PreparedStatement pst1 = conn.prepareStatement(query);
				ResultSet rs = pst1.executeQuery();){

				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return id;
	}

	public Filme buscarFilme(int id) throws IOException {
		Filme filme = null;
		String sql = "select f.id as id_filme, f.titulo, f.descricao, f.diretor, f.posterpath, f.popularidade, f.data_lancamento, g.id as id_genero, g.nome from Filme f inner join Genero g on f.id_genero = g.id where f.id = ?";

		try (PreparedStatement stm = conn.prepareStatement(sql)) {
			stm.setInt(1, id);

			try (ResultSet rs = stm.executeQuery()) {
				if (rs.next()) {
					// Set genero
					Genero genero = new Genero();
					genero.setId(rs.getInt("id_genero"));
					genero.setNome(rs.getString("nome"));

					// Set Filme
					filme = new Filme();
					filme.setId(rs.getInt("id_filme"));
					filme.setTitulo(rs.getString("titulo"));
					filme.setDescricao(rs.getString("descricao"));
					filme.setDiretor(rs.getString("diretor"));
					filme.setPosterPath(rs.getString("posterpath"));
					filme.setPopularidade(rs.getInt("popularidade"));
					filme.setDataLancamento(rs.getDate("data_lancamento"));
					filme.setGenero(genero);
				}
			}
			
		}	catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		return filme;		
	}

	public ArrayList<Filme> listarFilmes(String chave) throws IOException {
		ArrayList<Filme> lista = new ArrayList<>();
		String sql = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f. popularidade, f.data_lancamento, f.id_genero, g.nome " + "from filme f, genero g "
				+ "where f.id_genero = g.id and upper(f.titulo) like ?";
		try (PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, "%" + chave.toUpperCase() + "%");

			try (ResultSet rs = pst.executeQuery();) {

				Filme filme;
				Genero genero;
				while (rs.next()) {
					filme = new Filme();
					filme.setId(rs.getInt("f.id"));
					filme.setTitulo(rs.getString("f.titulo"));
					filme.setDescricao(rs.getString("f.descricao"));
					filme.setDiretor(rs.getString("f.diretor"));
					filme.setPosterPath(rs.getString("f.posterpath"));
					filme.setPopularidade(rs.getDouble("f.popularidade"));
					filme.setDataLancamento(rs.getDate("f.data_lancamento"));
					genero = new Genero();
					genero.setId(rs.getInt("f.id_genero"));
					genero.setNome(rs.getString("g.nome"));
					filme.setGenero(genero);
					lista.add(filme);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		return lista;
	}
	
	public ArrayList<Filme> listarFilmes() throws IOException {
		ArrayList<Filme> filmes = new ArrayList<>();
		String sql = "select f.id as id_filme, f.titulo, f.descricao, f.diretor, f.posterpath, f.popularidade, f.data_lancamento, g.id as id_genero, g.nome from Filme f inner join Genero g on f.id_genero = g.id order by f.titulo";

		try (PreparedStatement stm = conn.prepareStatement(sql);
				ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {
				// Set genero
				Genero genero = new Genero();
				genero.setId(rs.getInt("id_genero"));
				genero.setNome(rs.getString("nome"));

				// Set Filme
				Filme filme = new Filme();
				filme.setId(rs.getInt("id_filme"));
				filme.setTitulo(rs.getString("titulo"));
				filme.setDescricao(rs.getString("descricao"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setPosterPath(rs.getString("posterpath"));
				filme.setPopularidade(rs.getInt("popularidade"));
				filme.setDataLancamento(rs.getDate("data_lancamento"));
				filme.setGenero(genero);
				lista.add(filme);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}				
		return lista;
	}

	public void atualizarFilme(Filme filme) throws IOException {
		String sql = "update Filme set titulo=?, descricao=?, diretor=?, posterpath=?, popularidade=?, data_lancamento=?, id_genero=? where id= ?";

		try (PreparedStatement stm = conn.prepareStatement(sql)) {
			stm.setString(1, filme.getTitulo());
			stm.setString(2, filme.getDescricao());
			stm.setString(3, filme.getDiretor());
			stm.setString(4, filme.getPosterPath());
			stm.setDouble(5, filme.getPopularidade());
			if (filme.getDataLancamento() != null) {
				stm.setDate(6, new java.sql.Date(filme.getDataLancamento().getTime()));
			} else {
				pst.setDate(6,  null);
			}
			pst.setInt(7, filme.getGenero().getId());	
			pst.setInt(8, filme.getId());
			id = filme.getId();
			pst.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return id;
	}

	public void excluir(int id) throws IOException {
		String sql = "delete from Filme where id = ?";

		try (PreparedStatement stm = conn.prepareStatement(sql)) {
			stm.setInt(1, id);

			stm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}				
		return lista;
	}

}
