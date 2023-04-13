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

    public Automato uniaoAFD(Automato automato1, Automato automato2) {
        Automato automatoFinal = new Automato();

        // criando o produto cartesiano entre os estados dos automatos originais
        for (Estado estado1 : automato1.getEstados()) {
            for (Estado estado2 : automato2.getEstados()) {
                Estado novoEstado = new Estado((estado1.getNome() + "_" + estado2.getNome()),
                        (estado1.getId() * automato2.getEstados().size() + estado2.getId()));
                novoEstado.setInicial(estado1.isInicial() && estado2.isInicial() ? true : false);
                novoEstado.setFinal(estado1.isFinal() || estado2.isFinal() ? true : false);
                automatoFinal.addEstado(novoEstado);
            }
        }

        // criando as transições com os novos estados criados
        for (Estado estado1 : automato1.getEstados()) {
            for (Estado estado2 : automato2.getEstados()) {
                for (String simbolo : getAlfabeto(automato1, automato2)) {
                    Transicao novaTransicao = new Transicao();
                    int transicaoOrigem = estado1.getId() * automato2.getEstados().size() + estado2.getId();
                    int idEstado1 = getIdEstadoBySimbolo(estado1, simbolo, automato1.getTransicoes());
                    int idEstado2 = getIdEstadoBySimbolo(estado2, simbolo, automato2.getTransicoes());
                    int transicaoDestino = idEstado1 * automato2.getEstados().size() + idEstado2;

                    novaTransicao.setOrigem(transicaoOrigem);
                    novaTransicao.setDestino(transicaoDestino);
                    novaTransicao.setSimbolo(simbolo);
                    automatoFinal.addTransicao(novaTransicao);
                }
            }
        }

        return automatoFinal;
    }

    private int getIdEstadoBySimbolo(Estado estado, String simbolo, List<Transicao> trasicoesAutomato) {
        for (Transicao transicao : trasicoesAutomato) {
            if (transicao.getOrigem() == estado.getId() && transicao.getSimbolo().equals(simbolo))
                return transicao.getDestino();
        }
        return -1;
    }

    /**
     * Recupera o alfabeto do automato
     * 
     * @param automato1
     * @param automato2
     *
     */
    private List<String> getAlfabeto(Automato automato1, Automato automato2) {
        List<String> alfabeto = new ArrayList<String>();

        for (Transicao transicao : automato1.getTransicoes()) {
            if (!alfabeto.contains(transicao.getSimbolo())) {
                alfabeto.add(transicao.getSimbolo());
            }
        }
        for (Transicao transicao : automato2.getTransicoes()) {
            if (!alfabeto.contains(transicao.getSimbolo())) {
                alfabeto.add(transicao.getSimbolo());
            }
        }
        return alfabeto;
    }

    public List<Estado> getEstados() {
        return this.estados;
    }

    public void addEstado(Estado estado) {
        this.estados.add(estado);
    }

    public void addAllEstados(List<Estado> estados) {
        this.estados.addAll(estados);
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
