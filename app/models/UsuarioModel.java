package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "USUARIO")
public class UsuarioModel extends Model{
	
	@Column(name="SENHA")
    private String senha;
		
	@Column(name="TELEFONE")
    private String telefone;
	
	@Column(name="EMAIL",unique = true)
    private String email;
	
	@Column(name="CPF", unique = true)
    private String cpf;
	
	@Column(name="NOME")
	private String nome;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public ArrayList<ConsultorModel> getConsultores () {
		List<ConsultorModel> list = ConsultorModel.find("idAdvogado = ?",this.id).fetch();
		return (ArrayList<ConsultorModel>) list;
	}

}
