package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import objects.Calculo;

import models.*;


public class Simulador extends Controller {

	public static void index(){
		render();
	}

	public static void calcular(@Required String vlrFinanciado, @Required String vlrParcelaAtual, @Required String qtdParcelas, @Required String qtdParcelasPagas, String dataFinanciamento, @Required String tpPessoa, @Required String tpFinanciamento) throws java.text.ParseException{

		double vlrJuros=0;

		/* Converte a data passada por parâmetro */
		int mes = 0, ano =0;
		if(dataFinanciamento != null && !dataFinanciamento.isEmpty()){
			mes = getMes(dataFinanciamento.substring(4, 7));
			ano = Integer.parseInt(dataFinanciamento.substring(11,15));
		}

		/* Busco a taxa para o mês e ano informados, se houver */
		/* Pessoa Física */
		if(mes != 0 && ano != 0){
			if(tpPessoa.equals("F")){

				/* Veículo */
				if(tpFinanciamento.equals("V")){
					vlrJuros = TaxaFisicaModel.getTaxaByMesAno(ano, mes).getVlrVeiculo() / 100;
				}

				/* Cheque Especial */
				else if(tpFinanciamento.equals("C")){
					vlrJuros = TaxaFisicaModel.getTaxaByMesAno(ano, mes).getVlrChequeEspecial() / 100;
				}

				/* Crédito Pessoal */
				else if(tpFinanciamento.equals("P")){
					vlrJuros = TaxaFisicaModel.getTaxaByMesAno(ano, mes).getVlrCreditoPessoal() / 100;
				}

				/* Outros */
				else{
					vlrJuros = TaxaFisicaModel.getTaxaByMesAno(ano, mes).getVlrOutros() / 100;    		
				}
			}

			/* Pessoa Jurídica */
			else{

			}
		}
		/* Se não, uso taxa default de 1%*/
		else{
			vlrJuros = 0.01;
		}

		/* Calcula o novo valor da parcela */
		double vlrNovaParcela = calculaNovaParcela(Double.parseDouble(vlrFinanciado), Double.parseDouble(vlrParcelaAtual), Integer.parseInt(qtdParcelas), mes, ano, vlrJuros);

		/* Calculo o novo valor dos juros */
		double vlrNovoJuros = calculaJurosTotal(vlrNovaParcela, Integer.parseInt(qtdParcelas), Double.parseDouble(vlrFinanciado));

		/* Calculo o antigo valor dos juros */
		double vlrOldJuros = calculaJurosTotal(Double.parseDouble(vlrParcelaAtual), Integer.parseInt(qtdParcelas), Double.parseDouble(vlrFinanciado));

		/* Calcula valor indevido */
		double vlrIndevidoTotal = valorPagoIndevido((Double.parseDouble(vlrParcelaAtual) - vlrNovaParcela), vlrJuros, Integer.parseInt(qtdParcelasPagas));

		/* Valor final da parcela */
		double vlrFinalParcela = abateValorParcelas(vlrIndevidoTotal, vlrNovaParcela, Integer.parseInt(qtdParcelas) - Integer.parseInt(qtdParcelasPagas));


		/* Seto os valores para apresentação na tela */
		/* ***************************************** */
		Calculo c = new Calculo();
		DecimalFormat twoDForm = new DecimalFormat("#########.##");

		c.setVlrNovaParcela("R$ " + twoDForm.format(vlrFinalParcela));
		c.setVlrJurosNovo(twoDForm.format(vlrNovoJuros) + "%");
		c.setVlrJurosAntigo(twoDForm.format(vlrOldJuros) + "%");
		c.setVlrPagoIndevido("R$ " + twoDForm.format(vlrIndevidoTotal));
		/* ***************************************** */

		renderJSON(c);

	}

	/* Cálculo da parcela com a revisional (simulação) */
	private static double calculaNovaParcela(double vlrFinanciado, double vlrParcelaAtual, int qtdParcelas, int mes, int ano, double vlrJuros) {


		/* Multiplica-se o valor da parcela atual do cliente com o coeficiente de financiamento, 
		 * para se obter o valor da nova parcela
		 */
		return vlrFinanciado * calculaCoeficienteFinanciamento(vlrJuros, vlrFinanciado, qtdParcelas);
	}

	/* Cálculo do coenficiente de financiamento */
	private static double calculaCoeficienteFinanciamento(double vlrJuros, double vlrFinanciado, int qtdParcelas) {
		return (1+vlrJuros*qtdParcelas)/((((vlrJuros*(qtdParcelas-1))/2)+1)*qtdParcelas);
	}

	/* Valor indevido pago pelo cliente */
	public static double valorPagoIndevido(double vlrDifParcelas, double vlrJuros, int qtdParcelasPagas){
		return vlrDifParcelas * (Math.pow((1+vlrJuros), qtdParcelasPagas) - 1) / vlrJuros;
	}

	/* Abate o novo valor da parcela nas parcelas vicendas */
	public static double abateValorParcelas(double vlrIndevido, double vlrNovaParcela, int qtdParcelasVicendas){
		return vlrNovaParcela - (vlrIndevido/qtdParcelasVicendas);

	}

	/* Busca o inteiro equivalente ao mês passado por parâmetro */
	private static int getMes(String mes){
		if(mes.toUpperCase().equals("JAN")){
			return 1;
		}
		else if(mes.toUpperCase().equals("FEB")){
			return 2;
		}		
		else if(mes.toUpperCase().equals("MAR")){
			return 3;
		}		
		else if(mes.toUpperCase().equals("APR")){
			return 4;
		}		
		else if(mes.toUpperCase().equals("MAY")){
			return 5;
		}		
		else if(mes.toUpperCase().equals("JUN")){
			return 6;
		}		
		else if(mes.toUpperCase().equals("JUL")){
			return 7;
		}		
		else if(mes.toUpperCase().equals("AUG")){
			return 8;
		}		
		else if(mes.toUpperCase().equals("SEP")){
			return 9;
		}		
		else if(mes.toUpperCase().equals("OCT")){
			return 10;
		}		
		else if(mes.toUpperCase().equals("NOV")){
			return 11;
		}		
		else if(mes.toUpperCase().equals("DEC")){
			return 12;
		}
		return 0;
	}

	/* Cálculo de % juros total */
	private static double calculaJurosTotal(double vlrParcela, int qtdVezes, double vlrFinanciado){
		double vlrTotalNovo = vlrParcela * qtdVezes;

		/* Aplico regra de três */
		return ((vlrTotalNovo*100)/vlrFinanciado) - 100;
	}

}