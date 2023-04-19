import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // Teste de recuperação de dados

        Automato automato = new Automato();
        Automato automato2 = new Automato();

        automato.carregaDados("TesteAFD.jff");
        automato2.carregaDados("TesteAFD.jff");

        System.out.println("automato");
        impressaoAutomato(automato);
      
        System.out.println("\n\nUnião dos dois AFNs: ");
        impressaoAutomato(automato.uniaoAFN(automato, automato2));

        //AutomatoWriter geradorDeArqu = new AutomatoWriter();
        Automato automato3;
        FileWriter fileWriter = new FileWriter(new File("uniaoAFD.jff"), Charset.forName("UTF-8"));

        if(automato.isCompletAutomata() && automato2.isCompletAutomata()) {
            System.out.println("\n\nUnião dos dois AFDs: ");
            automato3 = automato.uniaoAFD(automato, automato2);
            AutomatoWriter.escreveAutomato(automato3, fileWriter);
            impressaoAutomato(automato3);
        }

        System.out.println("\nAlfabeto: ");
        List<String> alfabeto = automato.getAlfabeto();
        for(int i = 0; i < alfabeto.size(); i++) {
            System.out.println((i + 1) + "º: " + alfabeto.get(i));
        }

        if (automato.isCompletAutomata()) {
            System.out.println("O autômato é completo!!");
        } else {
            System.out.println("O autômato não é completo!!");
        }
    }

    private static void impressaoAutomato(Automato automato) {

        for (Estado estado : automato.getEstados()) {
            System.out.println("=========================");
            System.out.println("ID Estado:" + estado.getId());
            System.out.println("Nome Estado:" + estado.getNome());
            System.out.println("Estado Inicial:" + estado.isInicial());
            System.out.println("Estado Final:" + estado.isFinal());
            System.out.println("=========================");
        }

        for (Transicao transicao : automato.getTransicoes()) {
            System.out.println("=========================");
            System.out.println("Trasicao Origem: " + transicao.getOrigem());
            System.out.println("Trasicao Destino: " + transicao.getDestino());
            System.out.println("Com Símbolo: " + transicao.getSimbolo());
            System.out.println("=========================");
        }

        System.out.println("ID estado Inicial: " + automato.getEstadoInicial().getId());
    }
}
