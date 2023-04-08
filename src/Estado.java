import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.List;
import java.util.ArrayList;

public class Estado {

    private int id;

    private String nome;

    private boolean inicial = false;

    private boolean estadoFinal = false;

    public List<Estado> recuperaEstados(String pathArquivo) {

        List<Estado> estados = new ArrayList<Estado>();
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
                    Element eElemento = (Element) nEstado;

                    Estado estado = new Estado();
                    estado.setId(Integer.parseInt(eElemento.getAttribute("id")));
                    estado.setNome(eElemento.getAttribute("name"));
                    estado.setInicial(Boolean.parseBoolean(eElemento.getAttribute("initial")));
                    estado.SetFinal(Boolean.parseBoolean(eElemento.getAttribute("final")));

                    estados.add(estado);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estados;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean IsInicial() {
        return this.inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public boolean IsFinal() {
        return this.estadoFinal;
    }

    public void SetFinal(boolean estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
}
