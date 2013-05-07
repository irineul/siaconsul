package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import Daos.ProcessoDao;
import Enums.ProcessoTipos;

import models.ClienteModel;
import models.DocumentoModel;
import models.ProcessoModel;
import models.UsuarioModel;

import play.mvc.Controller;
import play.mvc.results.RenderTemplate;

public class Processo extends Controller {
	
    
	/**
	 * 
	 * @param idCliente
	 */
    public static void carrega(Long idCliente, List<String> erros, ProcessoModel processo) {
    	ClienteModel cliente =ClienteModel.findById(idCliente);
    	if (processo == null) {
    		processo = new ProcessoModel();
    	}
    	render(cliente, erros, processo);
    }
    
    /**
     * 
     * @param idProcesso
     */
    public static void ver(Long idProcesso) {
    	ProcessoModel processo = ProcessoModel.findById(idProcesso);
    	processo.getDocumentosExtra();
    	System.out.println(processo.getDescricao());
    	render(processo) ;
    }
    
    /**
     * 
     * @param processo
     * @param erros
     */
    public static void mostraErros(ProcessoModel processo, List<String> erros) {
    	renderTemplate("carrega",processo, erros) ;
    }
    
    /**
     * 
     * @param idCliente
     * @param descricao
     * @param title
     * @param files
     */
    public static void incluir(long idCliente, String descricao, String dataAberturaProcesso, String title, List<File> files) {
    	
    	ProcessoModel processo = new ProcessoModel();
    	processo.setIdCliente(idCliente);
    	processo.getCliente();
    	processo.setDescricao(descricao);
    	
    	List<String> erros = verificaInconsistencias(processo);
    	
    	if (erros == null) {
    		ProcessoDao.getInstance().salvar(processo);
    		ver(processo.getId());
    	
    	} else {
    	
    		carrega(idCliente, erros, processo);
    	} 
    	
    }
    
    
    
	/**
	 * 
	 * @param processo
	 * @return
	 */
	private static List<String> verificaInconsistencias(ProcessoModel processo) {
		
		
		List<String> erros = new ArrayList<String>();
		
		if (processo.getTipoProcesso() == null) {
			erros.add("Tipo Processo √© obrigat√≥rio");
		}
		
		if (ProcessoTipos.VEICULO.equals(processo.getTipoProcesso())) {
			List<String> errosVeiculo = verificaInconsistenciasProcessoVeiculo(processo);
			erros.addAll(errosVeiculo);
		}
		
		return erros;
	}

	/**
	 * 
	 * @param processo
	 * @return
	 */
	private static List<String> verificaInconsistenciasProcessoVeiculo(ProcessoModel processo) {
		List<String> erros = new ArrayList<String>();
		if (processo.getDocCarro() == null) {
			erros.add("Documento do carro √© obrigat√≥rio.");
		}
		return erros;
	}

    
}