package tms.transferidor.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tms.transferidor.entidade.Conta;

public class ContaDaoIntegrationTest {

    private static Connection connection;
    private ContaDao contaDao;

    @BeforeAll
    static void setupDatabase() throws Exception {
        // Criar conexão com SQLite em memória
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        try (Statement stmt = connection.createStatement()) {
            // Criar tabela Conta para teste
            stmt.executeUpdate("CREATE TABLE contas ("
            		+ "    numero INTEGER PRIMARY KEY AUTOINCREMENT,"
            		+ "    senhaCodificada TEXT NOT NULL,"
            		+ "    saldo NUMERIC NOT NULL"
            		+ ")");
        }
    }

    @AfterAll
    static void closeDatabase() throws Exception {
        connection.close();
    }

    @BeforeEach
    void setup() throws SQLException {
        contaDao = new ContaDao(connection);
    }

    @AfterEach
    void cleanTable() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM contas");
        }
    }

    @Test
    void ler_deveRetornarConta_quandoContaExiste() throws Exception {
        String insert = "INSERT INTO contas (numero, saldo, senhaCodificada) VALUES (1, 5000.00, 'abc123')";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(insert);
        }

        Conta conta = contaDao.ler(1);

        assertNotNull(conta);
        assertEquals(1, conta.getNumero());
        assertTrue(conta.getSaldo().compareTo(new BigDecimal("5000.00")) == 0);
        assertEquals("abc123", conta.getSenhaCodificada());
    }

    @Test
    void atualizarSaldo_deveAtualizarSaldoNoBanco() throws Exception {
        String insert = "INSERT INTO contas (numero, saldo, senhaCodificada) VALUES (2, 1000.00, 'xyz789')";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(insert);
        }

        Conta conta = contaDao.ler(2);
        conta.setSaldo(new BigDecimal("1500.00"));

        contaDao.atualizarSaldo(conta);

        Conta contaAtualizada = contaDao.ler(2);
        assertTrue(contaAtualizada.getSaldo().compareTo(new BigDecimal("1500.00")) == 0);    }

}
