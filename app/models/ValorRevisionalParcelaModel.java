package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "VALOR_REVISIONAL_PARCELA")
public class ValorRevisionalParcelaModel extends Model{
	
	@Column(name="VALOR")
    private long valor;
	
	@Column(name="NUMERO_PARCELA")
	private String numeroParcela;
	
	@Column(name="ID_CALCULO")
	private String idCalculo;
	
	@Column(name="ID_VALOR_REVISIONAL")
	private int idValorRevisional;
	
	
	public ValorRevisionalModel getRevisional() {
		return ValorRevisionalModel.findById(this.idValorRevisional);
	}


	public long getValor() {
		return valor;
	}


	public void setValor(long valor) {
		this.valor = valor;
	}


	public String getNumeroParcela() {
		return numeroParcela;
	}


	public void setNumeroParcela(String numeroParcela) {
		this.numeroParcela = numeroParcela;
	}


	public String getIdCalculo() {
		return idCalculo;
	}


	public void setIdCalculo(String idCalculo) {
		this.idCalculo = idCalculo;
	}
	
}
