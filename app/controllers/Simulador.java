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
    	/*String dataFormatada = dataFinanciamento.replace(" 00:00:00 GMT-0300 (BRT)", "");
        DateFormat df = new SimpleDateFormat("EEE MMM dd yyyy");
        Date d1=null;
        try {
            d1 = df.parse(dataFormatada);
        } catch (ParseException e) {    			
            System.out.println(e.getMessage());
        } */
    	
    	//DateTime dataFinanc = DateTime.parse(dataFinanciamento);
    	int mes = 2;
    	int ano = 2011;
    	
    	/* Busco a taxa para o mês e ano informados */
    	/* Pessoa Física */
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
    	
    	/* Calcula o novo valor da parcela */
    	double vlrNovaParcela = calculaNovaParcela(Double.parseDouble(vlrFinanciado), Double.parseDouble(vlrParcelaAtual), Integer.parseInt(qtdParcelas), mes, ano, vlrJuros);
    	
    	/* Calcula valor indevido */
    	double vlrIndevidoTotal = valorPagoIndevido((Double.parseDouble(vlrParcelaAtual) - vlrNovaParcela), vlrJuros, Integer.parseInt(qtdParcelasPagas));
    	
    	/* Valor final da parcela */
    	double vlrFinalParcela = abateValorParcelas(vlrIndevidoTotal, vlrNovaParcela, Integer.parseInt(qtdParcelas) - Integer.parseInt(qtdParcelasPagas));
    	
    	Calculo c = new Calculo();
    	
    	DecimalFormat twoDForm = new DecimalFormat("#########.##");
    	c.setVlrNovaParcela("R$ " + twoDForm.format(vlrFinalParcela));
    	c.setVlrJurosNovo(twoDForm.format(vlrJuros*100) + " %");
    	
    	//TODO buscar valor de juros antigos
    	//c.setVlrJurosAntigo(String.valueOf(mes));
    	c.setVlrJurosAntigo("5,40 %");
    	//c.setVlrJurosAntigo(dataFormatada);
    	c.setVlrPagoIndevido("R$ " + twoDForm.format(vlrIndevidoTotal));
    	
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
    
}