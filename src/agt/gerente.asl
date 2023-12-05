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

+vagaDisponivel (X, Agent) <-
    .print("Vaga disponivel: ", X);
    .send(Agent, tell, vagaDisponivel(X)).

+idVaga (X, Agent) <-
    .print("Id da vaga: ", X);
    .send(Agent, tell, idVaga(X)).

+motoristaSaindo (X, Agent) <-
    .print("Motorista saindo da vaga");
    !repassarSaida(Agent).

+!repassarSaida(Agent) <- .send(Agent, achieve, sairEstacionamento).

+!verificarDisponivel(TIPOVAGA, Agente) <-
    .print("Verificando disponibilidade: ", TIPOVAGA, Agente);
    consultarVaga(TIPOVAGA, Agente).

+!repassarProposta (Preco, Id, Tipo, Agent) <-
    .print("Proposta para vaga id: ", Id);
    .print("Preco da proposta: R$", Preco);
    .print("Tipo da vaga: ", Tipo);
    .print("Gerente está mandando a proposta para o dono.");
    .print("Nome do agente: ", Agent);
    .send(dono, achieve, analisarProposta(Preco, Id, Tipo, Agent)).

+!liberarMotorista (Id, Agent) <-
    .print(Agent, " estacionando na vaga: ", Id);
    ocuparVaga(Id).
    //liberarVaga(Id, Agent).