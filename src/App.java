public class App {
    public static void main(String[] args) throws Exception {
        // Teste de recuperação de dados

        Automato automato = new Automato();
        Automato automato2 = new Automato();

        automato.carregaDados("TesteAFD.jff");
        automato2.carregaDados("TesteAFD.jff");

        System.out.println("automato");
        impressaoAutomato(automato);

        System.out.println("\nUnião dos dois autômatos: ");
        impressaoAutomato(automato.uniaoAFN(automato, automato2));

        System.out.println("\nAlfabeto: ");
        String[] alfabeto = automato.getAlphabet();
        for(int i = 0; i < alfabeto.length; i++) {
            System.out.println((i + 1) + "º: " + alfabeto[i]);
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

        /*if(automato.isCompletAutomata()){
            System.out.println("O autômato é completo!!");
        }else{
            System.out.println("O autômato não é completo!!"); 
        }*/
    }
}
