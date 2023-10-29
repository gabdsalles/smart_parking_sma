package env;

import cartago.*;
import java.util.List;
import java.util.ArrayList;

public class ParkControl extends Artifact {
    List<Vaga> listaVagas = new ArrayList<Vaga>();
    Proposta proposta = new Proposta();

    void init() {
        defineObsProperty("VagaDisponivel", false);
        
        for (int i = 1; i < 7; i++) 
            listaVagas.add(new Vaga(i, TipoVagaEnum.CURTA));
        
        for (int i = 7; i < 10; i++) 
            listaVagas.add(new Vaga(i, TipoVagaEnum.LONGA));
        
        for (int i = 10; i < 14; i++) 
            listaVagas.add(new Vaga(i, TipoVagaEnum.COBERTA));

        for (int i = 14; i < 20; i++)
            listaVagas.add(new Vaga(i, TipoVagaEnum.DESCOBERTA));

        for (Vaga vaga : listaVagas) {
            if (vaga.getId() == 6)
                return;
            vaga.ocuparVaga();
        }
    }

    @OPERATION
    void consultarVaga(String tipoVaga) {
        for (Vaga vaga : listaVagas) {
            if (vaga.getTipoVaga().equals(tipoVaga.toUpperCase()) && vaga.isDisponivel()) {
                defineObsProperty("vagaDisponivel", true);
                defineObsProperty("idVaga", vaga.getId());
                return;
            }
        }
        defineObsProperty("vagaDisponivel", false);
    }

    @OPERATION
    void reservarVaga(int idVaga) {
        for (Vaga vaga : listaVagas) {
            if ((vaga.getId() == idVaga) && vaga.isDisponivel()) {
                vaga.ocuparVaga();
                defineObsProperty("vagaDisponivel", false);
                defineObsProperty("idVaga", vaga.getId());
                return;
            }
        }
    }

    @OPERATION
    void liberarVaga(int idVaga) {
        for (Vaga vaga : listaVagas) {
            if ((vaga.getId() == idVaga) && !vaga.isDisponivel()) {
                vaga.liberarVaga();
                defineObsProperty("vagaDisponivel", true);
                defineObsProperty("idVaga", vaga.getId());
                return;
            }
        }
    }

    @OPERATION
    void analisarProposta() {
        Double precoProposta = proposta.getPrecoProposta();
        Double precoTabela = proposta.getPrecoTabela();

        if (precoProposta >= precoTabela * 0.8) {
            switch (proposta.getTipoVaga()) {
                case "CURTA":
                    Double taxaDisponivel = verificarQuantidadeDisponivel(TipoVagaEnum.CURTA);
                    defineObsProperty("decisaoProposta", getResultadoProposta(taxaDisponivel));
                    break;
            }
        } else {
            defineObsProperty("decisaoProposta", false);
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

    boolean getResultadoProposta(Double taxaDisponivel) {
        Double precoProposta = proposta.getPrecoProposta();
        Double precoTabela = proposta.getPrecoTabela();
        
        if (taxaDisponivel >= 0.75) {
            return true;
        } else if (taxaDisponivel >= 0.5 && precoProposta >= precoTabela) {
            return true;
        } else if (precoProposta > precoTabela * 1.25) {
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