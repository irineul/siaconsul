package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name = "TAXA")
public class TaxaModel extends Model{
	
	@Column(name="VALOR")
    private long valor;
	
	@Column(name="ANO")
	private int ano;
	
	@Column(name="MES")
	private int mes;
	
	@Column(name="ID_VALOR_REVISIONAL")
	private int idValorRevisional;
	
	public ValorRevisionalModel getRevisional() {
		return GenericModel.findById(this.idValorRevisional);
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}
	
}
