package controllers;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.List;

import models.ClienteModel;
import models.DocumentoModel;
import models.ProcessoModel;
import models.UsuarioModel;

import play.mvc.Controller;

public class Processo extends Controller {

    
    public static void carrega(Long idCliente) {
    	ClienteModel cliente =ClienteModel.findById(idCliente);
    	render(cliente);
    }
    
    public static void ver(Long idProcesso) {
    	ProcessoModel processo = ProcessoModel.findById(idProcesso);
    	processo.getDocumentosDoProcesso();
    	System.out.println(processo.getDescricao());
    	render(processo) ;
    }
    
    public static void incluir(long idCliente, String descricao, String title, List<File> files) {
    	ProcessoModel pm = new ProcessoModel();
    	pm.setIdCliente(idCliente);
    	pm.setDescricao(descricao);
    	pm.save();
    	for(File f: files) {
	    	DocumentoModel doc = new DocumentoModel();
	    	doc.setIdProcesso(pm.getId());
	    	doc.setFile(f);
	    	doc.setData(new GregorianCalendar());
	    	doc.save();
    	}
    	ver(pm.getId());
    }
    
    
}