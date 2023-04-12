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
            parser.parse(new File(pathArquivo), new AutomatoHandler(this));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Realiza a União de 2 automatos Finitos não deterministicos
     *
     * @param automato1
     * @param automato2
     * @return
     */
    public Automato uniaoAFN(Automato automato1, Automato automato2) {
        Automato automatoFinal = new Automato();

        // criando novo estado incial e setando no automato final
        Estado novoEstado = new Estado("q0", 0);
        novoEstado.setInicial(true);
        novoEstado.setFinal(false);
        automatoFinal.addEstado(novoEstado);

        // Criando o estado final
        int idEstadoFinal = automato1.getEstados().size() + automato2.getEstados().size() + 1;
        novoEstado = new Estado("q" + idEstadoFinal, idEstadoFinal);
        novoEstado.setInicial(false);
        novoEstado.setFinal(true);
        automatoFinal.addEstado(novoEstado);

        // Gerando transicao do novo estado inicial criado para os antigos inicias do
        // automatos originais
        automatoFinal.addAllTransicao(geraTransicoesNovoEstadoInicial(automato1, automato2));

        // renomeia e adiciona os estados no automato final
        automatoFinal.addAllEstado(renomeiaEstados(automato1, automato2));
        automatoFinal.addAllTransicao(reorganizarTransicoes(automato1, automato2));

        return automatoFinal;
    }

    /**
     * Renomeia os estados dos automatos originais
     *
     * @param automato1
     * @param automato2
     * @return
     */
    private List<Estado> renomeiaEstados(Automato automato1, Automato automato2) {
        List<Estado> listaEstados = new ArrayList<Estado>();
        Estado novoEstado = new Estado(null, 0);

        for (Estado estado : automato1.getEstados()) {
            novoEstado.setId(estado.getId() + 1);
            novoEstado.setNome("q" + (estado.getId() + 1) + "_1"); // o nº 1 significa que é do automato 1
            listaEstados.add(novoEstado);
        }

        for (Estado estado : automato2.getEstados()) {
            int idNovoEstado = estado.getId() + automato1.getEstados().size() + 1;
            novoEstado.setId(idNovoEstado);
            novoEstado.setNome("q" + idNovoEstado + "_2"); // o nº 1 significa que é do automato 2
            listaEstados.add(novoEstado);
        }

        return listaEstados;
    }

    private List<Transicao> reorganizarTransicoes(Automato automato1, Automato automato2) {
        List<Transicao> listaTransicoes = new ArrayList<Transicao>();
        Transicao novaTransicao = new Transicao();

        for (Transicao transicao : automato1.getTransicoes()) {
            novaTransicao.setOrigem(transicao.getOrigem() + 1);
            novaTransicao.setDestino(transicao.getDestino() + 1);
            novaTransicao.setSimbolo(transicao.getSimbolo());
            listaTransicoes.add(novaTransicao);
        }

        for (Transicao transicao : automato2.getTransicoes()) {
            novaTransicao.setOrigem(transicao.getOrigem() + 1 + automato2.getEstados().size());
            novaTransicao.setDestino(transicao.getDestino() + 1 + automato2.getEstados().size());
            novaTransicao.setSimbolo(transicao.getSimbolo());
            listaTransicoes.add(novaTransicao);
        }
        return listaTransicoes;
    }

    /**
     * Gera novas transições do novo estado inicial criado para os antigos
     * estados iniciais dos automatos originais
     *
     * @param automato1
     * @param automato2
     * @return
     */
    private List<Transicao> geraTransicoesNovoEstadoInicial(Automato automato1, Automato automato2) {
        List<Transicao> novasTransicoes = new ArrayList<Transicao>();
        Transicao novaTransicao = new Transicao();

        // gerando inicialmente para o automato 1
        novaTransicao.setOrigem(0);
        novaTransicao.setDestino(automato1.getEstadoInicial().getId() + 1);
        novaTransicao.setSimbolo("");
        novasTransicoes.add(novaTransicao);

        // gerando para o automato 2
        novaTransicao.setOrigem(0);
        novaTransicao.setDestino(automato2.getEstadoInicial().getId() + 1 + automato1.getEstados().size());
        novaTransicao.setSimbolo("");
        novasTransicoes.add(novaTransicao);

        return novasTransicoes;
    }

    public List<Estado> getEstados() {
        return this.estados;
    }

    public void addEstado(Estado estado) {
        this.estados.add(estado);
    }

    public void addAllEstado(List<Estado> estados) {
        this.estados.addAll(estados);
    }

    public List<Transicao> getTransicoes() {
        return this.transicoes;
    }

    public void addTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
    }

    public void addAllTransicao(List<Transicao> transicoes) {
        this.transicoes.addAll(transicoes);
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
