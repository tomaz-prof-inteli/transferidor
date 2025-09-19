package tms.transferidor.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tms.transferidor.entidade.Conta;

public class ContaDao {
	private Connection conexao;
	private PreparedStatement pstmListar;
	private PreparedStatement pstmCriar;
	private PreparedStatement pstmLer;
	private PreparedStatement pstmAtualizarSaldo;
	public ContaDao(String url) throws SQLException {
		conexao = DriverManager.getConnection(url);
		pstmListar = conexao.prepareStatement("SELECT * FROM contas");
		pstmCriar = conexao.prepareStatement("INSERT INTO contas (senhaCodificada, saldo) VALUES (?, ?)");
		pstmLer = conexao.prepareStatement("SELECT * FROM contas WHERE numero=?");
		pstmAtualizarSaldo = conexao.prepareStatement("UPDATE contas SET saldo=? WHERE numero=?");
	}
	public List<Conta> listar() throws SQLException {
		List<Conta> contas = new ArrayList<>();
		ResultSet rs = pstmListar.executeQuery();
		while (rs.next()) {
			int n = rs.getInt("numero");
			String senhaCodificada = rs.getString("senhaCodificada");
			BigDecimal saldo = rs.getBigDecimal("saldo");
			Conta c = new Conta(n, senhaCodificada, saldo);
			contas.add(c);
		}
		return contas;
	}
	public Conta criar(Conta c) throws SQLException {
		pstmCriar.setString(1, c.getSenhaCodificada());
		pstmCriar.setBigDecimal(2, c.getSaldo());
		pstmCriar.executeUpdate();
		ResultSet rs = pstmCriar.getGeneratedKeys();
		Conta nova = null;
		if (rs.next()) {
			int numero = rs.getInt(1);
			nova = c.clone();
			nova.setNumero(numero);
		}
		return nova;
	}
	public Conta ler(int numero) throws SQLException {
		Conta c = null;
		pstmLer.setInt(1, numero);
		ResultSet rs = pstmLer.executeQuery();
		if (rs.next()) {
			String senhaCodificada = rs.getString("senhaCodificada");
			BigDecimal saldo = rs.getBigDecimal("saldo");
			c = new Conta(numero, senhaCodificada, saldo);
		}
		return c;
	}
	public void atualizarSaldo(Conta c) throws SQLException {
		pstmAtualizarSaldo.setBigDecimal(1, c.getSaldo());
		pstmAtualizarSaldo.setInt(2, c.getNumero());
		pstmAtualizarSaldo.executeUpdate();
	}
	public void close() throws SQLException {
		pstmListar.close();
		conexao.close();
	}
	public void iniciarTransacao() throws SQLException {
		conexao.setAutoCommit(false);
	}
	public void commitTransacao() throws SQLException {
		conexao.commit();
		conexao.setAutoCommit(true);
	}
	public void rollbackTransacao() throws SQLException {
		conexao.rollback();
		conexao.setAutoCommit(true);
	}
	
}
