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

    /**
     * Recupera o alfabeto do automato
     *
     */
    public List<String> getAlfabeto() {
        List<String> alfabeto = new ArrayList<String>();

        for (Transicao transicao : transicoes) {
            if (!alfabeto.contains(transicao.getSimbolo())) {
                alfabeto.add(transicao.getSimbolo());
            }
        }
        
        return alfabeto;
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

        // Criando o estado final e setando no automato final
        int idEstadoFinal = automato1.getEstados().size() + automato2.getEstados().size() + 1;
        novoEstado = new Estado("q" + idEstadoFinal, idEstadoFinal);
        novoEstado.setInicial(false);
        novoEstado.setFinal(true);
        automatoFinal.addEstado(novoEstado);

        // Gerando transicao do novo estado inicial criado, para os antigos inicias do
        // automatos originais
        automatoFinal.addAllTransicao(geraTransicoesNovoEstadoInicial(automato1, automato2));

        // Gerando transicao dos antigos finais para o novo estado final criado
        automatoFinal.addAllTransicao(geraTrancisoesNovoEstadoFinal(automato1, automato2));

        // renomeia e adiciona os estados no automato final
        automatoFinal.addAllEstados(renomeiaEstados(automato1, automato2));
        automatoFinal.addAllTransicao(reorganizarTransicoes(automato1, automato2));

        return automatoFinal;
    }

    private List<Transicao> reorganizarTransicoes(Automato automato1, Automato automato2) {
        List<Transicao> listaTransicoes = new ArrayList<Transicao>();
        Transicao novaTransicao = new Transicao();

        for (Transicao transicao : automato1.getTransicoes()) {
            novaTransicao = new Transicao();
            novaTransicao.setOrigem(transicao.getOrigem() + 1);
            novaTransicao.setDestino(transicao.getDestino() + 1);
            novaTransicao.setSimbolo(transicao.getSimbolo());
            listaTransicoes.add(novaTransicao);
        }

        for (Transicao transicao : automato2.getTransicoes()) {
            novaTransicao = new Transicao();
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
    private List<Estado> renomeiaEstados(Automato automato1, Automato automato2) {
        List<Estado> listaEstados = new ArrayList<Estado>();
        Estado novoEstado = new Estado(null, 0);

        for (Estado estado : automato1.getEstados()) {
            novoEstado = new Estado(null, 0);
            novoEstado.setId(estado.getId() + 1);
            novoEstado.setNome("q" + (estado.getId() + 1) + "_1"); // o nº 1 significa que é do automato 1
            listaEstados.add(novoEstado);
        }

        for (Estado estado : automato2.getEstados()) {
            novoEstado = new Estado(null, 0);
            int idNovoEstado = estado.getId() + automato1.getEstados().size() + 1;
            novoEstado.setId(idNovoEstado);
            novoEstado.setNome("q" + idNovoEstado + "_2"); // o nº 1 significa que é do automato 2
            listaEstados.add(novoEstado);
        }

        return listaEstados;
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
        novaTransicao = new Transicao();
        novaTransicao.setOrigem(0);
        novaTransicao.setDestino(automato2.getEstadoInicial().getId() + 1 + automato1.getEstados().size());
        novaTransicao.setSimbolo("");
        novasTransicoes.add(novaTransicao);

        return novasTransicoes;
    }

    private List<Transicao> geraTrancisoesNovoEstadoFinal(Automato automato1, Automato automato2) {
        List<Transicao> novasTransicoes = new ArrayList<Transicao>();
        Transicao novaTransicao = new Transicao();
        int idEstadoFinal = automato1.getEstados().size() + automato2.getEstados().size() + 1;

        for (Estado estado : automato1.getEstadosFinais()) {
            novaTransicao = new Transicao();
            novaTransicao.setOrigem(estado.getId() + 1);
            novaTransicao.setDestino(idEstadoFinal);
            novaTransicao.setSimbolo("");
            novasTransicoes.add(novaTransicao);
        }

        for (Estado estado : automato2.getEstadosFinais()) {
            novaTransicao = new Transicao();
            novaTransicao.setOrigem(estado.getId() + 1 + automato2.getEstados().size());
            novaTransicao.setDestino(idEstadoFinal);
            novaTransicao.setSimbolo("");

            novasTransicoes.add(novaTransicao);
        }

        return novasTransicoes;
    }

    public boolean isCompletAutomata(){
        //Passo 1: descobri o alfabeto do automato
        List<String> alfabeto = getAlfabeto();

        //Passo 2: pegar o ID de cada estado
        int[] idDoEstado = pegarIdsDosEstados();
        
        // Passo 3: verifica todas as transições de cada estado. É importante ressaltar
        //          que cada transição possui um ID referente ao estado ao qual está associada.
        //          Se ele não possui transição com determinado símbolo, então cria-se um
        //          estado para recebe tal transição.

        // Cada posição do vetor abaixo está associado a um símbolo do alfabeto
        int[] totalDeTransicoes = new int[alfabeto.size()]; 
        //int numeroDeTransicoesDiferentes = 0;

        //Seleciona o estado um por um
        for(int i = 0; i < idDoEstado.length; i++) {

            //Verifica todas as transições
            for(Transicao transicao : transicoes) {
                //Seleciona as transições correspondente ao estado 
                if(idDoEstado[i] == transicao.getOrigem()){
                    //Seleciona os símbolos do estado um por um
                    for(int x = 0; x < alfabeto.size(); x++){
                        //Se o símbolo da transição for igual ao símbolo do alfabeto
                        if( transicao.getSimbolo().compareTo(alfabeto.get(x)) == 0){
                            //Cada posição do vetor abaixo corresponde a um símbolo do alfabeto
                            totalDeTransicoes[x] += 1;
                        }
                    }
                }
            }

            // Verificar o número de transições para cada símbolo do alfabeto 
            for(int x = 0; x < alfabeto.size(); x++){
                if (totalDeTransicoes[x] > 1 || totalDeTransicoes[x] == 0) {
                    return false;
                }

                totalDeTransicoes[x] = 0;
            }
        }
        return true;
    }

    private int[] pegarIdsDosEstados() {
        int[] idDoEstado = new int[estados.size()];
        int i = 0;
        for(Estado estado : estados){
            idDoEstado[i] = estado.getId();
        }

        return idDoEstado;
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
            if (estado.isFinal())
                estados.add(estado);

        return estados;
    }
}
