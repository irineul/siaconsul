package Daos;

import java.util.ArrayList;
import java.util.List;

import controllers.Consultor;

import models.ClienteModel;
import models.ConsultorModel;
import models.ProcessoModel;

public class AdvogadoDao {


	public static AdvogadoDao self;

	private AdvogadoDao(){};

	public static AdvogadoDao getInstance() {
		if (self == null) {
			self = new AdvogadoDao();
		}
		return self;
	}
}

