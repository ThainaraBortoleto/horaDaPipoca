package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

@Controller
public class ManterFilmesController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;

	@RequestMapping("/")
	public String inicio() {
		return "index";
	}

	@RequestMapping("/inicio")
	public String inicio1() {
		return "index";
	}

	@RequestMapping("/listar_filmes")
	public String listarFilmes(HttpSession session) {
		session.setAttribute("lista", null);
		return "ListarFilmes";
	}

	@RequestMapping("/novo_filme")
	public String novoFilme(HttpSession session) {
		try {
			ArrayList<Genero> generos = gService.listarGeneros();
			session.setAttribute("generos", generos);
			return "CriarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping("/inserir_filme")
	public String inserirFilme(Filme filme, Model model) {
		try {
			Genero genero = gService.buscarGenero(filme.getGenero().getId());
			filme.setGenero(genero);
			model.addAttribute("filme", filme);
			fService.inserirFilme(filme);
			return "VisualizarFilme";

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping("/visualizar_filme")
	public String visualizarFilme(HttpSession session, @RequestParam String id) {
		try {
			Filme verFilme = fService.buscarFilme(Integer.parseInt(id));
			session.setAttribute("filme", verFilme);
			return "VisualizarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "ListarFilmes";
	}

	@RequestMapping("/excluir_filme")
	public String excluirFilme(Model model, @RequestParam String id) {
		try {
			fService.excluirFilme(Integer.parseInt(id));
			model.addAttribute("chave", "");
			return "redirect:buscar_filmes";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Erro";
	}

	@RequestMapping("/editar_filme")
	public String editarFilme(Model model, @RequestParam String id) {
		try {
			ArrayList<Genero> generos = gService.listarGeneros();
			model.addAttribute("generos", generos);
			model.addAttribute("filme", fService.buscarFilme(Integer.parseInt(id)));
			return "AlterarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Erro";
	}

	@RequestMapping("/buscar_filmes")
	public String buscarFilmes(HttpSession session, @RequestParam String chave) {
		try {
			ArrayList<Filme> lista;

			if (chave != null && chave.length() > 0)
				lista = fService.listarFilmes(chave);
			else
				lista = fService.listarFilmes();

			session.setAttribute("lista", lista);
			return "ListarFilmes";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}

	@RequestMapping("/listar_genero")
	public String listarGenero(Model model) {
		try {
			ArrayList<Genero> generos = gService.listarGeneros();
			ArrayList<Filme> filmes = fService.listarFilmes();
			ArrayList<String> genFlista = new ArrayList<>();
			Set<String> hs = new HashSet<>();
			for (Filme film : filmes) {
				genFlista.add(film.getGenero().getNome());
			}

			hs.addAll(genFlista);
			genFlista.clear();
			genFlista.addAll(hs);

			model.addAttribute("glista", generos);
			model.addAttribute("genLista", genFlista);
			model.addAttribute("flista", filmes);
			return "ListarFilmesGenero";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}

	@RequestMapping("/salvar_filme")
	public String salvarFilme(Filme filme, Model model) {
		System.out.println(filme);
		try {
			Genero genero = gService.buscarGenero(filme.getGenero().getId());
			filme.setGenero(genero);
			System.out.println(filme);
			model.addAttribute("filme", filme);
			fService.atualizarFilme(filme);
			return "VisualizarFilme";

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}

}
