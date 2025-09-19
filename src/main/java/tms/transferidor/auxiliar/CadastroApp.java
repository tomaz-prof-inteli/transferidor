package tms.transferidor.auxiliar;

import static tms.transferidor.util.ESUtil.input;
import static tms.transferidor.util.ESUtil.print;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import tms.transferidor.dao.ContaDao;
import tms.transferidor.entidade.Conta;
import tms.transferidor.util.PropertiesUtil;
import tms.transferidor.util.SenhaUtil;

public class CadastroApp {

	static void listar(ContaDao dao) throws SQLException {
		List<Conta> contas = dao.listar();
		print("\nLISTA DE CONTAS:");
		if (contas.size() == 0) {
			print("(não há nenhuma conta por enquanto...)");
		}
		for (Conta c : contas) {
			print(c.toString());
		}
	}

	static void criar(ContaDao dao) throws Exception {
		print("\nCRIAÇÃO DE CONTA:");
		String strSaldo = input("Saldo da nova conta: ");
		BigDecimal saldo = new BigDecimal(strSaldo);
		String senha = input("Senha da nova conta: ");
		String senhaCodificada = SenhaUtil.hash(senha);
		Conta c = new Conta(senhaCodificada, saldo);
		Conta nova = dao.criar(c);
		print("Conta " + nova.getNumero() + " criada com sucesso!");
	}

	public static void main(String[] args) throws Exception {
		String url = PropertiesUtil.carregar().getProperty("url");
		ContaDao dao = new ContaDao(url);

		String menu = "\n(1) Listar contas";
		menu += "\n(2) Criar conta";
		menu += "\n(0) Sair";
		menu += "\nEscolha uma opção: ";

		boolean sair = false;

		while (!sair) {
			String opcao = input(menu);
			switch (opcao) {
			case "1":
				listar(dao);
				break;
			case "2":
				criar(dao);
				break;
			case "0":
				sair = true;
				break;
			default:
				print("Opção inválida!");
			}
		}

		print("\nFIM!");
		dao.close();
	}

}
