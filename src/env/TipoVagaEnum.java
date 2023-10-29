package env;

public enum TipoVagaEnum {
    LONGA,
    CURTA,
    COBERTA,
    DESCOBERTA;

    public String tipoVaga() {
        switch (this) {
            case LONGA:
                return "Longa";
            case CURTA:
                return "Curta";
            case COBERTA:
                return "Coberta";
            case DESCOBERTA:
                return "Descoberta";
            default:
                return "Tipo de vaga inválido";
        }
    }

    public static TipoVagaEnum setTipoVaga(String tipoVaga) {
        tipoVaga = tipoVaga.toUpperCase();
        try {
            return TipoVagaEnum.valueOf(tipoVaga);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de vaga inválido");
            return null; 
        }
    }
}
