{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }
// Agent gerente in project trabalho1_sma

/* Initial beliefs and rules */
margemLucro(0.2).
caixa(1000).

/* Initial goals */

/* Plans */

+precoTabela (X) <-
    .print("Preco Tabela -> ", X);
    setPrecoTabela(X).

+!analisarProposta (Preco, Id, Tipo) <-
    .print("Proposta Recebida: R$", Preco, " id: ", Id, " - ", Tipo);
    consultTable(Tipo);
    setIdVaga(Id);
    setTipoVaga(Tipo);
    setPrecoProposta(Preco);
    analisarProposta.

+decisaoProposta (X) <-
    .send(motorista, achieve, decisaoProposta(X));
    .print("Definindo Proposta: ", X).