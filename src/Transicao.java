import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;

import java.util.ArrayList;

import java.util.List;

public class Transicao {

    private int origem; // from

    private int destino; // to

    private String simbolo; // read

    /**
     * Recupera transicoes do arquivo .jff
     * 
     * @param pathArquivo
     * @return Lista de transicoes
     */
    public List<Transicao> recuperaTransicoes(String pathArquivo) {
        List<Transicao> transicoes = new ArrayList<Transicao>();
        try {
            File file = new File(pathArquivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList listaTrasicoes = doc.getElementsByTagName("transition");
            for (int i = 0; i < listaTrasicoes.getLength(); i++) {
                Node nTrasicoes = listaTrasicoes.item(i);

                if (nTrasicoes.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElemento = (Element) nTrasicoes;

                    Transicao transicao = new Transicao();
                    transicao.setOrigem(
                            Integer.parseInt(eElemento.getElementsByTagName("from").item(0).getTextContent()));
                    transicao.setDestino(
                            Integer.parseInt(eElemento.getElementsByTagName("from").item(0).getTextContent()));
                    transicao.setSimbolo(eElemento.getElementsByTagName("read").item(0).getTextContent());
                    transicoes.add(transicao);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transicoes;
    }

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
