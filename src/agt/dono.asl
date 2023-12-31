{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }
// Agent dono in project trabalho1_sma

/* Initial beliefs and rules */
margemLucro(0.2).

/* Initial goals */

/* Plans */

+precoTabela (X) <-
    .print("Preco da Tabela: R$", X);
    setPrecoTabela(X).

+decisaoProposta (X, Agent, IdVaga) <-
    .send(Agent, achieve, analisarResposta(X));
    if (X == true) {
        .print("Enviando resultado da proposta para o motorista: proposta aceita.");
    } elif (X == false) {
        .print("Enviando resultado da proposta para o motorista: proposta rejeitada.");
        liberarVaga(IdVaga);
    }.

+!analisarProposta (Preco, Id, Tipo, Agent) <-
    .print("Proposta Recebida: R$", Preco, " id: ", Id, " - ", Tipo);
    consultTable(Tipo);
    setIdVaga(Id);
    setTipoVaga(Tipo);
    setPrecoProposta(Preco);
    ?margemLucro(Margem);
    analisarProposta(Margem, Agent, Id).