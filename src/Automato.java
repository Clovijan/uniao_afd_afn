import java.util.ArrayList;
import java.util.List;

public class Automato {

    private List<Estado> estados;

    private List<Transicao> transicoes;

    private Estado estadoInicial;

    private List<Estado> estadosFinais = new ArrayList<Estado>();

    /**
     * Popula os dados do automato setando estados, transicoes.
     * 
     * @param pathArquivo
     */
    public void carregaDados(String pathArquivo) {

        Estado estados = new Estado();
        Transicao trasicoes = new Transicao();

        this.estados = estados.recuperaEstados(pathArquivo);
        this.transicoes = trasicoes.recuperaTransicoes(pathArquivo);

        for (Estado estado : this.estados) {
            if (estado.IsInicial())
                this.estadoInicial = estado;
            if (estado.IsFinal())
                this.estadosFinais.add(estado);
        }

    }

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
