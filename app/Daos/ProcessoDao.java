package Daos;

import java.util.ArrayList;
import java.util.List;

import Enums.ProcessoTipos;

import models.ProcessoModel;

public class ProcessoDao {

	
	public static ProcessoDao self;
	
	private ProcessoDao(){};
	
	public static ProcessoDao getInstance() {
		if (self == null) {
			self = new ProcessoDao();
		}
		return self;
	}
	
	public ProcessoModel salvar(ProcessoModel processo){
		processo.save();
		processo.getProcuracao().setIdProcesso(processo.getId());
		processo.getProcuracao().save();
		processo.getDeclaracaoDeHipos().setIdProcesso(processo.getId());
		processo.getDeclaracaoDeHipos().save();
		return processo;
	}

}
