package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "VALOR_REVISIONAL")
public class ValorRevisionalModel extends Model{
	
	@Column(name="VALOR_FINAL")
    private long valorFinal;
	
	@Column(name="METODO")
	private String metodo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="ID_PROCESSO")
	private int idProcesso;
	
	public List<ValorRevisionalParcelaModel> getParcelas () {
		List<ValorRevisionalParcelaModel> result =  ValorRevisionalParcelaModel.find("idValorRevisional=?", this.id).fetch();
		return result;
	}
	
	public List<TaxaModel> getTaxaUtilizada () {
		List<TaxaModel> result =  TaxaModel.find("idValorRevisional=?", this.id).fetch();
		return result;
	}
	
	public ProcessoModel getProcesso() {
		return ProcessoModel.findById(this.idProcesso);
	}

	public long getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(long valorFinal) {
		this.valorFinal = valorFinal;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
