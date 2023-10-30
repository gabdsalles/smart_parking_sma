{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }
// Agent gerente in project trabalho1_sma

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+curtasDisponiveis (Qntd) <- .print("Quantidade de vagas curtas disponiveis: ", Qntd).

+longasDisponiveis(Qntd) <- .print("Quantidade de vagas longas disponíveis: ", Qntd).

+cobertasDisponiveis (Qntd) <- .print("Quantidade de vagas cobertas disponíveis: ", Qntd).

+descobertasDisponiveis (Qntd) <- .print("Quantidade de vagas descobertas disponíveis: ", Qntd).

+vagaDisponivel (X) <-
    .print("Vaga disponivel: ", X);
    .send(motorista, tell, vagaDisponivel(X)).

+idVaga (X) <-
    .print("Id da vaga: ", X);
    .send(motorista, tell, idVaga(X)).

+motoristaSaindo (X) <-
    .print("Motorista saindo da vaga");
    !repassarSaida.

+!repassarSaida <- .send(motorista, achieve, sairEstacionamento).

+!verificarDisponivel(TIPOVAGA) <-
    .print("Verificando disponibilidade: ", TIPOVAGA);
    consultarVaga(TIPOVAGA).

+!repassarProposta (Preco, Id, Tipo) <-
    .print("Proposta para vaga id: ", Id);
    .print("Preco da proposta: R$", Preco);
    .print("Tipo da vaga: ", Tipo);
    .print("Gerente está mandando a proposta para o dono.");
    .send(dono, achieve, analisarProposta(Preco, Id, Tipo)).

+!liberarMotorista (Id) <-
    .print("Motorista estacionando na vaga: ", Id);
    ocuparVaga(Id);
    liberarVaga(Id).