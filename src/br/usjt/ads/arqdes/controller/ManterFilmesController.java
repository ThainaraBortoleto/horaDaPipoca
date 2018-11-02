package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.entity.Usuario;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;
import br.usjt.ads.arqdes.model.service.LoginService;

@Controller
public class ManterFilmesController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;
	@Autowired
	private LoginService lService;

	// Formatação de Datas
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	// Request Padrao
	@RequestMapping("/")
	public String inicio() {
		return "index";
	}

	@RequestMapping("/inicio")
	public String inicio1() {
		return "index";
	}

	// Request Login - TODO
	// Faz Login
	@RequestMapping("/fazer_login")
	public String fazerLogin(HttpSession session, Usuario usuario) {
		try {
			usuario = lService.login(usuario);
			session.setAttribute("usuario", usuario);
			if (usuario != null) {
				return "redirect:administracao";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:inicio";
	}

	// Faz Logout
	@RequestMapping("/fazer_logout")
	public String deslogar(HttpSession session) {
		session.removeAttribute("usuario");
		return "redirect:inicio";
	}

	@RequestMapping("/listar_filmes")
	public String listarFilmes(HttpSession session) {
		session.setAttribute("lista", null);
		return "ListarFilmes";
	}

	@RequestMapping("/novo_filme")
	public String novoFilme(HttpSession session) {
		try {
			List<Genero> generos = gService.listarGeneros();
			session.setAttribute("generos", generos);
			return "CriarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping("/inserir_filme")
	public String inserirFilme(@Valid Filme filme, BindingResult result, Model model) {
		try {
			if (!result.hasFieldErrors("titulo")) {
				Genero genero = gService.buscarGenero(filme.getGenero().getId());
				filme.setGenero(genero);
				model.addAttribute("filme", filme);
				fService.inserirFilme(filme);
				return "VisualizarFilme";
			} else {
				return "CriarFilme";
			}

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
			List<Genero> generos = gService.listarGeneros();
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
			List<Filme> lista;

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
			List<Genero> generos = gService.listarGeneros();
			List<Filme> filmes = fService.listarFilmes();
			List<String> genFlista = new ArrayList<>();
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
