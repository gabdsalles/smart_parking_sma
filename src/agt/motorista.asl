{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }
// Agent motorista in project trabalho1_sma

/* Initial beliefs and rules */
tentativas(0).

/* Initial goals */

!estacionar.

/* Plans */

+tipoVaga(X) <- .print("Tipo da vaga escolhido: ", X).

+precoVaga (X) <- .print("Proposta: R$", X).

+idVaga (X) <- .print("IdVaga retornado: ", X).

+vagaDisponivel (X) <- 
    if(X == true) {
        !fazerProposta;
    } elif (X == false) {
        !sairEstacionamento;
    }.

+!estacionar : true <- 
    defineVagaType;
    !checarDisponibilidade.

+!checarDisponibilidade : true <-
        ?tipoVaga(X);
        .print("Verificando preco da vaga");
        consultPrice(X);
        .my_name(Agent);
        .send(gerente, achieve, verificarDisponivel(X, Agent)).

+!fazerProposta <-
    .print("Fazendo proposta");
    ?tentativas(X);
    +tentativas(X+1);
    ?precoVaga(Preco);
    ?idVaga(Id);
    ?tipoVaga(Tipo);
    .my_name(Agent);
    .print("Nome do agente: ", Agent);
    .print("Preco da vaga -> ", Preco);
    .print("Id da vaga -> ", Id);
    .print("Tipo da vaga -> ", Tipo);
    .print("Tentativas -> ", X);
    .send(gerente, achieve, repassarProposta(Preco, Id, Tipo, Agent)).

+!analisarResposta(X) <-
    ?tentativas(Tentativas);

    if(X == true) {
        .print("Proposta aceita");
        ?idVaga(Id);
        .my_name(Agent);
        .send(gerente, achieve, liberarMotorista(Id, Agent));
        .print("Estacionar");
    } elif (X == false) {
        .print("Proposta recusada");
        if (Tentativas == 1) {
            .print("Refazer proposta");
            .print("--------------------------------------------------------------");
            !refazerProposta;
        } elif (Tentativas == 2) {
            !sairEstacionamento;
        }
    }.

+!refazerProposta <-
    //?vagaDisponivel(X);
    //-vagaDisponivel(X);
    !checarDisponibilidade;
    ?vagaDisponivel(Y);
    
    if (Y == true) {
        !fazerProposta;
    } elif (Y == false) {
        !sairEstacionamento;
    }.

+!sairEstacionamento <-
    .print("Saindo do estacionamento").
