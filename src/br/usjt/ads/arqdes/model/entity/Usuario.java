package br.usjt.ads.arqdes.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Size(min = 5, max = 45)
	private String usuario;
	@NotNull
	@Size(min = 5, max = 45)
	private String senha;
	@NotNull
	private int tipo;

	public Usuario() {

	}

	public Usuario(int id, int tipo, String usuario, String senha) {
		this.setId(id);
		this.setTipo(tipo);
		this.setUsuario(usuario);
		this.setSenha(senha);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", senha=" + senha + ", tipo=" + tipo + "]";
	}

}
