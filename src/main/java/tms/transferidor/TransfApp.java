package tms.transferidor;

import static tms.transferidor.util.ESUtil.print;

import java.io.IOException;
import java.sql.SQLException;

import tms.transferidor.casouso.TransfCasoUso;
import tms.transferidor.dao.ContaDao;
import tms.transferidor.servico.TransfServico;
import tms.transferidor.ui.TransfUI;
import tms.transferidor.util.PropertiesUtil;

public class TransfApp {

	private static ContaDao dao;
	private static TransfCasoUso casoUso;
	private static TransfUI ui;
	private static TransfServico servico;
	
	public static void main(String[] args) {
		
		String url;
		try {
			url = PropertiesUtil.carregar().getProperty("url");
			dao = new ContaDao(url);
			ui = new TransfUI();
			servico = new TransfServico(dao);
			casoUso = new TransfCasoUso(ui, servico);
			casoUso.executar();
		} catch (IOException e) {
			print("Arquivo application.properties não encontrado!");
		} catch (SQLException e) {
			print("Problema ao executar operação na base de dados!");
			print("Mensagem do erro: " + e.getMessage());
		}

	}

}
