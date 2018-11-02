
package br.usjt.ads.arqdes.model.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.ads.arqdes.model.dao.LoginDAO;
import br.usjt.ads.arqdes.model.entity.Usuario;


@Service
public class LoginService {

	@Autowired
	private LoginDAO loginDAO;

	@Transactional
	public Usuario login(Usuario usuario) throws IOException {
		return loginDAO.login(usuario);
	}

}