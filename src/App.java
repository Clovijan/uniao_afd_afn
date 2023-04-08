public class App {
    public static void main(String[] args) throws Exception {

        Automato automato = new Automato();
        Estado estadosAutomato = new Estado();

        automato.setEstados(estadosAutomato.recuperaEstados("C:/Users/Clovjan Rocha/Downloads/Teste.jff"));

        for (Estado estado : automato.getEstados()) {
            System.out.println("ID Estado:" + estado.getId());
            System.out.println("Nome Estado:" + estado.getNome());
            System.out.println("Estado Inicial:" + estado.IsInicial());
            System.out.println("Estado Final:" + estado.IsFinal());
        }
    }
}
