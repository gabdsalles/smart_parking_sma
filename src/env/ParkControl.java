package env;

import cartago.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class ParkControl extends Artifact {
    List<Vaga> listaVagas = new ArrayList<Vaga>();
    Proposta proposta = new Proposta();

    void init() {
        defineObsProperty("VagaDisponivel", false);
        
        Random random = new Random();
        boolean isOcupada;
        int cobertasDisponiveis = 0;
        int curtasDisponiveis = 0;
        int longasDisponiveis = 0;
        int descobertasDisponiveis = 0;
        
        for (int i = 1; i < 7; i++) {
            listaVagas.add(new Vaga(i, TipoVagaEnum.CURTA));
            if (isOcupada = random.nextBoolean()) {
                listaVagas.get(i - 1).ocuparVaga();
            }
            else {
                curtasDisponiveis++;
            }
        }
        
        for (int i = 7; i < 14; i++) {
            listaVagas.add(new Vaga(i, TipoVagaEnum.LONGA));
            if (isOcupada = random.nextBoolean()) {
                listaVagas.get(i - 1).ocuparVaga();
            }else
                longasDisponiveis++;
        }
        
        for (int i = 14; i < 21; i++) {
            listaVagas.add(new Vaga(i, TipoVagaEnum.COBERTA));
            if (isOcupada = random.nextBoolean()) {
                listaVagas.get(i - 1).ocuparVaga();
            } else
                cobertasDisponiveis++;
        }

        for (int i = 21; i < 28; i++) {
            listaVagas.add(new Vaga(i, TipoVagaEnum.DESCOBERTA));
            if (isOcupada = random.nextBoolean()) {
                listaVagas.get(i - 1).ocuparVaga();
            } else
                descobertasDisponiveis++;
        }

        defineObsProperty("curtasDisponiveis", curtasDisponiveis);
        defineObsProperty("longasDisponiveis", longasDisponiveis);
        defineObsProperty("cobertasDisponiveis", cobertasDisponiveis);
        defineObsProperty("descobertasDisponiveis", descobertasDisponiveis);
    }

    @OPERATION
    void consultarVaga(String tipoVaga, String agente) {
        for (Vaga vaga : listaVagas) {
            if (vaga.getTipoVaga().equals(tipoVaga.toUpperCase()) && vaga.isDisponivel()) {
                defineObsProperty("vagaDisponivel", true, agente);
                defineObsProperty("idVaga", vaga.getId(), agente);
                vaga.ocuparVaga();
                return;
            }
        }
        defineObsProperty("vagaDisponivel", false);
    }

    @OPERATION
    void ocuparVaga(int idVaga) {
        for (Vaga vaga : listaVagas) {
            if ((vaga.getId() == idVaga) && vaga.isDisponivel()) {
                vaga.ocuparVaga();
                return;
            }
        }
    }

    @OPERATION
    void liberarVaga(int idVaga, String agente) {
        for (Vaga vaga : listaVagas) {
            if ((vaga.getId() == idVaga) && !vaga.isDisponivel()) {
                vaga.liberarVaga();
                defineObsProperty("motoristaSaindo", true, agente);
                return;
            }
        }
    }

    
    @OPERATION
    void liberarVaga(int idVaga) {
        for (Vaga vaga : listaVagas) {
            if ((vaga.getId() == idVaga) && !vaga.isDisponivel()) {
                vaga.liberarVaga();
                //defineObsProperty("motoristaSaindo", true, agente);
                return;
            }
        }
    }

    @OPERATION
    void analisarProposta(double margemLucro, String agent, int idVaga) {
        Double precoProposta = proposta.getPrecoProposta();
        Double precoTabela = proposta.getPrecoTabela();

        if (precoProposta >= precoTabela * 0.8) {
            switch (proposta.getTipoVaga()) {
                case "CURTA":
                    Double taxaDisponivel = verificarQuantidadeDisponivel(TipoVagaEnum.CURTA);
                    defineObsProperty("decisaoProposta", getResultadoProposta(taxaDisponivel, margemLucro), agent, idVaga);
                    break;
                case "LONGA":
                    taxaDisponivel = verificarQuantidadeDisponivel(TipoVagaEnum.LONGA);
                    defineObsProperty("decisaoProposta", getResultadoProposta(taxaDisponivel, margemLucro), agent, idVaga);
                    break;
                case "COBERTA":
                    taxaDisponivel = verificarQuantidadeDisponivel(TipoVagaEnum.COBERTA);
                    defineObsProperty("decisaoProposta", getResultadoProposta(taxaDisponivel, margemLucro), agent, idVaga);
                    break;
                case "DESCOBERTA":
                    taxaDisponivel = verificarQuantidadeDisponivel(TipoVagaEnum.DESCOBERTA);
                    defineObsProperty("decisaoProposta", getResultadoProposta(taxaDisponivel, margemLucro), agent, idVaga);
                    break;
            }
        } else {
            defineObsProperty("decisaoProposta", false, agent, idVaga);
        }
    }

    Double verificarQuantidadeDisponivel(TipoVagaEnum tipoVaga) {
        int quantidadeDisponivel = 0;
        int quantidadeVagas = 0;
        for (Vaga vaga : listaVagas) {
            if (vaga.getTipoVaga().equals(tipoVaga.tipoVaga().toUpperCase())) {
                quantidadeVagas++;
                if (vaga.isDisponivel())
                    quantidadeDisponivel++;
            }
        }

        return (double) (quantidadeDisponivel / quantidadeVagas);
    }
    
    boolean getResultadoProposta(Double taxaDisponivel, Double margemLucro) {
        Double precoProposta = proposta.getPrecoProposta();
        Double precoTabela = proposta.getPrecoTabela();
        
        if (taxaDisponivel >= 0.75) {
            return true;
        } else if (taxaDisponivel >= 0.5 && precoProposta >= precoTabela) {
            return true;
        } else if (precoProposta > precoTabela + (precoTabela * margemLucro)) {
            return true;
        } else {
            return false;
        }
    }

    @OPERATION
    void setIdVaga(int id) {
        proposta.setId(id);
    }

    @OPERATION
    void setTipoVaga(String tipoVaga) {
        proposta.setTipoVaga(tipoVaga);
    }

    @OPERATION
    void setPrecoProposta(double precoProposta) {
        proposta.setPrecoProposta(precoProposta);
    }

    @OPERATION
    void setPrecoTabela(double precoTabela) {
        proposta.setPrecoTabela(precoTabela);
    }

}