package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Util;

import models.AdvogadoModel;
import models.ClienteModel;
import models.ConsultorModel;
import models.DocumentoModel;
import models.ProcessoModel;
import play.mvc.Controller;
import Daos.ProcessoDao;
import Enums.ProcessoTipos;

public class Processo extends BaseController {
	

	public static void lista() {
    	ConsultorModel consultor = others.Util.getConsultorLogado();
    	List<ProcessoModel> processos = ProcessoDao.getInstance().ListarByIdConsultor(consultor.getId());
    	render(processos);
    }
	
	/**
	 * 
	 * @param idCliente
	 */
    public static void carrega(Long idCliente, ProcessoModel processo, File proc) {
    	
    	List<String> erros = new ArrayList<String>();
    	
    	String operacao = params.get("operacao", String.class);
    	if ("INCLUIR".equals(operacao)) {
	    	processo = montaProcessoFromRequest();
	    	
	    	//TODO: Pegar esse parametro da simulacao
	    	processo.setTipoProcesso(ProcessoTipos.VEICULO);
	    	
	    	erros = verificaInconsistencias(processo);
	    	if (erros == null || erros.size() == 0) {
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
    	
    	render(cliente, erros, processo, proc);
    }
    

	/**
	 * Pega todos os parametros da tela e seta no 
	 * objeto ProcessoModel
	 * 
	 * @return
	 */
	private static ProcessoModel montaProcessoFromRequest() {
		
		ProcessoModel processo = new ProcessoModel();
		
		ConsultorModel cm = others.Util.getConsultorLogado();
    	processo.setIdConsultor(cm.getId());
		
    	processo.setIdCliente(params.get("idCliente", Long.class));
    	processo.setDescricao(params.get("descricao", String.class));
    	processo.setBanco(params.get("banco", String.class));
    	processo.setDataAberturaProcesso(params.get("dataAberturaProcesso", Date.class));
    	
    	DocumentoModel procuracao = getDocumentoFromRequest("idDocumentoProcuracao","procuracaoDoc");
    	processo.setProcuracao(procuracao);
    	
    	DocumentoModel declaracao = getDocumentoFromRequest("idDocumentoDeclaracao", "declaracaoDoc");
    	processo.setDeclaracaoDeHipos(declaracao);
    	
    	DocumentoModel docCarro = getDocumentoFromRequest("idDocumentoCarro", "docCarro");
    	processo.setDocCarro(docCarro);
    	
		return processo;
	}


	/**
	 * 
	 * @param id
	 * @param fieldName
	 * @return
	 */
	private static DocumentoModel getDocumentoFromRequest(String id, String fieldName) {
		DocumentoModel dm = null;
		Long idProcuracao = params.get(id, Long.class);
    	File arq = params.get(fieldName, File.class);
    	if ( (idProcuracao == null || idProcuracao == 0) && arq != null ){
	    	dm = new DocumentoModel();
	    	dm.setData(new Date());
	    	dm.setFile(arq);
	    	dm.save();
    	} else if (idProcuracao != null) {
    		dm = DocumentoModel.findById(idProcuracao);
    	}
    	return dm;
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