package env;

public class Vaga {
    private int id;
    private TipoVagaEnum tipoVaga;
    private boolean disponivel;

    public Vaga(int id, TipoVagaEnum tipoVaga) {
        this.id = id;
        this.tipoVaga = tipoVaga;
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void ocuparVaga() {
        this.disponivel = false;
    }

    public void liberarVaga() {
        this.disponivel = true;
    }

    public String getVagaInfo() {
        System.out.println("Vaga: " + this.id + " - " + this.tipoVaga.tipoVaga() + " - " + this.disponivel);
        return "Vaga: " + this.id + " - " + this.tipoVaga.tipoVaga() + " - " + this.disponivel;
    }

    public String getTipoVaga() {
        return this.tipoVaga.tipoVaga().toString().toUpperCase();
    }
}