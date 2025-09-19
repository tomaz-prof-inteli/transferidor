package tms.transferidor.casouso;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tms.transferidor.entidade.Conta;
import tms.transferidor.servico.SaldoInsuficienteException;
import tms.transferidor.servico.SenhaInvalidaException;
import tms.transferidor.servico.TransfServico;
import tms.transferidor.servico.ValorAcimaLimiteException;
import tms.transferidor.ui.TransfUI;

class TransfCasoUsoIntegrationTest {

    @Mock
    private TransfUI ui;

    @Mock
    private TransfServico servico;

    @InjectMocks
    private TransfCasoUso casoUso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executar_deveExecutarFluxoComSucesso() throws Exception {
        Conta contaOrigem = mock(Conta.class);
        Conta contaDestino = mock(Conta.class);

        when(ui.lerNumeroContaOrigem()).thenReturn(1001);
        when(servico.lerConta(1001)).thenReturn(contaOrigem);
        when(ui.lerSenha()).thenReturn("senha123");
        // autenticar não lança exceção para senha correta
        doNothing().when(servico).autenticar("senha123", contaOrigem);

        when(ui.lerNumeroContaDestino()).thenReturn(2001);
        when(servico.lerConta(2001)).thenReturn(contaDestino);
        when(ui.lerValorTransferencia()).thenReturn(new BigDecimal("1000.00"));
        doNothing().when(servico).transferir(contaOrigem, contaDestino, new BigDecimal("1000.00"));

        casoUso.executar();

        verify(ui).lerNumeroContaOrigem();
        verify(servico).lerConta(1001);
        verify(ui).lerSenha();
        verify(servico).autenticar("senha123", contaOrigem);
        verify(ui).lerNumeroContaDestino();
        verify(servico).lerConta(2001);
        verify(ui).lerValorTransferencia();
        verify(servico).transferir(contaOrigem, contaDestino, new BigDecimal("1000.00"));
        verify(ui).avisarTransferenciaEfetuada();
    }

    @Test
    void executar_deveTratarSenhaInvalidaException() throws Exception {
        Conta contaOrigem = mock(Conta.class);

        when(ui.lerNumeroContaOrigem()).thenReturn(1001);
        when(servico.lerConta(1001)).thenReturn(contaOrigem);
        when(ui.lerSenha()).thenReturn("senhaErrada");
        doThrow(new SenhaInvalidaException()).when(servico).autenticar("senhaErrada", contaOrigem);

        casoUso.executar();

        verify(ui).avisarSenhaInvalida();

        // Não deve continuar após exceção
        verify(ui, never()).lerNumeroContaDestino();
        verify(ui, never()).lerValorTransferencia();
        verify(ui, never()).avisarTransferenciaEfetuada();
    }

    @Test
    void executar_deveTratarSaldoInsuficienteException() throws Exception {
        Conta contaOrigem = mock(Conta.class);
        Conta contaDestino = mock(Conta.class);

        when(ui.lerNumeroContaOrigem()).thenReturn(1001);
        when(servico.lerConta(1001)).thenReturn(contaOrigem);
        when(ui.lerSenha()).thenReturn("senha123");
        doNothing().when(servico).autenticar("senha123", contaOrigem);
        when(ui.lerNumeroContaDestino()).thenReturn(2001);
        when(servico.lerConta(2001)).thenReturn(contaDestino);
        when(ui.lerValorTransferencia()).thenReturn(new BigDecimal("10000.00"));
        doThrow(new SaldoInsuficienteException()).when(servico)
                .transferir(contaOrigem, contaDestino, new BigDecimal("10000.00"));

        casoUso.executar();

        verify(ui).avisarSaldoInsuficiente();

        verify(ui, never()).avisarTransferenciaEfetuada();
    }

    @Test
    void executar_deveTratarValorAcimaLimiteException() throws Exception {
        Conta contaOrigem = mock(Conta.class);
        Conta contaDestino = mock(Conta.class);

        when(ui.lerNumeroContaOrigem()).thenReturn(1001);
        when(servico.lerConta(1001)).thenReturn(contaOrigem);
        when(ui.lerSenha()).thenReturn("senha123");
        doNothing().when(servico).autenticar("senha123", contaOrigem);
        when(ui.lerNumeroContaDestino()).thenReturn(2001);
        when(servico.lerConta(2001)).thenReturn(contaDestino);
        when(ui.lerValorTransferencia()).thenReturn(new BigDecimal("10001.00"));
        doThrow(new ValorAcimaLimiteException()).when(servico)
                .transferir(contaOrigem, contaDestino, new BigDecimal("10001.00"));

        casoUso.executar();

        verify(ui).avisarValorAcimaLimite();

        verify(ui, never()).avisarTransferenciaEfetuada();
    }

    @Test
    void executar_lancaSQLException_quandoServicoLancarSQLException() throws Exception {
        Conta contaOrigem = mock(Conta.class);

        when(ui.lerNumeroContaOrigem()).thenReturn(1001);
        when(servico.lerConta(1001)).thenReturn(contaOrigem);
        when(ui.lerSenha()).thenReturn("senha123");
        doNothing().when(servico).autenticar("senha123", contaOrigem);
        when(ui.lerNumeroContaDestino()).thenReturn(2001);
        // Simulando SQLException ao obter conta destino
        when(servico.lerConta(2001)).thenThrow(new SQLException("Erro no banco"));

        // SQLException deve ser propagada pelo método
        assertThrows(SQLException.class, () -> casoUso.executar());
    }
}
