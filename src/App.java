import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Teste de recuperação de dados

        Automato automato = new Automato();
        Automato automato2 = new Automato();

        automato.carregaDados("TesteAFD.jff");
        automato2.carregaDados("TesteAFD2.jff");

        if (automato.isCompletAutomata(automato, automato2)) {
            System.out.println("O autômato 1 é completo!!");
        } else {
            System.out.println("O autômato 1 não é completo!!");
        }

        if (automato2.isCompletAutomata(automato, automato2)) {
            System.out.println("O autômato 2 é completo!!");
        } else {
            System.out.println("O autômato 2 não é completo!!");
        }

        System.out.println("automato 1:");
        impressaoAutomato(automato);

        System.out.println("automato 2:");
        impressaoAutomato(automato);
        
        Automato automato3;

        Scanner scan = new Scanner(System.in);

        System.out.print("Operação em AFNs (1) ou em AFDs (2)? ");
        if(scan.nextInt() == 1){
            System.out.println("\n\nUnião dos dois AFNs: ");
            automato3 = automato.uniaoAFN(automato, automato2);
            impressaoAutomato(automato3);
            FileWriter fileWriterAFN = new FileWriter(new File("uniaoAFN.jff"), Charset.forName("UTF-8"));
            AutomatoWriter.escreveAutomato(automato3, fileWriterAFN);
        } else{
            System.out.println("\n\nUnião dos dois AFDs: ");
            automato3 = automato.uniaoAFD(automato, automato2);
            impressaoAutomato(automato3);
            FileWriter fileWriterAFD = new FileWriter(new File("uniaoAFD.jff"), Charset.forName("UTF-8"));
            AutomatoWriter.escreveAutomato(automato3, fileWriterAFD);
        }

        scan.close();

        System.out.println("\nAlfabeto: ");
        List<String> alfabeto = automato.getAlfabeto(automato, automato2);
        for(int i = 0; i < alfabeto.size(); i++) {
            System.out.println((i + 1) + "º: " + alfabeto.get(i));
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
