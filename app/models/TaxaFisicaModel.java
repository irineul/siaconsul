package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "TAXA_FISICA")
public class TaxaFisicaModel extends Model{
	
	@Column(name="ID")
	private long id;
	
	@Column(name="ANO")
	private int ano;
	
	@Column(name="MES")
	private int mes;

	@Column(name="VLR_CREDITO_PESSOAL")
	private double vlrCreditoPessoal;
	
	@Column(name="VLR_CHEQUE_ESPECIAL")
	private double vlrChequeEspecial;
	
	@Column(name="VLR_VEICULO")
	private double vlrVeiculo;
	
	@Column(name="VLR_OUTROS")
	private double vlrOutros;
	
	@Column(name="VLR_TOTAL")
	private double vlrTotal;

	public void setId(int id) {
		this.id = id;
	}

	public static TaxaFisicaModel getTaxaByMesAno (int ano, int mes) {
		List<TaxaFisicaModel> list = TaxaFisicaModel.find("ano = ? AND mes = ?",ano, mes).fetch();
		if(list.size() > 0)
			return list.get(0);
		return new TaxaFisicaModel();
	}
	
	public double getVlrChequeEspecial() {
		return vlrChequeEspecial;
	}

	public void setVlrChequeEspecial(double vlrChequeEspecial) {
		this.vlrChequeEspecial = vlrChequeEspecial;
	}

	public double getVlrVeiculo() {
		return vlrVeiculo;
	}

	public void setVlrVeiculo(double vlrVeiculo) {
		this.vlrVeiculo = vlrVeiculo;
	}

	public double getVlrOutros() {
		return vlrOutros;
	}

	public void setVlrOutros(double vlrOutros) {
		this.vlrOutros = vlrOutros;
	}

	public double getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(double vlrTotal) {
		this.vlrTotal = vlrTotal;
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

	public double getVlrCreditoPessoal() {
		return vlrCreditoPessoal;
	}

	public void setVlrCreditoPessoal(double vlrCreditoPessoal) {
		this.vlrCreditoPessoal = vlrCreditoPessoal;
	}
	
}
