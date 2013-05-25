package others;
public class ValorRevisional {
	
	/**
	 * 
	 * Carrega os dados do cliente para a tela
	 * 
	 * @param tipoFinanciamento : 1 = Moto, 2 = Carro, 3 = Caminhão, 4 = Crédito imobiliário, 5 = Cheque Especial, 6 = Outros
	 * @param vlrFinanciamento
	 * @param vlrDiferenca : Diferença dos valores (Pré e pós revisional)
	 * 
	 */	
	public static double vlrRevisional(int tipoFinanciamento, double vlrFinanciamento, double vlrDiferenca)
	{
		if(tipoFinanciamento == 1)
		{
			return getValorFinanciamentoMoto(vlrFinanciamento);
		}
		else if(tipoFinanciamento == 1)
		{
			return getValorFinanciamentoCarro(vlrFinanciamento);
		}
		else if(tipoFinanciamento == 3)
		{
			return getValorFinanciamentoCaminhao(vlrFinanciamento);
		}
		else if(tipoFinanciamento == 4)
		{
			return getValorCreditoImobiliario(vlrFinanciamento, vlrDiferenca);
		}
		else if(tipoFinanciamento == 5)
		{
			return getValorChequeEspecial(vlrFinanciamento);
		}		
		else
		{
			return getValorOutros(vlrFinanciamento, vlrDiferenca);
		}
	}
	
	private static double getValorFinanciamentoMoto(double vlrFinanciamento)
	{
		/* Se valor financiado da  moto for acima de R$15.000,00 segue a mesma tabela que carro */
		if(vlrFinanciamento <= 15000)
		{
			return 1200;
		}
		else
		{
			return getValorFinanciamentoCarro(vlrFinanciamento);
		}
	}
	
	private static double getValorFinanciamentoCarro(double vlrFinanciamento)
	{
		if(vlrFinanciamento <= 20000)
		{
			return 1500;
		}
		else if(vlrFinanciamento > 20000 && vlrFinanciamento <= 40000)
		{
			return 1800;
		}
		else
		{
			return 2000;
		}
	}
	
	private static double getValorFinanciamentoCaminhao(double vlrFinanciamento)
	{
		if(vlrFinanciamento < 180000)
		{
			return 2500;
		}
		else
		{
			return 3000;
		}
	}
	
	private static double getValorCreditoImobiliario(double vlrFinanciamento, double vlrDiferenca)
	{
		if(vlrFinanciamento <= 50000)
		{
			if(vlrDiferenca >= 10000)
			{
				return 1800;
			}
			else
			{
				return 1400;
			}
		}
		else if(vlrFinanciamento <= 100000)
		{
			if(vlrDiferenca >= 15000)
			{
				return 2500;
			}
			else
			{
				return 2000;
			}
		}
		else
		{
			if(vlrDiferenca >= 20000)
			{
				return 3500;
			}
			else
			{
				return 4000;
			}
		}
			
	}
	
	private static double getValorChequeEspecial(double vlrFinanciamento)
	{
		if(vlrFinanciamento <= 5000)
		{
			return 1000;
		}
		else if(vlrFinanciamento <= 10000)
		{
			return 1300;
		}
		else if(vlrFinanciamento <= 20000)
		{
			return 1600;
		}
		else if(vlrFinanciamento <= 30000)
		{
			return 1800;
		}
		else if(vlrFinanciamento <= 70000)
		{
			return 2300;
		}
		else
		{
			return 2600;
		}
	}
	
	private static double getValorOutros(double vlrFinanciamento, double vlrDiferenca)
	{
		if(vlrFinanciamento <= 50000)
		{
			return getValorFinanciamentoCarro(vlrFinanciamento);
		}
		else
		{
			if(vlrDiferenca <= 15000)
			{
				return 2500;
			}
			else if(vlrDiferenca <= 20000)
			{
				return 3000;
			}
			else if(vlrDiferenca <= 25000)
			{
				return 3500;
			}	
			else if(vlrDiferenca <= 25000)
			{
				return 3500;
			}
			else
			{
				return 4000;
			}
		}
	}
}
