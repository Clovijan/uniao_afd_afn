public class Estado {

    private int id;

    private String nome;

    private boolean inicial;

    private boolean estadoFinal;

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
