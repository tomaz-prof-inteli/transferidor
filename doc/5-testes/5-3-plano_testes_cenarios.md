# Plano de Testes de Cenários — Caso de Uso: Transferir valor para outra conta

## Cenário 1 — Transferência bem-sucedida


Pré-condições:

- Conta origem número: 1001, saldo: R$ 5.000,00.

- Conta destino número: 2001, saldo: R$ 3.000,00.

- Senha da conta origem válida: "senha123".

- Valor da transferência: R$ 1.000,00 (dentro do limite máximo R$ 10.000,00).


Passos:

1. Informar número da conta origem: 1001.

2. Validar existência da conta origem.

3. Informar senha: "senha123".

4. Validar a senha.

5. Informar número da conta destino: 2001.

6. Validar existência da conta destino.

7. Informar valor da transferência: R$ 1.000,00.

8. Verificar saldo suficiente na conta origem.

9. Verificar limite máximo.

10. Realizar a transferência.

11. Confirmar sucesso da operação.


Resultado esperado:

- Saldo da conta origem atualizado para R$ 4.000,00.

- Saldo da conta destino atualizado para R$ 4.000,00.

- Mensagem de sucesso exibida.


## Cenário 2 — Conta origem inexistente

Pré-condições:

- Conta origem número: 9999 (não existe).


Passos:

1. Informar número da conta origem: 9999.

Resultado esperado:

- Sistema informa que a conta não existe.

- Transferência é abortada.

## Cenário 3 — Senha inválida

Pré-condições:

- Conta origem número: 1001, saldo: R$ 5.000,00.

- Senha válida: "senha123".

- Senha incorreta informada: "senhaErrada".

Passos:

1. Informar número da conta origem: 1001.

2. Validar existência da conta.

3. Informar senha: "senhaErrada".

Resultado esperado:

- Sistema rejeita autenticação com mensagem de senha inválida.

- Transferência é abortada.

## Cenário 4 — Conta destino inexistente

Pré-condições:

- Conta origem número: 1001, saldo: R$ 5.000,00.

- Conta destino número: 8888 (inexistente).

- Senha válida: "senha123".

Passos:

1. Informar número da conta origem: 1001.

2. Validar conta origem.

3. Informar senha: "senha123".

4. Validar senha.

5. Informar número da conta destino: 8888.

Resultado esperado:

- Sistema informa que a conta destino não existe.

- Transferência é abortada.

## Cenário 5 — Saldo insuficiente

Pré-condições:

- Conta origem número: 1001, saldo: R$ 500,00.

- Conta destino número: 2001, saldo: R$ 3.000,00.

- Valor da transferência: R$ 1.000,00.

- Senha válida: "senha123".

Passos:

1. Contas e senha conforme pré-condições.

2. Informar número da conta origem: 1001.

3. Validar conta origem.

4. Informar senha: "senha123".

5. Validar senha.

6. Informar número da conta destino: 2001.

7. Validar conta destino.

8. Informar valor da transferência: R$ 1.000,00.

Resultado esperado:

- Sistema detecta saldo insuficiente e rejeita operação com mensagem adequada.

- Transferência é abortada.

## Cenário 6 — Valor acima do limite máximo

Pré-condições:

- Conta origem número: 1001, saldo: R$ 20.000,00.

- Conta destino número: 2001, saldo: R$ 3.000,00.

- Senha válida: "senha123".

- Valor da transferência: R$ 10.001,00 (limite máximo: R$ 10.000,00).

Passos:

1. Informar número da conta origem: 1001.

2. Validar conta origem.

3. Informar senha: "senha123".

4. Validar senha.

5. Informar número da conta destino: 2001.

6. Validar conta destino.

7. Informar valor da transferência: R$ 10.001,00.

Resultado esperado:

- Sistema rejeita a operação com mensagem de valor acima do limite.

- Transferência é abortada.

## Cenário 7 — Valor igual ao limite máximo permitido

Pré-condições:

- Conta origem número: 1001, saldo: R$ 15.000,00.

- Conta destino número: 2001, saldo: R$ 3.000,00.

- Senha válida: "senha123".

- Valor da transferência: R$ 10.000,00 (exatamente o limite).

Passos:

1. Informar número da conta origem: 1001.

2. Validar conta origem.

3. Informar senha: "senha123".

4. Validar senha.

5. Informar número da conta destino: 2001.

6. Validar conta destino.

7. Informar valor da transferência: R$ 10.000,00.

Resultado esperado:

- Sistema aceita o valor e realiza a transferência normalmente.

- Saldo da conta origem atualizado para R$ 5.000,00.

- Saldo da conta destino atualizado para R$ 13.000,00.

- Mensagem de sucesso exibida.

## Cenário 8 — Transferência com valor igual ao saldo da conta origem

Pré-condições:

- Conta origem número: 1001, saldo: R$ 1.000,00.

- Conta destino número: 2001, saldo: R$ 2.000,00.

- Senha válida: "senha123".

- Valor da transferência: R$ 1.000,00 (igual ao saldo da conta origem).

Passos:

1. Informar número da conta origem: 1001.

2. Validar existência da conta origem.

3. Informar senha: "senha123".

4. Validar senha.

5. Informar número da conta destino: 2001.

6. Validar existência da conta destino.

7. Informar valor da transferência: R$ 1.000,00.

Resultado esperado:

- Sistema aceita o valor e realiza a transferência com sucesso.

- Saldo da conta origem atualizado para R$ 0,00.

- Saldo da conta destino atualizado para R$ 3.000,00.

- Mensagem de sucesso exibida.
