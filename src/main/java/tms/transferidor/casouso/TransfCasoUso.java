package tms.transferidor.casouso;

import java.math.BigDecimal;
import java.sql.SQLException;

import tms.transferidor.entidade.Conta;
import tms.transferidor.servico.SaldoInsuficienteException;
import tms.transferidor.servico.SenhaInvalidaException;
import tms.transferidor.servico.TransfServico;
import tms.transferidor.servico.ValorAcimaLimiteException;
import tms.transferidor.ui.TransfUI;

public class TransfCasoUso {
	private TransfUI ui;
	private TransfServico servico;

	public TransfCasoUso(TransfUI ui, TransfServico servico) {
		super();
		this.ui = ui;
		this.servico = servico;
	}

	public void executar() throws SQLException {
		try {
			int numeroContaOrigem = ui.lerNumeroContaOrigem();
			Conta contaOrigem = servico.lerConta(numeroContaOrigem);
			String senha = ui.lerSenha();
			servico.autenticar(senha, contaOrigem);
			int numeroContaDestino = ui.lerNumeroContaDestino();
			Conta contaDestino = servico.lerConta(numeroContaDestino);
			BigDecimal valor = ui.lerValorTransferencia();
			servico.transferir(contaOrigem, contaDestino, valor);
			ui.avisarTransferenciaEfetuada();
		} catch(SenhaInvalidaException e) {
			ui.avisarSenhaInvalida();
		} catch(SaldoInsuficienteException e) {
			ui.avisarSaldoInsuficiente();
		} catch (ValorAcimaLimiteException e) {
			ui.avisarValorAcimaLimite();
		}
	}
}
