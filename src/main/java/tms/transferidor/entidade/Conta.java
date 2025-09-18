package tms.transferidor.entidade;

import java.math.BigDecimal;

public class Conta {
	private long numero;
	private String senhaCodificada;
	private BigDecimal saldo;
	public Conta() {
		super();
	}
	public Conta(String senhaCodificada, BigDecimal saldo) {
		super();
		this.senhaCodificada = senhaCodificada;
		this.saldo = saldo;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getSenhaCodificada() {
		return senhaCodificada;
	}
	public void setSenhaCodificada(String senhaCodificada) {
		this.senhaCodificada = senhaCodificada;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}
