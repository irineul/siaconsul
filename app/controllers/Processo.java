package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ClienteModel;
import models.DocumentoModel;
import models.ProcessoModel;
import play.mvc.Controller;
import Daos.ProcessoDao;
import Enums.ProcessoTipos;

public class Processo extends Controller {
	
    
	/**
	 * 
	 * @param idCliente
	 */
    public static void carrega(Long idCliente, ProcessoModel processo, File proc) {
    	
    	List<String> erros = new ArrayList<String>();
    	
    	String operacao = params.get("operacao", String.class);
    	if ("INCLUIR".equals(operacao)) {
	    	processo = montaProcessoFromRequest();
	    	erros = verificaInconsistencias(processo);
	    	if (erros == null) {
	    		ProcessoDao.getInstance().salvar(processo);
	    	}
    	}
    	
    	ClienteModel cliente =ClienteModel.findById(idCliente);
    	if (processo == null) {
    		processo = new ProcessoModel();
    	}
    	if (processo.getDataAberturaProcesso() == null) {
    		processo.setDataAberturaProcesso(new Date());

    	}
    	
    	
    	//processo = incluir(erros);
    	
    	render(cliente, erros, processo, proc);
    }
    
    /**
     * 
     * @param idProcesso
     */
    public static void ver(Long idProcesso) {
    	ProcessoModel processo = ProcessoModel.findById(idProcesso);
    	processo.getDocumentosExtra();
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
	private static ProcessoModel incluir(List<String> erros) {
		ProcessoModel processo = montaProcessoFromRequest();
    	erros = verificaInconsistencias(processo);
    	if (erros == null) {
    		ProcessoDao.getInstance().salvar(processo);
    	}
    	return processo;
    }

	/**
	 * Pega todos os parametros da tela e seta no 
	 * objeto ProcessoModel
	 * 
	 * @return
	 */
	private static ProcessoModel montaProcessoFromRequest() {
		
		ProcessoModel processo = new ProcessoModel();
    	processo.setIdCliente(params.get("idCliente", Long.class));
    	processo.setDescricao(params.get("descricao", String.class));
    	processo.setBanco(params.get("banco", String.class));
    	processo.setDataAberturaProcesso(params.get("dataAberturaProcesso", Date.class));
    	
    	/*Set Procuracao*/
    	Long idProcuracao = params.get("idDocumentoProcuracao", Long.class);
    	File arq = params.get("procuracaoDoc", File.class);
    	if ( (idProcuracao == null || idProcuracao == 0) && arq != null ){
	    	DocumentoModel dm = new DocumentoModel();
	    	dm.setData(new Date());
	    	dm.setFile(arq);
	    	dm.save();
	    	processo.setProcuracao(dm);
    	} else if (idProcuracao != null) {
    		DocumentoModel procuracao = DocumentoModel.findById(idProcuracao);
    		processo.setProcuracao(procuracao);
    	}
    	
    	/*Set Declaracao*/
    	Long idDeclaracao = params.get("idDocumentoDeclaracao", Long.class);
    	File declaracaoArq = params.get("declaracaoDoc", File.class);
    	if ( (idDeclaracao == null || idDeclaracao == 0) && declaracaoArq != null){
	    	DocumentoModel declaracao = new DocumentoModel();
	    	declaracao.setData(new Date());
	    	declaracao.setFile(declaracaoArq);
	    	declaracao.save();
	    	processo.setDeclaracaoDeHipos(declaracao);
    	} else if (idDeclaracao != null) {
    		DocumentoModel declaracao = DocumentoModel.findById(idDeclaracao);
    		processo.setDeclaracaoDeHipos(declaracao);
    	}

    	
    	
    	
    	
    	
		return processo;
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