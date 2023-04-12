public class Transicao {
    private int origem; // from
    private int destino; // to

    // Quando "" representa uma transição
    // vazia, ou seja, uma transição lambda.
    private String simbolo = ""; // read

    public int getOrigem() {
        return this.origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public int getDestino() {
        return this.destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getSimbolo() {
        return this.simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}

