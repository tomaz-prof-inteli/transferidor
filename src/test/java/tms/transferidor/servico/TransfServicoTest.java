package tms.transferidor.servico;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tms.transferidor.dao.ContaDao;
import tms.transferidor.entidade.Conta;
import tms.transferidor.util.SenhaUtil;

class TransfServicoTest {

    @Mock
    private ContaDao contaDao;

    @InjectMocks
    private TransfServico transfServico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock estático para SenhaUtil.validar
        // Será feito inline em cada teste que precisar
    }

    @Test
    void lerConta_deveRetornarConta_quandoDaoRetornaConta() throws SQLException {
        Conta conta = new Conta();
        conta.setNumero(123);
        when(contaDao.ler(123)).thenReturn(conta);

        Conta resultado = transfServico.lerConta(123);

        assertNotNull(resultado);
        assertEquals(123, resultado.getNumero());
        verify(contaDao).ler(123);
    }

    @Test
    void autenticar_deveSerSucesso_quandoSenhaValida() throws SenhaInvalidaException {
        Conta conta = new Conta();
        conta.setSenhaCodificada("abc123");

        try (var mockStatic = mockStatic(SenhaUtil.class)) {
            mockStatic.when(() -> SenhaUtil.validar("senhaCorreta", "abc123")).thenReturn(true);

            assertDoesNotThrow(() -> transfServico.autenticar("senhaCorreta", conta));

            mockStatic.verify(() -> SenhaUtil.validar("senhaCorreta", "abc123"), times(1));
        }
    }

    @Test
    void autenticar_deveLancarExcecao_quandoSenhaInvalida() {
        Conta conta = new Conta();
        conta.setSenhaCodificada("abc123");

        try (var mockStatic = mockStatic(SenhaUtil.class)) {
            mockStatic.when(() -> SenhaUtil.validar("senhaErrada", "abc123")).thenReturn(false);

            assertThrows(SenhaInvalidaException.class, 
                () -> transfServico.autenticar("senhaErrada", conta));

            mockStatic.verify(() -> SenhaUtil.validar("senhaErrada", "abc123"), times(1));
        }
    }

    @Test
    void transferir_deveAtualizarSaldoECodarTransacao_quandoValorValidoESaldoSuficiente() throws Exception {
        Conta origem = new Conta();
        origem.setSaldo(new BigDecimal("5000.00"));

        Conta destino = new Conta();
        destino.setSaldo(new BigDecimal("3000.00"));

        BigDecimal valor = new BigDecimal("1000.00");

        doNothing().when(contaDao).iniciarTransacao();
        doNothing().when(contaDao).atualizarSaldo(origem);
        doNothing().when(contaDao).atualizarSaldo(destino);
        doNothing().when(contaDao).commitTransacao();

        transfServico.transferir(origem, destino, valor);

        assertEquals(new BigDecimal("4000.00"), origem.getSaldo());
        assertEquals(new BigDecimal("4000.00"), destino.getSaldo());

        verify(contaDao).iniciarTransacao();
        verify(contaDao).atualizarSaldo(origem);
        verify(contaDao).atualizarSaldo(destino);
        verify(contaDao).commitTransacao();
        verify(contaDao, never()).rollbackTransacao();
    }

    @Test
    void transferir_deveLancarExcecaoSaldoInsuficiente_quandoSaldoOrigemMenorQueValor() {
        Conta origem = new Conta();
        origem.setSaldo(new BigDecimal("500.00"));

        Conta destino = new Conta();
        destino.setSaldo(new BigDecimal("3000.00"));

        BigDecimal valor = new BigDecimal("1000.00");

        assertThrows(SaldoInsuficienteException.class,
            () -> transfServico.transferir(origem, destino, valor));
        
        // ContaDao não deve ser chamado
        verifyNoInteractions(contaDao);
    }

    @Test
    void transferir_deveLancarExcecaoValorAcimaLimite_quandoValorMaiorQueMaximo() {
        Conta origem = new Conta();
        origem.setSaldo(new BigDecimal("20000.00"));

        Conta destino = new Conta();
        destino.setSaldo(new BigDecimal("3000.00"));

        BigDecimal valor = new BigDecimal("10001.00");

        assertThrows(ValorAcimaLimiteException.class,
            () -> transfServico.transferir(origem, destino, valor));

        verifyNoInteractions(contaDao);
    }

    @Test
    void transferir_deveFazerRollbackEPropagarExcecao_quandoDaoLancarSQLException() throws SQLException {
        Conta origem = new Conta();
        origem.setSaldo(new BigDecimal("5000.00"));

        Conta destino = new Conta();
        destino.setSaldo(new BigDecimal("3000.00"));

        BigDecimal valor = new BigDecimal("1000.00");

        doNothing().when(contaDao).iniciarTransacao();
        doThrow(new SQLException("Erro no banco")).when(contaDao).atualizarSaldo(destino);
        doNothing().when(contaDao).rollbackTransacao();

        SQLException e = assertThrows(SQLException.class,
            () -> transfServico.transferir(origem, destino, valor));

        assertEquals("Erro no banco", e.getMessage());

        verify(contaDao).iniciarTransacao();
        verify(contaDao).atualizarSaldo(origem);
        verify(contaDao).atualizarSaldo(destino);
        verify(contaDao).rollbackTransacao();
        verify(contaDao, never()).commitTransacao();
    }
}
