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

    public Automato uniaoAFN(Automato automato1, Automato automato2) {
        Automato automatoFinal = new Automato();

        // criando novo estado incial e setando no automato final
        Estado novoEstado = new Estado();
        novoEstado.setNome("q0");
        novoEstado.setId(0);
        novoEstado.setInicial(true);
        novoEstado.SetFinal(false);
        automatoFinal.setEstadoInicial(novoEstado);
        automatoFinal.getEstados().add(novoEstado);

        // renomeando os estados do automato 1
        automatoFinal.getEstados().addAll(renomeaEstados(automato1, 1));

        for (int j = 0; j < automato1.getTransicoes().size(); j++) {

        }

        return automatoFinal;
    }

    /**
     * 
     * @param automato
     * @param indiceAutomato indica se é o automato 1 ou o 2
     * @return
     */
    public List<Estado> renomeaEstados(Automato automato, int indiceAutomato) {

        List<Estado> novosEstados = new ArrayList<Estado>();
        Estado novoEstado = new Estado();
        // renomeando os estados do automato 1 e adicionando no automato final
        for (int i = 0; i < automato.getEstados().size(); i++) {
            novoEstado.setId(i + 1);
            novoEstado.setNome("q" + novoEstado.getId() + "_" + indiceAutomato);
            novosEstados.add(novoEstado);
        }

        return novosEstados;
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
