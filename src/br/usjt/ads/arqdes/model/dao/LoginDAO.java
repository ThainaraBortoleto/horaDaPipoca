package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.ads.arqdes.model.entity.Usuario;

@Repository
public class LoginDAO {
	@PersistenceContext
	EntityManager manager;

	public Usuario login(Usuario usuario) throws IOException {
		Query query = manager.createQuery("select u from Usuario u where u.usuario = :usuario and u.senha = :senha");
		query.setParameter("usuario", usuario.getUsuario());
		query.setParameter("senha", usuario.getSenha());
		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}