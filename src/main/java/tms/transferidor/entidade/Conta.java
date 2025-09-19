package tms.transferidor.entidade;

import java.math.BigDecimal;

public class Conta implements Cloneable {
	private int numero;
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
	public Conta(int numero, String senhaCodificada, BigDecimal saldo) {
		super();
		this.numero = numero;
		this.senhaCodificada = senhaCodificada;
		this.saldo = saldo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getSenhaCodificada() {
		return senhaCodificada;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "numero: " + numero + "\t saldo: R$ " + saldo + "\t senhaCodificada: " + senhaCodificada + "]";
	}
	@Override
	public Conta clone() {
		try {
			return (Conta) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
	
}
