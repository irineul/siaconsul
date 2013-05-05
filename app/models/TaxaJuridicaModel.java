package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "TAXA_JURIDICA")
public class TaxaJuridicaModel extends Model{
	
	@Column(name="ID")
	private int id;

	@Column(name="MES")
    private int mes;
	
	@Column(name="ANO")
    private int ano;
	
	@Column(name="VLR_DESC_DUPLIC")
    private double vlrDescDuplic;
	
	@Column(name="VLR_AQ_BENS")
	private double vlrAqBens;
	
	@Column(name="VLR_CONTA_GARANTIDA")
	private double vlrContaGarantida;

	@Column(name="VLR_HOT_MONEY")
	private double vlrHotMoney;
	
	@Column(name="VLR_CAPITAL_GIRO")
	private double vlrCapitalGiro;
	
	@Column(name="VLR_VENDOR")
	private double vlrVendor;
	
	@Column(name="VLR_TOTAL")
	private double vlrTotal;

	public void setId(int id) {
		this.id = id;
	}

	public long getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public long getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public double getVlrDescDuplic() {
		return vlrDescDuplic;
	}

	public void setVlrDescDuplic(double vlrDescDuplic) {
		this.vlrDescDuplic = vlrDescDuplic;
	}

	public double getVlrAqBens() {
		return vlrAqBens;
	}

	public void setVlrAqBens(double vlrAqBens) {
		this.vlrAqBens = vlrAqBens;
	}

	public double getVlrContaGarantida() {
		return vlrContaGarantida;
	}

	public void setVlrContaGarantida(double vlrContaGarantida) {
		this.vlrContaGarantida = vlrContaGarantida;
	}

	public double getVlrHotMoney() {
		return vlrHotMoney;
	}

	public void setVlrHotMoney(double vlrHotMoney) {
		this.vlrHotMoney = vlrHotMoney;
	}

	public double getVlrCapitalGiro() {
		return vlrCapitalGiro;
	}

	public void setVlrCapitalGiro(double vlrCapitalGiro) {
		this.vlrCapitalGiro = vlrCapitalGiro;
	}

	public double getVlrVendor() {
		return vlrVendor;
	}

	public void setVlrVendor(double vlrVendor) {
		this.vlrVendor = vlrVendor;
	}

	public double getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(double vlrTotal) {
		this.vlrTotal = vlrTotal;
	}
	
}
