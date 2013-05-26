package others;

import java.text.ParseException;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import models.AdvogadoModel;
import models.ConsultorModel;

public class Util {

	/* Busca o inteiro equivalente ao mês passado por parâmetro */
	public static int getMesInt(String mes)
	{
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

	/* Formata CPF/CNPJ */
	public static String formataCpfCnpj(String cpf){
		JFormattedTextField cpfCnpjFormatado = null;

		MaskFormatter mkFormatter = null;

		/* CPF */
		if(cpf.length() == 11){
			try {
				mkFormatter = new MaskFormatter("###.###.###-##");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* CNPJ */
		else{
			try {
				mkFormatter = new MaskFormatter("##.###.###/####-##");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		cpfCnpjFormatado = new JFormattedTextField(mkFormatter); 
		cpfCnpjFormatado.setText(cpf);

		return cpfCnpjFormatado.getText();
	}

	/* Removo caracter */
	public static String removerCaracter(String texto, String caracter)
	{
		return texto.replaceAll(".","");
	}
	
	
	public static ConsultorModel getConsultorLogado()
	{
		Long id = Long.parseLong(play.mvc.Scope.Session.current().get("idConsultor"));
		return ConsultorModel.findById(id);
	}
	
	public static AdvogadoModel getAdvogadoLogado()
	{
		Long id = Long.parseLong(play.mvc.Scope.Session.current().get("idUsuario"));
		List<AdvogadoModel> cm = AdvogadoModel.find("idUsuario=?", id).fetch();
		if (cm == null)
			return null;
		return cm.get(0);
	}
	
	public static String getTipousuarioLogado()
	{
		return play.mvc.Scope.Session.current().get("tpUsuario");
	}
	
	

}

