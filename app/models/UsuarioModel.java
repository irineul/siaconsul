package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name = "USUARIO")
public class UsuarioModel extends Model{
	
	@Column(name="SENHA")
    private String senha;

	@Column(name="NOME")
    private String nome;
	
	@Column(name="TELEFONE_RESIDENCIAL")
    private String telResidencial;

	@Column(name="TELEFONE_CELULAR")
    private String celular;

	@Column(name="IND_TIPO_PESSOA")
    private String tipoPessoa;
	
	@Column(name="EMAIL", unique = true)
    private String email;
	
	@Column(name="CPF_CNPJ")
    private String cpfCnpj;
	
	@Column(name="ENDERECO")
	private String endereco;

	@Column(name="RG", unique = true)
	private String rg;	


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

	public String getTelResidencial() {
		return telResidencial;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	
	public ArrayList<ConsultorModel> getConsultores () {
		List<ConsultorModel> list = ConsultorModel.find("idAdvogado = ?",this.id).fetch();
		return (ArrayList<ConsultorModel>) list;
	}

	public void setTelResidencial(String telefoneResidencial) {
		this.telResidencial = telefoneResidencial;
		
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

}
