import java.util.List;

public class Automato {

    private List<Estado> estados;

    private List<Transicao> transicoes;

    private Estado estadoInicial;

    private List<Estado> estadosFinais;

    public List<Estado> getEstados() {
        return this.estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Transicao> getTransicoes() {
        return this.transicoes;
    }

    public void setTransicoes(List<Transicao> transicoes) {
        this.transicoes = transicoes;
    }

    public Estado getEstadoInicial() {
        return this.estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public List<Estado> getEstadosFinais() {
        return this.estadosFinais;
    }

    public void setEstadosFinais(List<Estado> estadosFinais) {
        this.estadosFinais = estadosFinais;
    }
}
