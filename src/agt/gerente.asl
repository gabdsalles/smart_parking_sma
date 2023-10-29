{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }
// Agent gerente in project trabalho1_sma

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+!verificarDisponivel(TIPOVAGA) <-
    .print("verificando disponibilidade",TIPOVAGA);
    consultarVaga(TIPOVAGA).

+vagaDisponivel (X) <-
    .print("vaga disponivel: ", X);
    .send(motorista, tell, vagaDisponivel(X)).

+idVaga (X) <-
    .print("id da vaga: ", X);
    .send(motorista, tell, idVaga(X)).

+!fazerProposta (Preco, Id, Tipo) <-
    .print("fazendo proposta para vaga: ", Id);
    .print("preco da proposta: ", Preco);
    .print("tipo da vaga: ", Tipo);
    .send(dono, achieve, analisarProposta(Preco, Id, Tipo)).