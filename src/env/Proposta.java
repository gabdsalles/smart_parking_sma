package env;

public class Proposta {
    private int id;
    private Double precoProposta;
    private Double precoTabela;
    private TipoVagaEnum tipoVaga;
    private Double margemLucro;

    public Proposta() {}

    public int getId() {
        return id;
    }

    public Double getPrecoProposta() {
        return precoProposta;
    }

    public Double getPrecoTabela() {
        return precoTabela;
    }

    public String getTipoVaga() {
        return tipoVaga.tipoVaga().toUpperCase();
    }

    public Double getMargemLucro() {
        return margemLucro;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrecoProposta(Double precoProposta) {
        this.precoProposta = precoProposta;
    }

    public void setPrecoTabela(Double precoTabela) {
        this.precoTabela = precoTabela;
    }

    public void setTipoVaga(String tipoVaga) {
        this.tipoVaga = TipoVagaEnum.setTipoVaga(tipoVaga);
    }

    public void setMargemLucro(Double margemLucro) {
        this.margemLucro = margemLucro;
    }
}