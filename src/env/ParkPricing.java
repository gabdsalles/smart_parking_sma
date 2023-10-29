package env;

import cartago.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ParkPricing extends Artifact { 
    private Map<TipoVagaEnum, Double> precos;
    
    void init() {
        defineObsProperty("precoVaga", 0);

        precos = new HashMap<>();
        precos.put(TipoVagaEnum.CURTA, 10.0);
        precos.put(TipoVagaEnum.LONGA, 15.0);
        precos.put(TipoVagaEnum.COBERTA, 20.0);
        precos.put(TipoVagaEnum.DESCOBERTA, 12.0);
    }

    public Double getPreco(TipoVagaEnum tipoVaga) {
        return precos.getOrDefault(tipoVaga, 0.0);
    }

    @OPERATION
    void consultTable(String tipoVaga) {
        if (tipoVaga != null) {
            TipoVagaEnum typeVaga = TipoVagaEnum.setTipoVaga(tipoVaga);
            Double precoTabela = getPreco(typeVaga);

            defineObsProperty("precoTabela", precoTabela);
        }
    }
    
    @OPERATION
    void consultPrice(String tipoVaga) {
        if (tipoVaga != null) {
            TipoVagaEnum typeVaga = TipoVagaEnum.setTipoVaga(tipoVaga);
            Double precoTabela = getPreco(typeVaga);
            
            barganhar(precoTabela);
        }
    }

    void barganhar(Double precoTabela) {
        Random random = new Random();
        
        Double min = 0.8;
        Double max = 1.5;

        Double valorAleatorio = min + (max - min) * random.nextDouble();

        Double precoFinal = precoTabela * valorAleatorio;

        precoFinal = Math.round(precoFinal * 100.0) / 100.0;
        
        defineObsProperty("precoVaga", precoFinal);
    }
}