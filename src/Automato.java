import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.ArrayList;
import java.util.List;

public class Automato {
    private List<Estado> estados;
    private List<Transicao> transicoes;


    Automato() {
        this.estados = new ArrayList<Estado>();
        this.transicoes = new ArrayList<Transicao>();
    }

    /**
     * Popula os dados do automato setando estados, transicoes.
     *
     * @param pathArquivo
     */
    public void carregaDados(String pathArquivo) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new File(pathArquivo), new AutomatoReader(this));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Automato uniaoAFN(Automato automato1, Automato automato2) {
        Automato automatoFinal = new Automato();

        // criando novo estado incial e setando no automato final
        Estado novoEstado = new Estado("q0", 0);
        novoEstado.setInicial(true);
        novoEstado.setFinal(false);
        automatoFinal.addEstado(novoEstado);

        // renomeando os estados do automato 1
        automatoFinal.getEstados().addAll(renomeaEstados(automato1, 1));

        for (int j = 0; j < automato1.getTransicoes().size(); j++) {
            // TODO: fazer as transicoes
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
        Estado novoEstado = new Estado("", 0); // Um estado "vazio"

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

    public void addEstado(Estado estado) {
        this.estados.add(estado);
    }

    public List<Transicao> getTransicoes() {
        return this.transicoes;
    }

    public void addTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
    }

    public Estado getEstadoInicial() {
        for (Estado estado : this.estados)
            if (estado.isInicial())
                return estado;

        return null;
    }

    public List<Estado> getEstadosFinais() {
        List<Estado> estados = new ArrayList<Estado>();

        for (Estado estado : this.estados)
            if (estado.isInicial())
                estados.add(estado);

        return estados;
    }
}

