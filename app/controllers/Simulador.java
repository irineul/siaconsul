package controllers;

import play.data.validation.Required;
import play.mvc.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import others.Calculo;
import others.Util;


import models.*;


public class Simulador extends Controller {

    /** 
     * Locale Brasileiro 
     */  
    private static final Locale BRAZIL = new Locale("pt","BR");  
    /** 
     * Símbolos especificos do Real Brasileiro 
     */  
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);  
    /** 
     * Mascara de dinheiro para Real Brasileiro 
     */  
    public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);
	
	public static void index(){
		render();
	}

	public static void calcular(@Required String vlrFinanciado, @Required String vlrParcelaAtual, @Required String qtdParcelas, @Required String qtdParcelasPagas, String dataFinanciamento, @Required String tpPessoa, @Required String tpFinanciamento) throws java.text.ParseException{

		double vlrJuros=0;

		/* Converte a data passada por parâmetro */
		int mes = 0, ano =0;
		if(dataFinanciamento != null && !dataFinanciamento.isEmpty()){
			mes = Util.getMesInt(dataFinanciamento.substring(4, 7));
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

		c.setVlrNovaParcela(mascaraDinheiro(vlrFinalParcela, DINHEIRO_REAL));
		c.setVlrJurosNovo(twoDForm.format(vlrNovoJuros) + "%");
		c.setVlrJurosAntigo(twoDForm.format(vlrOldJuros) + "%");
		c.setVlrPagoIndevido(mascaraDinheiro(vlrIndevidoTotal, DINHEIRO_REAL));
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

	/* Cálculo de % juros total */
	private static double calculaJurosTotal(double vlrParcela, int qtdVezes, double vlrFinanciado){
		double vlrTotalNovo = vlrParcela * qtdVezes;

		/* Aplico regra de três */
		return ((vlrTotalNovo*100)/vlrFinanciado) - 100;
	}
	
	/* Mascara o dinheiro passado por parâmetro */
    private static String mascaraDinheiro(double valor, DecimalFormat moeda){  
        return moeda.format(valor);  
    }  	

}