import java.io.File;

import javax.lang.model.element.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

public class Automato {

    private List<Estado> estados;

    private List<Transicao> transicoes;

    private Estado estadoInicial;

    private List<Estado> estadosFinais;

    public Automato populaAutomato(String pathArquivo) {

        Automato automato = new Automato();
        try {
            File file = new File(pathArquivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList listaEstados = doc.getElementsByTagName("state");

            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node nEstado = listaEstados.item(i);

                if (nEstado.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nEstado;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return automato;
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
