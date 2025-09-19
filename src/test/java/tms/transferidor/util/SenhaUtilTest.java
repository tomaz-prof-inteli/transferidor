package tms.transferidor.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SenhaUtilTest {
    @Test
    void testHashValidacaoOk() throws Exception {
        String senhaOriginal = "minhaSenha123";

        // Gera hash da senha
        String hash = SenhaUtil.hash(senhaOriginal);

        // Validar senha correta deve retornar true
        assertTrue(SenhaUtil.validar(senhaOriginal, hash));
    }

    @Test
    void testSenhaIncorreta() throws Exception {
        String senhaOriginal = "minhaSenha123";
        String senhaErrada = "senhaErrada";

        String hash = SenhaUtil.hash(senhaOriginal);

        // Validar com senha incorreta deve retornar false
        assertFalse(SenhaUtil.validar(senhaErrada, hash));
    }

    @Test
    void testHashDiferenteParaSenhasIguais() throws Exception {
        String senhaOriginal = "minhaSenha123";

        String hash1 = SenhaUtil.hash(senhaOriginal);
        String hash2 = SenhaUtil.hash(senhaOriginal);

        // Os hashes devem ser diferentes por causa do salt diferente
        assertNotEquals(hash1, hash2);
    }
}
