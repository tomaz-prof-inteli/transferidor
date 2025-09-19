package tms.transferidor.servico;

import java.math.BigDecimal;
import java.sql.SQLException;

import tms.transferidor.dao.ContaDao;
import tms.transferidor.entidade.Conta;
import tms.transferidor.util.SenhaUtil;

public class TransfServico {
	private ContaDao contaDao;

	public TransfServico(ContaDao contaDao) {
		super();
		this.contaDao = contaDao;
	}

	public Conta lerConta(int numeroContaOrigem) throws SQLException {
		return contaDao.ler(numeroContaOrigem);
	}

	public void autenticar(String senha, Conta contaOrigem) throws SenhaInvalidaException {
		if (!SenhaUtil.validar(senha, contaOrigem.getSenhaCodificada())) {
			throw new SenhaInvalidaException();
		}
	}

	public void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws SaldoInsuficienteException, SQLException {
		if (contaOrigem.getSaldo().compareTo(valor) < 0) {
			throw new SaldoInsuficienteException();
		}
		contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
		contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
		
	    try {
	        contaDao.iniciarTransacao();
	        
	        contaDao.atualizarSaldo(contaOrigem);
	        contaDao.atualizarSaldo(contaDestino);
	        
	        contaDao.commitTransacao();
	    } catch (SQLException e) {
	        contaDao.rollbackTransacao();
	        throw e;
	    }
	}
	
}
