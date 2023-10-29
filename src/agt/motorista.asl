{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }
// Agent motorista in project trabalho1_sma

/* Initial beliefs and rules */
tipoVeiculo(carro).
tipoVaga("curta").
tentativas(0).

/* Initial goals */

!estacionar.

/* Plans */

+!estacionar : true
    <- !checarDisponibilidade.

+precoVaga (X) <- .print("Proposta: R$", X).

+!checarDisponibilidade : true <-
        ?tipoVaga(X);
        consultPrice(X);
        .print("Verificando preco da vaga");
        .send(gerente, achieve, verificarDisponivel(X)).

+vagaDisponivel (X) <- 
    if(X == true) {
        .print("Vaga disponivel");
        .print("Fazer proposta");
        !fazerProposta;
    } elif (X == false) {
        .print("Vaga indisponivel");
        .print("Sair do Estacionamento");
        // .send(gerente, achieve, !fazerProposta).
    }.

+!fazerProposta <-
    .print("Fazendo proposta");
    ?tentativas(X);
    +tentativas(X+1);
    ?precoVaga(Preco);
    ?idVaga(Id);
    ?tipoVaga(Tipo);
    .print("Preco da vaga -> ", Preco);
    .print("Id da vaga -> ", Id);
    .print("Tipo da vaga -> ", Tipo);
    .print("Tentativas -> ", X);
    .send(gerente, achieve, fazerProposta(Preco, Id, Tipo)).

+idVaga (X) <- .print("IdVaga retornado: ", X).

+!decisaoProposta(X) <-
    .print("Decisao da proposta via dono da vaga -> ", X);
    ?tentativas(Tentativas);

    if(X == true) {
        .print("Proposta aceita");
        .print("Estacionar");
    } elif (X == false) {
        .print("Proposta recusada");
        if (Tentativas == 1) {
            .print("Refazer proposta");
            !refazerProposta;
        } elif (Tentativas == 2) {
            .print("Sair do Estacionamento");
        }
    }.

+!refazerProposta <-
    ?vagaDisponivel(X);
    -vagaDisponivel(X);
    !checarDisponibilidade;
    ?vagaDisponivel(Y);
    if (Y == true) {
        !fazerProposta;
    } elif (Y == false) {
        !sairEstacionamento;
    }.
