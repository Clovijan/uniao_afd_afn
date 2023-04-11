public class App {
    public static void main(String[] args) throws Exception {
        // Teste de recuperação de dados

        Automato automato = new Automato();
        automato.carregaDados("C:/Users/Clovjan Rocha/Downloads/Teste.jff");

        impressaoAutomato(automato);
    }

    private static void impressaoAutomato(Automato automato) {

        for (Estado estado : automato.getEstados()) {
            System.out.println("ID Estado:" + estado.getId());
            System.out.println("Nome Estado:" + estado.getNome());
            System.out.println("Estado Inicial:" + estado.isInicial());
            System.out.println("Estado Final:" + estado.isFinal());
        }

        for (Transicao transicao : automato.getTransicoes()) {
            System.out.println("Trasicao Origem: " + transicao.getOrigem());
            System.out.println("Trasicao Destino: " + transicao.getDestino());
            System.out.println("Com Símbolo: " + transicao.getSimbolo());
        }

        System.out.println("ID estado Inicial: " + automato.getEstadoInicial().getId());
    }
}
