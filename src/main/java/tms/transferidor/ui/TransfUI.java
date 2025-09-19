package tms.transferidor.ui;

import static tms.transferidor.util.ESUtil.*;

import java.math.BigDecimal;

public class TransfUI {

	public int lerNumeroContaOrigem() {
		int n = Integer.parseInt(input("Número da conta de origem: "));
		return n;
	}

	public String lerSenha() {
		return input("Senha da conta de origem: ");
	}

	public int lerNumeroContaDestino() {
		int n = Integer.parseInt(input("Número da conta de destino: "));
		return n;
	}

	public BigDecimal lerValorTransferencia() {
		String s = input("Valor da transferência: ");
		BigDecimal valor = new BigDecimal(s);
		return valor;
	}

	public void avisarTransferenciaEfetuada() {
		print("TRANSFERÊNCIA REALIZADA COM SUCESSO!");
	}

	public void avisarSenhaInvalida() {
		print("SENHA INVÁLIDA!");
	}

	public void avisarSaldoInsuficiente() {
		print("SALDO INSUFICIENTE!");	
	}

	public void avisarValorAcimaLimite() {
		print("VALOR EXCEDE LIMITE MÁXIMO!");
	}

}
