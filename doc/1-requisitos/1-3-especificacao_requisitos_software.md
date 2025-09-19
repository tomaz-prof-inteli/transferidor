# Especificação de Requisitos de Software (SRS)

## Requisitos Funcionais

Os seguintes requisitos foram identificados a partir do caso de uso CDU1 TRANSFERIR VALOR PARA OUTRA CONTA.

##### RF001 – Entrada do número da conta origem
O sistema deve permitir que o usuário informe o número da conta de origem para iniciar a transferência.

##### RF002 – Validação de existência da conta origem
O sistema deve verificar se a conta de origem informada existe no banco de dados. Caso contrário, deve informar erro ao usuário.

##### RF003 – Autenticação do usuário pela senha
O sistema deve solicitar a senha do usuário titular da conta de origem e validar sua autenticidade.

##### RF004 – Informação do número da conta destino
O sistema deve permitir que o usuário informe o número da conta de destino da transferência.

##### RF005 – Validação de existência da conta destino
O sistema deve verificar se a conta de destino informada existe no banco de dados. Caso contrário, deve informar erro ao usuário.

##### RF006 – Entrada do valor de transferência
O sistema deve permitir que o usuário informe o valor a ser transferido entre as contas.

##### RF007 – Verificação de saldo suficiente
O sistema deve verificar se o saldo da conta de origem é suficiente para cobrir o valor da transferência. Se não houver saldo suficiente, deve informar erro.

##### RF008 – Verificação de limite máximo da transferência
O sistema deve verificar se o valor informado está dentro do limite máximo permitido para transferência. Caso contrário, deve informar erro.

##### RF009 – Execução da transferência
O sistema deve transferir o valor da conta origem para a conta destino como operação atômica, garantindo a integridade dos dados.

##### RF010 – Confirmação da transferência
O sistema deve informar ao usuário quando a transferência for efetuada com sucesso.
Requisitos Não Funcionais


## Requisitos não funcionais

Os seguintes requisitos não funcionais foram identificados no workshop com o parceiro.

##### RNF001 – Segurança na autenticação
O sistema deve garantir que a autenticação seja feita mediante senha que deve ser armazenada de forma segura no sistema.

##### RNF002 – Transação atômica no banco de dados
As operações de débito e crédito devem ser realizadas dentro de uma transação que garanta atomicidade, para evitar inconsistências.


## Restrições

##### RES001 - Linguagem de programação
O sistema deverá ser desenvolvido utilizando a linguagem de programação Java.

##### RES002 - Persistência
O sistema deverá persistir dados utilizando SQLite.

