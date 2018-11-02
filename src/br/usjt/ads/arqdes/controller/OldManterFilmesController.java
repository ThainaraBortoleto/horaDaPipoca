package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

/**
 * Servlet implementation class ManterFilmesController
 */
@WebServlet("/manterfilmes.do")
public class OldManterFilmesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String acao = request.getParameter("acao");
		RequestDispatcher dispatcher;
		FilmeService fService;
		GeneroService gService;
		Filme filme;
		Genero genero;
		HttpSession session;
		int idFilme;
		List<Genero> generos;
		List<Filme> filmes;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String diretor = request.getParameter("diretor");
		String posterPath = request.getParameter("posterPath");
		String popularidade = request.getParameter("popularidade") == null
				|| request.getParameter("popularidade").length() == 0 ? "0" : request.getParameter("popularidade");
		String dataLancamento = request.getParameter("dataLancamento") == null
				|| request.getParameter("dataLancamento").length() == 0 ? "" : request.getParameter("dataLancamento");
		String idGenero = request.getParameter("genero.id");
		String chave = request.getParameter("data[search]");

		switch (acao) {
		case "novo":
			gService = new GeneroService();
			generos = gService.listarGeneros();
			session = request.getSession();
			session.setAttribute("generos", generos);
			dispatcher = request.getRequestDispatcher("CriarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "criar":

			fService = new FilmeService();
			filme = new Filme();
			filme.setTitulo(titulo);
			filme.setDescricao(descricao);
			filme.setDiretor(diretor);

			gService = new GeneroService();
			genero = new Genero();
			genero.setId(Integer.parseInt(idGenero));
			genero.setNome(gService.buscarGenero(genero.getId()).getNome());
			filme.setGenero(genero);

			try {
				filme.setDataLancamento(formatter.parse(dataLancamento));
			} catch (ParseException e) {
				e.printStackTrace();
				filme.setDataLancamento(null);
			}

			filme.setPopularidade(Double.parseDouble(popularidade));
			filme.setPosterPath(posterPath);

			filme = fService.inserirFilme(filme);

			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "atualizar":
			idFilme = Integer.parseInt(request.getParameter("id"));
			fService = new FilmeService();
			filme = new Filme();

			Filme f2 = new Filme();
			f2 = fService.buscarFilme(idFilme);

			// ID FILME
			filme.setId(idFilme);

			// If the request parameter is empty, keep the same value
			String fTitulo = fService.isEmpty(titulo) ? f2.getTitulo() : titulo;
			filme.setTitulo(fTitulo);

			String fDescricao = fService.isEmpty(descricao) ? f2.getDescricao() : descricao;
			filme.setDescricao(fDescricao);

			String fDiretor = fService.isEmpty(diretor) ? f2.getDiretor() : diretor;
			filme.setDiretor(fDiretor);

			gService = new GeneroService();
			genero = new Genero();
			genero.setId(Integer.parseInt(idGenero));
			genero.setNome(gService.buscarGenero(genero.getId()).getNome());
			filme.setGenero(genero);

			try {
				String fDataLancamento = fService.isEmpty(dataLancamento) ? f2.getDataLancamento().toString()
						: dataLancamento;
				filme.setDataLancamento(formatter.parse(fDataLancamento));
			} catch (ParseException e) {
				e.printStackTrace();
				filme.setDataLancamento(null);
			}

			String fPopularidade = fService.isEmpty(popularidade) ? Double.toString(f2.getPopularidade())
					: popularidade;

			filme.setPopularidade(Double.parseDouble(fPopularidade));

			String fPosterPath = fService.isEmpty(posterPath) ? f2.getPosterPath() : posterPath;
			filme.setPosterPath(fPosterPath);

			fService.atualizarFilme(filme);
			request.setAttribute("filme", filme);

			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "reiniciar":
			session = request.getSession();
			session.setAttribute("lista", null);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		case "listar":
			session = request.getSession();
			fService = new FilmeService();
			List<Filme> lista;
			if (chave != null && chave.length() > 0) {
				lista = fService.listarFilmes(chave);
			} else {
				lista = fService.listarFilmes();
			}
			session.setAttribute("lista", lista);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		case "editar":
			idFilme = Integer.parseInt(request.getParameter("id"));

			fService = new FilmeService();
			filme = fService.buscarFilme(idFilme);

			gService = new GeneroService();
			generos = gService.listarGeneros();

			session = request.getSession();
			session.setAttribute("generos", generos);

			request.setAttribute("filme", filme);
			dispatcher = request.getRequestDispatcher("AlterarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "visualizar":
			idFilme = Integer.parseInt(request.getParameter("id"));

			fService = new FilmeService();
			filme = fService.buscarFilme(idFilme);

			request.setAttribute("filme", filme);
			dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
			dispatcher.forward(request, response);
			break;
		case "excluir":
			session = request.getSession();
			idFilme = Integer.parseInt(request.getParameter("id"));

			fService = new FilmeService();
			fService.excluirFilme(idFilme);

			filmes = fService.listarFilmes();

			session.setAttribute("lista", filmes);
			dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
			dispatcher.forward(request, response);
			break;
		case "listarGenero":
			session = request.getSession();
			fService = new FilmeService();
			gService = new GeneroService();
			generos = gService.listarGeneros();
			filmes = fService.listarFilmes();
			ArrayList<String> genFlista = new ArrayList<>();
			for (int i = 0; i < filmes.size(); i++) {
				if (i == 0) {
					genFlista.add(filmes.get(i).getGenero().getNome());
				} else {
					if (!filmes.get(i).getGenero().getNome().equals(filmes.get(i - 1).getGenero().getNome())) {
						genFlista.add(filmes.get(i).getGenero().getNome());
					}
				}
				System.out.println(i + " " + filmes.get(i));
			}

			session.setAttribute("glista", generos);
			session.setAttribute("flista", filmes);
			session.setAttribute("genLista", genFlista);
			dispatcher = request.getRequestDispatcher("ListarFilmesGenero.jsp");
			dispatcher.forward(request, response);
			break;
		case "listarPopularidade":
			session = request.getSession();
			fService = new FilmeService();
			filmes = fService.listarFilmes();
			ArrayList<Double> popularidades = new ArrayList<>();
			for (Filme film : filmes) {
				popularidades.add(film.getPopularidade());
			}

			Set<Double> hs = new HashSet<>();
			hs.addAll(popularidades);
			popularidades.clear();
			popularidades.addAll(hs);

			System.out.println(popularidades);
			session.setAttribute("filmes", filmes);
			session.setAttribute("popularidades", popularidades);
			dispatcher = request.getRequestDispatcher("ListarFilmesPopularidade.jsp");
			dispatcher.forward(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected ArrayList<ArrayList<Filme>> getLancamentos(ArrayList<Filme> lista) {
		ArrayList<ArrayList<Filme>> l = new ArrayList<ArrayList<Filme>>();
		l.add(new ArrayList<Filme>());
		Calendar cal = Calendar.getInstance();
		int mesAtual, mesFilme, anoFilme, anoAtual, contador = 0;
		mesAtual = cal.get(Calendar.MONTH);
		anoAtual = cal.get(Calendar.YEAR);
		ArrayList<Integer> anos = new ArrayList<Integer>();

		for (Filme f : lista) {
			cal.setTime(f.getDataLancamento());
			mesFilme = cal.get(Calendar.MONTH);
			anoFilme = cal.get(Calendar.YEAR);
			if (mesAtual == mesFilme && anoFilme == anoAtual) {
				l.get(contador).add(f);
			}

		}

		contador++;

		for (Filme f : lista) {
			cal.setTime(f.getDataLancamento());
			anoFilme = cal.get(Calendar.YEAR);

			if (!anos.contains(anoFilme)) {
				anos.add(anoFilme);
				l.add(new ArrayList<Filme>());
				contador++;
			}

			l.get(contador - 1).add(f);

		}

		return l;
	}

}
